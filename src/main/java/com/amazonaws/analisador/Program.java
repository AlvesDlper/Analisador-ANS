package com.amazonaws.analisador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.ansanalyzer.entities.MedicalOrder;
import com.ansanalyzer.entities.Patient;
import com.ansanalyzer.resources.AWSCredentialsCon;
import com.ansanalyzer.resources.DocTranslater;
import com.ansanalyzer.resources.MedicalTextAnalyzer;
import com.ansanalyzer.services.coveragecheck.PCRCovidCheck;
import com.ansanalyzer.utils.DocumentReader;

import software.amazon.awssdk.services.iotthingsgraph.model.CreateSystemTemplateRequest;

public class Program {

	public static void main(String[] args) throws IOException {
		
		AWSCredentialsProviderChain con = AWSCredentialsCon.getCredentials();
		
		Patient paciente = new Patient("Arthur", 26, 'm');
		File file = new File("C:\\Users\\Dell Optiplex 790 DT\\Desktop\\Docs testes\\RM TESTE.pdf");
		
		MedicalOrder medicalOrder = new MedicalOrder(paciente, file);
		//File file = new File("C:\\Users\\Dell Optiplex 790 DT\\Desktop\\Docs testes\\RM TESTE.pdf");
		
		//String teste = "MEDICAL REPORT Patient with cough, fever, precordial pain and tiredness. He had contact with a confirmed case of COVID 19. Request PCR test.";
	
		List<String> litt = DocumentReader.txtSentences(medicalOrder.getFile());
		
		System.out.println("LISTA SEGMENTADA");
		for (String sint : litt) {
			System.out.println(sint);
		}
		litt=DocTranslater.doTraduction(litt,"pt","en",con);
	 

		System.out.println("LISTA EM INGLES");
		for (String sint : litt) {
			System.out.println(sint);
			
		}		
		
	
		MedicalTextAnalyzer rm = new MedicalTextAnalyzer();
		System.out.println(litt.toString());
		List<String> sintomas = new ArrayList<String>(rm.symptomExtract(litt.toString(), con));
		
		System.out.println("LISTA EM INGLES");
		for (String sint : sintomas) {
			System.out.println(sint);
			
		}		
		sintomas=DocTranslater.doTraduction(sintomas, "en", "pt", con);
		
		
		PCRCovidCheck analisador = new PCRCovidCheck();
		System.out.println(analisador.coverageCheck(sintomas, paciente.getIdade()));
		
		System.out.println("LISTA ANALIZADA");
		
		for (String sint : sintomas) {
				System.out.println(sint);
				
			}		
		
		
	}}


