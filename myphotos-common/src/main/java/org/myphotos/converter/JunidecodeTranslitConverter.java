package org.myphotos.converter;

import javax.enterprise.context.ApplicationScoped;

import net.sf.junidecode.Junidecode;

@ApplicationScoped
class JunidecodeTranslitConverter implements TranslitConverter {

	@Override
	public String translit(String text) {
		return Junidecode.unidecode(text);
	}

}
