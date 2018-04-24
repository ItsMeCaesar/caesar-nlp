package com.hicaesar.nlp.validation;

import com.hicaesar.nlp.repository.EntityTypeRepository;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import com.hicaesar.nlp.vo.EntityTypeVO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeValidator {
    
    private static final Logger LOG = Logger.getLogger(EntityTypeValidator.class);
    
    private final EntityTypeRepository repository = new EntityTypeRepository();

    /**
     * Save a new entity type
     *
     * @param vo Value Object to be saved
     * @return Persisted value object
     * @throws CaesarException
     */
    public EntityTypeVO save(final EntityTypeVO vo) throws CaesarException {
        
        methodLog(LOG, "save", param(Constants.VO, vo));
        
        CommonValidator.validateRequired("name", vo.getName());
        
        vo.setName(vo.getName().toLowerCase());
        
        return repository.save(vo);
    }

    /**
     * List all entity types
     *
     * @return Persisted entity types
     * @throws CaesarException
     */
    public List<EntityTypeVO> list() throws CaesarException {
        
        methodLog(LOG, "list");
        
        return repository.list();
    }
    
}
