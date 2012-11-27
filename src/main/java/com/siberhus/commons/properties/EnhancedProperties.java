package com.siberhus.commons.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Because java.util.Properties is used spread around the world
 * but it lacks many properties such as properties ordering, charset
 * reload changed file automatically (file watchdog), type conversion
 * and the important one, multiple property values. Unfortunately
 * java.util.Properties is not interface so changing its implementation
 * is not easy. I do the dirty way by extending it however I don't use its
 * implementation I just use its interface I know it stupid but I don't
 * know the othor way to accomplish this.
 * 
 * WARNING: This properties implementation does not escape any values
 * So it may not compatible with java.util.Properties in some way.
 * @author hussachai
 *
 */
public class EnhancedProperties extends Properties implements ISimpleProperties{
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	private ISimpleProperties realProperties;
	
	public EnhancedProperties(){
		realProperties = new LineDelimProperties();
	}
	
	public EnhancedProperties(Properties props) {
		realProperties = new LineDelimProperties();
		for(String key : props.stringPropertyNames()){
			realProperties.setProperty(key, props.getProperty(key));
		}
	}
	
	public EnhancedProperties(ISimpleProperties props){
		this.realProperties = props;
	}
	
	public ISimpleProperties getDelegate(){
		return realProperties;
	}
	
	public File getFile() {
		return realProperties.getFile();
	}
	
	public void setFile(File file){
		realProperties.setFile(file);
	}
	
	public String getEncoding() {
		return realProperties.getEncoding();
	}
	
	public void setEncoding(String encoding){
		realProperties.setEncoding(encoding);
	}
	
	public void load(File file) throws IOException{
		load(file,Charset.defaultCharset().name());
	}
	
	public void load(File file,String charset) throws IOException{
		realProperties.load(file, charset);
	}
	
	public void loadAndWatch(File file,int delay) throws IOException {
		loadAndWatch(file,Charset.defaultCharset().name(),delay);
	}
	
	public void loadAndWatch(File file,String charset,int delay) throws IOException {
		realProperties.loadAndWatch(file, charset, delay);
	}
	@Override
	public void load(InputStream inStream) throws IOException {
		load(new InputStreamReader(inStream));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(Reader reader) throws IOException {
		realProperties.load(reader);
	}
	
	@Override
	public void loadFromXML(InputStream in) throws IOException,
			InvalidPropertiesFormatException {
		super.loadFromXML(in);
	}
	
	public void save(String comments) throws IOException{
		realProperties.save(comments);
	}
	
	@Override
	public void save(OutputStream out, String comments) {
		try {
            store(out, comments);
        } catch (IOException e) {}
	}
	
	@Override
	public void store(OutputStream out, String comments) throws IOException {
		store(new OutputStreamWriter(out), comments);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void store(Writer writer, String comments) throws IOException {
		realProperties.save(writer, comments);
	}

	@Override
	public synchronized void storeToXML(OutputStream os, String comment,
			String encoding) throws IOException {
		super.storeToXML(os, comment, encoding);
	}

	@Override
	public synchronized void storeToXML(OutputStream os, String comment)
			throws IOException {
		super.storeToXML(os, comment);
	}
	
	@Override
	public String getProperty(String key, String defaultValue) {
		return realProperties.getProperty(key,defaultValue);
	}
	
	@SuppressWarnings("unchecked")
	public String[] getPropertyAsArray(String key){
		return realProperties.getPropertyAsArray(key);
	}
	
	public String[] getPropertyAsArray(String key,String[] defaultValues){
		return realProperties.getPropertyAsArray(key, defaultValues);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getProperty(String key) {
		return realProperties.getProperty(key);
	}
	
	@Override
	public synchronized String setProperty(String key, String value) {
		String prevValue = getProperty(key);
		realProperties.setProperty(key, value);
		return prevValue;
	}
	
	@Override
	public String[] addPropertyValue(String key, String value) {
		return realProperties.addPropertyValue(key, value);
	}

	@Override
	public <T> T getProperty(Class<T> type, String key, T defaultValue) {
		return realProperties.getProperty(type, key, defaultValue);
	}
	
	@Override
	public <T> T getProperty(Class<T> type, String key) {
		return realProperties.getProperty(type, key);
	}
	
	@Override
	public <T>T[] getPropertyAsArray(Class<T> type, String key) {
		return realProperties.getPropertyAsArray(type, key);
	}
	
	@Override
	public void save(Writer writer, String comments) throws IOException {
		realProperties.save(writer, comments);
	}

	@Override
	public String[] setProperty(String key, String[] values) {
		return realProperties.setProperty(key, values);
	}
	
	
	@Override
	public void list(PrintStream out) {
		super.list(out);
	}

	@Override
	public void list(PrintWriter out) {
		super.list(out);
	}
	
	@Override
	public Set<String> stringPropertyNames() {
		return super.stringPropertyNames();
	}
	
	@Override
	public Enumeration<?> propertyNames() {
		return super.propertyNames();
	}
	
	public void setInterpolatable(boolean interpolatable){
		realProperties.setInterpolatable(interpolatable);
	}
	
	public boolean isInterpolatable(){
		return realProperties.isInterpolatable();
	}
	
	public void setInterpolator(IPropertyInterpolator interpolator){
		realProperties.setInterpolator(interpolator);
	}
	
	public IPropertyInterpolator getInterpolator(){
		return realProperties.getInterpolator();
	}
	
	public final String interpolate(String value){
		return realProperties.interpolate(value);
	}
	
	public EnhancedProperties duplicate(){
		return new EnhancedProperties(realProperties.duplicate());
	}
	
	//===========================================================//
	//OVERRIDE Hashtable (Parent of java.util.Properties class)
	
	@Override
	public synchronized void clear() {
		realProperties.clear();
	}
	
	@Override
	public synchronized Object clone() {
		
		return realProperties.duplicate();
	}
	
	@Override
	public synchronized boolean contains(Object value) {
//		return propsMap.contains(value);
		return realProperties.containsValue(value);
	}
	
	@Override
	public synchronized boolean containsKey(Object key) {
		return realProperties.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return realProperties.containsValue(value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized Enumeration<Object> elements() {
//		return propsMap.elements();
		return Collections.enumeration(realProperties.values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Map.Entry<Object,Object>> entrySet() {
		return realProperties.entrySet();
	}
	
	@Override
	public synchronized boolean equals(Object o) {
		return realProperties.equals(o);
	}

	@Override
	public synchronized Object get(Object key) {
		return realProperties.get(key);
	}
	
	@Override
	public synchronized int hashCode() {
		return realProperties.hashCode();
	}

	@Override
	public synchronized boolean isEmpty() {
		return realProperties.isEmpty();
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(keySet());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> keySet() {
		return realProperties.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Object put(Object key, Object value) {
		return realProperties.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void putAll(Map<? extends Object, ? extends Object> t) {
		realProperties.putAll(t);
	}
	
	@Override
	protected void rehash() {
//		propsMap.rehash();
	}

	@Override
	public synchronized Object remove(Object key) {
		return realProperties.remove(key);
	}

	@Override
	public synchronized int size() {
		return realProperties.size();
	}

	@Override
	public synchronized String toString() {
		return realProperties.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> values() {
		return realProperties.values();
	}
	
	public static void main(String[] args) throws IOException {
		EnhancedProperties p = new EnhancedProperties();
//		p.load(new InputStreamReader(new FileInputStream("resources/thailang.txt"),"UTF-8"));
		p.load(new File("resources/thailang.txt"),"UTF-8");
		System.out.println(p.getProperty("cat"));
		System.out.println(p.getPropertyAsArray("cat"));
		System.out.println("novalue="+p.getProperty("novalue"));
		System.out.println("notexist="+p.getProperty("notexist"));
		System.out.println("Clone="+ (p.clone() instanceof Map));
		System.out.println(p.propertyNames());
		System.out.println(p.stringPropertyNames());
		
		
	}

	@Override
	public Properties toProperties() {
		return realProperties.toProperties();
	}
	
	@Override
	public Properties toInterpolatedProperties(){
		return realProperties.toInterpolatedProperties();
	}
	
}
