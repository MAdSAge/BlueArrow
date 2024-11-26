package pyq.qbank.bluearrow;

import android.content.Context;
import android.util.Log;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import java.util.Arrays;

public class DataImporter {
    private static final String TAG = "DataImporter";
    private static final int BATCH_SIZE = 500;

    private final BoxStore boxStore;

    private final Box<entitySubject> subjectBox;
    private final Box<entityChapter> chapterBox;
    private final Box<entityTest> testBox;
    private final Box<entityMcq> mcqBox;

    public DataImporter(BoxStore boxStore) {
        this.boxStore = boxStore;

        subjectBox = boxStore.boxFor(entitySubject.class);
        chapterBox = boxStore.boxFor(entityChapter.class);
        testBox = boxStore.boxFor(entityTest.class);
        mcqBox = boxStore.boxFor(entityMcq.class);
    }

    public void importData(Context context) {
        try {
            importDataInBatches(context);
            importGrandTests(context);
        } catch (Exception e) {
            Log.e(TAG, "Data import failed", e);
        } finally {
            releaseResources();
        }
    }

    private void importDataInBatches(Context context) {
        // Import MCQ data
        processDataBatch(jsonLoader.loadSubjects(context), subjectBox);


        // Import icon order
        processDataBatch(jsonLoader.loadChapters(context), chapterBox);

        //Import test details
        processDataBatch(jsonLoader.loadTests(context),testBox);


    }



    private <T> void processDataBatch(T[] data, Box<T> box) {
        if (data == null || data.length == 0) return;

        for (int i = 0; i < data.length; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, data.length);
            T[] batch = Arrays.copyOfRange(data, i, end);

            boxStore.runInTx(() -> {
                for (T item : batch) {
                    if (item instanceof mcq_model)
                        ((mcq_model) item).setId(0);
                    else if (item instanceof test_model)
                        ((test_model) item).setId(0);
                    else if (item instanceof iconOrderLoader)
                        ((iconOrderLoader) item).setId(0);
                    else if(item instanceof test_details);
                }
                box.put(batch);
            });
        }
    }

    private void importGrandTests(Context context) {
        String[] grandTestFiles = {"part1.json", "part2.json", "part3.json","part4.json","part5.json","part6.json","part7.json","part8.json","part9.json","part10.json",
        "part11.json","part12.json","part13.json","part14.json","part15.json","part16.json","part17.json","part18.json","part19.json","part20.json"};

        for (String fileName : grandTestFiles) {
            entityMcq[] grandTest = jsonLoader.grandTestMcqLoader(context, fileName);
            processDataBatch(grandTest, mcqBox);
        }





    }

    private void releaseResources() {

        System.gc();


    }
}