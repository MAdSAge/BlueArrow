package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class StringEntity {
    @Id
    public long id;
    public String value;  // The string you want to store

    public StringEntity() {
    }

    public StringEntity(String value) {
        this.value = value;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
