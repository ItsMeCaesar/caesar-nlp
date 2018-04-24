package com.hicaesar.nlp.test.rest;

import com.hicaesar.nlp.support.exception.CaesarExceptionMapper;
import com.hicaesar.nlp.support.filter.ResponseCORSFilter;
import com.hicaesar.nlp.rest.CaesarNLPApplication;
import com.hicaesar.nlp.rest.DomainREST;
import com.hicaesar.nlp.rest.EntityREST;
import com.hicaesar.nlp.rest.EntityTypeREST;

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
        Assert.assertEquals(5, endpoints.size());

        Assert.assertTrue(endpoints.contains(ResponseCORSFilter.class));
        Assert.assertTrue(endpoints.contains(CaesarExceptionMapper.class));

        Assert.assertTrue(endpoints.contains(DomainREST.class));
        Assert.assertTrue(endpoints.contains(EntityREST.class));
        Assert.assertTrue(endpoints.contains(EntityTypeREST.class));

    }

}
