package com.siberhus.commons.lang;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassReloader extends ClassLoader {

	public ClassReloader(ClassLoader parent) {
		super(parent);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		return reload(name);
	}

	@SuppressWarnings("unchecked")
	public Class reload(Class clazz) throws ClassNotFoundException {
		return reload(clazz.getName());
	}

	/***************************************************************************
	 * Given a class already in the classpath of a parent classloader, reload
	 * that class via this classloader.
	 */
	@SuppressWarnings("unchecked")
	public Class reload(String clazz) throws ClassNotFoundException {
		try {
			String className = clazz;
			String classFile = className.replace('.', '/') + ".class";
			InputStream classStream = getParent()
					.getResourceAsStream(classFile);

			if (classStream == null) {
				throw new FileNotFoundException(classFile);
			}

			byte[] buf = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (;;) {
				int bytesRead = classStream.read(buf);
				if (bytesRead == -1) {
					break;
				}
				baos.write(buf, 0, bytesRead);
			}
			classStream.close();

			byte[] classData = baos.toByteArray();

			// now we have the raw class data, let's turn it into a class
			Class newClass = defineClass(className, classData, 0,
					classData.length);
			resolveClass(newClass);
			return newClass;
		} catch (Exception ex) {
			
			return getParent().loadClass(clazz);
		}
	}

}
