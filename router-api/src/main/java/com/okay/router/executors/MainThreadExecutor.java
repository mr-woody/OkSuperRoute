
package com.okay.router.executors;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor{

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            mainHandler.post(action);
        }
    }
}
