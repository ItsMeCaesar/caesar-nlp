package com.hicaesar.nlp.repository;

import com.hicaesar.nlp.repository.bson.ReportBSON;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.PagedVO;
import com.hicaesar.nlp.vo.ReportVO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class ReportRepository {

    private static final Logger LOG = Logger.getLogger(ReportRepository.class);

    /**
     * Save a new report issued by Caesar
     *
     * @param vo
     * @throws CaesarException
     */
    public void save(final ReportVO vo) throws CaesarException {

        methodLog(LOG, "save", param(Constants.VO, vo));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.REPORT);

        vo.setId(new ObjectId().toHexString());
        vo.setCreated(new Date());

        final Document model = ReportBSON.builder(vo).build();
        collection.insertOne(model);

    }

    /**
     * Set a specific report as 'viewed'
     *
     * @param id
     * @throws CaesarException
     */
    public void view(final String id) throws CaesarException {

        methodLog(LOG, "view", param(Constants.ID, id));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.REPORT);

        final Document value = new Document("$set", new Document(ReportBSON.VIEWED_KEY, new Date()));

        collection.updateOne(eq(ReportBSON.ID_KEY, new ObjectId(id)), value);
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

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.REPORT);

        final List<ReportVO> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().skip(pageSize * (page - 1)).limit(pageSize).iterator();) {
            while (cursor.hasNext()) {
                list.add(ReportBSON.parse(cursor.next()));
            }
        }

        return new PagedVO(list, collection.count());
    }

}
