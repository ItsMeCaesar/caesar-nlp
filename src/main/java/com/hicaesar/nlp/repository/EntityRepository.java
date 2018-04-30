package com.hicaesar.nlp.repository;

import com.hicaesar.nlp.repository.bson.CoreEntityBSON;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;

import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.util.StringUtil;
import com.hicaesar.nlp.vo.EntityVO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
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
    public void saveList(final List<EntityVO> vos) throws CaesarException {

        methodLog(LOG, "saveList", param("vos", vos));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final List<Document> docs = new ArrayList<>();
        vos.forEach(vo -> {
            vo.setId(new ObjectId().toHexString());
            docs.add(CoreEntityBSON.builder(vo).build());
        });

        collection.insertMany(docs);
    }

    /**
     * Retrieve an entity
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    public EntityVO get(final Locale locale, final String text) throws CaesarException {

        methodLog(LOG, "get", param("locale", locale), param("text", text));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final Bson query = Filters.and(Filters.eq(CoreEntityBSON.LOCALE_KEY, locale.toString()),
                Filters.eq(CoreEntityBSON.VALUE_KEY, text));

        EntityVO out = new EntityVO();

        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            if (cursor.hasNext()) {
                out = CoreEntityBSON.parse(cursor.next());
            }
        }

        return out;
    }

    /**
     * Performs text filter
     *
     * @param locale
     * @param type
     * @param value
     * @return
     * @throws CaesarException
     */
    public List<EntityVO> list(final Locale locale, final String type, final String value) throws CaesarException {

        methodLog(LOG, "list", param("locale", locale), param("type", type), param("value", value));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.ENTITY);

        final List<Bson> filters = new ArrayList<>();
        filters.add(Filters.eq(CoreEntityBSON.LOCALE_KEY, locale.toString()));
        filters.add(Filters.eq(CoreEntityBSON.TYPE_KEY, type));
        if (StringUtil.isNotEmpty(value)) {
            filters.add(Filters.text(value));
        }

        List<EntityVO> out = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find(Filters.and(filters))
                .projection(Projections.metaTextScore("score"))
                .sort(Sorts.metaTextScore("score"))
                .iterator()) {
            while (cursor.hasNext()) {
                out.add(CoreEntityBSON.parse(cursor.next()));
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

        collection.deleteOne(Filters.eq(CoreEntityBSON.ID_KEY, new ObjectId(id)));
    }

}
