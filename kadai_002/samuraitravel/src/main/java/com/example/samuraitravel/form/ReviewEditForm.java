package com.example.samuraitravel.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	private Integer evaluation;
	
	private String comment;
	
	private Integer houseId;
	
	private Integer userId;
	
}
