package com.hicaesar.nlp.repository.bson;

import com.hicaesar.nlp.support.Constants;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.EntityVO;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class EntityBSON {

    private static final Logger LOG = Logger.getLogger(EntityBSON.class);

    public static final String ID_KEY = "_id";
    public static final String LOCALE_KEY = "locale";
    public static final String VALUE_KEY = "value";
    public static final String TYPE_KEY = "type";

    /**
     * Private Constructor
     */
    private EntityBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final EntityVO vo) {

        methodLog(LOG, "builder", param(Constants.VO, vo));

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static EntityVO parse(final Document doc) {

        methodLog(LOG, "parse", param("doc", doc));
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final EntityVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final EntityVO vo) {
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

            out.put(ID_KEY, new ObjectId(vo.getId()));
            out.put(LOCALE_KEY, vo.getLocale());
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
        public EntityVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final ObjectId id = d.getObjectId(ID_KEY);
            final String locale = d.getString(LOCALE_KEY);
            final String type = d.getString(TYPE_KEY);
            final String value = d.getString(VALUE_KEY);

            final EntityVO out = new EntityVO();
            out.setId(id.toHexString());
            out.setLocale(locale);
            out.setType(type);
            out.setValue(value);

            return out;
        }
    }

}
