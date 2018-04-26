package com.hicaesar.nlp;

import com.hicaesar.nlp.repository.RepositoryFactory;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import com.hicaesar.nlp.support.log.MethodLog;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.IOException;
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

    private static MongodExecutable mongodExecutable;
    private static MongodProcess mongod;

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
            final MongodStarter starter = MongodStarter.getDefaultInstance();

            final IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
                    .build();

            mongodExecutable = starter.prepare(mongodConfig);
            mongod = mongodExecutable.start();

            RepositoryFactory.createIndexes();

        } catch (CaesarException | IOException ex) {
            log.logError(ex);
        }
    }

    /**
     * Project ending
     */
    @Override
    public void destroy() {

        if (mongod != null) {
            mongod.stop();
            mongodExecutable.stop();
        }
    }
}
