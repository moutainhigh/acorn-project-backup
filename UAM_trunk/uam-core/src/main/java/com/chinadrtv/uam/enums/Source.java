package com.chinadrtv.uam.enums;

public enum Source {

	ldap("LDAP");
	
	private String value;
	
	private Source(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public static Source getBy(String value) {
		for (Source s : Source.values()) {
			if (s.getValue().equalsIgnoreCase(value)) {
				return s;
			}
		}
		return null;
	}
}
