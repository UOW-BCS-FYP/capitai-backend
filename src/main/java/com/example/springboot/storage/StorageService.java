package com.example.springboot.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.model.UploadedFileInfo;
import com.example.springboot.model.UserInfo;

public interface StorageService {

    void init();

    UploadedFileInfo store(MultipartFile file);
	UploadedFileInfo store(MultipartFile file, UserInfo uploadedBy);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();
}
