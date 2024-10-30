package DBConfig;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBConfig {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "aula48";

    public static MongoDatabase getDataBase() {
        MongoClient mongoClient = MongoClients.create(URI);
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
