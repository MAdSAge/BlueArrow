package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.objectbox.Box;

public class customModuleFilter {

    entityCustomModule cm;

    public customModuleFilter(entityCustomModule cm) {
        this.cm = cm;
    }


    private String[] getChaptersFromSubjects() {
        String[] subjects = cm.getSubjectList().stream().map(StringEntity::getValue).toArray(String[]::new);

        if (subjects.length != 0) {
            Box<entityChapter> chapterBox = boxStore.boxFor(entityChapter.class);
            String[] query = chapterBox.query(entityChapter_.subject.oneOf(subjects)).build().property(entityChapter_.chapter).findStrings();
            return query;
        } else {
            return new String[0];
        }

    }


    protected List<entityMcq> getAllMCQS(int limit) {


        String[] chapters1 = getChaptersFromSubjects();
        String[] chapters2 = cm.getChapterList().stream().map(StringEntity::getValue).toArray(String[]::new);

        String[] mergedChapters = Stream.concat(Arrays.stream(chapters1), Arrays.stream(chapters2)).toArray(String[]::new);

        String[] tags = cm.getTagList().stream().map(StringEntity::getValue).toArray(String[]::new);

        Box<entityMcq> mcqBox = boxStore.boxFor(entityMcq.class);
        long[] query = mcqBox.query(entityMcq_.chapter.oneOf(mergedChapters)).build().property(entityMcq_.id).distinct().findLongs();

        List<entityMcq> mcqs = new ArrayList<>();


        for (long id : query) {
            entityMcq mcq = getMcq(id);

            // Split current MCQ tags
            String[] currentTags = mcq.getTags().trim().split(",");

            // Check if any of the tags match
            boolean hasMatchingTag = Arrays.stream(currentTags).map(String::trim) // Clean up whitespace
                    .anyMatch(tag -> Arrays.asList(tags).contains(tag));

            if (hasMatchingTag) {
                // Do something with the MCQ if a tag matches
                mcqs.add(getMcq(id));
                // Add your desired logic here (e.g., add to a list, perform an action, etc.)
            }


        }
        Collections.shuffle(mcqs);
        return mcqs.stream().limit(limit).collect(Collectors.toList());
    }


    private entityMcq getMcq(long id) {
        Box<entityMcq> McqBox = boxStore.boxFor(entityMcq.class);
        entityMcq mcq = McqBox.get(id);
        return mcq;
    }
}
