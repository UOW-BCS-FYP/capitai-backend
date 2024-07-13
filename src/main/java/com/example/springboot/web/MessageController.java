package com.example.springboot.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.MessageRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.MessageInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class MessageController {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/sbs/chat/{ai_type}")
    public ResponseEntity<MessageInfo[]> getMessages(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("ai_type") String type) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        List<MessageInfo> msgs = new ArrayList<MessageInfo>();
        for (MessageInfo msg : messageRepository.findAllByUserInfo(userInfo))
            if (msg.getAIType().equals(type))
                msgs.add(msg);
        return ResponseEntity.ok().body(msgs.toArray(new MessageInfo[msgs.size()]));
    }

    @PostMapping("/sbs/chat")
    public ResponseEntity<MessageInfo> addMessageAsUser(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody MessageInfo msg) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        msg.setUserInfo(userInfo);
        msg.setSentBy("user");
        return ResponseEntity.ok(messageRepository.save(msg));
    }

    @PostMapping("/sbs/chat/AI")
    public ResponseEntity<MessageInfo> addMessageAsAI(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody MessageInfo msg) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        msg.setUserInfo(userInfo);
        msg.setSentBy("ai");
        return ResponseEntity.ok(messageRepository.save(msg));
    }
}
