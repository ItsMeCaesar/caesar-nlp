package com.hicaesar.nlp.validation;

import com.hicaesar.nlp.repository.*;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.IntentVO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class DomainValidator {

    private static final Logger LOG = Logger.getLogger(DomainValidator.class);

    private final DomainRepository repository = new DomainRepository();

    /**
     * Save a new domain
     *
     * @param vo
     * @return
     * @throws CaesarException
     */
    public DomainVO save(final DomainVO vo) throws CaesarException {

        methodLog(LOG, "save", param(Constants.VO, vo));

        CommonValidator.validateRequired("name", vo.getName());

        CommonValidator.validateRequired("locale", vo.getLocale());

        CommonValidator.validateRequired("intents", vo.getIntents());

        for (final IntentVO intent : vo.getIntents()) {

            CommonValidator.validateRequired("text", intent.getText());
        }

        repository.save(vo);

        return vo;
    }

    /**
     * Retrieve a list of domains
     *
     * @return
     * @throws CaesarException
     */
    public List<DomainVO> list() throws CaesarException {

        methodLog(LOG, "list");
        
        return repository.list();
    }

    /**
     * Delete a domain
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