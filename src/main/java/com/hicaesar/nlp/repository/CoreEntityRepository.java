package com.hicaesar.nlp.repository;


import com.hicaesar.nlp.repository.bson.CoreEntityBSON;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;



import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.vo.CoreEntityVO;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;
import com.mongodb.client.model.InsertManyOptions;
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
public final class CoreEntityRepository {

    private static final Logger LOG = Logger.getLogger(CoreEntityRepository.class);

    /**
     * Private Constructor
     */
    private CoreEntityRepository() {
        super();
    }

    /**
     * Save a new domain
     *
     * @param vo
     * @return
     * @throws CaesarException
     */
    public static CoreEntityVO save(final CoreEntityVO vo) throws CaesarException {

        methodLog(LOG, "save", param("vo", vo));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

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
    public static void saveAll(final List<CoreEntityVO> vos) throws CaesarException {

        methodLog(LOG, "saveAll", param("vos", vos));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        final List<Document> docs = new ArrayList<>();
        vos.forEach(vo -> {
            vo.setId(new ObjectId().toHexString());
            docs.add(CoreEntityBSON.builder(vo).build());
        });

        collection.insertMany(docs, new InsertManyOptions().ordered(false)); // Duplicate write error
    }

    /**
     * Retrieve a list of domains
     *
     * @param locale
     * @param text
     * @return
     * @throws CaesarException
     */
    public static CoreEntityVO get(final Locale locale, final String text) throws CaesarException {

        methodLog(LOG, "get", param("locale", locale), param("text", text));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        final Bson query = and(eq(CoreEntityBSON.LOCALE_KEY, locale.toString()),
                eq(CoreEntityBSON.VALUE_KEY, text));

        CoreEntityVO out = new CoreEntityVO();

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
    public static List<CoreEntityVO> list(final Locale locale, final String text) throws CaesarException {

        methodLog(LOG, "list", param("locale", locale), param("text", text));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        final Bson query = and(eq(CoreEntityBSON.LOCALE_KEY, locale.toString()),
                text(text));

        List<CoreEntityVO> out = new ArrayList<>();

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
     * Delete a domain
     *
     * @param id
     * @throws CaesarException
     */
    public static void delete(final String id) throws CaesarException {

        methodLog(LOG, "delete", param("id", id));

        final MongoCollection<Document> collection = RepositoryFactory.getCollection(RepositoryCollectionType.CORE_ENTITY);

        collection.deleteOne(eq(CoreEntityBSON.ID_KEY, new ObjectId(id)));
    }

}
