package com.hicaesar.nlp.rest;

import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.log.CaesarLog;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.validation.DomainValidator;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.StatusVO;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
@Path("domain")
public final class DomainREST {

    private static final Logger LOG = Logger.getLogger(DomainREST.class);

    private final DomainValidator validator = new DomainValidator();

    @Context
    private HttpServletRequest req;

    /**
     * Save a new domain
     *
     * @param vo
     * @return
     * @throws CaesarException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DomainVO save(final DomainVO vo) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "save", req.getRemoteAddr(), "", param(Constants.VO, vo));

        try {

            return validator.save(vo);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * Retrieve a list of domains
     *
     * @return
     * @throws CaesarException
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DomainVO> list() throws CaesarException {

        methodLog(LOG, "list");

        try {

            return validator.list();

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * Delete a domain
     *
     * @param domainID
     * @return
     * @throws CaesarException
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusVO delete(@PathParam("id") final String domainID) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "delete", req.getRemoteAddr(), "", param("domainID", domainID));

        try {

            validator.delete(domainID);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
        return StatusVO.ok();
    }

}
