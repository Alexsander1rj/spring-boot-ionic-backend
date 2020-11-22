package com.alexcaetano.cursomc.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String fieldMsg;
	
	public FieldMessage(String fieldName, String fieldMsg) {
		super();
		this.fieldName = fieldName;
		this.fieldMsg = fieldMsg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldMsg() {
		return fieldMsg;
	}

	public void setFieldMsg(String fieldMsg) {
		this.fieldMsg = fieldMsg;
	}	
}
