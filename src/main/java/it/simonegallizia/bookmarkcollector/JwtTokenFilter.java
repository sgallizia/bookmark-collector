package it.simonegallizia.bookmarkcollector;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.simonegallizia.bookmarkcollector.models.Account;
import it.simonegallizia.bookmarkcollector.repositories.UserRepository;
import it.simonegallizia.bookmarkcollector.services.TokenValidationService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenValidationService tokenValidationService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        Account account = null;
        try {
            account = tokenValidationService.validate(token);
        }
        catch(Exception e) {
            throw e;
        }

        UserDetails userDetails = userRepository
                .findByEmail(account.getEmail());
        
        if (userDetails == null)
            throw new RuntimeException("Cannot find user");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
