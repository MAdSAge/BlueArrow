package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class entityTest {

    @Id
    public long id;

    private String chapter_id;
    private int course_id;
    private int duration;
    private long end_datetime;
    private String intro;
    private boolean is_coming_soon;
    private boolean is_paid;
    private boolean is_review_avl;
    private long last_updated;
    private int max_mcq_count;
    private int mcq_count;
    private int possible_score;
    private String published_status;
    private int solved;
    private int sort_order;
    private long start_datetime;
    private int status;
    private String subject_id;
    private String test_type;
    private String title;

    public entityTest() {

    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(long end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isIs_coming_soon() {
        return is_coming_soon;
    }

    public void setIs_coming_soon(boolean is_coming_soon) {
        this.is_coming_soon = is_coming_soon;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public boolean isIs_review_avl() {
        return is_review_avl;
    }

    public void setIs_review_avl(boolean is_review_avl) {
        this.is_review_avl = is_review_avl;
    }

    public long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(long last_updated) {
        this.last_updated = last_updated;
    }

    public int getMax_mcq_count() {
        return max_mcq_count;
    }

    public void setMax_mcq_count(int max_mcq_count) {
        this.max_mcq_count = max_mcq_count;
    }

    public int getMcq_count() {
        return mcq_count;
    }

    public void setMcq_count(int mcq_count) {
        this.mcq_count = mcq_count;
    }

    public int getPossible_score() {
        return possible_score;
    }

    public void setPossible_score(int possible_score) {
        this.possible_score = possible_score;
    }

    public String getPublished_status() {
        return published_status;
    }

    public void setPublished_status(String published_status) {
        this.published_status = published_status;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public long getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(long start_datetime) {
        this.start_datetime = start_datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
