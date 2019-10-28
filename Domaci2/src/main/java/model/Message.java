package model;

import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private Intent intent;
    private Object data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return "Message{" + "id='" + id + '\'' + ", intent=" + intent + '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
