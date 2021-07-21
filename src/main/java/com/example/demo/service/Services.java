package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.demo.model.Comment;
import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.model.Posts;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("services")
public class Services {

	@Autowired(required = true)
	KafkaTemplate<String, User> kafkaTemplateUser;

	@Autowired(required = true)
	KafkaTemplate<String, Post> kafkaTemplatePost;

	@Autowired(required = true)
	KafkaTemplate<String, Comment> kafkaTemplateComment;

	@Value("${get.posts.url}")
	private String getPostsUrl;

	@Value("${get.comments.url}")
	private String getCommentssUrl;

	@Value("${tocken}")
	private String TOCKEN;

	@Value("${post-url}")
	private String postUrl;

	@Value("${comment-url}")
	private String commentsUrl;
	@Value("${user-url}")
	private String userUrl;

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

	// kafka functions
	public void addPosts() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));
		headers.set("app-id", TOCKEN);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Content-Type", "application/json");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {
			RestTemplate restTemplate = new RestTemplate();

			for (int i = 2; i < 18; i++) {
				ResponseEntity<Map> response = restTemplate.exchange(postUrl + "?limit=50&page=" + i, HttpMethod.GET,
						entity, Map.class);

				List<Map<String, Object>> m = (List<Map<String, Object>>) response.getBody().get("data");

				// List<User> users = new ArrayList<>();
				for (Map<String, Object> map : m) {
					Post post = new Post();
					User u = new User();
					if (map.get("owner") != null) {
						Map ownerMap = ((Map) map.get("owner"));
						if (ownerMap.get("id") != null)
							u.setId((String) ownerMap.get("id"));
						u.setTitle((String) ownerMap.get("title"));
						u.setFirstName((String) ownerMap.get("firstName"));
						u.setLastName((String) ownerMap.get("lastName"));
						u.setEmail((String) ownerMap.get("email"));
						u.setPicture((String) ownerMap.get("ownepicture"));
						post.setOwner(u);
					}
					post.setId((String) map.get("id"));
					post.setImage((String) map.get("image"));
					post.setLink((String) map.get("link"));
					post.setTags((List<String>) map.get("tags"));
					post.setPublishDate((String) map.get("publishDate"));
					post.setText((String) map.get("text"));

					// System.out.println(post);
					kafkaTemplatePost.send("POST", "User", post);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void addUsers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));
		headers.set("app-id", TOCKEN);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Content-Type", "application/json");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {
			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<Map> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, Map.class);

			List<Map<String, Object>> m = (List<Map<String, Object>>) response.getBody().get("data");

			for (Map<String, Object> map : m) {
				User u = new User();
				u.setId((String) map.get("id"));
				u.setTitle((String) map.get("title"));
				u.setFirstName((String) map.get("firstName"));
				u.setLastName((String) map.get("lastName"));
				u.setEmail((String) map.get("email"));
				u.setPicture((String) map.get("picture"));

				System.out.println(u);
				kafkaTemplateUser.send("UJ_STUDIO", "User", u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// baki chhe post mathi data laine aavanu chhe

	public void addComments() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));
		headers.set("app-id", TOCKEN);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Content-Type", "application/json");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {
			RestTemplate restTemplate = new RestTemplate();
			for (int i = 2; i < 18; i++) {
			ResponseEntity<Map> postResponse = restTemplate.exchange(postUrl + "?limit=50&page=" + i, HttpMethod.GET,
						entity, Map.class);

				List<Map<String, Object>> m = (List<Map<String, Object>>) postResponse.getBody().get("data");
				for (Map<String, Object> map : m) {
					String postId = (String) map.get("id");///comment

					ResponseEntity<Map> response = restTemplate.exchange(commentsUrl+postId+"/comment", HttpMethod.GET, entity, Map.class);

					if(response.getBody()!=null){
						List<Map<String, Object>> g = (List<Map<String, Object>>) response.getBody().get("data");
						for (Map<String, Object> map1: g) {
						Comment c = new Comment();
							c.setId((String) map1.get("id"));
							c.setMessage((String)map1.get("message"));
							c.setPublishDate((String)map1.get("publishDate"));
							User u = new User();
							if (map.get("owner") != null) {
								Map ownerMap = ((Map) map.get("owner"));
								if (ownerMap.get("id") != null)
									u.setId((String) ownerMap.get("id"));
								u.setTitle((String) ownerMap.get("title"));
								u.setFirstName((String) ownerMap.get("firstName"));
								u.setLastName((String) ownerMap.get("lastName"));
								u.setEmail((String) ownerMap.get("email"));
								u.setPicture((String) ownerMap.get("ownepicture"));
								c.setOwner(u);
							}
							kafkaTemplateComment.send("COMMENT","COMMENT", c);

						}


					}
				}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
