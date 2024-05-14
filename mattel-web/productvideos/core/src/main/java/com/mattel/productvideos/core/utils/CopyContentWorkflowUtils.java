package com.mattel.productvideos.core.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS
 *
 */
public class CopyContentWorkflowUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(CopyContentWorkflowUtils.class);

	/**
	 * @param processArgs
	 * 			comma separate process args, ex : a=b;c=d,e=f
	 * @return
	 */
	public static Map<String, String> getProcessArgsList(String processArgs) {
		CopyContentWorkflowUtils.LOGGER.info("getProcessArgsList -> Start, processArgs : {}", processArgs);
		Map<String, String> argsMap = null;
		if (StringUtils.isNotEmpty(processArgs)) {
			argsMap = Arrays.asList(processArgs.split(",")).stream().map(str -> str.split("="))
					.collect(Collectors.toMap(str -> str[0], str -> str[1]));
		}
		CopyContentWorkflowUtils.LOGGER.info("CopyContentProcess -> Stop");
		return argsMap;
	}

	private CopyContentWorkflowUtils() {
		
	}

}
