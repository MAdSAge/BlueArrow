package pyq.qbank.bluearrow;

import android.content.Context;
import android.util.Log;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import java.util.Arrays;

public class DataImporter {
    private static final String TAG = "DataImporter";
    private static final int BATCH_SIZE = 500;

    private final Box<mcq_model> personBox;
    private final Box<iconOrderLoader> iconOrderBox;
    private final Box<test_model> testBox;
    private final BoxStore boxStore;

    public DataImporter(BoxStore boxStore) {
        this.boxStore = boxStore;
        personBox = boxStore.boxFor(mcq_model.class);
        iconOrderBox = boxStore.boxFor(iconOrderLoader.class);
        testBox = boxStore.boxFor(test_model.class);
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
        processDataBatch(jsonLoader.loadQbankMcq(context), personBox);

        // Import icon order
        processDataBatch(jsonLoader.loadIconOrder(context), iconOrderBox);

        // Import mini tests
        processDataBatch(jsonLoader.miniTestMcqLoaders(context), testBox);

        // Import subject tests
        processDataBatch(jsonLoader.subjectTestMcqLoader(context), testBox);
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
                }
                box.put(batch);
            });
        }
    }

    private void importGrandTests(Context context) {
        String[] grandTestFiles = {"part1.json", "part2.json", "part3.json","part4.json","part5.json","part6.json","part7.json","part8.json","part9.json","part10.json"};

        for (String fileName : grandTestFiles) {
            test_model[] grandTest = jsonLoader.grandTestMcqLoader(context, fileName);
            processDataBatch(grandTest, testBox);
        }
    }

    private void releaseResources() {

            System.gc(); // Suggest garbage collection

    }
}