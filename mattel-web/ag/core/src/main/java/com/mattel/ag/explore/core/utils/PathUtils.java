package com.mattel.ag.explore.core.utils;

/**
 * @author CTS. Utility for url path checking of CRM site.
 */

public class PathUtils {

	public static Boolean isExternal(String path) {
		return !(path.startsWith("/content") && !path.startsWith("/content/dam"));
		}

	/**
	 * Private Constructor for Sonar issue.
	 */
	private PathUtils() {
	}
}
