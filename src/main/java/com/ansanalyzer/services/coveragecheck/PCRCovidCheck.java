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
		String aux = "";
		String result= new String();
		
		if (sintoms.isEmpty()) {
			
			 result ="O paciente não preenche os critérios "
					+ "da diretriz de utilização da ANS.\n"
					+ "Negar exame ou solicitar novo relatório médico.";
			 
		}
		else if (age<12) {
			
			for (String sint : sintoms) {
				
				if (criteriosMSPed.contains(sint.toLowerCase())) {
					score+=1;
					aux+= sint+"-";
				}
			}
			if (score >=2) {
				result ="O Exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
						+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
						+ "Relatório médico, ele apresenta sintomas ("+aux+") que o classificam como"
						+ "caso suspeito";
			
			}
			else
				result="O paciente não preenche os critérios "
						+ "da diretriz de utilização da ANS.\n"
						+ "Não foram apresentados sintomas compativeis com um caso suspeito de Covid.\n"
						+ "Negar exame ou solicitar novo relatório médico.";
				
		}
		else if (age>=60) {
			
			for (String sint : sintoms) {
				
				if (criteriosMSIdoso.contains(sint.toLowerCase())) {
					score+=1;
					aux+= sint+"-";
				}
			}
			if (score >=2) {
				result="O Exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
						+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
						+ "Relatório médico, ele apresenta sintomas ("+aux+") que o classificam como"
						+ "caso suspeito";
			}
			else
				result="O paciente não preenche os critérios "
						+ "da diretriz de utilização da ANS.\n"
						+ "Negar exame ou solicitar novo relatório médico.";
			
		} else {
					
		for (String sint : sintoms) {
			
			if (criteriosMSAdulto.contains(sint.toLowerCase())) {
				score+=1;
				aux+= sint+"-";
			
			}
		}
		if (score >=2) {
			result="O Exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
					+ "os critérios da Diretriz de Utilização da ANS.\nConforme "
					+ "Relatório médico, ele apresenta sintomas ("+aux+") que o classificam como"
					+ "caso suspeito";
		}
		else
			{result="O paciente não preenche os critérios "
					+ "da diretriz de utilização da ANS.\n"
					+ "Negar exame ou solicitar novo relatório médico.";}
		
		}
		
		return result;
	}
	
	
	

}
