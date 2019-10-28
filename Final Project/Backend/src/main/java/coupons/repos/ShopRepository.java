package coupons.repos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import coupons.database.MongoConnection;
import coupons.models.Shop;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ShopRepository {

    private MongoCollection<Document> collection = MongoConnection.getShopsCollection();

    public List<Shop> get() {
        MongoCursor<Document> cursor = collection.find().iterator();

        return getShops(cursor);
    }

    public List<Shop> get(int page) {
        page = Math.max(0, page-1);

        MongoCursor<Document> cursor = collection.find().skip(20 * page).limit(20).iterator();

        return getShops(cursor);
    }

    public long total() {
        return collection.countDocuments();
    }

    public Shop find(String id) {
        Document document = collection.find(eq("_id", new ObjectId(id))).first();

        if (document != null) {
            return new Shop(id, document.getString("name"));
        } else {
            return null;
        }
    }

    public long delete(String id) {
        return collection.deleteMany(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    public Shop create(String name) {
        Shop shop = new Shop(name);

        Document doc = new Document("name", name);

        collection.insertOne(doc);

        shop.setId(doc.getObjectId("_id").toString());

        return shop;
    }

    private List<Shop> getShops(MongoCursor<Document> cursor) {
        List<Shop> shops = new ArrayList<>();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                Shop shop = new Shop(
                        document.getObjectId("_id").toString(),
                        document.getString("name")
                );
                shops.add(shop);
            }
        } finally {
            cursor.close();
        }

        return shops;
    }
}
