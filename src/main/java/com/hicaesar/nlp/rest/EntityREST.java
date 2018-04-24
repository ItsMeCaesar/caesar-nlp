package com.hicaesar.nlp.rest;

import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.log.CaesarLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.validation.EntityValidator;
import com.hicaesar.nlp.vo.EntityVO;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
@Path("entity")
public final class EntityREST {

    private static final Logger LOG = Logger.getLogger(EntityREST.class);

    private final EntityValidator validator = new EntityValidator();

    @Context
    private HttpServletRequest req;

    /**
     * Save a new entity
     *
     * @param vo Value Object to be saved
     * @return Persisted value object
     * @throws CaesarException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntityVO save(final EntityVO vo) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "save", req.getRemoteAddr(), "", param(Constants.VO, vo));

        try {

            return validator.save(vo);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * Save new entities
     *
     * @param vos
     * @return
     * @throws CaesarException
     */
    @POST
    @Path("list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusVO saveList(final List<EntityVO> vos) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "saveList", req.getRemoteAddr(), "", param("vos", vos));

        try {

            validator.saveList(vos);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
        return StatusVO.ok();
    }

    /**
     * List all entities
     *
     * @param locale
     * @param text
     * @return Persisted entities
     * @throws CaesarException
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntityVO> list(@QueryParam("locale") final String locale, @QueryParam("text") final String text) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "list", req.getRemoteAddr(), "", param("locale", locale), param(text, text));

        try {

            return validator.list(locale, text);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * Retrieve an entity
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EntityVO get(@QueryParam("locale") final String locale, @QueryParam("text") final String text) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "get", req.getRemoteAddr(), "", param("locale", locale), param(text, text));

        try {

            return validator.get(locale, text);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

    /**
     * Delete an entity
     *
     * @param id
     * @return
     * @throws CaesarException
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusVO delete(@PathParam("id") final String id) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "list", req.getRemoteAddr(), "", param("id", id));

        try {

            validator.delete(id);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
        return StatusVO.ok();
    }

}
