package com.hicaesar.nlp.repository.bson;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.support.util.StringUtil;
import com.hicaesar.nlp.vo.DomainVO;
import com.hicaesar.nlp.vo.IntentEntitySynonymVO;
import com.hicaesar.nlp.vo.IntentVO;

import com.mongodb.BasicDBList;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public class DomainBSON {

    private static final Logger LOG = Logger.getLogger(DomainBSON.class);

    public static final String ID_KEY = "_id";
    private static final String NAME_KEY = "name";
    private static final String LOCALE_KEY = "locale";
    private static final String ENTITY_SYNONYMS_KEY = "entitySynonyms";
    private static final String INTENTS_KEY = "intents";

    /**
     * Protected Constructor
     */
    protected DomainBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final DomainVO vo) {

        methodLog(LOG, "builder", "vo [" + vo + "]");

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static DomainVO parse(final Document doc) {

        methodLog(LOG, "parse", "doc [" + doc + "]");
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final DomainVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final DomainVO vo) {
            super();
            this.vo = vo;
        }

        /**
         * Build
         *
         * @return The BSON Document
         */
        public Document build() {

            methodLog(LOG, "build");

            final Document out = new Document();

            out.put(ID_KEY, StringUtil.isEmpty(vo.getId()) ? new ObjectId() : new ObjectId(vo.getId()));
            out.put(NAME_KEY, vo.getName());
            out.put(LOCALE_KEY, vo.getLocale());
            out.put(ENTITY_SYNONYMS_KEY, buildEntitySynonyms());
            out.put(INTENTS_KEY, buildIntents());

            return out;
        }

        /**
         * Build Entity Synonyms
         *
         * @return
         */
        private BasicDBList buildEntitySynonyms() {

            methodLog(LOG, "buildEntitySynonyms");

            final BasicDBList out = new BasicDBList();
            vo.getEntitySynonyms().forEach(es
                    -> out.add(IntentEntitySynonymBSON.builder(es).build())
            );
            return out;
        }

        /**
         * Build Entities
         *
         * @return
         */
        private BasicDBList buildIntents() {

            methodLog(LOG, "buildIntents");

            final BasicDBList out = new BasicDBList();
            vo.getIntents().forEach(i
                    -> out.add(IntentBSON.builder(i).build())
            );
            return out;
        }

    }

    /**
     * Parser class
     */
    public static class Parser {

        private final Document doc;

        /**
         * Constructor
         *
         * @param doc
         */
        public Parser(final Document doc) {
            this.doc = doc;
        }

        /**
         * Parse
         *
         * @return The Value Object
         */
        public DomainVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final ObjectId id = d.getObjectId(ID_KEY);
            final String name = d.getString(NAME_KEY);
            final String locale = d.getString(LOCALE_KEY);
            final List<IntentEntitySynonymVO> entitySynonyms = parseEntitySynonyms(d);
            final List<IntentVO> intents = parseIntents(d);

            final DomainVO out = new DomainVO();
            out.setId(id.toHexString());
            out.setName(name);
            out.setLocale(locale);
            out.setEntitySynonyms(entitySynonyms);
            out.setIntents(intents);

            return out;
        }

        /**
         * Parse the entity synonyms
         *
         * @param d
         * @return
         */
        private List<IntentEntitySynonymVO> parseEntitySynonyms(final Document d) {

            methodLog(LOG, "parseEntitySynonyms", param("d", d));

            final List<Object> array = (List<Object>) d.get(ENTITY_SYNONYMS_KEY);
            final List<IntentEntitySynonymVO> out = new ArrayList<>();

            array.forEach(val
                    -> out.add(IntentEntitySynonymBSON.parse((Document) val))
            );

            return out;
        }

        /**
         * Parse the intents
         *
         * @param d
         * @return
         */
        private List<IntentVO> parseIntents(final Document d) {

            methodLog(LOG, "parseIntents", param("d", d));

            final List<Object> array = (List<Object>) d.get(INTENTS_KEY);
            final List<IntentVO> out = new ArrayList<>();

            array.forEach(val
                    -> out.add(IntentBSON.parse((Document) val))
            );

            return out;
        }
    }

}
