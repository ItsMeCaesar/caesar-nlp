package com.hicaesar.nlp.validation;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import java.util.Collection;

public final class CommonValidator {

    private static final Logger LOG = Logger.getLogger(CommonValidator.class);

    /**
     * Private Constructor
     */
    private CommonValidator() {
        super();
    }

    /**
     * Validates if a field is null or empty
     *
     * @param name Field name
     * @param attribute Field to be validated
     * @throws CaesarException
     */
    public static void validateRequired(final String name, final String attribute) throws CaesarException {

        methodLog(LOG, "validateRequired", "name [" + name + "]", "attribute [" + attribute + "]");

        if (attribute == null || attribute.length() == 0) {
            throw CaesarException.exception(Status.BAD_REQUEST, "Parameter '" + name + "' is required");
        }
    }

    /**
     * Validates if a field is null or empty
     *
     * @param name Field name
     * @param attribute Field to be validated
     * @throws CaesarException
     */
    public static void validateRequired(final String name, final Object attribute) throws CaesarException {

        methodLog(LOG, "validateRequired", "name [" + name + "]", "attribute [" + attribute + "]");

        if (attribute == null) {
            throw CaesarException.exception(Status.BAD_REQUEST, "Parameter '" + name + "' is required");
        }
    }

    /**
     * Validates if a collection is null or empty
     *
     * @param name Field name
     * @param attribute Field to be validated
     * @throws CaesarException
     */
    public static void validateRequired(final String name, final Collection<?> attribute) throws CaesarException {

        methodLog(LOG, "validateRequired", "name [" + name + "]", "attribute [" + attribute + "]");

        if (attribute == null || attribute.isEmpty()) {
            throw CaesarException.exception(Status.BAD_REQUEST, "Parameter '" + name + "' is required");
        }
    }

    /**
     * Validates if a field is equals to zero
     *
     * @param name Field name
     * @param attribute Field to be validated
     * @throws CaesarException
     */
    public static void validateRequired(final String name, final long attribute) throws CaesarException {

        methodLog(LOG, "validateRequired", "name [" + name + "]", "attribute [" + attribute + "]");

        if (attribute == 0) {
            throw CaesarException.exception(Status.BAD_REQUEST, "Parameter '" + name + "' is required");
        }
    }

    /**
     * Validates if a field is equals to zero
     *
     * @param name Field name
     * @param attribute Field to be validated
     * @throws CaesarException
     */
    public static void validateRequired(final String name, final int attribute) throws CaesarException {

        methodLog(LOG, "validateRequired", "name [" + name + "]", "attribute [" + attribute + "]");

        if (attribute == 0) {
            throw CaesarException.exception(Status.BAD_REQUEST, "Parameter '" + name + "' is required");
        }
    }

}
