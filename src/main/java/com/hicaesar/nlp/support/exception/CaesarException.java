package com.hicaesar.nlp.support.exception;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.StatusVO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.WebFault;
import org.apache.log4j.Logger;

/**
 * Base class to exceptions
 *
 * @author Samuel Justo Waskow
 */
@WebFault
public class CaesarException extends Exception {

    private static final long serialVersionUID = 3384812054076266567L;

    private static final Logger LOG = Logger.getLogger(CaesarException.class);

    private final Status status;
    private final String msg;

    /**
     * Constructor
     *
     * @param status
     * @param msg
     */
    public CaesarException(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * Creates a WebApplicationException from a BloaseException use: throw
     * BloaseException.webException(e);
     *
     * @param e
     * @return
     */
    public static WebApplicationException webException(final CaesarException e) {

        methodLog(LOG, "webException", param("e", e));

        final Status status = e.getStatus() == null ? Status.INTERNAL_SERVER_ERROR : e.getStatus();

        if (status.equals(Status.INTERNAL_SERVER_ERROR)) {
            LOG.error("webException - status [" + status + "]", e);
        }

        final StatusVO statusVO = StatusVO.error(e.getMsg());
        return new WebApplicationException(Response.status(status).entity(statusVO).type(MediaType.APPLICATION_JSON).build());
    }

    /**
     * Creates a CaesarException
     *
     * @param status
     * @param msg
     * @return
     */
    public static CaesarException exception(final Status status, final String msg) {

        methodLog(LOG, "exception", param("status", status), param("msg", msg));

        return new CaesarException(status, msg);
    }

    /**
     * Creates a CaesarException logging the exception
     *
     * @param ex
     * @param status
     * @param msg
     * @return
     */
    public static CaesarException exception(final Exception ex, final Status status, final String msg) {

        methodLog(LOG, "exception", param("ex", ex), param("status", status), param("msg", msg));

        LOG.error("exception", ex);

        return exception(status, msg);
    }

    @Override
    public String toString() {
        return "{status=" + status + ", msg=" + msg + '}';
    }

    /* Gets And Sets */
    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
