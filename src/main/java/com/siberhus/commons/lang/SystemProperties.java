package com.siberhus.commons.lang;

/**
 * Based on Sun JDK 1.6.0 on WindowsXP
 * @author hussachai
 *
 */
final public class SystemProperties {
	
	
	public static final String AWT_TOOLKIT;//sun.awt.windows.WToolkit
	
	public static final String FILE_ENCODING;//MS874
	public static final String FILE_ENCODING_PKG;//sun.io
	public static final String FILE_SEPARATOR;// backslash \
	
	public static final String JAVA_AWT_GRAPHICSENV;//sun.awt.Win32GraphicsEnvironment
	public static final String JAVA_AWT_PRINTERJOB;//sun.awt.windows.WPrinterJob
	public static final String JAVA_CLASS_PATH;//C:\groovy-1.0\lib\groovy-starter.jar
	public static final String JAVA_CLASS_VERSION;//50.0
	public static final String JAVA_ENDORSED_DIRS;//C:\Program Files\Java\jdk1.6.0\jre\lib\endorsed
	public static final String JAVA_EXT_DIRS;//C:\Program Files\Java\jdk1.6.0\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
	public static final String JAVA_HOME;//C:\Program Files\Java\jdk1.6.0\jre
	public static final String JAVA_IO_TMPDIR;//C:\DOCUME~1\HUSSAC~1\LOCALS~1\Temp\
	public static final String JAVA_LIBRARY_PATH;//C:\Program Files\Java\jdk1.6.0\bin;.;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;;C:\Program Files\Common Files\GTK\2.0\bin;C:\Program Files\SecureCRT\;C:\Sun\SDK\bin;;c:\cygwin\bin;C:\DevSuiteHome\bin;C:\Apache Ant 1.6\bin;C:\Program Files\Java\jdk1.6.0\bin;C:\groovy-1.0\bin
	public static final String JAVA_RUNTIME_NAME;//Java(TM) SE Runtime Environment
	public static final String JAVA_RUNTIME_VERSION;//1.6.0-b105
	public static final String JAVA_SPECIFICATION_NAME;//Java Platform API Specification
	public static final String JAVA_SPECIFICATION_VENDOR;//Sun Microsystems Inc.
	public static final String JAVA_SPECIFICATION_VERSION;//1.6
	public static final String JAVA_VENDOR;//Sun Microsystems Inc.
	public static final String JAVA_VENDOR_URL;//http://java.sun.com/
	public static final String JAVA_VENDOR_URL_BUG;//http://java.sun.com/cgi-bin/bugreport.cgi
	public static final String JAVA_VERSION;//1.6.0
	public static final String JAVA_VM_INFO;//mixed mode, sharing
	public static final String JAVA_VM_NAME;//Java HotSpot(TM) Client VM
	public static final String JAVA_VM_SPECIFICATION_NAME;//Java Virtual Machine Specification
	public static final String JAVA_VM_SPECIFICATION_VENDOR;//Sun Microsystems Inc.
	public static final String JAVA_VM_SPECIFICATION_VERSION;//1.0
	public static final String JAVA_VM_VENDOR;//Sun Microsystems Inc.
	public static final String JAVA_VM_VERSION;//1.6.0-b105
	
	public static final String LINE_SEPARATOR;//\n or \r\n
	
	public static final String OS_ARCH;//x86
	public static final String OS_NAME;//Windows XP
	public static final String OS_VERSION; //5.1
	
	public static final String PATH_SEPARATOR;//;(semicolon in windows) : (colon in unix)
	
	public static final String SUN_ARCH_DATA_MODEL;//32
	public static final String SUN_BOOT_CLASS_PATH;//C:\Program Files\Java\jdk1.6.0\jre\lib\resources.jar;C:\Program Files\Java\jdk1.6.0\jre\lib\rt.jar;C:\Program Files\Java\jdk1.6.0\jre\lib\sunrsasign.jar;C:\Program Files\Java\jdk1.6.0\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.6.0\jre\lib\jce.jar;C:\Program Files\Java\jdk1.6.0\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.6.0\jre\classes
	public static final String SUN_CPU_ENDIAN;//little
	public static final String SUN_CPU_ISALIST;//pentium_pro+mmx pentium_pro pentium+mmx pentium i486 i386 i86
	public static final String SUN_DESKTOP;//windows
	public static final String SUN_IO_UNICODE_ENCODING;//UnicodeLittle
	public static final String SUN_JAVA_LAUNCHER;//SUN_STANDARD
	public static final String SUN_JNU_ENCODING;//MS874
	public static final String SUN_MANAGEMENT_COMPILER;//HotSpot Client Compiler
	public static final String SUN_OS_PATCH_LEVEL;//Service Pack 2
	
	public static final String USER_COUNTRY; //TH
	public static final String USER_DIR;//C:\Documents and Settings\Hussachai
	public static final String USER_HOME;//C:\Documents and Settings\Hussachai
	public static final String USER_LANGUAGE;//th
	public static final String USER_NAME;//Hussachai
	public static final String USER_TIMEZONE;//Asia/Bangkok
	public static final String USER_VARIANT;
	
	static{
		AWT_TOOLKIT = System.getProperty("awt.toolkit");
		
		FILE_ENCODING = System.getProperty("file.encoding");
		FILE_ENCODING_PKG = System.getProperty("file.encoding.pkg");
		FILE_SEPARATOR = System.getProperty("file.separator");
		
		JAVA_AWT_GRAPHICSENV = System.getProperty("java.awt.graphicsenv");
		JAVA_AWT_PRINTERJOB = System.getProperty("java.awt.printerjob");
		JAVA_CLASS_PATH = System.getProperty("java.class.path");
		JAVA_CLASS_VERSION = System.getProperty("java.class.version");
		JAVA_ENDORSED_DIRS = System.getProperty("java.endorsed.dirs");
		JAVA_EXT_DIRS = System.getProperty("java.ext.dirs");
		JAVA_HOME = System.getProperty("java.home");
		JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");
		JAVA_LIBRARY_PATH = System.getProperty("java.library.path");
		JAVA_RUNTIME_NAME = System.getProperty("java.runtime.name");
		JAVA_RUNTIME_VERSION = System.getProperty("java.runtime.version");
		JAVA_SPECIFICATION_NAME = System.getProperty("java.specification.name");
		JAVA_SPECIFICATION_VENDOR = System.getProperty("java.specification.vendor");
		JAVA_SPECIFICATION_VERSION = System.getProperty("java.specification.version");
		JAVA_VENDOR = System.getProperty("java.vendor");
		JAVA_VENDOR_URL = System.getProperty("java.vendor.url");
		JAVA_VENDOR_URL_BUG = System.getProperty("java.vendor.url.bug");
		JAVA_VERSION = System.getProperty("java.version");
		JAVA_VM_INFO = System.getProperty("java.vm.info");
		JAVA_VM_NAME = System.getProperty("java.vm.name");
		JAVA_VM_SPECIFICATION_NAME = System.getProperty("java.vm.specification.name");
		JAVA_VM_SPECIFICATION_VENDOR = System.getProperty("java.vm.specification.vendor");
		JAVA_VM_SPECIFICATION_VERSION = System.getProperty("java.vm.specification.version");
		JAVA_VM_VENDOR = System.getProperty("java.vm.vendor");
		JAVA_VM_VERSION = System.getProperty("java.vm.version");
		
		LINE_SEPARATOR = System.getProperty("line.separator");
		
		OS_ARCH = System.getProperty("os.arch");
		OS_NAME = System.getProperty("os.name");
		OS_VERSION = System.getProperty("os.version");
		
		PATH_SEPARATOR = System.getProperty("path.separator");
		
		SUN_ARCH_DATA_MODEL = System.getProperty("sun.arch.data.model");
		SUN_BOOT_CLASS_PATH = System.getProperty("sun.boot.class.path");
		SUN_CPU_ENDIAN = System.getProperty("sun.cpu.endian");
		SUN_CPU_ISALIST = System.getProperty("sun.cpu.isalist");
		SUN_DESKTOP = System.getProperty("sun.desktop");
		SUN_IO_UNICODE_ENCODING = System.getProperty("sun.io.unicode.encoding");
		SUN_JAVA_LAUNCHER = System.getProperty("sun.java.launcher");
		SUN_JNU_ENCODING = System.getProperty("sun.jnu.encoding");
		SUN_MANAGEMENT_COMPILER = System.getProperty("sun.management.compiler");
		SUN_OS_PATCH_LEVEL = System.getProperty("sun.os.patch.level");
		
		USER_COUNTRY = System.getProperty("user.country");
		USER_DIR = System.getProperty("user.dir");
		USER_HOME = System.getProperty("user.home");
		USER_LANGUAGE = System.getProperty("user.language");
		USER_NAME = System.getProperty("user.name");
		USER_TIMEZONE = System.getProperty("user.timezone");
		USER_VARIANT = System.getProperty("user.variant");
	}
	
}
