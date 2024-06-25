package com.cooksys.app.services;

import java.util.List;

import com.cooksys.app.dtos.HashtagResponseDto;

public interface HashtagService {
	List<HashtagResponseDto> getAllHashtags();
}
