package com.hicaesar.nlp.rest;

import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.log.CaesarLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.validation.EntityTypeValidator;
import com.hicaesar.nlp.vo.EntityTypeVO;
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
@Path("entitytype")
public final class EntityTypeREST {

    private static final Logger LOG = Logger.getLogger(EntityTypeREST.class);

    private final EntityTypeValidator validator = new EntityTypeValidator();

    @Context
    private HttpServletRequest req;

    /**
     * Save a new entity type
     *
     * @param vo Value Object to be saved
     * @return Persisted value object
     * @throws CaesarException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntityTypeVO save(final EntityTypeVO vo) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "save", req.getRemoteAddr(), "", param(Constants.VO, vo));

        try {

            return validator.save(vo);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * List all entity types
     *
     * @return Persisted entity types
     * @throws CaesarException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntityTypeVO> list() throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "list", req.getRemoteAddr(), "", "");

        try {

            return validator.list();

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }
    
    
    /**
     * Delete an entity
     *
     * @param typeID
     * @return
     * @throws CaesarException
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusVO delete(@PathParam("id") final String typeID) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "delete", req.getRemoteAddr(), "", param("typeID", typeID));

        try {

            validator.delete(typeID);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
        return StatusVO.ok();
    }

}
