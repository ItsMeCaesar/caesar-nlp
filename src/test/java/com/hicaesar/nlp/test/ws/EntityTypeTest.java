package com.hicaesar.nlp.test.ws;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.EntityTypeVO;
import com.hicaesar.nlp.ws.EntityTypeREST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.DeploymentContext;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeTest extends BaseTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(EntityTypeREST.class);
    }

    @Test
    public void testSaveNewEntityTypes() throws CaesarException {

        /* GET */
        final Map<String, Object> params = new HashMap<>();
        params.put("term", "você");

        Response response = super.get("entitytype");
        Assert.assertEquals(200, response.getStatus());
        List<EntityTypeVO> items = response.readEntity(new GenericType<List<EntityTypeVO>>() {
        });
        Assert.assertTrue(items.isEmpty());

       
    }
}
