package it.simonegallizia.bookmarkcollector.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Login {
	@NotBlank
	private String password;
	@Email(message = "Email is mandatory")
	private String email;	
}
