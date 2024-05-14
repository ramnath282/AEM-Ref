package com.mattel.productvideos.core.utils;

import org.junit.Test;
import org.mockito.InjectMocks;

public class CopyContentWorkflowUtilsTest {

	@InjectMocks
	CopyContentWorkflowUtils copyContentWorkflowUtils;
	
	@Test
	public void testGetProcessArgsList(){
		copyContentWorkflowUtils.getProcessArgsList("a=b,c=d,e=f");
	}
	
}
