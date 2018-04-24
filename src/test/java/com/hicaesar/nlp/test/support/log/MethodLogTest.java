package com.hicaesar.nlp.test.support.log;

import com.hicaesar.nlp.support.log.CaesarLog;
import com.hicaesar.nlp.support.log.MethodLog;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class MethodLogTest {

    private static final Logger LOG = Logger.getLogger(MethodLogTest.class);

    @Test
    public void testDebug() {

        final MethodLog log = CaesarLog.methodLog(LOG, "testDebug");
        log.logDebug("Message Debug");
        Assert.assertEquals("testDebug", log.name());
        log.finished();
    }

    @Test
    public void testTrace() {

        final MethodLog log = CaesarLog.methodLog(LOG, "testTrace");
        log.logTrace("Message Trace");
        Assert.assertEquals("testTrace", log.name());
        log.finished();
    }

    @Test
    public void testWarn() {

        final MethodLog log = CaesarLog.methodLog(LOG, "testWarn");
        log.logWarn("Message Warn");
        Assert.assertEquals("testWarn", log.name());
        log.finished();
    }

    @Test
    public void testInfo() {

        final MethodLog log = CaesarLog.methodLog(LOG, "testInfo");
        log.logInfo("Message Info");
        Assert.assertEquals("testInfo", log.name());
        log.finished();
    }
    
    
    @Test
    public void testError() {

        final MethodLog log = CaesarLog.methodLog(LOG, "testError");
        log.logError("Message error");
        Assert.assertEquals("testError", log.name());
        log.finished();
    }

}
