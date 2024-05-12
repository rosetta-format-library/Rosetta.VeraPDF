package com.exlibris.rosetta.repository.plugin.formatValidator.pdf;

import com.exlibris.dps.sdk.techmd.FormatValidationPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.verapdf.ReleaseDetails;
import org.verapdf.core.EncryptedPdfException;
import org.verapdf.core.ModelParsingException;
import org.verapdf.core.ValidationException;
import org.verapdf.gf.foundry.VeraGreenfieldFoundryProvider;
import org.verapdf.pdfa.Foundries;
import org.verapdf.pdfa.PDFAParser;
import org.verapdf.pdfa.PDFAValidator;
import org.verapdf.pdfa.results.TestAssertion;
import org.verapdf.pdfa.results.ValidationResult;

public class VeraPDFFormatValidatorPlugin implements FormatValidationPlugin {
	private static final String PLUGIN_VERSION_INIT_PARAM = "PLUGIN_VERSION_INIT_PARAM";

	private String pluginVersion = null;

	private ValidationResult result;

	private String agentName;

	private List<String> errorMessages = new ArrayList<>();

	@Override
	public boolean validateFormat(String fileName) {
		try {
			return validatePdf(fileName);
		} catch (ModelParsingException | EncryptedPdfException | FileNotFoundException | ValidationException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean validatePdf(String fileName) throws ModelParsingException,
			EncryptedPdfException, FileNotFoundException, ValidationException {
		VeraGreenfieldFoundryProvider.initialise();
		PDFAParser parser = Foundries.defaultInstance().createParser(new FileInputStream(fileName));
		PDFAValidator validator = Foundries.defaultInstance().createValidator(parser.getFlavour(), false);
		ValidationResult result = validator.validate(parser);
		if (result != null) {
			this.result = result;
			Map<String,Long> errorMessages = result.getTestAssertions().stream()
                    .map(TestAssertion::getMessage)
                    .collect(Collectors.groupingBy(message -> message,LinkedHashMap::new, Collectors.counting()));
			errorMessages.forEach((message, count) -> addError(message + " (" + count + " occurrences)"));
		}
		StringBuffer buffer = new StringBuffer();
		ReleaseDetails.getDetails().stream()
				.forEach(detail -> buffer.append(StringUtils.capitalize(detail
						.getId()) + " Version " + detail.getVersion() + " "));
		this.agentName = buffer.toString().trim();
		return result.isCompliant();
	}

	@Override
	public String getProfile() {
		return this.result.getProfileDetails().getName();
	}

	@Override
	public boolean isValid() {
		return this.result.isCompliant();
	}

	public String getAgentName() {
		return this.agentName;
	}

	@Override
	public String getAgent() {
		return "veraPDF  " + this.agentName + ", Plugin Version "
				+ this.pluginVersion;
	}

	public void initParams(Map<String, String> initParams) {
		this.pluginVersion = initParams.get(PLUGIN_VERSION_INIT_PARAM);
	}

	@Override
	public List<String> getErrors() {
		return this.errorMessages;
	}

	@Override
	public boolean isWellFormed() {
		return this.result.isCompliant();
	}

	@Override
	public String getValidationDetails() {
		return this.result.getValidationProfile().getDetails().getDescription();
	}

	protected void addError(String message) {
		if (this.errorMessages == null)
			this.errorMessages = new ArrayList<>();
		this.errorMessages.add(message);
	}
}
