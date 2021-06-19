package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Comments;
import com.example.demo.model.Posts;

@Service("services")
public class Services {

	@Value("${get.posts.url}")
	private String getPostsUrl;

	@Value("${get.comments.url}")
	private String getCommentssUrl;

	public Posts getPostForId(Integer id) throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));

			HttpEntity<?> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Posts> response = restTemplate.exchange(getPostsUrl + "/" + id, HttpMethod.GET, entity,
					Posts.class);
			return response.getBody();
		} catch (Exception e) {
			throw new Exception("Server Timeout");
		}

	}

	public ArrayList<Comments> getCommentsForId(Integer id) throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));

			HttpEntity<?> entity = new HttpEntity<>(headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ArrayList> response = restTemplate.exchange(getPostsUrl + "/" + id + getCommentssUrl,
					HttpMethod.GET, entity, ArrayList.class);
			return response.getBody();
		} catch (Exception e) {
			throw new Exception("Server Timeout");
		}
	}

}
