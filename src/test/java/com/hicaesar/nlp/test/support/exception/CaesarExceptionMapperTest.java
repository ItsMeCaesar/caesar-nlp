package com.hicaesar.nlp.test.support.exception;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.exception.CaesarExceptionMapper;
import com.hicaesar.nlp.vo.StatusVO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class CaesarExceptionMapperTest {

    @Test
    public void testMapper() {

        final CaesarExceptionMapper mapper = new CaesarExceptionMapper();

        Response response1 = mapper.toResponse(new CaesarException(Response.Status.CREATED, "test msg"));
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response1.getStatus());
        Assert.assertEquals("test msg", response1.getEntity());

        Response response2 = mapper.toResponse(new WebApplicationException("test 2", Response.Status.INTERNAL_SERVER_ERROR));
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
        Assert.assertNull(response2.getEntity());

        Response response3 = mapper.toResponse(new IllegalAccessException("test 3"));
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response3.getStatus());
        Assert.assertEquals(StatusVO.error("test 3").toString(), String.valueOf(response3.getEntity()));

    }

}
