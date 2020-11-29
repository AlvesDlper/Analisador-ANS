package com.ansanalyzer.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.ansanalyzer.entities.MedicalOrder;
import com.ansanalyzer.entities.Patient;
import com.ansanalyzer.resources.AWSCredentialsCon;
import com.ansanalyzer.resources.DocTranslater;
import com.ansanalyzer.resources.MedicalTextAnalyzer;
import com.ansanalyzer.services.coveragecheck.PCRCovidCheck;
import com.ansanalyzer.utils.DocumentReader;

/**
 * 
 * @author Arthur Alves
 *
 */

@WebServlet(name = "analisar", urlPatterns = {"/analisar"})
@MultipartConfig
public class ServletCoverageCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	 // Servlet implementado para realizar o processo de an�lise	 
	private static final String UPLOAD_DIR = "uploadedRMs";

	protected static String fileNameExtract(Part part) {
		String nomeFile[]= (part.getHeader("content-disposition").split(";"));
		String nomeArquivo= nomeFile[nomeFile.length-1];
		
		return Paths.get(nomeArquivo.substring(nomeArquivo.indexOf("=")+2, nomeArquivo.length()-1))
				.getFileName().toString();
	}
	
	//M�todo respons�vel por instanciar as classes e invocar o servi�o de an�lise de cobertura 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		try (PrintWriter out = response.getWriter()) {
			
			// Busca as credencias da AWS configuradas previamente no sistema
			AWSCredentialsProviderChain con = AWSCredentialsCon.getCredentials(); 
			
			
			char a[] = request.getParameter("patientSex").toCharArray();						
			Patient paciente = new Patient(request.getParameter("patientName"),
					Integer.parseInt(request.getParameter("patientAge")), a[0]);

			/* 	Define um diret�rio para upload do arquivo, neste caso para
			 * 	a pasta "uploadedRMs" criada no diret�rio do pr�prio projeto.
			 * O m�todo reques.getServletContext().getRealPath � repons�vel por obter o diret�rio.
			 *  Caso a pasta n�o exista, ela ser� criada.	
			 */
			String applicationPath = request.getServletContext().getRealPath("");
			String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

			
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
				System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
			}
			
			// Renomeia e salva o arquivo no diret�rio de uploads
			Part filePart = request.getPart("patientRM");
			String fileName = fileNameExtract(filePart);
			
			filePart.write(uploadFilePath + File.separator + fileName);
			
			//Instancia um pedido m�dico
			File docMedico = new File(uploadFilePath + File.separator + fileName);

			MedicalOrder medicalOrder = new MedicalOrder(paciente, docMedico);
			
			/* Separa o relat�rio em frases, Traduz para o ingl�s
			 * Extrai os sintomas descritos no relat�rio e Traduz para o portugu�s		 
			 */
			
			List<String> auxList = DocumentReader.txtSentences(medicalOrder.getFile());
			auxList = DocTranslater.doTraduction(auxList,"pt", "en", con);

			MedicalTextAnalyzer rm = new MedicalTextAnalyzer();				
			List<String> sintomas = new ArrayList<String>(rm.symptomExtract(auxList.toString(), con));
			
			sintomas = DocTranslater.doTraduction(sintomas, "en", "pt", con);
			
			// Instancia um analisador de cobertura e realiza an�lise
			PCRCovidCheck analisador = new PCRCovidCheck();
			medicalOrder.setResult(analisador.coverageCheck(sintomas,paciente.getIdade()));
			
			request.setAttribute("result", medicalOrder.getResult());
			request.setAttribute("sintomas",sintomas);
			RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/resposta.jsp");
			rd.forward(request, response);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

}
