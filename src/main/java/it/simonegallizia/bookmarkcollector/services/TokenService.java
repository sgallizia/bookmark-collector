package it.simonegallizia.bookmarkcollector.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import it.simonegallizia.bookmarkcollector.entities.User;

@Service
public class TokenService {


	@Value("${app.token.secretkey}")
	private String secretKey;
	
	@Value("${app.token.issuer}")
	private String issuer;
	
	public String produce(User user) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create().withClaim("email", user.getEmail()).withIssuer(issuer).sign(algorithm);
		return token;
	}

}
