package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotNull(message = "選択してください。")
	private Integer evaluation;
	
	@NotBlank(message = "コメントを入力してください。")
	private String comment;
	
	@NotNull
	private Integer houseId;
	
	@NotNull
	private Integer userId;
}
