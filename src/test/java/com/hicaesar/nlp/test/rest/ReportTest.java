package com.hicaesar.nlp.test.rest;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.rest.ReportREST;
import com.hicaesar.nlp.validation.ReportValidator;
import com.hicaesar.nlp.vo.PagedVO;
import com.hicaesar.nlp.vo.ReportVO;
import com.hicaesar.nlp.vo.StatusVO;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.DeploymentContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class ReportTest extends BaseTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(ReportREST.class);
    }

    @BeforeClass
    public static void setup() throws CaesarException {

        final ReportValidator validator = new ReportValidator();
        for (int i = 0; i < 40; i++) {

            final ReportVO r = new ReportVO();
            r.setText("Test text " + i);
            validator.save(r);

        }
    }

    @Test
    public void testRetrieveReportsAndMarkViewed() throws CaesarException {

        /* GET */
        Response response = super.get("report");
        Assert.assertEquals(400, response.getStatus());
        StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertFalse(status.isOk());
        Assert.assertEquals("Parameter 'page' is required", status.getMsg());

        final Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("page_size", 10);

        /* GET */
        response = super.get("report", params);
        Assert.assertEquals(200, response.getStatus());
        PagedVO<ReportVO> paged = response.readEntity(new GenericType<PagedVO<ReportVO>>() {
        });
        Assert.assertEquals(10, paged.getList().size());
        Assert.assertEquals(40, paged.getTotalSize());
        ReportVO r = paged.getList().get(0);
        Assert.assertEquals("Test text 0", r.getText());
        Assert.assertNull(r.getViewed());

        params.put("page", 2);
        params.put("page_size", 20);

        /* GET */
        response = super.get("report", params);
        Assert.assertEquals(200, response.getStatus());
        paged = response.readEntity(new GenericType<PagedVO<ReportVO>>() {
        });
        Assert.assertEquals(20, paged.getList().size());
        Assert.assertEquals(40, paged.getTotalSize());
        r = paged.getList().get(0);
        Assert.assertEquals("Test text 20", r.getText());
        Assert.assertNull(r.getViewed());

        /* VIEW */
        response = super.put("report/" + r.getId(), Entity.json(r));
        Assert.assertEquals(200, response.getStatus());
        status = response.readEntity(StatusVO.class);
        Assert.assertTrue(status.isOk());

        
        /* GET */
        response = super.get("report", params);
        Assert.assertEquals(200, response.getStatus());
        paged = response.readEntity(new GenericType<PagedVO<ReportVO>>() {
        });
        Assert.assertEquals(20, paged.getList().size());
        Assert.assertEquals(40, paged.getTotalSize());
        r = paged.getList().get(0);
        Assert.assertEquals("Test text 20", r.getText());
        Assert.assertNotNull(r.getViewed());
    }

}
