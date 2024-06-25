package com.cooksys.app.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.app.mapper.HashtagMapper;
import com.cooksys.app.repositories.HashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl {
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	
}
