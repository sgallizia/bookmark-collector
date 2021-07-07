package it.simonegallizia.bookmarkcollector.models;
import javax.validation.constraints.Email;

import it.simonegallizia.bookmarkcollector.validators.ValidPassword;
import lombok.Data;

@Data
public class ExtUser {
	private String name;
	private String lastName;
	private String user;
	@ValidPassword
	private String password;
	@Email(message = "Email is mandatory")
	private String email;
	
}
