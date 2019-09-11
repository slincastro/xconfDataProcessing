package xconf.data.processor.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

public class MongoRepository {

    public void Save(String data){

        MongoCredential credential = MongoCredential.createCredential("storm", "test", "stormSecret".toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));

        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection collection = database.getCollection("twitter");

        int array = (int) collection.count();
    }
}
