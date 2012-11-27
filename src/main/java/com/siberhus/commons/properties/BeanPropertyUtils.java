package com.siberhus.commons.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.siberhus.commons.converter.TypeConvertUtils;
import com.siberhus.commons.lang.SystemProperties;

public class BeanPropertyUtils {
	
	public static void writeBeanProperties(Class beanClass,boolean addTypeComment){
		writeBeanProperties(beanClass,addTypeComment,null);
	}
	
	public static void writeBeanProperties(Class beanClass,boolean addTypeComment
			,String propertyPrefix){
		try {
			writeBeanProperties(beanClass,addTypeComment,propertyPrefix,null,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeBeanProperties(Class beanClass, boolean addTypeComment
			, String propertyPrefix, File propsFile, String encoding) throws IOException{
		OutputStreamWriter writer = null;
		try{
			if(encoding==null){
				encoding = Charset.defaultCharset().name();
			}
			if(propsFile!=null){
				writer = new OutputStreamWriter(new FileOutputStream(propsFile),encoding);
			}else{
				writer = new OutputStreamWriter(System.out,encoding);
			}
			List<BeanProperty> beanPropList = getPropertyElementList(beanClass, propertyPrefix);
			for(BeanProperty beanProp : beanPropList){
				writer.append(SystemProperties.LINE_SEPARATOR);
				if(addTypeComment){
					writer.append("#"+beanProp.getType().getName());
					if(beanProp.getType().isEnum()){
						writer.append(" "+Arrays.toString(
								beanProp.getType().getEnumConstants()));
					}
					writer.append(SystemProperties.LINE_SEPARATOR);
				}
				writer.append(beanProp.getName()+"=");
				writer.append(SystemProperties.LINE_SEPARATOR);
			}
		}finally{
			if(writer!=null) writer.close();
		}
		
	}
	
	public static List<BeanProperty> setUpBeanProperties(ISimpleProperties props
			, Object target)throws Exception{
		String propertyPrefix = target.getClass().getName()+".";
		return setUpBeanProperties((Object)props,target,propertyPrefix);
	}
	
	public static List<BeanProperty> setUpBeanProperties(ISimpleProperties props
			, Object target, String propertyPrefix)throws Exception{
		return setUpBeanProperties((Object)props,target,propertyPrefix);
	}
	
	public static List<BeanProperty> setUpBeanProperties(Properties props, Object target)throws Exception{
		String propertyPrefix = target.getClass().getName()+".";
		return setUpBeanProperties((Object)props,target,propertyPrefix);
	}
	
	public static List<BeanProperty> setUpBeanProperties(Properties props
			, Object target, String propertyPrefix)throws Exception{
		return setUpBeanProperties((Object)props,target,propertyPrefix);
	}
	
	public static List<BeanProperty> getPropertyElementList(Class beanClass
			, String propertyPrefix){
		
		List<BeanProperty> beanPropList = new ArrayList<BeanProperty>();
		
		if(propertyPrefix==null){
			propertyPrefix = "";
		}
		for(Method method : beanClass.getMethods()){
			String methodName = method.getName();
			String propertyName = null;
			if(!methodName.startsWith("set") || methodName.length()<4){
				continue;
			}
			if(methodName.length()==4){
				propertyName = methodName.substring(3).toLowerCase();
			}else{
				//if length > 4
				propertyName = Character.toLowerCase(methodName.charAt(3))
					+methodName.substring(4);
			}
			if(!method.isAccessible()){
				method.setAccessible(true);
			}
			Class paramTypes[] = method.getParameterTypes();
			if(paramTypes.length!=1){
				continue;
			}
			Class paramType = paramTypes[0];
			if(!TypeConvertUtils.getRegisteredTypes().contains(paramType)){
				if(!paramType.isEnum()){
					continue;
				}
			}
			String propName = propertyPrefix + propertyName;
			BeanProperty propElem = new BeanProperty(propName,paramType,method);
			beanPropList.add(propElem);
			
		}
		return beanPropList;
	}
	
	private static List<BeanProperty> setUpBeanProperties(Object propsObj
			, Object target, String propertyPrefix)throws Exception{
		
		ISimpleProperties simpleProps = null;
		Properties props = null;
		if(propsObj instanceof ISimpleProperties){
			simpleProps = (ISimpleProperties)propsObj;
		}else{
			props = (Properties)propsObj;
		}
		
		List<BeanProperty> propElemList = getPropertyElementList(
				target.getClass(), propertyPrefix);
		for(BeanProperty propElem : propElemList){
			String propValue = null;
			if(props!=null){
				propValue = props.getProperty(propElem.getName());
			}else{
				propValue = simpleProps.getProperty(propElem.getName());
			}
			if(propValue!=null){
				Object value = TypeConvertUtils.convert(propValue, propElem.getType(), null);
				if(value!=null){
					propElem.getMethod().invoke(target,value);
				}
			}
		}
		return propElemList;
	}
}


