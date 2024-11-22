package pyq.qbank.bluearrow;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Array;

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
    public static mcq_model[] loadQbankMcq(Context context) {
        return loadJsonData(context, "questions.json", mcq_model[].class);
    }

    public static iconOrderLoader[] loadIconOrder(Context context) {
        return loadJsonData(context, "order_links.json", iconOrderLoader[].class);
    }

    public static test_model[] miniTestMcqLoaders(Context context) {
        return loadJsonData(context, "mini_test.json", test_model[].class);
    }

    public static test_model[] subjectTestMcqLoader(Context context) {
        return loadJsonData(context, "subject_test.json", test_model[].class);
    }
    public static test_details[]testDetailLoader(Context context){
        return loadJsonData(context,"all_test_details_pg.json",test_details[].class);
    }

    public static test_model[] grandTestMcqLoader(Context context, String fileName) {
        return loadJsonData(context, fileName, test_model[].class);
    }
}