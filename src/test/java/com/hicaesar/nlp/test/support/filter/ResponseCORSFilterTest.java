package com.hicaesar.nlp.test.support.filter;


import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.filter.ResponseCORSFilter;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author samuelwaskow
 */
@RunWith(MockitoJUnitRunner.class)
public final class ResponseCORSFilterTest {

    @InjectMocks
    private ResponseCORSFilter filter;

    @Mock
    private ContainerRequestContext request;

    @Mock
    private ContainerResponseContext response;

    @Test
    public void testFilter() throws CaesarException, IOException {

        final MultivaluedMap headers = new MultivaluedHashMap();
        headers.putSingle("HashID", "userhashkey");
        headers.putSingle("HashSecret", "userhashksecret");
        Mockito.when(response.getHeaders()).thenReturn(headers);

        filter.filter(request, response);

        Assert.assertNotNull(filter.getResponse());
        Assert.assertNotNull(filter.getResponse().getHeaders());
    }

    @Test
    public void testFilterBadResponse() throws CaesarException, IOException {

        final MultivaluedMap headers = new MultivaluedHashMap();
        headers.putSingle("HashID", "userhashkey");
        headers.putSingle("HashSecret", "userhashksecret");
        Mockito.when(response.getHeaders()).thenReturn(headers);

        Mockito.when(response.getMediaType()).thenReturn(null);

        filter.filter(request, response);

        Assert.assertNotNull(filter.getResponse());
        Assert.assertNotNull(filter.getResponse().getHeaders());

    }

    @Test
    public void testFilterCharset() throws CaesarException, IOException {

        final MultivaluedMap headers = new MultivaluedHashMap();
        headers.putSingle("HashID", "userhashkey");
        headers.putSingle("HashSecret", "userhashksecret");
        Mockito.when(response.getHeaders()).thenReturn(headers);

        Mockito.when(response.getMediaType()).thenReturn(new MediaType(MediaType.APPLICATION_JSON, "", "utf-8"));
        filter.filter(request, response);
        Assert.assertNotNull(filter.getResponse());
        Assert.assertNotNull(filter.getResponse().getHeaders());
        
        Mockito.when(response.getMediaType()).thenReturn(new MediaType(MediaType.APPLICATION_JSON, ""));
        filter.filter(request, response);
        Assert.assertNotNull(filter.getResponse());
        Assert.assertNotNull(filter.getResponse().getHeaders());
    }

}
