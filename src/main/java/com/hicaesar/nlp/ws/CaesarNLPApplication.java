package com.hicaesar.nlp.ws;


import com.hicaesar.nlp.support.exception.CaesarExceptionMapper;
import com.hicaesar.nlp.support.filter.ResponseCORSFilter;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author samuelwaskow
 */
@ApplicationPath("/")
public final class CaesarNLPApplication extends Application {

    /**
     * Default Constructor
     */
    public CaesarNLPApplication() {
        super();
    }

    /**
     * Provide endpoints
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(ResponseCORSFilter.class);
        resources.add(CaesarExceptionMapper.class);

//        resources.add(BotREST.class);
//        resources.add(BotConfigREST.class);
//        resources.add(SessionChatREST.class);
//        resources.add(ChatraREST.class);
//        resources.add(ClientREST.class);
//        resources.add(FacebookREST.class);
//        resources.add(RDStationREST.class);
//        resources.add(SessionREST.class);
//        resources.add(LoginREST.class);
//        resources.add(SurveyREST.class);
//        resources.add(TextMemoryREST.class);

        return resources;
    }

}
