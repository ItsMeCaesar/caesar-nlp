package com.hicaesar.nlp.repository.bson;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.IntentEntitySynonymVO;
import com.mongodb.BasicDBList;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 *
 * @author samuelwaskow
 */
public final class IntentEntitySynonymBSON {

    private static final Logger LOG = Logger.getLogger(IntentEntitySynonymBSON.class);

    private static final String VALUE_KEY = "value";
    private static final String SYNONYMS_KEY = "synonyms";

    /**
     * Private Constructor
     */
    private IntentEntitySynonymBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final IntentEntitySynonymVO vo) {

        methodLog(LOG, "builder", "vo [" + vo + "]");

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static IntentEntitySynonymVO parse(final Document doc) {

        methodLog(LOG, "parse", "doc [" + doc + "]");
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final IntentEntitySynonymVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final IntentEntitySynonymVO vo) {
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

            out.put(VALUE_KEY, vo.getValue());
            out.put(SYNONYMS_KEY, buildSynonyms());

            return out;
        }

        /**
         * Build Synonyms
         *
         * @return
         */
        private BasicDBList buildSynonyms() {

            methodLog(LOG, "buildSynonyms");

            final BasicDBList out = new BasicDBList();

            vo.getSynonyms().forEach(s -> out.add(s));

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
        public IntentEntitySynonymVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final String value = d.getString(VALUE_KEY);
            final List<String> synonyms = parseSynonyms(d);

            final IntentEntitySynonymVO out = new IntentEntitySynonymVO();
            out.setValue(value);
            out.setSynonyms(synonyms);

            return out;
        }

        /**
         * Parse the synonyms
         *
         * @param d
         * @return
         */
        private List<String> parseSynonyms(final Document d) {

            methodLog(LOG, "parseSynonyms", param("d", d));

            final List<Object> array = (List<Object>) d.get(SYNONYMS_KEY);
            final List<String> out = new ArrayList<>();

            array.forEach(val -> out.add((String) val));

            return out;
        }
    }

}
