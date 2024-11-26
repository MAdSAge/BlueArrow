package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class iconOrderLoader {
    @Id
    public long id;

    public String super_chapter;
    public String subject;
    public String subject_thumbnail;
    public int subject_order;
    public String lesson;
    public int lesson_order;
    public String chapter;
    public String chapter_url;
    public String chapter_thumbnail;
    public int chapter_order;
    public int chapter_progress;
    public boolean chapter_completed;
    public int mcq_count;
    public int skip_count;
    public int correct_count;
    public int wrong_count;

    public iconOrderLoader(){}


    public iconOrderLoader(String super_chapter, String subject, String subject_thumbnail, int subject_order, String lesson, int lesson_order, String chapter, String chapter_url, String chapter_thumbnail, int chapter_order) {
        this.super_chapter = super_chapter;
        this.subject = subject;
        this.subject_thumbnail = subject_thumbnail;
        this.subject_order = subject_order;
        this.lesson = lesson;
        this.lesson_order = lesson_order;
        this.chapter = chapter;
        this.chapter_url = chapter_url;
        this.chapter_thumbnail = chapter_thumbnail;
        this.chapter_order = chapter_order;
    }

    public int getSkip_count() {
        return skip_count;
    }

    public void setSkip_count(int skip_count) {
        this.skip_count = skip_count;
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

    public int getChapter_progress() {
        return chapter_progress;
    }
    public int getMcq_count() {
        return mcq_count;
    }
    public void setMcq_count(int mcq_count) {
        this.mcq_count = mcq_count;
    }

    public void setChapter_progress(int chapter_progress) {
        this.chapter_progress = chapter_progress;
    }

    public boolean isChapter_completed() {
        return chapter_completed;
    }

    public void setChapter_completed(boolean chapter_completed) {
        this.chapter_completed = chapter_completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSuper_chapter() {
        return super_chapter;
    }

    public void setSuper_chapter(String super_chapter) {
        this.super_chapter = super_chapter;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject_thumbnail() {
        return subject_thumbnail;
    }

    public void setSubject_thumbnail(String subject_thumbnail) {
        this.subject_thumbnail = subject_thumbnail;
    }

    public int getSubject_order() {
        return subject_order;
    }

    public void setSubject_order(int subject_order) {
        this.subject_order = subject_order;
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapter_url() {
        return chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public String getChapter_thumbnail() {
        return chapter_thumbnail;
    }

    public void setChapter_thumbnail(String chapter_thumbnail) {
        this.chapter_thumbnail = chapter_thumbnail;
    }

    public int getChapter_order() {
        return chapter_order;
    }

    public void setChapter_order(int chapter_order) {
        this.chapter_order = chapter_order;
    }
}
