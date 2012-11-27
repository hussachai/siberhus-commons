package com.siberhus.commons.lang;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

public class ReflectionFieldCache {

	private static final Map<String, Field> CACHES = new Hashtable<String, Field>();

	protected ReflectionFieldCache() {
	}

	public static synchronized ReflectionFieldCache createInstance(
			Class targetClass, boolean recursive) {
		if (targetClass == null) {
			throw new IllegalArgumentException("targetClass cannot be null");
		}
		ReflectionFieldCache c = new ReflectionFieldCache();
		Class currentClass = targetClass;
		while (currentClass != Object.class) {
			for (Field field : currentClass.getDeclaredFields()) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				CACHES.put(field.getName(), field);
			}
			if (!recursive) {
				break;
			}
			currentClass = currentClass.getSuperclass();
		}

		return c;
	}

	public Field getField(String name) {
		return CACHES.get(name);
	}
	
	public boolean hasField(String name){
		return CACHES.containsKey(name);
	}
	
}
