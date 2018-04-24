package com.hicaesar.nlp.test.vo;

import com.hicaesar.nlp.vo.EntityVO;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class EntityVOTest {

    @Test
    public void testHashcode() {

        final EntityVO vo1 = new EntityVO();
        final EntityVO vo2 = new EntityVO();

        Assert.assertEquals(vo1.hashCode(), vo2.hashCode());

        vo2.setValue("homo-sapiens");
        Assert.assertNotEquals(vo1.hashCode(), vo2.hashCode());
    }

    @Test
    public void testEquals() {

        final EntityVO vo1 = new EntityVO();
        final EntityVO vo2 = new EntityVO();

        Assert.assertEquals(vo1, vo2);

        vo2.setValue("homo-sapiens");
        Assert.assertNotEquals(vo1, vo2);
        Assert.assertNotEquals(vo1, null);
        Assert.assertNotEquals(vo1, new Object());
    }

}
