package com.woodys.router.activityresult;

import android.content.Intent;

public interface ActivityResultCallback {
    void onResult(int resultCode, Intent data);
}
