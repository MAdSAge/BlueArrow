package pyq.qbank.bluearrow;

public class subject_model {

    private String subjectName;
    private int  subjectImage;
    private int subjectTopicCount;
    private int Progress;

    public subject_model(String subjectName, int subjectImage, int subjectTopicCount, int progress) {
        this.subjectName = subjectName;
        this.subjectImage = subjectImage;
        this.subjectTopicCount = subjectTopicCount;
        this.Progress = progress;
    }

    public subject_model(int subjectTopicCount, int subjectImage, String subjectName) {
        this.subjectTopicCount = subjectTopicCount;
        this.subjectImage = subjectImage;
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(int subjectImage) {
        this.subjectImage = subjectImage;
    }

    public int getSubjectTopicCount() {
        return subjectTopicCount;
    }

    public void setSubjectTopicCount(int subjectTopicCount) {
        this.subjectTopicCount = subjectTopicCount;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }
}