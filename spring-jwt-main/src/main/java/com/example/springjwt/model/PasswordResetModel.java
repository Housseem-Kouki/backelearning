package com.example.springjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetModel {
	@JsonProperty("NewPassword")
	private String NewPassword;
	@JsonProperty("ConfirmPassword")
	private String ConfirmPassword;
}
