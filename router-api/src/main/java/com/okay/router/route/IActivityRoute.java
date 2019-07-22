
package com.okay.router.route;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.okay.router.activityresult.ActivityResultCallback;
import com.okay.router.extras.RouteBundleExtras;
import com.okay.router.configs.Constants;
import com.okay.router.callback.InternalCallback;

/**
 * <p>
 * Base on the {@link IBaseRoute}, This interface provided some methods
 * to set some extras data for used by {@link android.app.Activity#startActivityForResult(Intent, int)}
 * </p>
 */
public interface IActivityRoute extends IBaseRoute<IActivityRoute> {

    /**
     * Create intent by {@link RouteBundleExtras} and {@link Bundle} that parsed by uri
     * @param context The context to create intent
     * @return Intent that contains of extras data and bundle that parsed by uri
     */
    Intent createIntent (Context context);

    /**
     * Set request code for {@link android.app.Activity#startActivityForResult(Intent, int)}
     * @param requestCode request code
     * @return IActivityRoute
     */
    IActivityRoute requestCode(int requestCode);

    IActivityRoute resultCallback(ActivityResultCallback callback);

    IActivityRoute setOptions(Bundle options);

    /**
     * Set anim for {@link android.app.Activity#overridePendingTransition(int, int)}
     * @param enterAnim enter animation
     * @param exitAnim exit animation
     * @return IActivityRoute
     */
    IActivityRoute setAnim (int enterAnim, int exitAnim);

    /**
     * Associate with {@link Intent#addFlags(int)}
     * @param flag flag
     * @return IActivityRoute
     */
    IActivityRoute addFlags(int flag);

    /**
     * Launch routing by {@link Fragment}
     * @param fragment the fragment to startActivity
     */
    void open(Fragment fragment);

    class EmptyActivityRoute extends EmptyBaseRoute<IActivityRoute> implements IActivityRoute {

        public EmptyActivityRoute(InternalCallback internal) {
            super(internal);
        }

        @Override
        public Intent createIntent(Context context) {
            internal.invoke(context);
            return new Intent();
        }

        @Override
        public IActivityRoute requestCode(int requestCode) {
            internal.getExtras().setRequestCode(requestCode);
            return this;
        }

        @Override
        public IActivityRoute resultCallback(ActivityResultCallback callback) {
            internal.getExtras().putValue(Constants.KEY_RESULT_CALLBACK, callback);
            return this;
        }

        @Override
        public IActivityRoute setOptions(Bundle options) {
            internal.getExtras().putValue(Constants.KEY_ACTIVITY_OPTIONS, options);
            return this;
        }

        @Override
        public IActivityRoute setAnim(int enterAnim, int exitAnim) {
            internal.getExtras().setInAnimation(enterAnim);
            internal.getExtras().setOutAnimation(exitAnim);
            return this;
        }

        @Override
        public IActivityRoute addFlags(int flag) {
            internal.getExtras().addFlags(flag);
            return this;
        }

        @Override
        public void open(Fragment fragment) {
            internal.invoke(fragment.getActivity());
        }
    }
}
