
package com.woodys.router.extras;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.woodys.router.interceptors.RouteInterceptor;
import com.woodys.router.route.IBaseRoute;
import com.woodys.router.callback.RouteCallback;
import com.woodys.router.tools.TempDataCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extra container contains {@link RouteBundleExtras#extras} and {@link RouteBundleExtras#interceptors}
 * <p>
 *      <i>extras: the extra bundle data set by {@link IBaseRoute#addExtras(Bundle)}</i>
 *      <i>interceptors: the extra RouteInterceptor set by {@link IBaseRoute#addInterceptor(RouteInterceptor)}</i>
 * </p>
 *
 * @see IBaseRoute
 */
public final class RouteBundleExtras implements Parcelable{

    private ArrayList<RouteInterceptor> interceptors = new ArrayList<>();
    private RouteCallback callback;
    private Map<String, Object> additionalMap = new HashMap<>();

    private Bundle extras = new Bundle();
    // the extras belows is only supports for ActivityRoute.
    private int requestCode = -1;
    private int inAnimation = -1;
    private int outAnimation = -1;
    private int flags = 0;

    public RouteBundleExtras() {}

    public void addInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null && !getInterceptors().contains(interceptor)) {
            getInterceptors().add(interceptor);
        }
    }

    public void removeInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null) {
            getInterceptors().remove(interceptor);
        }
    }

    public void removeAllInterceptors() {
        getInterceptors().clear();
    }

    public List<RouteInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setCallback(RouteCallback callback) {
        this.callback = callback;
    }

    public RouteCallback getCallback() {
        return callback;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getInAnimation() {
        return inAnimation;
    }

    public void setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
    }

    public int getOutAnimation() {
        return outAnimation;
    }

    public void setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
    }

    public int getFlags() {
        return flags;
    }

    public void addFlags(int flags) {
        this.flags |= flags;
    }

    public <T> T getValue(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }

        try {
            //noinspection unchecked
            return (T) additionalMap.get(key);
        } catch (ClassCastException cast) {
            return null;
        }
    }

    public void putValue(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }

        additionalMap.put(key, value);
    }

    // ------------------------- divider for parcelable ------------------------
    private RouteBundleExtras(Parcel in) {
        requestCode = in.readInt();
        inAnimation = in.readInt();
        outAnimation = in.readInt();
        flags = in.readInt();
        extras = in.readBundle(getClass().getClassLoader());

        ArrayList<RouteInterceptor> interceptors = TempDataCache.get().get(in.readInt());
        RouteCallback callback = TempDataCache.get().get(in.readInt());
        Map<String, Object> additionalMap = TempDataCache.get().get(in.readInt());

        if (interceptors != null) {
            this.interceptors = interceptors;
        }
        if (callback != null) {
            this.callback = callback;
        }
        if (additionalMap != null) {
            this.additionalMap = additionalMap;
        }
    }

    public static final Creator<RouteBundleExtras> CREATOR = new Creator<RouteBundleExtras>() {
        @Override
        public RouteBundleExtras createFromParcel(Parcel in) {
            return new RouteBundleExtras(in);
        }

        @Override
        public RouteBundleExtras[] newArray(int size) {
            return new RouteBundleExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(requestCode);
        dest.writeInt(inAnimation);
        dest.writeInt(outAnimation);
        dest.writeInt(this.flags);

        dest.writeBundle(extras);

        dest.writeInt(TempDataCache.get().put(interceptors));
        dest.writeInt(TempDataCache.get().put(callback));
        dest.writeInt(TempDataCache.get().put(additionalMap));
    }

    public Bundle getExtras() {
        return extras;
    }

    public void addExtras(Bundle extras) {
        if (extras != null) {
            this.extras.putAll(extras);
        }
    }
}
