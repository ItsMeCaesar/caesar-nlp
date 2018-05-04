package com.hicaesar.nlp.repository.bson;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import com.hicaesar.nlp.support.util.StringUtil;
import com.hicaesar.nlp.vo.EntityTypeVO;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeBSON {

    private static final Logger LOG = Logger.getLogger(EntityTypeBSON.class);

    public static final String ID_KEY = "_id";
    private static final String NAME_KEY = "name";
    private static final String COLOR_KEY = "color";

    /**
     * Private Constructor
     */
    private EntityTypeBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final EntityTypeVO vo) {

        methodLog(LOG, "builder", "vo [" + vo + "]");

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static EntityTypeVO parse(final Document doc) {

        methodLog(LOG, "parse", "doc [" + doc + "]");
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final EntityTypeVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final EntityTypeVO vo) {
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
            out.put(COLOR_KEY, vo.getColor());

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
        public EntityTypeVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final ObjectId id = d.getObjectId(ID_KEY);
            final String name = d.getString(NAME_KEY);
            final String color = d.getString(COLOR_KEY);

            final EntityTypeVO out = new EntityTypeVO();
            out.setId(id.toHexString());
            out.setName(name);
            out.setColor(color);

            return out;
        }

    }

}
