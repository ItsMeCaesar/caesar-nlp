package com.hicaesar.nlp.test.rest;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.EntityTypeVO;
import com.hicaesar.nlp.rest.EntityTypeREST;
import java.util.List;
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
public final class EntityTypeTest extends BaseTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(EntityTypeREST.class);
    }

    @Test
    public void testSaveNewEntityTypes() throws CaesarException {

        /* GET */
        Response response = super.get("entitytype");
        Assert.assertEquals(200, response.getStatus());
        List<EntityTypeVO> items1 = response.readEntity(new GenericType<List<EntityTypeVO>>() {
        });
        Assert.assertTrue(items1.isEmpty());

        final EntityTypeVO vo1 = new EntityTypeVO();
        vo1.setName("org");
        response = super.post("entitytype", Entity.json(vo1));
        Assert.assertEquals(200, response.getStatus());
        EntityTypeVO persistedVo1 = response.readEntity(EntityTypeVO.class);
        Assert.assertFalse(persistedVo1.getId().isEmpty());

        final EntityTypeVO vo2 = new EntityTypeVO();
        vo2.setName("person");
        response = super.post("entitytype", Entity.json(vo2));
        Assert.assertEquals(200, response.getStatus());
        EntityTypeVO persistedVo2 = response.readEntity(EntityTypeVO.class);
        Assert.assertFalse(persistedVo2.getId().isEmpty());

        response = super.get("entitytype");
        Assert.assertEquals(200, response.getStatus());
        List<EntityTypeVO> items2 = response.readEntity(new GenericType<List<EntityTypeVO>>() {
        });
        Assert.assertEquals(2, items2.size());

    }
}
