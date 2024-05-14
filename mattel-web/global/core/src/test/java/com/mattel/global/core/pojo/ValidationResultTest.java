package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ValidationResultTest {

	ValidationResult validationResult;
	
	List<String> errorMessages = new ArrayList<>();
	
	@Before
	public void setUp() throws IllegalArgumentException, IllegalAccessException{
		validationResult = new ValidationResult();
		validationResult.setSuccess(true);
		validationResult.addErrorMessage("Error");
		MemberModifier.field(ValidationResult.class, "errorMessages").set(validationResult, errorMessages);
			
	}

	@Test
	public void testSuccess() {
		assertSame(true, validationResult.isSuccess());
	}
	
	@Test
	public void testFailure() {
		assertSame(errorMessages, validationResult.getErrorMessages());
	
	}

}
