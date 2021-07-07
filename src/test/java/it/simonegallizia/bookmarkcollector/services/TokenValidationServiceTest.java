package it.simonegallizia.bookmarkcollector.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.models.Account;

@ExtendWith(MockitoExtension.class)
public class TokenValidationServiceTest {
	
	private TokenValidationService tokenValidatonService = new TokenValidationService();
	private String secretKey = "testSecretKey";
	private String issuer = "testIssuer";
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(tokenValidatonService, "secretKey", secretKey);
		ReflectionTestUtils.setField(tokenValidatonService, "issuer", issuer);
	}
	
	@DisplayName("Test the validation of a valid token")
	@Test
	public void testValidateValidToken() {
		User user = new User();
		user.setEmail("test@test.it");
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create().withClaim("email", user.getEmail()).withIssuer(issuer).sign(algorithm);
		Account account = tokenValidatonService.validate(token);
		assertEquals(true, user.getEmail().compareTo(account.getEmail()) == 0);	
	}
	
	@DisplayName("Test the validation of an invalid token")
	@Test
	public void testValidateInvalidToken() {
		try {
			tokenValidatonService.validate("wrongtoken");
			assert(false);
		} catch (Exception e) {
			assertThat(true);
		}
	}
}
