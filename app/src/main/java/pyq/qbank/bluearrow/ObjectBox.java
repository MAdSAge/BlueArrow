package pyq.qbank.bluearrow;

import android.content.Context;

import io.objectbox.BoxStore;

public class ObjectBox {

    private static BoxStore store;

    private static BoxStore inMemoryStore;

    public static void init(Context context) {
        store = MyObjectBox.builder()
                .androidContext(context)
                .build();

//        inMemoryStore = MyObjectBox.builder()
//                .androidContext(context)
//                .inMemory("test-db")
//                .build();

    }

    public static BoxStore get() { return store; }
}
