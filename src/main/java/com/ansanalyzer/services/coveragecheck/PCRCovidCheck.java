package com.ansanalyzer.services.coveragecheck;

import java.util.List;

/*	Classe responsável por efetuar a análise de cobertura
 * 	Para o exame PCR-COVID, conforme as normas da ANS
 * 
 */
public class PCRCovidCheck {

		private final List<String> criteriosMSAdulto = 
				List.of("febre", "febril", "calafrios",
						 "dor de garganta","dor de cabeça","cefaleia",
						 "tosse","tossindo","coriza","distúrbios olfativos",
						 "distúrbios gustativos","perda do paladar","perda do olfato",
						 "sem olfato","sem paladar","diarreia");
		private final List<String >criteriosMSPed = 
				List.of("febre", "febril", "calafrios",
						"dor de garganta","dor de cabeça",
						"cefaleia", "tosse","tossindo", "coriza", "distúrbios olfativos",
						"distúrbios gustativos", "perda do paladar", "perda do olfato",
						"sem olfato", "sem paladar", "obstrução nasal", "congestão nasal", "diarreia");
		private final List<String >criteriosMSIdoso = 
				List.of("febre", "febril", "calafrios", "dor de garganta",
						"dor de cabeça","cefaleia", "tosse","tossindo", "coriza", "distúrbios olfativos",
						"distúrbios gustativos", "perda do paladar", "perda do olfato", "sem olfato",
						"sem paladar", "diarreia", "sincope", "desmaio", "confusão mental",
						"sonolência", "irritabilidade", "falta de apetite", "inapetência");
		
			
	public String coverageCheck(List <String> sintoms, int age) {
		
		
		int score=0;
		
		String result= new String();
		
		if (sintoms.isEmpty()) {
			
			 result =" O paciente não preenche os critérios "
						+ "da diretriz de utilização da ANS. \n"
						+ "Não foram apresentados sintomas compativeis com um caso suspeito de COVID-19. \n"
						+ "Negar exame ou solicitar novo relatório médico.";;
			 
		}
		else if (age<12) {
			
			for (String sint : sintoms) {
				
				if (criteriosMSPed.contains(sint.toLowerCase())) {
					score+=1;
					
				}
			}
			if (score >=2) {
				result =" O exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
						+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
						+ "Relatório médico, ele apresenta sintomas que o classificam como "
						+ "caso suspeito de COVID-19";
			
			}
			else
				result=" O paciente não preenche os critérios "
						+ "da diretriz de utilização da ANS. \n"
						+ "Não foram apresentados sintomas compativeis com um caso suspeito de COVID-19. \n"
						+ "Negar exame ou solicitar novo relatório médico.";
				
		}
		else if (age>=60) {
			
			for (String sint : sintoms) {
				
				if (criteriosMSIdoso.contains(sint.toLowerCase())) {
					score+=1;
					
				}
			}
			if (score >=2) {
				result=" O exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
						+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
						+ "Relatório médico, ele apresenta sintomas que o classificam como "
						+ "caso suspeito de COVID-19";
			}
			else
				result=" O paciente não preenche os critérios "
						+ "da diretriz de utilização da ANS. \n"
						+ "Não foram apresentados sintomas compativeis com um caso suspeito de COVID-19. \n"
						+ "Negar exame ou solicitar novo relatório médico.";
			
		} else {
					
		for (String sint : sintoms) {
			
			if (criteriosMSAdulto.contains(sint.toLowerCase())) {
				score+=1;
				
			
			}
		}
		if (score >=2) {
			result=" O exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
					+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
					+ "Relatório médico, ele apresenta sintomas que o classificam como "
					+ "caso suspeito de COVID-19";
		}
		else
			{result=" O paciente não preenche os critérios "
					+ "da diretriz de utilização da ANS. \n"
					+ "Não foram apresentados sintomas compativeis com um caso suspeito de COVID-19. \n"
					+ "Negar exame ou solicitar novo relatório médico.";}
		
		}
		
		return result;
	}
	
	
	

}
