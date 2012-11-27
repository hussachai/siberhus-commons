package com.siberhus.tools.datagen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberRandomDataGenerator<T extends Number> extends DataGenerator<T>{
	
	private Number minValue;
	
	private Number maxValue;
	
	private int precision;
	
	private RoundingMode roundingMode;
	
	private boolean useFormula = false;
	
	private String formula = "x*32";//math formula
	
	public NumberRandomDataGenerator() {
		super();
	}

	public NumberRandomDataGenerator(Locale locale) {
		super(locale);
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Number getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Number maxValue) {
		this.maxValue = maxValue;
	}

	public Number getMinValue() {
		return minValue;
	}

	public void setMinValue(Number minValue) {
		this.minValue = minValue;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public RoundingMode getRoundingMode() {
		return roundingMode;
	}

	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	public boolean isUseFormula() {
		return useFormula;
	}

	public void setUseFormula(boolean useFormula) {
		this.useFormula = useFormula;
	}

	public T getValue(Class<? extends T> targetClass) throws Exception {
		
		return null;
	}

	
}
