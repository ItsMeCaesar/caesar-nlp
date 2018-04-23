package com.hicaesar.nlp.repository.bson;

import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.EntityVO;
import com.hicaesar.nlp.vo.IntentVO;
import com.mongodb.BasicDBList;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 *
 * @author samuelwaskow
 */
public class IntentBSON {

    private static final Logger LOG = Logger.getLogger(IntentBSON.class);

    private static final String TEXT_KEY = "text";
    private static final String INTENT_KEY = "intent";
    private static final String ENTITIES_KEY = "entities";

    /**
     * Private Constructor
     */
    private IntentBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final IntentVO vo) {

        methodLog(LOG, "builder", "vo [" + vo + "]");

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static IntentVO parse(final Document doc) {

        methodLog(LOG, "parse", "doc [" + doc + "]");
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final IntentVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final IntentVO vo) {
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

            out.put(TEXT_KEY, vo.getText());
            out.put(INTENT_KEY, vo.getIntent());
            out.put(ENTITIES_KEY, buildEntities());

            return out;
        }

        /**
         * Build Entities
         *
         * @return
         */
        private BasicDBList buildEntities() {

            methodLog(LOG, "buildEntities");

            final BasicDBList out = new BasicDBList();
            vo.getEntities().forEach(e
                    -> out.add(EntityBSON.builder(e).build())
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
        public IntentVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final String text = d.getString(TEXT_KEY);
            final String intent = d.getString(INTENT_KEY);
            final List<EntityVO> entities = parseEntities(d);

            final IntentVO out = new IntentVO();
            out.setText(text);
            out.setIntent(intent);
            out.setEntities(entities);

            return out;
        }

        /**
         * Parse the entities
         *
         * @param d
         * @return
         */
        private List<EntityVO> parseEntities(final Document d) {

            methodLog(LOG, "parseEntities", param("d", d));

            final List<Object> array = (List<Object>) d.get(ENTITIES_KEY);
            final List<EntityVO> out = new ArrayList<>();

            array.forEach(val
                    -> out.add(EntityBSON.parse((Document) val))
            );

            return out;
        }
    }

}
