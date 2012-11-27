package com.siberhus.commons.converter;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class DefaultTypeConverterFactory implements ITypeConverterFactory {

	/** A rather generic-heavy Map that maps target type to TypeConverter. */
	private Map<Class, Class<? extends ITypeConverter>> converters = new HashMap<Class, Class<? extends ITypeConverter>>();

	public DefaultTypeConverterFactory() {
		init();
	}

	/**
	 * Places all the known convertible types and type converters into an
	 * instance level Map.
	 */
	public void init() {

		register(Boolean.class, BooleanTypeConverter.class);
		register(Boolean.TYPE, BooleanTypeConverter.class);
		register(Byte.class, ByteTypeConverter.class);
		register(Byte.TYPE, ByteTypeConverter.class);
		register(Short.class, ShortTypeConverter.class);
		register(Short.TYPE, ShortTypeConverter.class);
		register(Integer.class, IntegerTypeConverter.class);
		register(Integer.TYPE, IntegerTypeConverter.class);
		register(Long.class, LongTypeConverter.class);
		register(Long.TYPE, LongTypeConverter.class);
		register(Float.class, FloatTypeConverter.class);
		register(Float.TYPE, FloatTypeConverter.class);
		register(Double.class, DoubleTypeConverter.class);
		register(Double.TYPE, DoubleTypeConverter.class);
		register(Date.class, DateTypeConverter.class);
		register(Calendar.class, CalendarTypeConverter.class);
		register(BigInteger.class, BigIntegerTypeConverter.class);
		register(BigDecimal.class, BigDecimalTypeConverter.class);
		
		// Now some less useful, but still helpful converters
		register(String.class, StringTypeConverter.class);
		register(Object.class, ObjectTypeConverter.class);
		register(Character.class, CharacterTypeConverter.class);
		register(Class.class, ClassTypeConverter.class);
		register(File.class, FileTypeConverter.class);
		
		register(java.sql.Date.class, SqlDateTypeConverter.class);
		register(Timestamp.class, SqlTimestampTypeConverter.class);
		register(Time.class, SqlTimeTypeConverter.class);
		register(Locale.class, LocaleTypeConverter.class);
		register(Pattern.class, PatternTypeConverter.class);
	}

	protected Map<Class, Class<? extends ITypeConverter>> getTypeConverters() {
		return this.converters;
	}

	public ITypeConverter getTypeConverter(Class forType, Locale locale)
			throws Exception {
		// First take a look in our map of Converters for one registered for
		// this type.
		Class<? extends ITypeConverter> clazz = this.converters.get(forType);

		if (clazz != null) {
			return getInstance(clazz, locale);
		} else if (forType.isEnum()) {
			// If we didn't find one, maybe this class is an enum?
			return getInstance(EnumeratedTypeConverter.class, locale);
		} else {
			return null;
		}
	}

	public ITypeConverter getInstance(Class<? extends ITypeConverter> clazz,
			Locale locale) throws Exception {
		// TODO: add thread local caching of converter classes
		ITypeConverter converter = clazz.newInstance();
		converter.setLocale(locale);
		return converter;
	}

	public void register(Class type, Class<? extends ITypeConverter> converter) {
		converters.put(type, converter);
	}

	public void unregister(Class type) {
		converters.remove(type);
	}
	
	public Collection<Class> getRegisteredTypes(){
		return converters.keySet();
	}
	
}
