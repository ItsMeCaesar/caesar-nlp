package com.hicaesar.nlp.support.log;

import org.apache.log4j.Logger;

/**
 *
 * @author joaozito
 */
public class MethodLog {

    private final long startedAt;
    private final Logger log;
    private final String methodName;
    private final CaesarLog acessor;

    /**
     * Constructor
     *
     * @param log
     * @param methodName
     * @param acessor
     */
    public MethodLog(Logger log, String methodName, CaesarLog acessor) {
        this.startedAt = System.currentTimeMillis();
        this.log = log;
        this.methodName = methodName;
        this.acessor = acessor;
    }

    /**
     * DEBUG log
     *
     * @param message
     */
    public void logDebug(String message) {
        this.acessor.logDebug(log, methodName, message);
    }

    /**
     * TRACE log
     *
     * @param message
     */
    public void logTrace(String message) {
        this.acessor.logTrace(log, methodName, message);
    }

    /**
     * INFO log
     *
     * @param message
     */
    public void logInfo(String message) {
        this.acessor.logInfo(log, methodName, message);
    }

    /**
     * WARN log
     *
     * @param message
     */
    public void logWarn(String message) {
        this.acessor.logWarn(log, methodName, message);
    }

    /**
     * ERROR log
     *
     * @param message
     */
    public void logError(Object message) {
        this.acessor.logError(log, methodName, message);
    }

    /**
     * logs the exit of method, logging the time elapsed
     */
    public void finished() {
        this.acessor.logMethodExit(log, methodName, startedAt);
    }

    /**
     * returns the method name (shortcut)
     *
     * @return
     */
    public String name() {
        return methodName;
    }

}
