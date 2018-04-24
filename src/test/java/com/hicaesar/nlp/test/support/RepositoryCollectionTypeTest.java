package com.hicaesar.nlp.test.support;

import com.hicaesar.nlp.support.RepositoryCollectionType;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class RepositoryCollectionTypeTest {

    @Test
    public void test() {
        Assert.assertEquals(3, RepositoryCollectionType.values().length);
        
        Assert.assertEquals(RepositoryCollectionType.ENTITY, RepositoryCollectionType.valueOf("ENTITY"));
        Assert.assertEquals(RepositoryCollectionType.DOMAIN, RepositoryCollectionType.valueOf("DOMAIN"));
        Assert.assertEquals(RepositoryCollectionType.ENTITY_TYPE, RepositoryCollectionType.valueOf("ENTITY_TYPE"));
    }

}
