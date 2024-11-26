package pyq.qbank.bluearrow;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Array;


//TODO: implementing the Compressed json import from TOR URLS
public class jsonLoader {
    private static final String TAG = "JsonLoader";
    private static final Gson gson = new Gson();

    public static <T> T[] loadJsonData(Context context, String fileName, Class<T[]> dataType) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open(fileName)))) {

            T[] data = gson.fromJson(reader, dataType);
            Log.d(TAG, "Successfully loaded " + fileName + ": " + (data != null ? data.length : 0) + " items");

            return data != null ? data : (T[]) Array.newInstance(dataType.getComponentType(), 0);
        } catch (IOException e) {
            Log.e(TAG, "Error loading " + fileName, e);
            return (T[]) Array.newInstance(dataType.getComponentType(), 0);
        }
    }

    // Existing methods converted to use generic method
    public static entitySubject[] loadSubjects(Context context) {
        return loadJsonData(context, "subject_order.json", entitySubject[].class);
    }

    public static entityChapter[] loadChapters(Context context) {
        return loadJsonData(context, "chapter_order.json", entityChapter[].class);
    }

    public static entityTest[] loadTests(Context context) {
        return loadJsonData(context, "test_order.json", entityTest[].class);
    }


    public static entityMcq[] grandTestMcqLoader(Context context, String fileName) {
        return loadJsonData(context, fileName, entityMcq[].class);
    }
}