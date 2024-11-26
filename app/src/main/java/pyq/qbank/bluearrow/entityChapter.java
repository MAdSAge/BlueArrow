package pyq.qbank.bluearrow;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class entityChapter {

    @Id
    public long id;
    private String chapter;
    private int chapter_order;
    private String chapter_thumbnail;
    private String chapter_url;
    private int chapter_id;
    private String lesson;
    private int lesson_order;
    private int mcq_count;
    private String subject;
    private int subject_order;
    private String subject_thumbnail;
    private String super_chapter;
    private boolean Chapter_completed;
    private int correct_count;
    private int wrong_count;
    private int skip_count;

    public entityChapter() {
    }

    public boolean isChapter_completed() {
        return Chapter_completed;
    }

    public void setChapter_completed(boolean chapter_completed) {
        Chapter_completed = chapter_completed;
    }

    public int getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(int correct_count) {
        this.correct_count = correct_count;
    }

    public int getWrong_count() {
        return wrong_count;
    }

    public void setWrong_count(int wrong_count) {
        this.wrong_count = wrong_count;
    }

    public int getSkip_count() {
        return skip_count;
    }

    public void setSkip_count(int skip_count) {
        this.skip_count = skip_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public int getChapter_order() {
        return chapter_order;
    }

    public void setChapter_order(int chapter_order) {
        this.chapter_order = chapter_order;
    }

    public String getChapter_thumbnail() {
        return chapter_thumbnail;
    }

    public void setChapter_thumbnail(String chapter_thumbnail) {
        this.chapter_thumbnail = chapter_thumbnail;
    }

    public String getChapter_url() {
        return chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public int getLesson_order() {
        return lesson_order;
    }

    public void setLesson_order(int lesson_order) {
        this.lesson_order = lesson_order;
    }

    public int getMcq_count() {
        return mcq_count;
    }

    public void setMcq_count(int mcq_count) {
        this.mcq_count = mcq_count;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSubject_order() {
        return subject_order;
    }

    public void setSubject_order(int subject_order) {
        this.subject_order = subject_order;
    }

    public String getSubject_thumbnail() {
        return subject_thumbnail;
    }

    public void setSubject_thumbnail(String subject_thumbnail) {
        this.subject_thumbnail = subject_thumbnail;
    }

    public String getSuper_chapter() {
        return super_chapter;
    }

    public void setSuper_chapter(String super_chapter) {
        this.super_chapter = super_chapter;
    }
}
