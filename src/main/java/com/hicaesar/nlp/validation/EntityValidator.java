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
        
        CommonValidator.validateRequired("locale", vo.getLocale());
        
        CommonValidator.validateRequired("type", vo.getType());
        
        CommonValidator.validateRequired("value", vo.getValue());
        
        vo.setType(vo.getType().toLowerCase());
        vo.setValue(vo.getValue().toLowerCase());
        
        return repository.save(vo);
    }

    /**
     * List persisted entities
     *
     * @param locale
     * @param text
     * @return Persisted entity types
     * @throws CaesarException
     */
    public List<EntityVO> list(final String locale, final String text) throws CaesarException {
        
        methodLog(LOG, "list", param("locale", locale), param("text", text));
        
        CommonValidator.validateRequired("locale", locale);
        
        final String[] loc = locale.split("_");
        
        return repository.list(new Locale(loc[0], loc[1]), text);
    }

    /**
     * Delete an entity
     *
     * @param id
     * @throws CaesarException
     */
    public void delete(final String id) throws CaesarException {
        
        methodLog(LOG, "delete", param("id", id));
        
        CommonValidator.validateRequired("id", id);
        
        repository.delete(id);
    }
    
}
