package com.test.kvstore;

import android.content.SharedPreferences;
import android.util.Log;

import com.test.kvstore.object.GlobalConfig;

import io.fastkv.FastPreferences;

public class CommonStoreV1 {
    public static final String NAME = "common_store";
    // public static final SharedPreferences preferences = GlobalConfig.appContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    public static final SharedPreferences preferences = FastPreferences.adapt(GlobalConfig.appContext, NAME);

    static {
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> Log.d("MyTag", "changed key:" + key));
    }

    public static final String LAUNCH_COUNT = "launch_count";
    public static final String DEVICE_ID = "device_id";
    public static final String INSTALL_ID = "install_id";

}
