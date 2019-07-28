package org.myphotos.web.util;

import org.myphotos.infra.exception.business.ValidationException;

public class UrlExtractorUtils {

	/**
	 * Extracts path variable value from specified {@code url}
	 * 
	 * @param url    URL to extract path variable value from
	 * @param prefix prefix part of the URL that should be ommited
	 * @param suffix suffix part of the URL that should be ommited
	 * @return path variable value obtained from specified {@code url}
	 * @throws ValidationException if can not extract path variable
	 */
	public static String getPathVariableValue(String url, String prefix, String suffix) {
		if (isLengthCorrect(url, prefix, suffix) && isPrefixAndSuffixIncluded(url, prefix, suffix)) {
			return url.substring(prefix.length(), url.length() - suffix.length());
		} else {
			throw new ValidationException(String
					.format("Can't extract path variable from url=% with prefix=% and suffix=%s", url, prefix, suffix));
		}
	}

	private static boolean isPrefixAndSuffixIncluded(String url, String prefix, String suffix) {
		return url.startsWith(prefix) && url.endsWith(suffix);
	}

	private static boolean isLengthCorrect(String url, String prefix, String suffix) {
		return url.length() >= prefix.length() + suffix.length();
	}

	private UrlExtractorUtils() {
	}
}
