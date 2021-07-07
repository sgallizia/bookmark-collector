package it.simonegallizia.bookmarkcollector.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.simonegallizia.bookmarkcollector.entities.User;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

	private TokenService tokenService = new TokenService();
	private String secretKey = "testSecretKey";
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(tokenService, "secretKey", secretKey);
	}
	
	@DisplayName("Test creation of the token")
	@Test
	public void testProduce() {
		User user = new User();
		user.setEmail("test@test.it");
		String token = tokenService.produce(user);
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedToken = verifier.verify(token);
		assertEquals(true, user.getEmail().compareTo(decodedToken.getClaims().get("email").asString()) == 0);	
	}
}
