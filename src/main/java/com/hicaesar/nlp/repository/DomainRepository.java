package com.hicaesar.nlp.repository;

import com.hicaesar.nlp.repository.bson.DomainBSON;
import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.DomainVO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class DomainRepository {

    private static final Logger LOG = Logger.getLogger(DomainRepository.class);

    /**
     * Save a new domain
     *
     * @param vo
     * @return
     * @throws CaesarException
     */
    public DomainVO save(final DomainVO vo) throws CaesarException {

        methodLog(LOG, "save", param("vo", vo));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.DOMAIN);

        vo.setId(new ObjectId().toHexString());
        collection.insertOne(DomainBSON.builder(vo).build());

        return vo;
    }

    /**
     * Retrieve a list of domains
     *
     * @return
     * @throws CaesarException
     */
    public List<DomainVO> list() throws CaesarException {

        methodLog(LOG, "list");

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.DOMAIN);

        List<DomainVO> out = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                out.add(DomainBSON.parse(cursor.next()));
            }
        }

        return out;
    }

    /**
     * Delete a domain
     *
     * @param id
     * @throws CaesarException
     */
    public void delete(final String id) throws CaesarException {

        methodLog(LOG, "delete", param("id", id));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.DOMAIN);

        collection.deleteOne(eq(DomainBSON.ID_KEY, new ObjectId(id)));
    }

}
