package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import java.util.List;
import java.util.Map;

@Entity
public class entitySubject {

    @Id
    long id;

    private String subject_id;
    private int category;
    private String course_id;
    private int lesson_type_count_1;
    private int order;
    private String parent_id;
    private String published_status;
    private int solved_lesson_type_count_1;
    private int sort_order;
    private String sub_title;
    private String thumbnail;
    private String title;



    @Transient
    private long last_updated;

    @Transient
    private int childType;

    @Transient
    private String root_subject_id;

    @Transient
    private int solved_lesson_count;

    @Transient
    private int solved_lesson_type_count_0;

    @Transient
    private int lesson_count;

    @Transient
    private int lesson_type_count_0;

    @Transient
    private String editor_id;

    @Transient
    private String theight;

    @Transient
    private String twidth;

    @Transient
    private List<String> parent_ids;

    @Transient
    private Map<String, String> image_attribution;


    public entitySubject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getLesson_type_count_1() {
        return lesson_type_count_1;
    }

    public void setLesson_type_count_1(int lesson_type_count_1) {
        this.lesson_type_count_1 = lesson_type_count_1;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getPublished_status() {
        return published_status;
    }

    public void setPublished_status(String published_status) {
        this.published_status = published_status;
    }

    public int getSolved_lesson_type_count_1() {
        return solved_lesson_type_count_1;
    }

    public void setSolved_lesson_type_count_1(int solved_lesson_type_count_1) {
        this.solved_lesson_type_count_1 = solved_lesson_type_count_1;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(long last_updated) {
        this.last_updated = last_updated;
    }

    public int getChildType() {
        return childType;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }

    public String getRoot_subject_id() {
        return root_subject_id;
    }

    public void setRoot_subject_id(String root_subject_id) {
        this.root_subject_id = root_subject_id;
    }

    public int getSolved_lesson_count() {
        return solved_lesson_count;
    }

    public void setSolved_lesson_count(int solved_lesson_count) {
        this.solved_lesson_count = solved_lesson_count;
    }

    public int getSolved_lesson_type_count_0() {
        return solved_lesson_type_count_0;
    }

    public void setSolved_lesson_type_count_0(int solved_lesson_type_count_0) {
        this.solved_lesson_type_count_0 = solved_lesson_type_count_0;
    }

    public int getLesson_count() {
        return lesson_count;
    }

    public void setLesson_count(int lesson_count) {
        this.lesson_count = lesson_count;
    }

    public int getLesson_type_count_0() {
        return lesson_type_count_0;
    }

    public void setLesson_type_count_0(int lesson_type_count_0) {
        this.lesson_type_count_0 = lesson_type_count_0;
    }

    public String getEditor_id() {
        return editor_id;
    }

    public void setEditor_id(String editor_id) {
        this.editor_id = editor_id;
    }

    public String getTheight() {
        return theight;
    }

    public void setTheight(String theight) {
        this.theight = theight;
    }

    public String getTwidth() {
        return twidth;
    }

    public void setTwidth(String twidth) {
        this.twidth = twidth;
    }

    public List<String> getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(List<String> parent_ids) {
        this.parent_ids = parent_ids;
    }

    public Map<String, String> getImage_attribution() {
        return image_attribution;
    }

    public void setImage_attribution(Map<String, String> image_attribution) {
        this.image_attribution = image_attribution;
    }
}
