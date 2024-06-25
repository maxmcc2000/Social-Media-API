package com.cooksys.app.dtos;


import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagResponseDto {
	//This DTO should return Label, firstUsed, and lastUsed
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
}
