package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.objectbox.Box;

public class GrandTestImporter {

    private static final int BATCH_SIZE = 10; // Adjust based on memory and processing speed
    private static final int THREAD_POOL_SIZE = 5;
    private static final int MAX_RETRIES = 3; // Maximum retry attempts for each file
    private static final String TAG = "GrandTestImporter";

    Box<entityMcq> mcqBox;

    public GrandTestImporter(Box<entityMcq> mcqBox) {
        this.mcqBox = mcqBox;
    }

    private void importGrandTestsFromUrl(Context context) throws IOException {
        String[] grandTestFiles = java.util.stream.IntStream.rangeClosed(1, 1000)
                .mapToObj(i -> "part" + i + ".json.gz")
                .toArray(String[]::new);

        TorFileDownloader tr = new TorFileDownloader(context);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (String file : grandTestFiles) {
            // Submit each download task to the executor for concurrent processing
            executorService.submit(() -> {
                int attempt = 0;
                boolean success = false;
                while (attempt < MAX_RETRIES && !success) {
                    try {
                        attempt++;
                        Log.i(TAG, "Attempting to download file: " + file + " (Attempt " + attempt + ")");

                        // Download the JSON as a JsonArray from the Onion URL
                        JsonArray json = tr.downloadFile("http://z5tkb2tiuesvhtfaixavc4cs2qcrfpriujxl6ujr2va4adwgu53ys2qd.onion/" + file);

                        // Deserialize JSON to entityMcq[] objects
                        Gson gson = new Gson();
                        entityMcq[] data = gson.fromJson(json, entityMcq[].class);

                        // Process the data in batches (batch size determined by BATCH_SIZE)
                        processDataBatch(data, mcqBox);
                        success = true;  // If no exception, consider the download a success
                        Log.i(TAG, "Successfully downloaded and processed file: " + file);

                    } catch (IOException e) {
                        // Handle the exception, log the error, and retry if necessary
                        Log.e(TAG, "Failed to download file: " + file + " (Attempt " + attempt + ")", e);
                        if (attempt == MAX_RETRIES) {
                            Log.e(TAG, "Max retries reached for file: " + file);
                        }
                    }
                }
            });
        }

        // Shutdown the executor service gracefully
        executorService.shutdown();
    }

    private <T> void processDataBatch(T[] data, Box<T> box) {
        if (data == null || data.length == 0) return;

        for (int i = 0; i < data.length; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, data.length);
            T[] batch = Arrays.copyOfRange(data, i, end);

            boxStore.runInTx(() -> {
                for (T item : batch) {
                    if (item instanceof entityMcq)
                        ((entityMcq) item).setId(0);
                    else if (item instanceof entityChapter)
                        ((entityChapter) item).setId(0);
                    else if (item instanceof entityTest)
                        ((entityTest) item).setId(0);
                }
                box.put(batch);
            });
        }
    }
}
