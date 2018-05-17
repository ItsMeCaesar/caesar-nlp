package com.hicaesar.nlp.test.services;

import com.hicaesar.nlp.repository.DomainRepository;
import com.hicaesar.nlp.repository.EntityRepository;
import com.hicaesar.nlp.rest.ReportREST;
import com.hicaesar.nlp.services.IntentEntityService;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.test.BaseTest;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.EntityVO;
import com.hicaesar.nlp.vo.IntentVO;
import com.hicaesar.nlp.vo.PagedVO;
import com.hicaesar.nlp.vo.ReportVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.glassfish.jersey.test.DeploymentContext;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class IntentEntityServiceTest extends BaseTest {

    private final DomainRepository domainRepository = new DomainRepository();
    private static final EntityRepository ENTITY_REPOSITORY = new EntityRepository();
    
    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(ReportREST.class);
    }

    @BeforeClass
    public static void setup() throws CaesarException {

        final EntityVO vo1 = new EntityVO();
        vo1.setLocale("pt_BR");
        vo1.setType("person");
        vo1.setValue("martin lutherking");

        ENTITY_REPOSITORY.save(vo1);
    }

    @Test
    public void testExtractEntity() throws CaesarException {

        final DomainVO vo = new DomainVO();
        vo.setId(new ObjectId().toHexString());
        vo.setName("Teste Domínio");
        vo.setLocale("pt_BR");
        vo.setIntents(new ArrayList<>());
        vo.setEntitySynonyms(new ArrayList<>());
        
        domainRepository.save(vo);

        final IntentVO intent = new IntentVO();
        intent.setText("eu ouvi sábias palavras do martin lutherking");
        intent.setIntent("conheco_pessoa");
        intent.setEntities(new ArrayList<>());

        vo.getIntents().add(intent);

        final IntentEntityService service = new IntentEntityService(vo);
        service.run();
        
        
        final List<DomainVO> domains = domainRepository.list();
        Assert.assertFalse(domains.isEmpty());
        
        final DomainVO domain = domains.get(0);
        Assert.assertEquals(1, domain.getIntents().get(0).getEntities().size());
        Assert.assertEquals("martin lutherking", domain.getIntents().get(0).getEntities().get(0).getValue());
        Assert.assertEquals("person", domain.getIntents().get(0).getEntities().get(0).getType());
        
        
        final Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("page_size", 10);

        /* GET */
        Response response = super.get("report", params);
        org.junit.Assert.assertEquals(200, response.getStatus());
        PagedVO<ReportVO> paged = response.readEntity(new GenericType<PagedVO<ReportVO>>() {
        });
        Assert.assertEquals(1, paged.getList().size());
        Assert.assertEquals(1, paged.getTotalSize());
        ReportVO r = paged.getList().get(0);
        Assert.assertEquals("Domain: Teste Domínio | A new entity [martin lutherking, person] has been added to the intent [eu ouvi sábias palavras do martin lutherking].", r.getText());
        Assert.assertNull(r.getViewed());
    }

}
