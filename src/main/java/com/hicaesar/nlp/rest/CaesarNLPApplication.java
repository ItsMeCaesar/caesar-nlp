package com.hicaesar.nlp.rest;


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

        resources.add(DomainREST.class);
        resources.add(EntityREST.class);
        resources.add(EntityTypeREST.class);

        return resources;
    }

}
