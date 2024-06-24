package com.cooksys.app.dtos;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagResponseDto {
	//This DTO should return Id, Label, firstUsed, and lastUsed
	private Long id;
	
	private String label;
	
	private LocalDateTime firstUsed;
	
	private LocalDateTime lastUsed;
}
