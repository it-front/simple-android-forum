package com.jogeeks.content.secrets;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.JsonObject;

@Rest(rootUrl = "http://arabappz.net/secrets/api" , converters={GsonHttpMessageConverter.class})
public interface MyRestClient  {
	
	@Get("/get_recent_posts")
    ResponseEntity<JsonObject> getRecentPosts();
	
	
	
	

}
