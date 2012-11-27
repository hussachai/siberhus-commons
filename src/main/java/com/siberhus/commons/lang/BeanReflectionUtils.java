package com.siberhus.commons.lang;

import java.lang.reflect.Method;

/**
 * I borrow some of these method from Apache Click in org.apache.click.util.ContainerUtils
 * @author hussachai
 *
 */
public class BeanReflectionUtils {
	
	/**
     * Return the getter method name for the given property name.
     *
     * @param property the property name
     * @return the getter method name for the given property name.
     */
    public static String toGetterName(String property) {
    	StringBuilder buffer = new StringBuilder(property.length() + 3);

        buffer.append("get");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }
    
    /**
     * Return the is getter method name for the given property name.
     *
     * @param property the property name
     * @return the is getter method name for the given property name.
     */
    public static String toIsGetterName(String property) {
    	StringBuilder buffer = new StringBuilder(property.length() + 3);

        buffer.append("is");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }
    
	/**
     * Return the setter method name for the given property name.
     *
     * @param property the property name
     * @return the setter method name for the given property name.
     */
    public static String toSetterName(String property) {
        StringBuilder buffer = new StringBuilder(property.length() + 3);

        buffer.append("set");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }
	/**
     * Find the object getter method for the given property.
     * <p/>
     * If this method cannot find a 'get' property it tries to lookup an 'is'
     * property.
     *
     * @param object the object to find the getter method on
     * @param property the getter property name specifying the getter to lookup
     * @param path the full expression path (used for logging purposes)
     * @return the getter method
     */
    public final static Method findGetter(Object object, String property,
        String path) {
    	
        // Find the getter for property
        String getterName = toGetterName(property);

        Method method = null;
        Class sourceClass = object.getClass();

        try {
            method = sourceClass.getMethod(getterName, (Class[]) null);
        } catch (Exception e) {
        }

        if (method == null) {
            String isGetterName = toIsGetterName(property);
            try {
                method = sourceClass.getMethod(isGetterName, (Class[]) null);
            } catch (Exception e) {
                StringBuilder buffer = new StringBuilder();
                buffer.append("Result: neither getter methods '");
                buffer.append(getterName).append("()' nor '");
                buffer.append(isGetterName).append("()' was found on class: '");
                buffer.append(object.getClass().getName()).append("'.");
                throw new RuntimeException(buffer.toString(), e);
            }
        }
        return method;
    }
    
    /**
     * Invoke the getterMethod for the given source object.
     *
     * @param getterMethod the getter method to invoke
     * @param source the source object to invoke the getter method on
     * @param property the getter method property name (used for logging)
     * @param path the full expression path (used for logging)
     * @return the getter result
     */
    public final static Object invokeGetter(Method getterMethod, Object source,
        String property, String path) {

        try {
            // Retrieve target object from getter
            return getterMethod.invoke(source, new Object[0]);

        } catch (Exception e) {
            // Log detailed error message of why getter failed
            StringBuilder buffer = new StringBuilder();
            buffer.append("Result: error occurred while trying to get");
            buffer.append(" instance of '");
            buffer.append(getterMethod.getReturnType().getName());
            buffer.append("' using method: '");
            buffer.append(getterMethod.getName()).append("()' of class '");
            buffer.append(source.getClass().getName()).append("'.");
            throw new RuntimeException(buffer.toString(), e);
        }
    }
    
    /**
     * Find the source object setter method for the given property.
     *
     * @param source the source object to find the setter method on
     * @param property the property which setter needs to be looked up
     * @param targetClass the setter parameter type
     * @param path the full expression path (used for logging purposes)
     * @return the setter method
     */
    public final static Method findSetter(Object source,
        String property, Class targetClass, String path) {
        Method method = null;

        // Find the setter for property
        String setterName = toSetterName(property);

        Class sourceClass = source.getClass();
        Class[] classArgs = { targetClass };
        try {
            method = sourceClass.getMethod(setterName, classArgs);
        } catch (Exception e) {
            // Log detailed error message of why setter lookup failed
            StringBuilder buffer = new StringBuilder();
            buffer.append("Result: setter method '");
            buffer.append(setterName).append("(").append(targetClass.getName());
            buffer.append(")' was not found on class '");
            buffer.append(source.getClass().getName()).append("'.");
            throw new RuntimeException(buffer.toString(), e);
        }
        return method;
    }

    /**
     * Invoke the setter method for the given source and target object.
     *
     * @param setterMethod the setter method to invoke
     * @param source the source object to invoke the setter method on
     * @param target the target object to set
     * @param property the setter method property name (used for logging)
     * @param path the full expression path (used for logging)
     */
    public final static void invokeSetter(Method setterMethod, Object source,
        Object target, String property, String path) {

        try {
            Object[] objectArgs = {target};
            setterMethod.invoke(source, objectArgs);

        } catch (Exception e) {
            // Log detailed error message of why setter failed
            StringBuilder buffer = new StringBuilder();
            buffer.append("Result: error occurred while trying to set an");
            buffer.append(" instance of '");
            buffer.append(target.getClass().getName()).append("' using method '");
            buffer.append(setterMethod.getName()).append("(");
            buffer.append(target.getClass());
            buffer.append(")' of class '").append(source.getClass()).append("'.");
            throw new RuntimeException(buffer.toString(), e);
        }
    }
}
