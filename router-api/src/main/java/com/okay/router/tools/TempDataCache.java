package com.okay.router.tools;

import java.util.Arrays;

/**
 * 进行临时性存储。方便将不方便放入Intent中进行存储的数据进行存储：
 *
 *
 * <pre>
 * // 举例：跨页面传递Context
 *
 * // 跳转前存储context
 * Intent intent = getIntent();
 * int index = TempDataCache.get().put(context);
 * intent.putExtra(KEY_INDEX_CONTEXT, index);
 * startActivity(intent);
 *
 * // 跳转后读取context
 * Intent intent = getIntent();
 * int index = intent.getExtra(KEY_INDEX_CONTEXT);
 * // 读取后自动从CacheStore容器中移除。
 * Context context = TempDataCache.get().get(index);
 * </pre>
 */
public class TempDataCache {

    private Object[] objects = new Object[10];

    private static TempDataCache INSTANCE = new TempDataCache();
    private TempDataCache(){}
    public static TempDataCache get() {
        return INSTANCE;
    }

    public <T> T get(int index) {
        if (index < 0 || index >= objects.length) {
            return null;
        }
        Object value = objects[index];
        objects[index] = null;
        try {
            //noinspection unchecked
            return (T) value;
        } catch (ClassCastException cast) {
            return null;
        }
    }

    public int put(Object value) {
        if (value == null) {
            return -1;
        }
        int index = findIndex(value);
        objects[index] = value;
        return index;
    }

    private int findIndex(Object value) {
        int firstEmptyIndex = -1;
        for (int i = 0; i < objects.length; i++) {
            Object item = objects[i];
            if (item == null && firstEmptyIndex == -1) {
                firstEmptyIndex = i;
            }

            if (item == value) {
                return i;
            }
        }

        if (firstEmptyIndex == -1) {
            // 到此说明容器已满，需要扩容。定每次扩容大小为10
            int lastLength = objects.length;
            objects = Arrays.copyOf(objects, lastLength + 10);
            return lastLength;
        }
        // 返回首个空闲元素索引值。
        return firstEmptyIndex;
    }
}