package org.myphotos.converter;

import javax.enterprise.inject.Vetoed;

/**
 * Converts non ASCII text into ASCII.
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface TranslitConverter {

	String translit(String text);
}
