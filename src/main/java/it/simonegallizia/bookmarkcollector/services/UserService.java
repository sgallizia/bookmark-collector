package it.simonegallizia.bookmarkcollector.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.models.ExtUser;
import it.simonegallizia.bookmarkcollector.models.Login;
import it.simonegallizia.bookmarkcollector.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	TokenService tokenService;
	public User signup(ExtUser extUser) {
		User user = User.fromModel(extUser);
		user.setPassword(encoder.encode(user.getPassword()));
		if(userRepository.findByEmail(user.getEmail()) == null) {
			return userRepository.save(user);
		}
		else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already exists");
		}
	}
	
	public String signin(Login loginData) {
		User user = userRepository.findByEmail(loginData.getEmail());
		if(user != null) {
			if(encoder.matches(loginData.getPassword(), user.getPassword())) {
				return tokenService.produce(user);
			}
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is incorrect");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email does not exist");
		}		
	}
}
