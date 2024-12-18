package pyq.qbank.bluearrow;

public class chapter_model {
    private String lesson;
    private String chapterName;
    private int chapterQuestionCount;
    private int progress;


    public chapter_model(String chapterName, int chapterQuestionCount, int progress,String lesson) {
        this.chapterName = chapterName;
        this.chapterQuestionCount = chapterQuestionCount;
        this.progress = progress;
        this.lesson = lesson;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getChapterQuestionCount() {
        return chapterQuestionCount;
    }

    public void setChapterQuestionCount(int chapterQuestionCount) {
        this.chapterQuestionCount = chapterQuestionCount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
