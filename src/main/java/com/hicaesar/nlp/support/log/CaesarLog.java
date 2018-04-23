package com.hicaesar.nlp.support.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Base log class - SINGLETON
 *
 * @author Samuel Justo Waskow
 *
 */
public final class CaesarLog {

    private final StringBuilder str;
    private static CaesarLog instance;

    private CaesarLog() {
        str = new StringBuilder();
    }

    public static CaesarLog getInstance() {
        if (instance == null) {
            instance = new CaesarLog();
        }
        return instance;
    }

    /**
     * Log method entering
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param params
     */
    public void logMethodEntering(final Logger log, final String methodName, final String... params) {
        if (log.isTraceEnabled()) {
            str.setLength(0);
            getParamsString(params);
            str.append("Entering.");
            logTrace(log, methodName, str.toString());
        }
    }

    /**
     * Log WS method entering
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param requestIP
     * @param requestUser
     */
    public void logWSEntering(final Logger log, final String methodName, final String requestIP, final String requestUser) {
        logWSEntering(log, methodName, requestIP, requestUser, "");
    }

    /**
     * Log WS method entering
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param requestIP
     * @param requestUser
     * @param params
     */
    public void logWSEntering(final Logger log, final String methodName, final String requestIP, final String requestUser, final String... params) {
        str.setLength(0);
        if (requestUser == null) {
            str.append("call [").append(requestIP).append("] ");
        } else {
            str.append("call [").append(requestIP).append(", ").append(requestUser).append("] ");
        }
        getParamsString(params);
        str.append("Entering.");
        logInfo(log, methodName, str.toString());
    }

    /**
     * Log the total time execution time for a method
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param start
     */
    public void logMethodExit(final Logger log, final String methodName, final long start) {
        logInfo(log, methodName, "Execution time: " + (System.currentTimeMillis() - start) / 1000 + " seconds");
    }

    /**
     * TRACE log
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param message Message to be logged
     */
    public void logTrace(final Logger log, final String methodName, final String message) {
        if (log.isTraceEnabled()) {
            log.trace(methodName + " - " + message);
        }
    }

    /**
     * ALL log
     *
     * @param log
     * @param methodName
     * @param message
     */
    public void logAll(final Logger log, final String methodName, final String message) {
        if (log.isEnabledFor(Level.ALL)) {
            log.log(Level.ALL, methodName + " - " + message);
        }
    }

    /**
     * DEBUG log
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param message Message to be logged
     */
    public void logDebug(final Logger log, final String methodName, final String message) {
        if (log.isDebugEnabled()) {
            log.debug(methodName + " - " + message);
        }
    }

    /**
     * INFO log
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param message Message to be logged
     */
    public void logInfo(final Logger log, final String methodName, final String message) {
        if (log.isInfoEnabled()) {
            log.info(methodName + " - " + message);
        }
    }

    /**
     * WARN log
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param message Message to be logged
     */
    public void logWarn(final Logger log, final String methodName, final String message) {
        if (log.isEnabledFor(Level.WARN)) {
            log.warn(methodName + " - " + message);
        }
    }

    /**
     * ERROR log
     *
     * @param log Log4J Object
     * @param methodName Method in which the log has called
     * @param message Message to be logged
     */
    public void logError(final Logger log, final String methodName, final Object message) {
        log.error(methodName + " - " + message);
    }

    /**
     * Adds params to the log
     *
     * @param params General methods parameters
     */
    private void getParamsString(final String... params) {
        if (params != null) {
            for (String param : params) {
                if (param != null && !param.isEmpty()) {
                    str.append(param).append(" ");
                }
            }
        }
    }

    /**
     * Class wrapper
     *
     * @param log
     * @param methodName
     * @param params
     * @return
     */
    public static MethodLog methodLog(final Logger log, final String methodName, final String... params) {
        final CaesarLog bloaseLog = getInstance();
        bloaseLog.logMethodEntering(log, methodName, params);
        return new MethodLog(log, methodName, bloaseLog);
    }
    
    /**
     * Formats the param value
     * @param name
     * @param value
     * @return 
     */
    public static String param(final String name, final Object value) {
        return name + " [" + value + "]";
    }

}
