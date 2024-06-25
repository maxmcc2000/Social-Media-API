package com.cooksys.app.controllers.advice;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.app.dtos.HashtagResponseDto;
import com.cooksys.app.dtos.TweetResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	@GetMapping
	public List<HashtagResponseDto> getAllHashtags() {
		return null;
	}
	
	@GetMapping("{label}")
	public List<TweetResponseDto> getHashtagsByLabel() {
		return null;
	}
}
