package com.hicaesar.nlp.test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.exception.CaesarExceptionMapper;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.security.PermitAll;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public class BaseTest extends JerseyTest {

    private static final Logger LOG = Logger.getLogger(BaseTest.class);

    /**
     * Set Jackson as provider
     *
     * @param config
     */
    @Override
    protected void configureClient(ClientConfig config) {
        config.register(JacksonJsonProvider.class);
    }

    /**
     * Setup the test class
     *
     */
    @BeforeClass
    public static void setUpClass() {

        methodLog(LOG, "setUpClass");

    }

    /**
     * Configure the Grizzly context
     *
     * @param classes
     * @return
     */
    protected DeploymentContext configureDeployment(final Class<?>... classes) {
        final List<Class<?>> classesList = new ArrayList<>();
        
        classesList.add(CaesarExceptionMapper.class);
        classesList.addAll(Arrays.asList(classes));
        
        final Class<?>[] resources = classesList.toArray(new Class<?>[classesList.size()]);
        final ResourceConfig rc = new ResourceConfig(resources);
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
        rc.addProperties(properties);
        final ServletContainer sc = new ServletContainer(rc);
        final ServletDeploymentContext.Builder dc = ServletDeploymentContext.forServlet(sc);
        return dc.build();
    }

    /**
     * Instantiate the container
     *
     * @return
     */
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    /**
     * GET HTTP method
     *
     * @param url
     * @return
     */
    protected Response get(String url) {
        return target(url)
                .request()
                .get();
    }

    /**
     * DELETE HTTP method
     *
     * @param url
     * @return
     */
    protected Response delete(String url) {
        return target(url)
                .request()
                .delete();
    }

    /**
     * GET HTTP method
     *
     * @param url
     * @param params
     * @return
     */
    protected Response get(String url, Map<String, Object> params) {

        WebTarget out = target(url);

        for (Entry<String, Object> entry : params.entrySet()) {
            out = out.queryParam(entry.getKey(), entry.getValue());
        }

        return out
                .request()
                .get();
    }

    /**
     * POST HTTP method
     *
     * @param url
     * @param voEntity
     * @return
     */
    protected Response post(String url, Entity<?> voEntity) {

        return target(url)
                .request()
                .post(voEntity);
    }

    /**
     * POST HTTP method
     *
     * @param url
     * @param voEntity
     * @param headers
     * @return
     */
    protected Response post(String url, Entity<?> voEntity, MultivaluedMap<String, Object> headers) {
        return target(url)
                .request()
                .headers(headers)
                .post(voEntity);
    }

    /**
     * PUT HTTP method
     *
     * @param url
     * @param voEntity
     * @return
     */
    protected Response put(String url, Entity<?> voEntity) {

        return target(url)
                .request()
                .put(voEntity);
    }

    @Test
    public void testRequiredParameters() {

    }

    /**
     * To enable the tests for this class
     *
     * @return
     */
    @Override
    protected DeploymentContext configureDeployment() {
        return configureDeployment(TestREST.class);
    }

    /**
     * Private class to mock endpoints
     */
    private final class TestREST {

        @OPTIONS
        @PermitAll
        public void endpoint1() throws CaesarException {

        }

    }

}
