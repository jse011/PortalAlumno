package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC_Table;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.io.File;
import java.util.ArrayList;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class DowloadYoutube {

    private Context context;
    private static final String TAG = DowloadYoutube.class.getSimpleName();

    public DowloadYoutube(Context context) {
        this.context = context;
    }

    public void execute(RepositorioFileUi recursosUI) {
        new YouTubeExtractor(context) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                try {
                    if (ytFiles != null) {
                       ArrayList<Object> formatsToShowList = new ArrayList<>();
                        for (int i = 0, itag; i < ytFiles.size(); i++) {
                            itag = ytFiles.keyAt(i);
                            YtFile ytFile = ytFiles.get(itag);

                            if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                                String filename;
                                if (vMeta.getTitle().length() > 55) {
                                    filename = vMeta.getTitle().substring(0, 55) + "." + ytFile.getFormat().getExt();
                                } else {
                                    filename = vMeta.getTitle() + "." + ytFile.getFormat().getExt();
                                }
                                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
                                downloadFromUrl(ytFile.getUrl(), vMeta.getTitle(), filename, recursosUI);

                                break;
                            }
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "error al descargar", Toast.LENGTH_SHORT).show();
                }

            }
        }.extract(recursosUI.getUrl(), true, false);


    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName, RepositorioFileUi recursosUi) {
        Log.d(TAG, "youtubeDlUrl: " + youtubeDlUrl);
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        RecursoDidacticoEventoC recursoDidacticoEventoC = SQLite.select()
                .from(RecursoDidacticoEventoC.class)
                .where(RecursoDidacticoEventoC_Table.recursoDidacticoId.eq(recursosUi.getRecursoId()))
                .querySingle();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        recursosUi.setPath(file.getPath()+"/"+fileName);

        if(recursoDidacticoEventoC!=null){
            recursoDidacticoEventoC.setLocalUrl(file.getPath()+"/"+fileName);
            recursoDidacticoEventoC.save();
        }

    }



}
