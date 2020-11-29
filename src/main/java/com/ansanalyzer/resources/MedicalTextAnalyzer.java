package com.ansanalyzer.resources;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.comprehendmedical.AWSComprehendMedical;
import com.amazonaws.services.comprehendmedical.AWSComprehendMedicalClient;
import com.amazonaws.services.comprehendmedical.model.DetectEntitiesV2Request;
import com.amazonaws.services.comprehendmedical.model.DetectEntitiesV2Result;
import com.amazonaws.services.comprehendmedical.model.Entity;
import com.amazonaws.services.comprehendmedical.model.Trait;


/*
 * Class respons�vel por extrair os sintomas descritos no relat�rio m�dico
 */
public class MedicalTextAnalyzer {

	public Set<String> symptomExtract(String text, AWSCredentialsProviderChain credentials) {
		
		Set<String> res = new HashSet<>();
		AWSComprehendMedical client = AWSComprehendMedicalClient.builder().withCredentials(credentials)
				.withRegion("us-east-2").build();

		DetectEntitiesV2Request request = new DetectEntitiesV2Request();
		request.setText(text);

		DetectEntitiesV2Result result = client.detectEntitiesV2(request);
				
		
		List<Entity> resultado = result.getEntities();
		
		for (Entity entities : resultado) {
			
			if ((entities.getCategory().equals("MEDICAL_CONDITION")) && (!entities.getTraits().isEmpty())){
					boolean aux=false;
				for(Trait tr : entities.getTraits()) {
					
					if (tr.getName().equals("SYMPTOM")) {
						aux=true;
					}
					if (tr.getName().equals("NEGATION")) {
						aux=false;
					}
					
					
				}
				
					if (aux==true) 
						res.add(entities.getText());
				}
										
			}
		return res;
		}
		
		
		
	}
	


