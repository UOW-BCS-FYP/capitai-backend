package com.example.springboot.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.BookingRepository;
import com.example.springboot.dao.TourRepository;
import com.example.springboot.dao.TourReviewRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.TourBookRequestDTO;
import com.example.springboot.model.BookingInfo;
import com.example.springboot.model.TourInfo;
import com.example.springboot.model.TourReview;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.Dispute;
import com.example.springboot.dao.DisputeRepository;

@RestController
@RequestMapping("/api/v1/")
public class DisputeController {

    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final DisputeRepository disputeRepository;

    @Autowired
    public DisputeController(TourRepository tourRepository, UserRepository userRepository, BookingRepository bookingRepository, DisputeRepository disputeRepository) {
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.disputeRepository = disputeRepository;
    }

    @GetMapping("/disputes")
    public List<Dispute> getAllDisputes() {
        var disputes = disputeRepository.findAll();
        return Streamable.of(disputes).toList();
    }
}
