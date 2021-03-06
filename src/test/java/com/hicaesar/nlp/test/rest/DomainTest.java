package com.hicaesar.nlp.test.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hicaesar.nlp.rest.DomainREST;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.util.FileUtil;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.StatusVO;
import java.io.IOException;
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
public final class DomainTest extends BaseTest {

    private final FileUtil fu = new FileUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(DomainREST.class);
    }

    @Test
    public void testCRUD() throws CaesarException, IOException {


        /* GET LIST  */
        Response response = super.get("domain/list");
        Assert.assertEquals(200, response.getStatus());
        List<DomainVO> items1 = response.readEntity(new GenericType<List<DomainVO>>() {
        });
        Assert.assertTrue(items1.isEmpty());

        /* POST */
        final String path = fu.getPath(this.getClass(), "domain/global_pt_BR.json");
        final String content = fu.readFromFile(path, true);
        final DomainVO vo1 = mapper.readValue(content, DomainVO.class);

        response = super.post("domain", Entity.json(vo1));
        Assert.assertEquals(200, response.getStatus());
        DomainVO persistedVo1 = response.readEntity(DomainVO.class);
        Assert.assertNotNull(persistedVo1.getId());

        /* GET LIST  */
        response = super.get("domain/list");
        Assert.assertEquals(200, response.getStatus());
        List<DomainVO> items2 = response.readEntity(new GenericType<List<DomainVO>>() {
        });
        Assert.assertEquals(1, items2.size());
        Assert.assertFalse(items2.get(0).getEntitySynonyms().isEmpty());
        Assert.assertFalse(items2.get(0).getIntents().isEmpty());
        
        /* UPDATE */
        items2.get(0).setName("Local Domain");
        response = super.put("domain", Entity.json(items2.get(0)));
        Assert.assertEquals(200, response.getStatus());
        DomainVO updatedVO = response.readEntity(DomainVO.class);
        Assert.assertEquals(items2.get(0).getId(), updatedVO.getId());
        Assert.assertEquals("Local Domain", updatedVO.getName());

        /* DELETE */
        response = super.delete("domain/" + items2.get(0).getId());
        Assert.assertEquals(200, response.getStatus());
        StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertTrue(status.isOk());

        /* GET LIST  */
        response = super.get("domain/list");
        Assert.assertEquals(200, response.getStatus());
        List<DomainVO> items4 = response.readEntity(new GenericType<List<DomainVO>>() {
        });
        Assert.assertTrue(items4.isEmpty());
    }

    @Test
    public void testInvalidNew() throws CaesarException {

        final DomainVO vo1 = new DomainVO();

        Response response = super.post("domain", Entity.json(vo1));
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("Parameter 'name' is required", status.getMsg());
    }

    @Test
    public void testInvalidDelete() throws CaesarException {

        Response response = super.delete("domain/1");
        Assert.assertEquals(400, response.getStatus());

        final StatusVO status = response.readEntity(StatusVO.class);
        Assert.assertEquals("invalid hexadecimal representation of an ObjectId: [1]", status.getMsg());
    }

}
