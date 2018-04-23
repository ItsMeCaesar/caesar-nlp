package com.hicaesar.nlp;

import com.hicaesar.nlp.repository.RepositoryFactory;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import com.hicaesar.nlp.support.log.MethodLog;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class StartupServlet extends HttpServlet {

    private static final long serialVersionUID = 7096172982558483885L;
    private static final Logger LOG = Logger.getLogger(StartupServlet.class);

    /**
     * Project initialization
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {

        final MethodLog log = methodLog(LOG, "init", "config [" + config + "]");

        super.init(config);

        try {
            RepositoryFactory.getDB();
        } catch (CaesarException ex) {
            log.logError(ex);
        }
    }

}
