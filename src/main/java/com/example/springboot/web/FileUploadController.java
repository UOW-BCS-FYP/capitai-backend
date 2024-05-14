package com.example.springboot.web;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.springboot.model.UploadedFileInfo;
import com.example.springboot.service.UserService;
import com.example.springboot.service.UserServiceImpl;
import com.example.springboot.storage.StorageFileNotFoundException;
import com.example.springboot.storage.StorageService;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    private final StorageService storageService;
    private final UserService userService;
    
    @Autowired
    public FileUploadController(StorageService storageService, UserServiceImpl userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/files")
    public ResponseEntity<?> listUploadedFiles() {
        return ResponseEntity.ok().body(storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString())
            .collect(Collectors.toList()));
    }

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

    @PostMapping(path = "/files/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    // @RequestMapping(path = "/files/upload", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public UploadedFileInfo handleFileUpload(@RequestPart("file") MultipartFile file) {
        UploadedFileInfo uploaded = storageService.store(file, userService.getCurrentUserInfo());
        // return ResponseEntity.ok().body("You successfully uploaded " + file.getOriginalFilename() + "!");
        return uploaded;
    }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
