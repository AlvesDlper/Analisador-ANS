package com.ansanalyzer.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentReader {

	
	public DocumentReader() {}
	
	public static List<String> txtSentences (File file) throws IOException{
		
		
		
		try (PDDocument doc= PDDocument.load(file)) {
			
			PDFTextStripper tStripper = new PDFTextStripper();
			tStripper.setLineSeparator(" ");
			String fileText = tStripper.getText(doc);

			SentenceSegmenter sentenceSegmenter = new SentenceSegmenter();
	        List<String> sentences = new ArrayList<>();
			sentences = sentenceSegmenter.segment(fileText, "pt-br");
			
		
			doc.close();
			return sentences;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
}
