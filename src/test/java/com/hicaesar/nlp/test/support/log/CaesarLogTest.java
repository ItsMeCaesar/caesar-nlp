package com.hicaesar.nlp.test.support.log;


import com.hicaesar.nlp.support.log.CaesarLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import junit.framework.Assert;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class CaesarLogTest {

    private static final Logger LOG = Logger.getLogger(CaesarLogTest.class);

    @Test
    public void testMethodEntering() {

        LOG.setLevel(Level.TRACE);
        CaesarLog.getInstance().logMethodEntering(LOG, "testMethodEntering", "");
        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logMethodEntering(LOG, "testMethodEntering", "");
        CaesarLog.getInstance().logMethodEntering(LOG, "testMethodEntering", (String) null);
        CaesarLog.getInstance().logMethodEntering(LOG, "testMethodEntering", new String[]{null, "", "a"});
    }

    @Test
    public void testLogWSEntering() {

        CaesarLog.getInstance().logWSEntering(LOG, "logWSEntering", "IP", "USER");
        CaesarLog.getInstance().logWSEntering(LOG, "logWSEntering", "IP", "USER", "PARAMS");
        CaesarLog.getInstance().logWSEntering(LOG, "logWSEntering", "IP", null, "PARAMS");
    }

    @Test
    public void testLogMethodExit() {

        CaesarLog.getInstance().logMethodExit(LOG, "logMethodExit", 0);
        CaesarLog.getInstance().logMethodExit(LOG, "logMethodExit", 5000);
    }

    @Test
    public void testLogTrace() {

        LOG.setLevel(Level.TRACE);
        CaesarLog.getInstance().logTrace(LOG, "logTrace", "TRACE");
        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logTrace(LOG, "logTrace", "TRACE");
    }

    @Test
    public void testLogAll() {

        LOG.setLevel(Level.ALL);
        CaesarLog.getInstance().logAll(LOG, "logAll", "ALL");
        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logAll(LOG, "logAll", "ALL");
    }

    @Test
    public void testLogDebug() {

        LOG.setLevel(Level.DEBUG);
        CaesarLog.getInstance().logDebug(LOG, "logDebug", "DEBUG");
        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logDebug(LOG, "logDebug", "DEBUG");
    }

    @Test
    public void testLogInfo() {

        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logInfo(LOG, "logInfo", "INFO");
        LOG.setLevel(Level.WARN);
        CaesarLog.getInstance().logInfo(LOG, "logInfo", "INFO");
    }

    @Test
    public void testLogWarn() {

        LOG.setLevel(Level.ERROR);
        CaesarLog.getInstance().logWarn(LOG, "logWarn", "WARN");
        LOG.setLevel(Level.INFO);
        CaesarLog.getInstance().logWarn(LOG, "logWarn", "WARN");
    }

    @Test
    public void testLogError() {

        LOG.setLevel(Level.ERROR);
        CaesarLog.getInstance().logError(LOG, "logError", "ERROR");
    }

    @Test
    public void testParam() {

        final String value = "log";

        String logValue = param("log", value);
        Assert.assertEquals("log [log]", logValue);
    }
}
