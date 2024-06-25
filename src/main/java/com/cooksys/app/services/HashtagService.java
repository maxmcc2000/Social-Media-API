package com.cooksys.app.services;

import java.util.List;

import com.cooksys.app.dtos.HashtagResponseDto;
import com.cooksys.app.entities.Hashtag;

public interface HashtagService {
	List<HashtagResponseDto> getAllHashtags();

	Hashtag createHashtag (String hashtag);

}
