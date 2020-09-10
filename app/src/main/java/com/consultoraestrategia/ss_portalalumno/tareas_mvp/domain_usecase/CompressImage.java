package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import android.content.Context;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

import java.io.File;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import me.shaohui.advancedluban.OnMultiCompressListener;

public class CompressImage {
    Context context;
    private String TAG = "CompressImageTAG";

    public CompressImage(Context context) {
        this.context = context;
    }

    public void execute(List<File> mFileList, Callback callback){
        Luban.compress(context, mFileList)
                //.putGear(Luban.THIRD_GEAR)
                .setMaxSize(790)                // limit the final image size（unit：Kb）
                .setMaxHeight(3264)             // limit image height
                .setMaxWidth(3264)              // limit image width
                .putGear(Luban.CUSTOM_GEAR)
                .launch(new OnMultiCompressListener() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "start");
                    }

                    @Override
                    public void onSuccess(List<File> fileList) {
                        callback.onload(true, fileList );
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onload(false, mFileList );
                    }
                });
    }

    public interface Callback{
        void onload(boolean success, List<File> mFileList);
    }
}
