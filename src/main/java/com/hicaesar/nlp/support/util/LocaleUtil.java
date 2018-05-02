package com.hicaesar.nlp.support.util;

import java.util.Locale;

/**
 *
 * @author samuelwaskow
 */
public final class LocaleUtil {

    /**
     * Transform a string into a locale object
     *
     * @param text
     * @return
     */
    public static Locale getLocale(final String text) {

        final String[] loc = text.split("_");

        return new Locale(loc[0], loc[1]);
    }

}
