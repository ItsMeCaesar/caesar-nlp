package com.hicaesar.nlp.services;

import com.hicaesar.nlp.repository.DomainRepository;
import com.hicaesar.nlp.repository.EntityRepository;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.support.log.MethodLog;
import com.hicaesar.nlp.support.util.LocaleUtil;
import com.hicaesar.nlp.validation.ReportValidator;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.EntityVO;
import com.hicaesar.nlp.vo.IntentEntityVO;
import com.hicaesar.nlp.vo.IntentVO;
import com.hicaesar.nlp.vo.ReportVO;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class IntentEntityService implements Runnable {

    private static final Logger LOG = Logger.getLogger(IntentEntityService.class);

    private final DomainVO domain;
    private final DomainRepository domainRepository = new DomainRepository();
    private final EntityRepository entityRepository = new EntityRepository();
    private final ReportValidator reportValidator = new ReportValidator();

    /**
     * Constructor
     *
     * @param domain
     */
    public IntentEntityService(final DomainVO domain) {
        this.domain = domain;
    }

    /**
     * Main method
     */
    @Override
    public void run() {

        final MethodLog log = methodLog(LOG, "run");

        try {

            for (final IntentVO intent : domain.getIntents()) {
                processIntent(domain.getLocale(), intent);
            }

            domainRepository.update(domain);

        } catch (CaesarException ex) {
            log.logError(ex);
        }
    }

    /**
     * Process an intent
     *
     * @param locale
     * @param intent
     * @exception CaesarException
     */
    private void processIntent(final String locale, final IntentVO intent) throws CaesarException {

        final MethodLog log = methodLog(LOG, "processIntent", param("locale", locale), param("intent", intent));

        final String[] tokens = intent.getText().split(" ");

        for (final String token : tokens) {

            if (token.length() > 2) {

                final List<EntityVO> entities = processToken(LocaleUtil.getLocale(locale), token, intent);

                log.logDebug(entities.toString());
            }
        }

    }

    /**
     * Process the sentence token
     *
     * @param locale
     * @param token
     * @param intent
     * @return
     * @throws CaesarException
     */
    private List<EntityVO> processToken(final Locale locale, final String token, final IntentVO intent) throws CaesarException {

        methodLog(LOG, "processToken", param("locale", locale), param("token", token), param("intent", intent));

        final List<EntityVO> entities = entityRepository.list(locale, "", token);

        entities.forEach(e -> {

            if (intent.getText().contains(e.getValue())
                    && intent.getEntities().stream()
                            .filter(es -> es.getValue().equals(e.getValue()) && es.getType().equals(e.getType()))
                            .count() == 0) {

                addIntentEntity(intent, e);
            }
        });
        return entities;
    }

    /**
     * Add an entity to a intent
     *
     * @param intent
     * @param entity
     */
    private void addIntentEntity(final IntentVO intent, final EntityVO entity) {

        final MethodLog log = methodLog(LOG, "addIntentEntity", param("intent", intent), param("entity", entity));

        final IntentEntityVO newEntity = new IntentEntityVO();
        newEntity.setValue(entity.getValue());
        newEntity.setType(entity.getType());
        final int length = entity.getValue().length();
        final int start = intent.getText().indexOf(entity.getValue());
        newEntity.setStart(start);
        newEntity.setEnd(start + length);

        intent.getEntities().add(newEntity);

        try {
            final String text = "Domain: " + domain.getName() + " | A new entity [" + entity.getValue() + ", " + entity.getType() + "] has been added to the intent [" + intent.getText() + "].";
            reportValidator.save(new ReportVO(text));
        } catch (final CaesarException e) {
            log.logError(e);
        }
    }

}
