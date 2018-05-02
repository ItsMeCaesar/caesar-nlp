package com.hicaesar.nlp.test.support.util;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.util.LocaleUtil;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class LocaleUtilTest {

    @Test
    public void testPtBR() throws CaesarException {

        final String text = "pt_BR";

        final Locale locale = LocaleUtil.getLocale(text);

        Assert.assertEquals("BR", locale.getCountry());
        Assert.assertEquals("pt", locale.getLanguage());

    }

    @Test
    public void testEnUS() throws CaesarException {

        final String text = "en_US";

        final Locale locale = LocaleUtil.getLocale(text);

        Assert.assertEquals("US", locale.getCountry());
        Assert.assertEquals("en", locale.getLanguage());

    }

}
