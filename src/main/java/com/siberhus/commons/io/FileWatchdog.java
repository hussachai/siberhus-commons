package com.siberhus.commons.io;

import java.io.File;
import java.nio.charset.Charset;

/*
 * Copyright 1999-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Check every now and then that a certain file has not changed. If it has, then
 * call the {@link #doOnChange} method.
 * 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author SiberHus
 * @since version 0.9.1
 */
public abstract class FileWatchdog extends Thread {

	/**
	 * The default delay between every file modification check, set to 60
	 * seconds.
	 */
	static final public long DEFAULT_DELAY = 60000;

	/**
	 * The delay to observe between every check. By default set {@link
	 * #DEFAULT_DELAY}.
	 */
	private long delay = DEFAULT_DELAY;

	private File file;
	private long lastModif = 0;
	private boolean warnedAlready = false;
	private boolean interrupted = false;

	private String encoding;
	
	protected FileWatchdog(File file) {
		this.file = file;
		setDaemon(true);
		checkAndConfigure();
	}
	
	protected FileWatchdog(String filename) {
		this(new File(filename));
	}
	

	abstract protected void doOnChange();

	protected void checkAndConfigure() {
		boolean fileExists;
		try {
			fileExists = file.exists();
		} catch (SecurityException e) {
			System.out.println("Was not allowed to read check file existance, file:["
					+ file.getPath() + "].");
			interrupted = true; // there is no point in continuing
			return;
		}

		if (fileExists) {
			long l = file.lastModified(); // this can also throw a
											// SecurityException
			if (l > lastModif) { // however, if we reached this point this
				lastModif = l; // is very unlikely.
				doOnChange();
				warnedAlready = false;
			}
		} else {
			if (!warnedAlready) {
				System.out.println("[" + file.getPath() + "] does not exist.");
				warnedAlready = true;
			}
		}
	}

	@SuppressWarnings("static-access")
	public void run() {
		while (!interrupted) {
			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e) {
				// no interruption expected
				e.printStackTrace();
			}
			checkAndConfigure();
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public String getEncoding() {
		if(encoding==null){
			encoding = Charset.defaultCharset().name();
		}
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * Set the delay to observe between each check of the file changes.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}
	
}

