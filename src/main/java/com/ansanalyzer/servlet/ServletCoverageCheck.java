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
	
	
	 // Servlet implementado para realizar o processo de análise	 
	private static final String UPLOAD_DIR = "uploadedRMs";

	protected static String fileNameExtract(Part part) {
		String nomeFile[]= (part.getHeader("content-disposition").split(";"));
		String nomeArquivo= nomeFile[nomeFile.length-1];
		
		return Paths.get(nomeArquivo.substring(nomeArquivo.indexOf("=")+2, nomeArquivo.length()-1))
				.getFileName().toString();
	}
	
	//Método responsável por instanciar as classes e invocar o serviço de análise de cobertura 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		try (PrintWriter out = response.getWriter()) {
			
			// Busca as credencias da AWS configuradas previamente no sistema
			AWSCredentialsProviderChain con = AWSCredentialsCon.getCredentials(); 
			
			
			char a[] = request.getParameter("patientSex").toCharArray();						
			Patient paciente = new Patient(request.getParameter("patientName"),
					Integer.parseInt(request.getParameter("patientAge")), a[0]);

			/* 	Define um diretório para upload do arquivo, neste caso para
			 * 	a pasta "uploadedRMs" criada no diretório do próprio projeto.
			 * O método reques.getServletContext().getRealPath é reponsável por obter o diretório.
			 *  Caso a pasta não exista, ela será criada.	
			 */
			String applicationPath = request.getServletContext().getRealPath("");
			String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

			
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
				System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
			}
			
			// Renomeia e salva o arquivo no diretório de uploads
			Part filePart = request.getPart("patientRM");
			String fileName = fileNameExtract(filePart);
			
			filePart.write(uploadFilePath + File.separator + fileName);
			
			//Instancia um pedido médico
			File docMedico = new File(uploadFilePath + File.separator + fileName);

			MedicalOrder medicalOrder = new MedicalOrder(paciente, docMedico);
			
			/* Separa o relatório em frases, Traduz para o inglês
			 * Extrai os sintomas descritos no relatório e Traduz para o português		 
			 */
			
			List<String> auxList = DocumentReader.txtSentences(medicalOrder.getFile());
			auxList = DocTranslater.doTraduction(auxList,"pt", "en", con);

			MedicalTextAnalyzer rm = new MedicalTextAnalyzer();				
			List<String> sintomas = new ArrayList<String>(rm.symptomExtract(auxList.toString(), con));
			
			sintomas = DocTranslater.doTraduction(sintomas, "en", "pt", con);
			
			// Instancia um analisador de cobertura e realiza análise
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
