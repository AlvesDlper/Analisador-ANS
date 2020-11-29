package com.ansanalyzer.resources;

import java.util.List;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

/*
 * Classe que faz a tradução de texto.
 */
public class DocTranslater {

	public DocTranslater() {
	}

	
	public static List<String> doTraduction(List<String> sentences, String sLang, String tLang,AWSCredentialsProviderChain credentials) {

		String region = "us-east-2";

		String sourceLang = sLang;
		String targetLang = tLang;
		int i = 0;
		String aux;

		

		AmazonTranslate translate = AmazonTranslateClient.builder().withCredentials(credentials).withRegion(region).build();

		for (String sentence : sentences) {

			TranslateTextRequest request = new TranslateTextRequest().withText(sentence)
					.withSourceLanguageCode(sourceLang)
					.withTargetLanguageCode(targetLang);
			TranslateTextResult result = translate.translateText(request);
			aux = result.getTranslatedText();
			sentences.set(i, aux);
			i++;
		}

		return sentences;

	}

}
