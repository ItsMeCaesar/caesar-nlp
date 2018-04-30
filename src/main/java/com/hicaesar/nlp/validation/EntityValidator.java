package com.hicaesar.nlp.validation;

import com.hicaesar.nlp.repository.EntityRepository;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.EntityVO;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class EntityValidator {

    private static final Logger LOG = Logger.getLogger(EntityValidator.class);

    private final EntityRepository repository = new EntityRepository();

    /**
     * Save a new entity type
     *
     * @param vo Value Object to be saved
     * @return Persisted value object
     * @throws CaesarException
     */
    public EntityVO save(final EntityVO vo) throws CaesarException {

        methodLog(LOG, "save", param(Constants.VO, vo));

        validateCommonParams(vo);

        return repository.save(vo);
    }

    /**
     * Save new entities
     *
     * @param vos
     * @throws CaesarException
     */
    public void saveList(final List<EntityVO> vos) throws CaesarException {

        methodLog(LOG, "saveList", param("vos", vos));

        for (final EntityVO vo : vos) {
            validateCommonParams(vo);
        }

        repository.saveList(vos);
    }

    /**
     * Retrieve an entity
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    public EntityVO get(final String locale, final String text) throws CaesarException {

        methodLog(LOG, "get", param("locale", locale), param("text", text));

        CommonValidator.validateRequired("locale", locale);

        final String[] loc = locale.split("_");

        return repository.get(new Locale(loc[0], loc[1]), text);
    }

    /**
     * List persisted entities
     *
     * @param locale
     * @param type
     * @param value
     * @return Persisted entity types
     * @throws CaesarException
     */
    public List<EntityVO> list(final String locale, final String type, final String value) throws CaesarException {

        methodLog(LOG, "list", param("locale", locale), param("type", type), param("value", value));

        CommonValidator.validateRequired("locale", locale);

        CommonValidator.validateRequired("type", type);

        final String[] loc = locale.split("_");

        return repository.list(new Locale(loc[0], loc[1]), type, value);
    }

    /**
     * Delete an entity
     *
     * @param id
     * @throws CaesarException
     */
    public void delete(final String id) throws CaesarException {

        methodLog(LOG, "delete", param("id", id));

        repository.delete(id);
    }

    /**
     * Validate entity common parameters
     *
     * @param vo
     * @throws CaesarException
     */
    private void validateCommonParams(final EntityVO vo) throws CaesarException {

        methodLog(LOG, "validateCommonParams", param(Constants.VO, vo));

        CommonValidator.validateRequired("locale", vo.getLocale());
        CommonValidator.validateRequired("type", vo.getType());
        CommonValidator.validateRequired("value", vo.getValue());

        vo.setType(vo.getType().toLowerCase().trim());
        vo.setValue(vo.getValue().toLowerCase().trim());
    }

}
