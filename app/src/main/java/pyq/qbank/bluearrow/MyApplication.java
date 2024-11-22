package pyq.qbank.bluearrow;

import android.app.Application;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends Application {

    protected static BoxStore boxStore;


    @Override
    public void onCreate() {
        super.onCreate();

        //initializing Object box
        ObjectBox.init(this);
        boxStore = ObjectBox.get();



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            registerActivityLifecycleCallbacks(new AppActivityLifecycleCallbacks());
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }






    private class AppActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            // Disable screenshots for each activity
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        @Override
        public void onActivityStarted(Activity activity) {}
        @Override
        public void onActivityResumed(Activity activity) {}
        @Override
        public void onActivityPaused(Activity activity) {}
        @Override
        public void onActivityStopped(Activity activity) {}
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
        @Override
        public void onActivityDestroyed(Activity activity) {}
    }

}
