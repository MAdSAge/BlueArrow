package pyq.qbank.bluearrow;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class entityCustomModule {

    @Id
    public long id;

    public int QuestionCount;
    public String CustomModuleName;

    // Relations to StringEntity for subject, tag, and chapter lists
    public ToMany<StringEntity> subjectList;
    public ToMany<StringEntity> tagList;
    public ToMany<StringEntity> chapterList;
    public String selectionFilter;

    // Default constructor
    public entityCustomModule() {
        // ObjectBox automatically handles the ToMany initialization, so no need to manually initialize them
    }

    public void setSelectionFilter(String selectionFilter) {
        this.selectionFilter = selectionFilter;
    }

    public String getCustomModuleName() {
        return CustomModuleName;
    }

    public void setCustomModuleName(String customModuleName) {
        CustomModuleName = customModuleName;
    }

    public int getQuestionCount() {
        return QuestionCount;
    }

    public void setQuestionCount(int questionCount) {
        QuestionCount = questionCount;
    }

    // Utility methods to add items to the lists
    public void addSubject(String subject) {
        subjectList.add(new StringEntity(subject));
    }

    public void addTag(String tag) {
        tagList.add(new StringEntity(tag));
    }

    public void addChapter(String chapter) {
        chapterList.add(new StringEntity(chapter));
    }


    // Utility methods to remove items from the lists
    public void removeSubject(String subject) {
        StringEntity subjectToRemove = findEntityByValue(subjectList, subject);
        if (subjectToRemove != null) {
            subjectList.remove(subjectToRemove);
        }
    }

    public void removeTag(String tag) {
        StringEntity tagToRemove = findEntityByValue(tagList, tag);
        if (tagToRemove != null) {
            tagList.remove(tagToRemove);
        }
    }

    public void removeChapter(String chapter) {
        StringEntity chapterToRemove = findEntityByValue(chapterList, chapter);
        if (chapterToRemove != null) {
            chapterList.remove(chapterToRemove);
        }
    }


    // Helper method to find a StringEntity by its value
    private StringEntity findEntityByValue(ToMany<StringEntity> list, String value) {
        for (StringEntity entity : list) {
            if (entity.value.equals(value)) {
                return entity;
            }
        }
        return null;
    }

    // Getter and Setter methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToMany<StringEntity> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(ToMany<StringEntity> subjectList) {
        this.subjectList = subjectList;
    }

    public ToMany<StringEntity> getTagList() {
        return tagList;
    }

    public void setTagList(ToMany<StringEntity> tagList) {
        this.tagList = tagList;
    }

    public ToMany<StringEntity> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ToMany<StringEntity> chapterList) {
        this.chapterList = chapterList;
    }

    public String getSelectionFilter() {
        return selectionFilter;
    }
}
