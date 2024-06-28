package com.cooksys.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cooksys.app.dtos.HashtagResponseDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	private final HashtagService hashtagService;
	
	@GetMapping
	public List<HashtagResponseDto> getAllHashtags() {
		//Call hashtag service
        return hashtagService.getAllHashtags();
	}
	
	@GetMapping("{label}")
	public List<TweetResponseDto> getHashtagsByLabel(@PathVariable String label) {
		return hashtagService.getHashtagsByLabel(label);
	}

}
