package com.hicaesar.nlp.test.ws;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.EntityVO;
import com.hicaesar.nlp.vo.StatusVO;
import com.hicaesar.nlp.ws.EntityREST;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.DeploymentContext;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTest extends BaseTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(EntityREST.class);
    }

    @Test
    public void testCRUD() throws CaesarException {

        final Locale locale = new Locale("pt", "BR");

        final Map<String, Object> params = new HashMap<>();
        params.put("locale", locale.toString());
        params.put("text", "lama");

        /* GET */
        Response response = super.get("entity", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items1 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        Assert.assertTrue(items1.isEmpty());

        final EntityVO vo1 = new EntityVO();
        vo1.setLocale(locale.toString());
        vo1.setType("person");
        vo1.setValue("dalai lama");

        response = super.post("entity", Entity.json(vo1));
        Assert.assertEquals(200, response.getStatus());
        EntityVO persistedVo1 = response.readEntity(EntityVO.class);
        Assert.assertFalse(persistedVo1.getId().isEmpty());

        final EntityVO vo2 = new EntityVO();
        vo2.setLocale(locale.toString());
        vo2.setType("misc");
        vo2.setValue("lama");
        response = super.post("entity", Entity.json(vo2));
        Assert.assertEquals(200, response.getStatus());
        EntityVO persistedVo2 = response.readEntity(EntityVO.class);
        Assert.assertFalse(persistedVo2.getId().isEmpty());

        response = super.get("entity", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items2 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        Assert.assertEquals(2, items2.size());
        
        response = super.delete("entity/" + items2.get(0).getId());
        Assert.assertEquals(200, response.getStatus());
        StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertTrue(status.isOk());
        

    }
}
