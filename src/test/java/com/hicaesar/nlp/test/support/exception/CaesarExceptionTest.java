package com.hicaesar.nlp.test.support.exception;

import com.hicaesar.nlp.support.exception.CaesarException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class CaesarExceptionTest {

    @Test
    public void testConstructor() {

        CaesarException ex = CaesarException.exception(Status.ACCEPTED, "test");
        Assert.assertEquals(Status.ACCEPTED, ex.getStatus());
        Assert.assertEquals("test", ex.getMsg());
    }

    @Test
    public void testConstructorBloase2() {

        final IllegalAccessException e = new IllegalAccessException("Teste saoi");

        final CaesarException ex = CaesarException.exception(e, Status.ACCEPTED, "test");
        Assert.assertEquals(Status.ACCEPTED, ex.getStatus());
        Assert.assertEquals("test", ex.getMsg());

        Assert.assertNull(ex.getCause());
        Assert.assertNull(ex.getMessage());
    }

    @Test
    public void testConstructorWeb() {

        final IllegalAccessException e = new IllegalAccessException("Teste saoi");


        final CaesarException ex = CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "test");
        final WebApplicationException webx = CaesarException.webException(ex);

        final Response response = webx.getResponse();

        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        final CaesarException ie = CaesarException.exception(Status.INTERNAL_SERVER_ERROR, "");
        final WebApplicationException internalError = CaesarException.webException(ie);
        Assert.assertEquals(ie.getStatus().getStatusCode(), internalError.getResponse().getStatus());

        final WebApplicationException nullError = CaesarException.webException(CaesarException.exception(null, ""));
        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), nullError.getResponse().getStatus());

        final WebApplicationException badgateway = CaesarException.webException(CaesarException.exception(Status.BAD_GATEWAY, ""));
        Assert.assertEquals(Status.BAD_GATEWAY.getStatusCode(), badgateway.getResponse().getStatus());

    }

}
