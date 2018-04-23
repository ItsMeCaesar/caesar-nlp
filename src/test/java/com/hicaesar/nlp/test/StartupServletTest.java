package com.hicaesar.nlp.test;

import com.hicaesar.nlp.StartupServlet;
import com.hicaesar.nlp.support.exception.CaesarException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author samuelwaskow
 */
@RunWith(MockitoJUnitRunner.class)
public final class StartupServletTest {

    @InjectMocks
    private StartupServlet servlet;

    @Mock
    private ServletConfig config;
    
    @Test
    public void testServlet() throws CaesarException, ServletException {

        servlet.init(config);
    }

}
