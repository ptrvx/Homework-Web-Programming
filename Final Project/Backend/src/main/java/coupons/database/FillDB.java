package coupons.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FillDB {

    public static void main(String[] args) {

        MongoConnection.getMongoDatabase().drop();

        MongoCollection<Document> usersCollection = MongoConnection.getUsersCollection();
        MongoCollection<Document> shopsCollection = MongoConnection.getShopsCollection();
        MongoCollection<Document> couponsCollection = MongoConnection.getCouponCollection();

        usersCollection.insertOne(new Document("firstName", "Petar")
                .append("lastName", "Vukovic")
                .append("privilege", "ADMINISTRATOR")
                .append("username", "pvukovic16")
                .append("password", "aaa")
        );

        usersCollection.insertOne(new Document("firstName", "Bogdan")
                .append("lastName", "Vukovic")
                .append("privilege", "OPERATOR")
                .append("username", "bvukovic17")
                .append("password", "bbb")
        );

        usersCollection.insertOne(new Document("firstName", "Admin")
                .append("lastName", "Admins")
                .append("privilege", "ADMINISTRATOR")
                .append("username", "ptrve")
                .append("password", "123")
        );

        Document maxi = new Document("name", "maxi");
        Document roda = new Document("name", "roda");
        Document vero = new Document("name", "vero");
        Document usce = new Document("name", "usce");
        Document delta = new Document("name", "delta");

        shopsCollection.insertOne(maxi);
        shopsCollection.insertOne(roda);
        shopsCollection.insertOne(vero);
        shopsCollection.insertOne(usce);
        shopsCollection.insertOne(delta);

        List<Document> shops = new ArrayList<>();
        shops.add(maxi);
        shops.add(roda);
        shops.add(vero);
        shops.add(usce);
        shops.add(delta);

        Random random = new Random();

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

        couponsCollection.insertOne(new Document("product", "prod1")
                .append("shop", maxi)
                .append("discountedPrice", 44.)
                .append("originalPrice", 50.)
                .append("validFrom", today)
                .append("validTo", tomorrow)
        );

        couponsCollection.insertOne(new Document("product", "prod2")
                .append("shop", roda)
                .append("discountedPrice", 24.)
                .append("originalPrice", 30.)
                .append("validFrom", today)
                .append("validTo", tomorrow)
        );

        couponsCollection.insertOne(new Document("product", "prod3")
                .append("shop", vero)
                .append("discountedPrice", 14.)
                .append("originalPrice", 20.)
                .append("validFrom", today)
        );


        for (int i = 0; i < 127; i++) {
            couponsCollection.insertOne(new Document("product", "prod" + (i + 3))
                    .append("shop", shops.get(i%shops.size()))
                    .append("discountedPrice", 1. + random.nextInt(100) + 10)
                    .append("originalPrice", 1. + (random.nextInt(100) + 120))
                    .append("validFrom", today)
            );
        }


    }
}
