package coupons.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {

    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase mongoDatabase = mongoClient.getDatabase("kupoman");

    private static MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
    private static MongoCollection<Document> shopsCollection = mongoDatabase.getCollection("shops");
    private static MongoCollection<Document> couponCollection = mongoDatabase.getCollection("coupons");

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoCollection<Document> getUsersCollection() {
        return usersCollection;
    }

    public static MongoCollection<Document> getShopsCollection() {
        return shopsCollection;
    }

    public static MongoCollection<Document> getCouponCollection() {
        return couponCollection;
    }

}
