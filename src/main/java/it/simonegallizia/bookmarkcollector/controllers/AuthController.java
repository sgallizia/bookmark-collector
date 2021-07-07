package it.simonegallizia.bookmarkcollector.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import it.simonegallizia.bookmarkcollector.models.ExtUser;
import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.models.Account;
import it.simonegallizia.bookmarkcollector.models.Login;
import it.simonegallizia.bookmarkcollector.services.TokenValidationService;
import it.simonegallizia.bookmarkcollector.services.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenValidationService tokenValidationService;
	
	
	@PostMapping("/sign-up")
	public User signUp(@Valid @RequestBody ExtUser user) {
		return userService.signup(user);
	}
	
	@PostMapping("/sign-in")
	public String signIn(@Valid @RequestBody Login login) throws GeneralSecurityException, IOException {
		return userService.signin(login);
	}
	
	@PostMapping("/validate")
	public Account validate(@RequestBody @NotBlank String token) {
		return tokenValidationService.validate(token);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
