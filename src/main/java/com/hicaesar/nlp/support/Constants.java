package com.hicaesar.nlp.support;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author samuelwaskow
 */
public final class Constants {

    public static final String ENCODING = "UTF-8";
    public static final String VO = "vo";
    public static final String ID = "id";
    public static final ExecutorService THREAD_EXECUTOR = Executors.newFixedThreadPool(5);

    /**
     * Private Constructor
     */
    private Constants() {
        super();
    }

}
