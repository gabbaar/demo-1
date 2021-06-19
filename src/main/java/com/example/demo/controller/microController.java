package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comments;
import com.example.demo.model.Posts;
import com.example.demo.service.Services;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class microController {

	@Autowired
	private Services services;

	@GetMapping("/post/{id}")
	public ResponseEntity<?> getPost(@PathVariable Integer id) throws Exception {
		Posts result = services.getPostForId(id);
		if (result == null)
			return (ResponseEntity<?>) ResponseEntity.noContent();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/comments/{id}")
	public ResponseEntity<?> getComments(@PathVariable Integer id) throws Exception {
		List<Comments> result = services.getCommentsForId(id);
		if (result == null)
			return (ResponseEntity<?>) ResponseEntity.noContent();
		return ResponseEntity.ok(result);
	}

}
