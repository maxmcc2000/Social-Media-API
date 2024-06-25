package com.cooksys.app.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.cooksys.app.exceptions.BadRequestException;
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

	public Hashtag createHashtag (String hashtag) {

		if (hashtagRepository.existsByLabel(hashtag)) {
			throw new BadRequestException("Something is wrong, system trying to create a hashtag that already exists.");
		} Hashtag hashtagToBeCreated = new Hashtag();
		hashtagToBeCreated.setLabel(hashtag);
		hashtagToBeCreated.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
		return hashtagToBeCreated;
	}
	
}
