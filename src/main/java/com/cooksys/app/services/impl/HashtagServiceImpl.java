package com.cooksys.app.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.TweetMapper;
import com.cooksys.app.repositories.TweetRepository;
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
	private final TweetRepository tweetRepository;

	private final HashtagMapper hashtagMapper;
	private final TweetMapper tweetMapper;

	@Override
	public List<HashtagResponseDto> getAllHashtags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	public Hashtag createHashtag (String hashtag) {

		if (hashtagRepository.existsByLabel(hashtag)) {
			throw new BadRequestException("Something is wrong, system trying to create a hashtag that already exists.");
		} Hashtag hashtagToBeCreated = new Hashtag();
		hashtagToBeCreated.setLabel(hashtag);
		hashtagToBeCreated.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
		return hashtagToBeCreated;
	}

	@Override
	public List<TweetResponseDto> getHashtagsByLabel(String label) {

		Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(label);
		if (optionalHashtag.isEmpty()) throw new NotFoundException(label + "hashtag not found.");
		Hashtag hashtagWithLabel = optionalHashtag.get();

		List<Tweet> tweetsWithHashtagLabel = new ArrayList<>();

		for (Tweet tweet : hashtagWithLabel.getTweets()) {
			if (!tweet.isDeleted()) {
				tweetsWithHashtagLabel.add(tweet);
			}
		}
		return tweetMapper.entitiesToResponseDtos(tweetsWithHashtagLabel);
	}

}
