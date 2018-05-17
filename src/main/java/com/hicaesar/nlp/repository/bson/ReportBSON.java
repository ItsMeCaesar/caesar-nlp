package com.hicaesar.nlp.repository.bson;

import com.hicaesar.nlp.support.Constants;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.ReportVO;
import java.util.Date;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class ReportBSON {
    
    private static final Logger LOG = Logger.getLogger(ReportBSON.class);

    public static final String ID_KEY = "_id";
    private static final String TEXT_KEY = "text";
    private static final String CREATED_KEY = "created";
    public static final String VIEWED_KEY = "viewed";

    /**
     * Private Constructor
     */
    private ReportBSON() {
        super();
    }

    /**
     * Instantiate the builder
     *
     * @param vo
     * @return
     */
    public static Builder builder(final ReportVO vo) {

        methodLog(LOG, "builder", param(Constants.VO, vo));

        return new Builder(vo);
    }

    /**
     * Parse the db document
     *
     * @param doc
     * @return
     */
    public static ReportVO parse(final Document doc) {

        methodLog(LOG, "parse", param("doc", doc));
        return new Parser(doc).parse();
    }

    /**
     * Builder class
     */
    public static class Builder {

        private final ReportVO vo;

        /**
         * Constructor
         *
         * @param vo
         */
        public Builder(final ReportVO vo) {
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
            out.put(TEXT_KEY, vo.getText());
            out.put(CREATED_KEY, vo.getCreated());
            out.put(VIEWED_KEY, vo.getViewed());

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
        public ReportVO parse() {

            methodLog(LOG, "parse");

            final Document d = this.doc;

            final ObjectId id = d.getObjectId(ID_KEY);
            final String text = d.getString(TEXT_KEY);
            final Date created = d.getDate(CREATED_KEY);
            final Date viewed = d.getDate(VIEWED_KEY);

            final ReportVO out = new ReportVO();
            out.setId(id.toHexString());
            out.setText(text);
            out.setCreated(created);
            out.setViewed(viewed);

            return out;
        }
    }

}
