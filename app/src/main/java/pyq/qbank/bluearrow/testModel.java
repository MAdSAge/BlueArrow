package pyq.qbank.bluearrow;

import java.util.Date;

public class testModel {

    int id;
    String title;
    long timStamp;
    String type;

    public testModel(String title, long year, int id, String type) {
        this.title = title;
        this.timStamp = year;
        this.id = id;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getYear() {
        return timStamp;
    }

    public void setYear(long year) {
        timStamp = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
