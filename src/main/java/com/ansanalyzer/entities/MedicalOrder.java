package com.ansanalyzer.entities;

import java.io.File;


public class MedicalOrder {

	private Long id;
	private Patient patient;
	private String result;
	File file;
	
	public MedicalOrder(Patient patient, File file) {
		
		
		this.patient = patient;
		this.file = file;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
		
	
}
