package it.simonegallizia.bookmarkcollector.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordConstraintValidatorTest {
	@InjectMocks
	private PasswordConstraintValidator validator;
	
	@Mock
	private ConstraintViolationBuilder builder; 
	
	@Mock
	private ConstraintValidatorContext context;
	
	@DisplayName("Test password")
	@Test
	public void testMail() {
		
		when(context.buildConstraintViolationWithTemplate(ArgumentMatchers.anyString())).thenReturn(builder);
		when(builder.addConstraintViolation()).thenReturn(context);
		
		String password = "password";
		assertFalse(validator.isValid(password, context));
		
		password = "password!01";
		assertFalse(validator.isValid(password, context));
		
		password = "passwor";
		assertFalse(validator.isValid(password, context));
		
		password = "pass wordwithwhitespace";
		assertFalse(validator.isValid(password, context));
		
		password = "Password01";
		assertFalse(validator.isValid(password, context));
		
		password = "Password!";
		assertFalse(validator.isValid(password, context));
		
		password = "Pass!01";
		assertFalse(validator.isValid(password, context));
		
		password = "Password!01";
		assertTrue(validator.isValid(password, context));
		
		verify(context, times(7)).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
		verify(builder, times(7)).addConstraintViolation();
	}
}
