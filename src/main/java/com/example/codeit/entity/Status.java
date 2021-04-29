package com.example.codeit.entity;

public enum Status {
	
	NOT_PROCESSED("Not Processed"),
	PROCESSED("Processed"),
	FAILED("Failed");

	 private final String displayValue;
	    
	 private Status(String displayValue) {
	    this.displayValue = displayValue;
	  }
	    
	 public String getDisplayValue() {
	    return displayValue;
	  }

}
