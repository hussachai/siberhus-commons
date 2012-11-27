package com.siberhus.commons.properties;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BeanPropertyUtilsTest {
	
	@Test
	public void testFullPropertyName()throws Exception{
		ISimpleProperties props = new SimpleProperties();
		String propertyPrefix = "com.siberhus.commons.properties.BeanPropertyUtilsTest$Dog";
		props.setProperty(propertyPrefix+".name", "Bie");
		props.setProperty(propertyPrefix+".enabled", "true");
		props.setProperty(propertyPrefix+".animalId", "5654");
		props.setProperty(propertyPrefix+".sex", "Female");
		Dog dog = new Dog();
		List<BeanProperty> propNameList = BeanPropertyUtils
			.setUpBeanProperties(props, dog);
		Assert.assertEquals(4,propNameList.size());
		Assert.assertEquals("Bie", dog.getName());
		Assert.assertTrue(dog.isEnabled());
		Assert.assertEquals(5654L, dog.getAnimalId().longValue());
		Assert.assertEquals(Sex.Female, dog.getSex());
	}
	
	@Test
	public void testShortPropertyName()throws Exception{
		ISimpleProperties props = new SimpleProperties();
		props.setProperty("Dog.name", "Bie");
		props.setProperty("Dog.enabled", "true");
		props.setProperty("Dog.animalId", "5654");
		props.setProperty("Dog.sex", "Male");
		Dog dog = new Dog();
		List<BeanProperty> propNameList = BeanPropertyUtils
			.setUpBeanProperties(props, dog, "Dog.");
		Assert.assertEquals(4,propNameList.size());
		Assert.assertEquals("Bie", dog.getName());
		Assert.assertTrue(dog.isEnabled());
		Assert.assertEquals(5654L, dog.getAnimalId().longValue());
		Assert.assertEquals(Sex.Male, dog.getSex());
		Sex.values();
		props.setProperty("name", "Bie");
		BeanPropertyUtils.setUpBeanProperties(props, dog, "");
		Assert.assertEquals("Bie", dog.getName());
	}
	
	public static void main(String[] args) {
		System.out.println(Sex.class.isEnum());
	}
	enum Sex {
		Male, Female , None
	}
	class Animal {
		private boolean enabled;
		private String name;
		private Long animalId;
		private Sex sex;
		
		public void setUp(){}
		
		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public Long getAnimalId() {
			return animalId;
		}

		public void setAnimalId(Long animalId) {
			this.animalId = animalId;
		}

		public Sex getSex() {
			return sex;
		}

		public void setSex(Sex sex) {
			this.sex = sex;
		}
		
	}
	class Breed {}
	class Dog extends Animal{
		private Breed breed;
		public Breed getBreed() {
			return breed;
		}

		public void setBreed(Breed breed) {
			this.breed = breed;
		}
		
		private void setSomething(String a){
			
		}
		
	}
}
