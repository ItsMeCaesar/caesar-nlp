package com.hicaesar.nlp.test.rest;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.EntityVO;
import com.hicaesar.nlp.vo.StatusVO;
import com.hicaesar.nlp.rest.EntityREST;
import java.util.ArrayList;
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
        params.put("type", "person");

        /* GET LIST  */
        Response response = super.get("entity/list", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items1 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        for (final EntityVO e : items1) {
            response = super.delete("entity/" + e.getId());
            Assert.assertEquals(200, response.getStatus());
            StatusVO status = response.readEntity(StatusVO.class);
            Assert.assertTrue(status.isOk());
        }

        /* POST */
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
        vo2.setType("person");
        vo2.setValue("lama");
        response = super.post("entity", Entity.json(vo2));
        Assert.assertEquals(200, response.getStatus());
        EntityVO persistedVo2 = response.readEntity(EntityVO.class);
        Assert.assertFalse(persistedVo2.getId().isEmpty());

        /* GET LIST */
        response = super.get("entity/list", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items2 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        Assert.assertEquals(2, items2.size());

        /* DELETE */
        response = super.delete("entity/" + items2.get(0).getId());
        Assert.assertEquals(200, response.getStatus());
        StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertTrue(status.isOk());

        /* GET LIST */
        response = super.get("entity/list", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items3 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        Assert.assertEquals(1, items3.size());

        /* GET LIST */
        params.put("value", "unidentified");
        response = super.get("entity/list", params);
        Assert.assertEquals(200, response.getStatus());
        List<EntityVO> items4 = response.readEntity(new GenericType<List<EntityVO>>() {
        });
        Assert.assertTrue(items4.isEmpty());

    }

    @Test
    public void testSaveList() throws CaesarException {

        final Locale locale = new Locale("pt", "BR");

        /* POST */
        final List<EntityVO> list = new ArrayList<>();

        final EntityVO vo1 = new EntityVO();
        vo1.setLocale(locale.toString());
        vo1.setType("person");
        vo1.setValue("Bruce Willis");
        list.add(vo1);

        final EntityVO vo2 = new EntityVO();
        vo2.setLocale(locale.toString());
        vo2.setType("ORG");
        vo2.setValue("Nações Unidas");

        Response response = super.post("entity/list", Entity.json(list));
        Assert.assertEquals(200, response.getStatus());

        /* GET */
        Map<String, Object> params = new HashMap<>();
        params.put("locale", locale.toString());
        params.put("text", "bruce willis");

        response = super.get("entity", params);
        Assert.assertEquals(200, response.getStatus());
        EntityVO item1 = response.readEntity(EntityVO.class);
        Assert.assertEquals("person", item1.getType());
        Assert.assertEquals(locale.toString(), item1.getLocale());
        Assert.assertEquals("bruce willis", item1.getValue());

        /* GET */
        params.put("text", "Blze");
        response = super.get("entity", params);
        Assert.assertEquals(200, response.getStatus());
        EntityVO item2 = response.readEntity(EntityVO.class);
        Assert.assertNull(item2.getId());
    }

    @Test
    public void testInvalidNewEntity() throws CaesarException {

        final EntityVO vo1 = new EntityVO();

        Response response = super.post("entity", Entity.json(vo1));
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("Parameter 'locale' is required", status.getMsg());
    }

    @Test
    public void testInvalidNewEntities() throws CaesarException {

        final List<EntityVO> list = new ArrayList<>();
        list.add(new EntityVO());

        Response response = super.post("entity/list", Entity.json(list));
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("Parameter 'locale' is required", status.getMsg());
    }

    @Test
    public void testInvalidGet() throws CaesarException {

        Response response = super.get("entity", new HashMap<>());
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("Parameter 'locale' is required", status.getMsg());
    }

    @Test
    public void testInvalidList() throws CaesarException {

        Response response = super.get("entity/list", new HashMap<>());
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("Parameter 'locale' is required", status.getMsg());
    }

    @Test
    public void testInvalidDelete() throws CaesarException {

        Response response = super.delete("entity/1");
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("invalid hexadecimal representation of an ObjectId: [1]", status.getMsg());
    }

}
