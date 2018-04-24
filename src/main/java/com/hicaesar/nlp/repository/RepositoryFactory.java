package com.hicaesar.nlp.repository;

import com.hicaesar.nlp.support.ConstantType;
import com.hicaesar.nlp.support.RepositoryCollectionType;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;

import com.hicaesar.nlp.support.log.MethodLog;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author samuelwaskow
 */
public final class RepositoryFactory {

    private static final Logger LOG = Logger.getLogger(RepositoryFactory.class);

    private static final MongoClientURI URI = new MongoClientURI(ConstantType.NOSQL_URI.propertyValue());
    private static MongoClient client;
    private static MongoDatabase db;

    /**
     * Private Constructor
     */
    private RepositoryFactory() {
        super();
    }

    /**
     * Retrieve the MongoDB
     *
     * @return
     * @throws CaesarException
     */
    public static MongoDatabase getDB() throws CaesarException {

        methodLog(LOG, "getDB");

        try {
            if (client == null || db == null) {
                client = new MongoClient(URI);
                db = client.getDatabase(URI.getDatabase());
            }
            return db;
        } catch (Exception e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "Error instantiating the database");
        }
    }

    /**
     * Create required indexes
     *
     * @throws CaesarException
     */
    public static void createIndexes() throws CaesarException {

        methodLog(LOG, "createIndexes");
        
        createIndex(RepositoryCollectionType.ENTITY, Indexes.text("value"));
        createIndex(RepositoryCollectionType.ENTITY, Indexes.ascending("locale"));

    }

    /**
     * Adds a basic index to a collection
     *
     * @param collectionToCreate
     * @param index
     * @throws CaesarException
     */
    public static void createIndex(final RepositoryCollectionType collectionToCreate, final Bson index) throws CaesarException {

        methodLog(LOG, "createIndex", "collectionToCreate [" + collectionToCreate + "]", "index [" + index + "]");

        createIndex(collectionToCreate, index, new IndexOptions());
    }

    /**
     * Adds an index to a collection with advanced properties
     *
     * @param collectionToCreate
     * @param indexToCreate
     * @param options
     * @throws CaesarException
     */
    public static void createIndex(final RepositoryCollectionType collectionToCreate, final Bson indexToCreate, final IndexOptions options) throws CaesarException {

        final MethodLog log = methodLog(LOG, "createIndex", "collectionToCreate [" + collectionToCreate + "]", "indexToCreate [" + indexToCreate + "]");

        final MongoCollection<Document> collection = getDB().getCollection(collectionToCreate.name());
        final String status = collection.createIndex(indexToCreate, options);
        log.logDebug(status);
    }

    /**
     * Removes an index from a collection
     *
     * @param collectionToRemove
     * @param indexToRemove
     * @throws CaesarException
     */
    public static void removeIndex(final RepositoryCollectionType collectionToRemove, final Bson indexToRemove) throws CaesarException {

        methodLog(LOG, "removeIndex", "collectionToRemove [" + collectionToRemove + "]", "indexToRemove [" + indexToRemove + "]");

        final MongoCollection<Document> collection = getDB().getCollection(collectionToRemove.name());
        collection.dropIndex(indexToRemove);
    }

    /**
     * Retrieves a collection give its repository type
     *
     * @param type
     * @return
     * @throws CaesarException
     */
    public static MongoCollection<Document> getCollection(final RepositoryCollectionType type) throws CaesarException {

        methodLog(LOG, "getCollection", "type [" + type + "]");

        return getDB().getCollection(type.name());
    }
}
