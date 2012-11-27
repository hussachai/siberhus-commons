package com.siberhus.commons.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoaderUtils {

	private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtils.class);
	
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			logger.debug("Cannot access thread context ClassLoader "
					+ "- falling back to system class loader", ex);
		}
		if (cl == null)
			cl = (ClassLoaderUtils.class).getClassLoader();
		return cl;
	}

	public static ClassLoader overrideThreadContextClassLoader(
			ClassLoader classLoaderToUse) {
		Thread currentThread = Thread.currentThread();
		ClassLoader threadContextClassLoader = currentThread
				.getContextClassLoader();
		if (classLoaderToUse != null
				&& !classLoaderToUse.equals(threadContextClassLoader)) {
			currentThread.setContextClassLoader(classLoaderToUse);
			return threadContextClassLoader;
		} else {
			return null;
		}
	}
	
}
