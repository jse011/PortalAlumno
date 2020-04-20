package com.consultoraestrategia.ss_portalalumno.util;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;

public class UtilsGlide {
    private static RequestOptions getOptions() {
        return new RequestOptions();
    }

    public static RequestOptions getGlideRequestOptionsSimple() {
        return getOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }


    public static RequestOptions getGlideRequestOptions() {
        return getOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_outline_black);
    }

    public static RequestOptions getGlideRequestOptions(@DrawableRes int res) {
        return getOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(res);
    }

    public static RequestOptions getGlideRequestOptions(Drawable drawable) {
        return getOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(drawable);
    }
}
