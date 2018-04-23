package com.hicaesar.nlp.test.ws;

import com.hicaesar.nlp.support.exception.CaesarExceptionMapper;
import com.hicaesar.nlp.support.filter.ResponseCORSFilter;
import com.hicaesar.nlp.ws.CaesarNLPApplication;
import com.hicaesar.nlp.ws.EntityTypeREST;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class CaesarNLPApplicationTest {

    private final CaesarNLPApplication app = new CaesarNLPApplication();

    @Test
    public void testGetClasses() {

        final Set<Class<?>> endpoints = app.getClasses();
        Assert.assertEquals(3, endpoints.size());

        Assert.assertTrue(endpoints.contains(ResponseCORSFilter.class));
        Assert.assertTrue(endpoints.contains(CaesarExceptionMapper.class));

        Assert.assertTrue(endpoints.contains(EntityTypeREST.class));

    }

}
