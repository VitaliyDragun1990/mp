package org.myphotos.converter;

import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.PropertiesSource;

import net.sf.junidecode.Junidecode;

@ApplicationScoped
class JunidecodeTranslitConverter implements TranslitConverter {
	
	private Properties customTranslitDictionary;
	
	@Inject
	void setCustomTranslitDictionary(@PropertiesSource("classpath:translit.properties") Properties properties) {
		this.customTranslitDictionary = extendTranslitProperties(properties);
	}

	@Override
	public String translit(String text) {
		String result = customTranslit(text);
		return Junidecode.unidecode(result);
	}
	
	private String customTranslit(String text) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			String ch = String.valueOf(text.charAt(i));
			result.append(customTranslitDictionary.getProperty(ch, ch));
		}
		return result.toString();
	}
	
	private Properties extendTranslitProperties(final Properties props) {
		Properties result = (Properties) props.clone();
		for (Map.Entry<Object, Object> entry : props.entrySet()) {
			addUpperCaseVariant(result, String.valueOf(entry.getKey()),  String.valueOf(entry.getValue()));
		}
		return result;
	}

	private void addUpperCaseVariant(Properties result, String key, String value) {
		if (value.length() <= 1) {
			result.setProperty(key.toUpperCase(), value.toUpperCase());
		} else {
			result.setProperty(key.toUpperCase(), Character.toUpperCase(value.charAt(0)) + value.substring(1));
		}
	}

}
