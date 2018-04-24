package com.hicaesar.nlp.repository.bson;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import com.hicaesar.nlp.vo.IntentEntityVO;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 *
 * @author samuelwaskow
 */
public final class IntentEntityBSON {

    private static final Logger LOG = Logger.getLogger(IntentEntityBSON.class);

    private static final String START_KEY = "start";
    private static final String END_KEY = "end";
    private static final String VALUE_KEY = "value";
    private static final String TYPE_KEY = "type";

    /**
     * Private Constructor
     */
    private IntentEntityBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final IntentEntityVO vo) {

        methodLog(LOG, "builder", "vo [" + vo + "]");

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static IntentEntityVO parse(final Document doc) {

        methodLog(LOG, "parse", "doc [" + doc + "]");
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final IntentEntityVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final IntentEntityVO vo) {
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

            out.put(START_KEY, vo.getStart());
            out.put(END_KEY, vo.getEnd());
            out.put(TYPE_KEY, vo.getType());
            out.put(VALUE_KEY, vo.getValue());

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
        public IntentEntityVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final int start = d.getInteger(START_KEY);
            final int end = d.getInteger(END_KEY);
            final String type = d.getString(TYPE_KEY);
            final String value = d.getString(VALUE_KEY);

            final IntentEntityVO out = new IntentEntityVO();
            out.setStart(start);
            out.setEnd(end);
            out.setType(type);
            out.setValue(value);

            return out;
        }
    }

}
