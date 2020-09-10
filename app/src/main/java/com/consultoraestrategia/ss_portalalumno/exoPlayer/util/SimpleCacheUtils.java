package com.consultoraestrategia.ss_portalalumno.exoPlayer.util;

import android.content.Context;

import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class SimpleCacheUtils {

    private static SimpleCache mInstance;

    private SimpleCacheUtils() {
    }

    public static SimpleCache getInstance(Context context) {
        if(mInstance==null){
            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(((150L * 1024L * 1024L)));
            DatabaseProvider databaseProvider = new ExoDatabaseProvider(context);
            mInstance = new SimpleCache(new File(context.getCacheDir(), "media"), evictor, databaseProvider);
        }

        return mInstance;
    }
}
