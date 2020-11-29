package com.ansanalyzer.services.coveragecheck;

import java.util.List;

/*	Classe respons�vel por efetuar a an�lise de cobertura
 * 	Para o exame PCR-COVID, conforme as normas da ANS
 * 
 */
public class PCRCovidCheck {

		private final List<String> criteriosMSAdulto = 
				List.of("febre", "febril", "calafrios",
						 "dor de garganta","dor de cabe�a","cefaleia",
						 "tosse","tossindo","coriza","dist�rbios olfativos",
						 "dist�rbios gustativos","perda do paladar","perda do olfato",
						 "sem olfato","sem paladar","diarreia");
		private final List<String >criteriosMSPed = 
				List.of("febre", "febril", "calafrios",
						"dor de garganta","dor de cabe�a",
						"cefaleia", "tosse","tossindo", "coriza", "dist�rbios olfativos",
						"dist�rbios gustativos", "perda do paladar", "perda do olfato",
						"sem olfato", "sem paladar", "obstru��o nasal", "congest�o nasal", "diarreia");
		private final List<String >criteriosMSIdoso = 
				List.of("febre", "febril", "calafrios", "dor de garganta",
						"dor de cabe�a","cefaleia", "tosse","tossindo", "coriza", "dist�rbios olfativos",
						"dist�rbios gustativos", "perda do paladar", "perda do olfato", "sem olfato",
						"sem paladar", "diarreia", "sincope", "desmaio", "confus�o mental",
						"sonol�ncia", "irritabilidade", "falta de apetite", "inapet�ncia");
		
			
	public String coverageCheck(List <String> sintoms, int age) {
		
		
		int score=0;
		String aux = "";
		String result= new String();
		
		if (sintoms.isEmpty()) {
			
			 result ="O paciente n�o preenche os crit�rios "
					+ "da diretriz de utiliza��o da ANS.\n"
					+ "Negar exame ou solicitar novo relat�rio m�dico.";
			 
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
						+ "os crit�rios da Diretriz de Utiliza��o da ANS.\nConforme "
						+ "Relat�rio m�dico, ele apresenta sintomas ("+aux+") que o classificam como"
						+ "caso suspeito";
			
			}
			else
				result="O paciente n�o preenche os crit�rios "
						+ "da diretriz de utiliza��o da ANS.\n"
						+ "N�o foram apresentados sintomas compativeis com um caso suspeito de Covid.\n"
						+ "Negar exame ou solicitar novo relat�rio m�dico.";
				
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
						+ "os crit�rios da Diretriz de Utiliza��o da ANS.\nConforme "
						+ "Relat�rio m�dico, ele apresenta sintomas ("+aux+") que o classificam como"
						+ "caso suspeito";
			}
			else
				result="O paciente n�o preenche os crit�rios "
						+ "da diretriz de utiliza��o da ANS.\n"
						+ "Negar exame ou solicitar novo relat�rio m�dico.";
			
		} else {
					
		for (String sint : sintoms) {
			
			if (criteriosMSAdulto.contains(sint.toLowerCase())) {
				score+=1;
				aux+= sint+"-";
			
			}
		}
		if (score >=2) {
			result="O Exame deve ser autorizado.\nO paciente de "+age+" anos, preenche "
					+ "os crit�rios da Diretriz de Utiliza��o da ANS.\nConforme "
					+ "Relat�rio m�dico, ele apresenta sintomas ("+aux+") que o classificam como"
					+ "caso suspeito";
		}
		else
			{result="O paciente n�o preenche os crit�rios "
					+ "da diretriz de utiliza��o da ANS.\n"
					+ "Negar exame ou solicitar novo relat�rio m�dico.";}
		
		}
		
		return result;
	}
	
	
	

}
