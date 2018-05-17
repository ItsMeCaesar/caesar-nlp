package com.hicaesar.nlp.validation;

import com.hicaesar.nlp.repository.ReportRepository;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.PagedVO;
import com.hicaesar.nlp.vo.ReportVO;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class ReportValidator {

    private static final Logger LOG = Logger.getLogger(ReportValidator.class);

    private final ReportRepository repository = new ReportRepository();

    /**
     * Save a new report issued by Caesar
     *
     * @param vo
     * @throws CaesarException
     */
    public void save(final ReportVO vo) throws CaesarException {

        methodLog(LOG, "save", param(Constants.VO, vo));

        CommonValidator.validateRequired("text", vo.getText());

        repository.save(vo);
    }

    /**
     * Set a specific report as 'viewed'
     *
     * @param id
     * @throws CaesarException
     */
    public void view(final String id) throws CaesarException {

        methodLog(LOG, "view", param(Constants.ID, id));

        CommonValidator.validateRequired(Constants.ID, id);

        repository.view(id);
    }

    /**
     * Retrieve a list of generated reports
     *
     * @param page
     * @param pageSize
     * @return
     * @throws CaesarException
     */
    public PagedVO<ReportVO> list(final int page, final int pageSize) throws CaesarException {

        methodLog(LOG, "list", param("page", page), param("pageSize", pageSize));

        CommonValidator.validateRequired("page", page);

        CommonValidator.validateRequired("pageSize", pageSize);

        return repository.list(page, pageSize);
    }

}
