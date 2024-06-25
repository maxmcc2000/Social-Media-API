package com.cooksys.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagRequestDto {
	//This DTO should only contain a label (thats all we got)
	private String label;
	
}
