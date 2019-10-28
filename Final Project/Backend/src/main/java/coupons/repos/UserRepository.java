package coupons.repos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import coupons.database.MongoConnection;
import coupons.enums.Privilege;
import coupons.exceptions.DataException;
import coupons.models.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;


public class UserRepository {

    private MongoCollection<Document> collection = MongoConnection.getUsersCollection();

    public User getId(String id) throws DataException {
        Document document = collection.find(eq("_id", new ObjectId(id))).first();

        return getUser(document);
    }

    public User get(String username) throws DataException {

        Document document = collection.find(eq("username", username)).first();

        return getUser(document);
    }

    private User getUser(Document document) throws DataException {
        try {
            if (document != null) {
                return new User(
                        document.getObjectId("_id").toString(),
                        document.getString("firstName"),
                        document.getString("lastName"),
                        Privilege.valueOf(document.getString("privilege")),
                        document.getString("username"),
                        document.getString("password")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DataException();
        }
    }

    public User create(String firstName, String lastName, String privilege, String username, String password) {

        Document entry = new Document()
                .append("username", username)
                .append("password", password)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("privilege", privilege);

        collection.insertOne(entry);

        User user = new User(entry.getObjectId("_id").toString(),
                entry.getString("firstName"),
                entry.getString("lastName"),
                Privilege.valueOf(entry.getString("privilege")),
                entry.getString("username"),
                entry.getString("password")
        );

        System.out.println("Added new user: " + user);

        return user;

    }

}
