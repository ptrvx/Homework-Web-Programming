package coupons.repos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import coupons.database.MongoConnection;
import coupons.models.Coupon;
import coupons.models.Shop;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class CouponRepository {

    private MongoCollection<Document> collection = MongoConnection.getCouponCollection();

    public List<Coupon> get(int page, boolean active) {
        page = Math.max(0, page-1);
        Date today = new Date();
        MongoCursor<Document> cursor;

        if (active) {
            cursor = collection.find(and(lte("validFrom", today), or(gte("validTo", today), eq("validTo", null)))).skip(20 * page).limit(20).iterator();
        } else {
            cursor = collection.find().skip(20 * page).limit(20).iterator();
        }

        return getCoupons(cursor);
    }

    public List<Coupon> get(String shopId) {
        MongoCursor<Document> cursor = collection.find(eq("shop._id", new ObjectId(shopId))).iterator();

        return getCoupons(cursor);
    }

    public long deleteShop(String shopId) {
        return collection.deleteMany(eq("shop._id", new ObjectId(shopId))).getDeletedCount();
    }

    public long delete(String id) {
        return collection.deleteMany(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    public long count(boolean active) {
        Date today = new Date();
        if (active) {
            return collection.countDocuments(and(lte("validFrom", today), or(gte("validTo", today), eq("validTo", null))));
        } else {
            return collection.countDocuments();
        }
    }

    private List<Coupon> getCoupons(MongoCursor<Document> cursor) {
        List<Coupon> coupons = new ArrayList<>();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                Document shop = document.get("shop",Document.class);
                Coupon coupon = new Coupon(
                        document.getObjectId("_id").toString(),
                        new Shop(shop.getObjectId("_id").toString(), shop.getString("name")),
                        document.getString("product"),
                        document.getDouble("discountedPrice").floatValue(),
                        document.getDouble("originalPrice").floatValue(),
                        document.getDate("validFrom"),
                        document.getDate("validTo")
                );
                coupons.add(coupon);
            }

        }
        finally {
            cursor.close();
        }

        return coupons;
    }

}
