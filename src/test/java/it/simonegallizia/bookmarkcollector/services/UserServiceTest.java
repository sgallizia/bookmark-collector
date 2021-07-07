package it.simonegallizia.bookmarkcollector.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.models.ExtUser;
import it.simonegallizia.bookmarkcollector.models.Login;
import it.simonegallizia.bookmarkcollector.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	UserRepository userRepository;
	
	@Spy
	TokenService tokenService = new TokenService();
	
	@InjectMocks
	UserService userService;
	
	@Spy
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(tokenService, "secretKey", "testSecretKey");
		ReflectionTestUtils.setField(tokenService, "issuer", "testIssuer");
	}
	
	
	@DisplayName("Test Sign up with correct information")
	@Test
	public void testSignUp() {
		ExtUser user = new ExtUser();
		user.setEmail("info@unittest.it");
		user.setPassword("Test!0102");
		User intUser = User.fromModel(user);
		intUser.setPassword(encoder.encode(user.getPassword()));
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(null);
		when(userRepository.save(intUser)).thenReturn(intUser);
		User userOutput = userService.signup(user);
		verify(userRepository).save(intUser);
		verify(userRepository).findByEmail("info@unittest.it");
		assertEquals(userOutput != null, true);
	}
	
	@DisplayName("Test Sign up with email which already exists")
	@Test
	public void testSignUpEmailAlreadyExists() {
		ExtUser extUser = new ExtUser();
		extUser.setEmail("info@unittest.it");
		extUser.setPassword("Test!0102");
		User user = new User();
		user.setEmail("info@unittest.it");
		user.setPassword("Test!0102");
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		try {
			userService.signup(extUser);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY , true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
	}
	
	@DisplayName("Test sign in with correct data")
	@Test
	public void testSignWithCorrectData() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		User user = new User();
		user.setPassword(encoder.encode("testpassword"));
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		String token = userService.signin(loginData);
		verify(userRepository).findByEmail("info@unittest.it");
		assertEquals(token != null && token.length() > 0, true);
		
	}
	
	@DisplayName("Test sign in with wrong email")
	@Test
	public void testSignWithWrongEmail() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(null);
		try {
			userService.signin(loginData);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.FORBIDDEN && e.getReason() == "Email does not exist", true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
		
	}
	
	@DisplayName("Test sign in with wrong password")
	@Test
	public void testSignWithWrongPassword() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		User user = new User();
		user.setPassword(encoder.encode("testwrongpassword"));
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		try {
			userService.signin(loginData);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.FORBIDDEN && e.getReason() == "Password is incorrect", true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
		
	}
}
