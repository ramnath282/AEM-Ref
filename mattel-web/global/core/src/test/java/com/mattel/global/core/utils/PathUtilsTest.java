package com.mattel.global.core.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * @author CTS. Utility for url path checking of CRM site.
 */

public class PathUtilsTest {
	
	@Test
	public void testShouldverifyIfPathIsStartingWithContentAndNotWithContentDamIsExternal() {
		assertFalse(PathUtils.isExternal("/content/mypath/pagepath"));
		}
	
	@Test
	public void testShouldverifyIfPathIsNotStartingWithContentIsExternal() {
		assertTrue(PathUtils.isExternal("/test/content/test/path"));
		}
	
	@Test
	public void testShouldverifyIfPathIsStartingWithContentDamIsExternal() {
		assertTrue(PathUtils.isExternal("/content/dam/path"));
		}

	
}
