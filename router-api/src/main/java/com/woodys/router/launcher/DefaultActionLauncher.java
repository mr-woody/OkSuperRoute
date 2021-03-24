
package com.woodys.router.launcher;

import android.content.Context;
import android.os.Bundle;

import com.woodys.router.route.ActionSupport;
import com.woodys.router.route.impl.ActionRoute;

/**
 * Default Action Launcher for {@link ActionRoute}
 */
public class DefaultActionLauncher extends ActionLauncher{

    @Override
    public void open(Context context) {
        final ActionSupport support = newInstance(rule.getRuleClz());
        final Bundle data = new Bundle();
        data.putAll(bundle);
        data.putAll(extras.getExtras());
        getExecutor().execute(new ActionRunnable(support, context, data));
    }

    private static class ActionRunnable implements Runnable {

        ActionSupport support;
        Context context;
        Bundle data;

        ActionRunnable(ActionSupport support, Context context, Bundle data) {
            this.support = support;
            this.context = context;
            this.data = data;
        }

        @Override
        public void run() {
            support.invoke(context, data);
        }
    }

    private ActionSupport newInstance(String name) {
        try {
            return (ActionSupport) Class.forName(name).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("create instance of %s failed", name), e);
        }
    }
}
