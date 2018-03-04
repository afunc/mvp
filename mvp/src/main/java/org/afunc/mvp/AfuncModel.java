package org.afunc.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 紫紫 on 2017/8/7
 *         Q157596462@outlook.com
 *         描述：
 */
public class AfuncModel {

    private Context mContext;
    private static Map<String, AfuncModel> mInstanceMap = new HashMap<>();

    private final String TAG = "AfuncModel";
    /**
     * 缓存对象文件目录
     */
    private final String OBJECT_CACHE = "ObjectCache";
    private String mObjectCachePath;
    protected SharedPreferences mSP;
    protected SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public AfuncModel(Context context) {
        mContext=context;
        mSP = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        mEditor = mSP.edit();
        mObjectCachePath = mContext.getExternalFilesDir(OBJECT_CACHE).getAbsolutePath();
    }

    @SuppressWarnings("unchecked")
    public static <T extends AfuncModel> T getInstance(Class<T> model) {
        if (!mInstanceMap.containsKey(model.getSimpleName())) {
            synchronized (model) {
                try {
                    T instance = model.newInstance();
                    mInstanceMap.put(model.getSimpleName(), instance);
                    return instance;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return (T) mInstanceMap.get(model.getSimpleName());
    }

    /**
     * 通过sp保存基本类型数据
     * @param key key
     * @param value value
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public int getInt(String key, int defaultInt) {
        return mSP.getInt(key, defaultInt);
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public float getFloat(String key, float defaultFloat) {
        return mSP.getFloat(key, defaultFloat);
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public long getLong(String key, long defaultLong) {
        return mSP.getLong(key, defaultLong);
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public String getString(String key, String defaultString) {
        return mSP.getString(key, defaultString);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public boolean getBooolean(String key, boolean defaultBoolean) {
        return mSP.getBoolean(key, defaultBoolean);
    }

    public void setStringSet(String key, Set<String> set) {
        mEditor.putStringSet(key, set);
    }

    public Set<String> getStringSet(String key, Set<String> defaultSet) {
        return mSP.getStringSet(key, defaultSet);
    }

    /**
     * 通过文件保存对象数据，对象必须可序列化。通过清理app数据即可清理掉数据
     * 目录:SDCard/Android/data/应用包名/data/files/ObjectCache
     * @param key key
     * @param value value
     */
    public void putObject(String key, Object value) {
        File objectFile;
        try {
            objectFile = new File(mObjectCachePath + "/" + key);
            if (objectFile.exists()) {
                objectFile.delete();
            }

            if (objectFile.createNewFile()) {
                ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream(objectFile));
                obs.writeObject(value);
                obs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "文件流打开失败。");
        }

    }

    public Object getObject(String key) {
        File objectFile = new File(mObjectCachePath + "/" + key);
        if (!objectFile.exists()) {
            Log.e(TAG, "该对象没有被缓存");
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectFile));
            Object result = ois.readObject();
            ois.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "对象缓存读取失败");
        }
        return null;
    }

    public void clearCacheObject() {
        File cacheDir = new File(mObjectCachePath);
        if (cacheDir.exists() && cacheDir.isDirectory()) {
            deleteDir(cacheDir);
        }
    }

    protected void deleteDir(File dir) {
        if (dir.isDirectory() && dir.listFiles().length > 0) {
            for (File file : dir.listFiles()) {
                deleteDir(file);
            }
        }
        dir.delete();
    }
}
