package com.hicaesar.nlp.test.support.util;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.util.StringUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class StringUtilTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<StringUtil> constructor = StringUtil.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testCapFirstLetterSentence() throws CaesarException {

        final String actual = " loren ipsun color ";
        final String expected = "Loren Ipsun Color";
        final String processed = StringUtil.capFirstLetterSentence(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.capFirstLetterSentence(null));
        Assert.assertEquals("", StringUtil.capFirstLetterSentence(""));
    }

    @Test
    public void testCapFirstLetter() throws CaesarException {

        final String actual = " loren ipsun color ";
        final String expected = "Loren ipsun color";
        final String processed = StringUtil.capFirstLetter(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.capFirstLetter(null));
        Assert.assertEquals("", StringUtil.capFirstLetter(""));
        Assert.assertEquals("a", StringUtil.capFirstLetter("a"));
    }


    @Test
    public void testSimpler() throws CaesarException {

        final String actual = "Coração";
        final String expected = "coracao";
        final String processed = StringUtil.simpler(actual);

        Assert.assertEquals(expected, processed);
        Assert.assertEquals("", StringUtil.simpler(null));
    }

    @Test
    public void testCoalesceEmpty() throws CaesarException {

        final String[] actual = new String[]{"", null, "Coração", ""};
        final String expected = "Coração";
        final String processed = StringUtil.coalesceEmpty(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertNull(StringUtil.coalesceEmpty((String) null));
        Assert.assertNull(StringUtil.coalesceEmpty(new String[]{}));
    }

    @Test
    public void testIsEmpty() throws CaesarException {

        final String actual1 = "teste";
        final String actual2 = "";
        final String actual3 = null;

        Assert.assertFalse(StringUtil.isEmpty(actual1));
        Assert.assertTrue(StringUtil.isEmpty(actual2));
        Assert.assertTrue(StringUtil.isEmpty(actual3));
    }

    @Test
    public void testIsNotEmpty() throws CaesarException {

        final String actual1 = "teste";
        final String actual2 = "";

        Assert.assertTrue(StringUtil.isNotEmpty(actual1));
        Assert.assertFalse(StringUtil.isNotEmpty(actual2));
    }

    @Test
    public void testEmpty() throws CaesarException {

        final String actual1 = "teste";
        final String actual2 = "";
        final String actual3 = null;

        Assert.assertEquals("teste", StringUtil.empty(actual1));
        Assert.assertEquals("", StringUtil.empty(actual2));
        Assert.assertEquals("", StringUtil.empty(actual3));
    }

    @Test
    public void testOnlyNumbers() throws CaesarException {

        final String actual = "Coração de Leão 1982 ";
        final String expected = "1982";
        final String processed = StringUtil.onlyNumbers(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.onlyNumbers(null));
    }
    
    @Test
    public void testOnlyLetters() throws CaesarException {

        final String actual = "Coração de Leão 1982 ";
        final String expected = "Coração de Leão";
        final String processed = StringUtil.onlyLetters(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.onlyLetters(null));
    }
    
    @Test
    public void testRemoveDiacritics() throws CaesarException {

        final String actual = "Coração de Leão 1982 ";
        final String expected = "Coracao de Leao 1982 ";
        final String processed = StringUtil.removeDiacritics(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.removeDiacritics(null));
    }

    @Test
    public void testIsNumeric() throws CaesarException {

        final String actual1 = "Coração de Leão 1982 ";
        final String actual2 = "1982";

        Assert.assertFalse(StringUtil.isNumeric(actual1));
        Assert.assertTrue(StringUtil.isNumeric(actual2));
    }

    @Test
    public void testOnlyAlphaNumericNumbers() throws CaesarException {

        final String actual = "@(#*&(&*&# Coração de Leão 1982 (&*#@*(#@";
        final String expected = "CoraçãodeLeão1982";
        final String processed = StringUtil.onlyAlphanumerics(actual);

        Assert.assertEquals(expected, processed);

        Assert.assertEquals("", StringUtil.onlyAlphanumerics(null));
    }

}
