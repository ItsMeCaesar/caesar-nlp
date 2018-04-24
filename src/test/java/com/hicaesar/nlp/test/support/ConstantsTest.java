package com.hicaesar.nlp.test.support;

import com.hicaesar.nlp.support.Constants;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class ConstantsTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        org.junit.Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test() {
        Assert.assertEquals("vo", Constants.VO);
        Assert.assertEquals("UTF-8", Constants.ENCODING);
    }

}
