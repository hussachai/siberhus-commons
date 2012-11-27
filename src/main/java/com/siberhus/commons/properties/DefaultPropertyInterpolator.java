package com.siberhus.commons.properties;

import java.util.Properties;

import org.apache.commons.lang.text.StrSubstitutor;

public class DefaultPropertyInterpolator implements IPropertyInterpolator {
	
	private boolean propertiesLookupEnabled = true;
	
	private StrSubstitutor propertiesSubStitutor;
	
	private boolean systemPropertiesLookupEnabled = true;
	
	private StrSubstitutor systemPropertiesSubStitutor;
	
	private boolean systemEnvLookupEnabled = true;
	
	private StrSubstitutor systemEnvSubStitutor;
	
	public DefaultPropertyInterpolator(ISimpleProperties properties){
		propertiesSubStitutor = new StrSubstitutor(properties);
		systemPropertiesSubStitutor = new StrSubstitutor(
			DefaultStrLookup.systemPropertiesLookup());
		systemEnvSubStitutor = new StrSubstitutor(DefaultStrLookup.systemEnvLookup());
	}
	
	public DefaultPropertyInterpolator(Properties properties){
		propertiesSubStitutor = new StrSubstitutor(properties);
		systemPropertiesSubStitutor = new StrSubstitutor(
			DefaultStrLookup.systemPropertiesLookup());
		systemEnvSubStitutor = new StrSubstitutor(DefaultStrLookup.systemEnvLookup());
	}
	
	public boolean isPropertiesLookupEnabled() {
		return propertiesLookupEnabled;
	}
	
	public void setPropertiesLookupEnabled(boolean propertiesLookupEnabled) {
		this.propertiesLookupEnabled = propertiesLookupEnabled;
	}

	public boolean isSystemPropertiesLookupEnabled() {
		return systemPropertiesLookupEnabled;
	}

	public void setSystemPropertiesLookupEnabled(
			boolean systemPropertiesLookupEnabled) {
		this.systemPropertiesLookupEnabled = systemPropertiesLookupEnabled;
	}

	public boolean isSystemEnvLookupEnabled() {
		return systemEnvLookupEnabled;
	}

	public void setSystemEnvLookupEnabled(boolean systemEnvLookupEnabled) {
		this.systemEnvLookupEnabled = systemEnvLookupEnabled;
	}
	
	public StrSubstitutor getPropertiesSubStitutor() {
		return propertiesSubStitutor;
	}

	public void setPropertiesSubStitutor(StrSubstitutor propertiesSubStitutor) {
		this.propertiesSubStitutor = propertiesSubStitutor;
	}

	public StrSubstitutor getSystemPropertiesSubStitutor() {
		return systemPropertiesSubStitutor;
	}

	public void setSystemPropertiesSubStitutor(
			StrSubstitutor systemPropertiesSubStitutor) {
		this.systemPropertiesSubStitutor = systemPropertiesSubStitutor;
	}

	public StrSubstitutor getSystemEnvSubStitutor() {
		return systemEnvSubStitutor;
	}

	public void setSystemEnvSubStitutor(StrSubstitutor systemEnvSubStitutor) {
		this.systemEnvSubStitutor = systemEnvSubStitutor;
	}

	public String interpolate(String value){
		if(value==null){
			return null;
		}
		if(isPropertiesLookupEnabled()){
			value = propertiesSubStitutor.replace(value);
		}
		if(isSystemPropertiesLookupEnabled()){
			value = systemPropertiesSubStitutor.replace(value);
		}
		if(isSystemEnvLookupEnabled()){
			value = systemEnvSubStitutor.replace(value);
		}
		return value;
	}
	
}
