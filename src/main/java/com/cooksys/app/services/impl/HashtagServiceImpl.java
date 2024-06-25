package com.cooksys.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.app.dtos.HashtagResponseDto;
import com.cooksys.app.entities.Hashtag;
import com.cooksys.app.mapper.HashtagMapper;
import com.cooksys.app.repositories.HashtagRepository;
import com.cooksys.app.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService{
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	@Override
	public List<HashtagResponseDto> getAllHashtags() {
		List<HashtagResponseDto> allHashtagsDto = hashtagMapper.entitiesToDtos(hashtagRepository.findAll()); //Grab all hashtags from DB, and turn into response DTOs
		return allHashtagsDto;
	}
	
}
