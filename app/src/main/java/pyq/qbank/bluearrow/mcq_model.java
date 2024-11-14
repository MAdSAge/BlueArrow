package pyq.qbank.bluearrow;

public class mcq_model {

    int id;
    String details;
    String mcq_id;
    String status;
    String question_title;
    String question_add;
    String question_img;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String explanation;
    String subject;
    String chapter;
    boolean bookmarked;
    int answer;
    int correctAnswer;


    public mcq_model(int id, String details, String mcq_id, String status, String question_title, String question_add, String question_img, String optionA, String optionB, String optionC, String optionD, String explanation, String subject, String chapter, boolean bookmarked, int answer, int correctAnswer) {
        this.id = id;
        this.details = details;
        this.mcq_id = mcq_id;
        this.status = status;
        this.question_title = question_title;
        this.question_add = question_add;
        this.question_img = question_img;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.explanation = explanation;
        this.subject = subject;
        this.chapter = chapter;
        this.bookmarked = bookmarked;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMcq_id() {
        return mcq_id;
    }

    public void setMcq_id(String mcq_id) {
        this.mcq_id = mcq_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
