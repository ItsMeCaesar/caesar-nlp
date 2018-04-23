package com.hicaesar.nlp.support.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Bloase Team
 */
@Provider
public class ResponseCORSFilter implements ContainerResponseFilter {

    private ContainerResponseContext response;

    /**
     * Performs the CORS filtering
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");

        final MediaType type = response.getMediaType();
        if (type != null) {
            String contentType = type.toString();
            if (!contentType.contains("charset")) {
                contentType = contentType + ";charset=utf-8";
                response.getHeaders().putSingle("Content-Type", contentType);
            }
        }
        this.response = response;
    }

    public ContainerResponseContext getResponse() {
        return response;
    }
    
}
