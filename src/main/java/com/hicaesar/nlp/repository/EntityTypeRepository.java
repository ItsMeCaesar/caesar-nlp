package com.hicaesar.nlp.repository;

import com.hicaesar.nlp.repository.bson.EntityTypeBSON;
import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import com.hicaesar.nlp.vo.EntityTypeVO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeRepository {

    private static final Logger LOG = Logger.getLogger(EntityTypeRepository.class);

   /**
     * Save a new entity type
     *
     * @param vo Value Object to be saved
     * @return Persisted value object
     * @throws CaesarException
     */
    public EntityTypeVO save(final EntityTypeVO vo) throws CaesarException {

        methodLog(LOG, "save", param(Constants.VO, vo));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        vo.setId(new ObjectId().toHexString());

        final Document model = EntityTypeBSON.builder(vo).build();
        collection.insertOne(model);

        return vo;
    }

    /**
     * List all entity types
     *
     * @return Persisted entity types
     * @throws CaesarException
     */
    public List<EntityTypeVO> list() throws CaesarException {

        methodLog(LOG, "list");

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        final List<EntityTypeVO> out = new ArrayList<>();

        try (final MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                final Document doc = cursor.next();
                out.add(EntityTypeBSON.parse(doc));
            }
        }

        return out;
    }

}
