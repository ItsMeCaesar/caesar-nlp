package com.hicaesar.nlp.test.support;

import com.hicaesar.nlp.support.ConstantType;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class ConstantTypeTest {
    
    @Test
    public void test() {
        Assert.assertFalse(ConstantType.NOSQL_URI.propertyValue().contains("${"));
    }
    
}
