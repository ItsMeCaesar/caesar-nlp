package com.hicaesar.nlp.support.exception;

import com.hicaesar.nlp.vo.StatusVO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;


import javax.ws.rs.WebApplicationException;

@Provider
public final class CaesarExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(CaesarExceptionMapper.class);

    /**
     * 
     * @param e
     * @return 
     */
    @Override
    public Response toResponse(final Exception e) {

        LOG.error("toResponse", e);

        if (e instanceof CaesarException) {
            
            final CaesarException be = (CaesarException) e;
            return Response.status(be.getStatus()).entity(be.getMsg()).type(MediaType.APPLICATION_JSON).build();
            
        } else if (e instanceof WebApplicationException) {
           
            final WebApplicationException we = (WebApplicationException) e;
            return we.getResponse();
            
        } else {

            final StatusVO statusVO = StatusVO.error(e.getLocalizedMessage());
            return Response.status(Status.BAD_REQUEST).entity(statusVO).type(MediaType.APPLICATION_JSON).build();
        }
    }
}