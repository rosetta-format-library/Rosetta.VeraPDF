package com.exlibris.rosetta.repository.plugin.mdExtractor.pdf;

public class maintest {

	
    public static void main(String[] args) {
    	try {
			PDFHULMDExtractorPlugin f = new PDFHULMDExtractorPlugin();
			f.extract("TWG test suite A001-pdfa1-fail-a.pdf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
