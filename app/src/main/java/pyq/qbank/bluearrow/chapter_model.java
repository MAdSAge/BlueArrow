package pyq.qbank.bluearrow;

public class chapter_model {
    private String chapterName;
    private int chapterQuestionCount;
    private int progress;

    public chapter_model(String chapterName, int chapterQuestionCount, int progress) {
        this.chapterName = chapterName;
        this.chapterQuestionCount = chapterQuestionCount;
        this.progress = progress;
    }

    public String getChapterName() {
        return chapterName;
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
