package com.hicaesar.nlp.support;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Reflects the file constants.properties
 *
 * @author joaozito
 */
public enum ConstantType {

    NOSQL_URI;

    private static final Properties properties = new Properties();
    private static final Logger LOG = Logger.getLogger(ConstantType.class);

    static {
        try {
            properties.load(ConstantType.class.getClassLoader().getResourceAsStream("caesar.properties"));
        } catch (IOException e) {
            LOG.error(e.getClass().getSimpleName() + ": caesar properties from the system have not been loaded", e);
        }
    }

    /**
     * Provide the property value or the enum
     *
     * @return
     */
    public String propertyValue() {
        final String out = properties.getProperty(this.name());
        return (out == null) ? this.name() : out;
    }

}
