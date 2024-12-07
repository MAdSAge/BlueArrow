package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class entityMcq {
    @Id
    public long id;
    
    private String chapter;
    private String chapter_id;
    private int correctAnswer;
    private String details;
    private String explanation;
    private int combined_mcq_id;
    private String mcq_id;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String question_add;
    private String question_img;
    private String question_title;
    private String status;
    private String subject;
    private String tags;
    private  long time;
    private int userAnswer;
    private boolean bookMarked;

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isBookMarked() {
        return bookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        this.bookMarked = bookMarked;
    }

    public entityMcq(){

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

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getCombined_mcq_id() {
        return combined_mcq_id;
    }

    public void setCombined_mcq_id(int combined_mcq_id) {
        this.combined_mcq_id = combined_mcq_id;
    }

    public String getMcq_id() {
        return mcq_id;
    }

    public void setMcq_id(String mcq_id) {
        this.mcq_id = mcq_id;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getQuestion_add() {
        return question_add;
    }

    public void setQuestion_add(String question_add) {
        this.question_add = question_add;
    }

    public String getQuestion_img() {
        return question_img;
    }

    public void setQuestion_img(String question_img) {
        this.question_img = question_img;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
