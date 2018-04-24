package com.hicaesar.nlp.test.validation;

import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.validation.CommonValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class CommonValidatorTest {

    @Test(expected = CaesarException.class)
    public void testValidateRequiredCollection() throws CaesarException {

        CommonValidator.validateRequired("collection", Collections.emptyList());
        CommonValidator.validateRequired("collection", Collections.emptyMap());
        CommonValidator.validateRequired("collection", Collections.emptySet());
        CommonValidator.validateRequired("collection", (Collection<?>) null);

        final ArrayList<FormDataBodyPart> bodyParts = new ArrayList<>();
        CommonValidator.validateRequired("collection", bodyParts);

    }

    @Test(expected = CaesarException.class)
    public void testValidateRequiredString() throws CaesarException {

        CommonValidator.validateRequired("string", "");
    }

    @Test(expected = CaesarException.class)
    public void testValidateRequiredInt() throws CaesarException {

        CommonValidator.validateRequired("int", 0);
    }

    @Test(expected = CaesarException.class)
    public void testValidateRequiredLong() throws CaesarException {

        CommonValidator.validateRequired("long", 0l);
        CommonValidator.validateRequired("long", Long.valueOf("0"));

    }

    @Test(expected = CaesarException.class)
    public void testValidateRequiredObject() throws CaesarException {

        final Object obj = null;
        CommonValidator.validateRequired("object", obj);
    }

}
