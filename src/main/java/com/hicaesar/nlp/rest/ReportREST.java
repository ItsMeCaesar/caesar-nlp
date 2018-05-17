package com.hicaesar.nlp.rest;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.log.CaesarLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.validation.ReportValidator;
import com.hicaesar.nlp.vo.PagedVO;
import com.hicaesar.nlp.vo.ReportVO;
import com.hicaesar.nlp.vo.StatusVO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
@Path("report")
public final class ReportREST {

    private static final Logger LOG = Logger.getLogger(ReportREST.class);

    private final ReportValidator validator = new ReportValidator();

    @Context
    private HttpServletRequest req;

    /**
     * Set a specific report as 'viewed'
     *
     * @param reportID
     * @return
     * @throws CaesarException
     */
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusVO view(@PathParam("id") final String reportID) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "view", req.getRemoteAddr(), "", param("reportID", reportID));

        try {

            validator.view(reportID);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
        return StatusVO.ok();
    }

    /**
     * Retrieve a list of generated reports
     *
     * @param page
     * @param pageSize
     * @return
     * @throws CaesarException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PagedVO<ReportVO> list(@QueryParam("page") final int page, @QueryParam("page_size") final int pageSize) throws CaesarException {

        CaesarLog.getInstance().logWSEntering(LOG, "list", req.getRemoteAddr(), "", param("page", page), param("pageSize", pageSize));

        try {

            return validator.list(page, pageSize);

        } catch (final CaesarException e) {
            throw CaesarException.webException(e);
        }
    }

}
