package com.hicaesar.nlp.repository;


import com.hicaesar.nlp.repository.bson.CoreEntityBSON;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;



import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.vo.EntityVO;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author samuelwaskow
 */
public final class EntityRepository {

    private static final Logger LOG = Logger.getLogger(EntityRepository.class);


    /**
     * Save a new domain
     *
     * @param vo
     * @return
     * @throws CaesarException
     */
    public EntityVO save(final EntityVO vo) throws CaesarException {

        methodLog(LOG, "save", param("vo", vo));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        vo.setId(new ObjectId().toHexString());

        final Document model = CoreEntityBSON.builder(vo).build();
        collection.insertOne(model);

        return vo;
    }

    /**
     * Save many documents
     *
     * @param vos
     *
     * @throws CaesarException
     */
    public void saveAll(final List<EntityVO> vos) throws CaesarException {

        methodLog(LOG, "saveAll", param("vos", vos));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final List<Document> docs = new ArrayList<>();
        vos.forEach(vo -> {
            vo.setId(new ObjectId().toHexString());
            docs.add(CoreEntityBSON.builder(vo).build());
        });

        collection.insertMany(docs); 
    }

    /**
     * Retrieve a entity
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    public EntityVO get(final Locale locale, final String text) throws CaesarException {

        methodLog(LOG, "get", param("locale", locale), param("text", text));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final Bson query = and(eq(CoreEntityBSON.LOCALE_KEY, locale.toString()),
                eq(CoreEntityBSON.VALUE_KEY, text));

        EntityVO out = new EntityVO();

        try (final MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                final Document doc = cursor.next();
                out = CoreEntityBSON.parse(doc);
            }
        }

        return out;
    }

    /**
     * Performs text filter
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    public List<EntityVO> list(final Locale locale, final String text) throws CaesarException {

        methodLog(LOG, "list", param("locale", locale), param("text", text));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final Bson query = and(eq(CoreEntityBSON.LOCALE_KEY, locale.toString()),
                text(text));

        List<EntityVO> out = new ArrayList<>();

        try (final MongoCursor<Document> cursor = collection.find(query)
                .projection(Projections.metaTextScore("score"))
                .sort(Sorts.metaTextScore("score"))
                .iterator()) {
            while (cursor.hasNext()) {
                final Document doc = cursor.next();
                out.add(CoreEntityBSON.parse(doc));
            }
        }

        return out;
    }

    /**
     * Delete an entity
     *
     * @param id
     * @throws CaesarException
     */
    public void delete(final String id) throws CaesarException {

        methodLog(LOG, "delete", param("id", id));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        collection.deleteOne(eq(CoreEntityBSON.ID_KEY, new ObjectId(id)));
    }

}
