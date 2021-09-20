package com.consultoraestrategia.ss_portalalumno.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OpenIntents {
    /**
     * Open file
     *
     * @param uri path to file
     */
    @Deprecated
    public static void openFile(Uri uri, Context context)  throws Exception {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String extencion = uri.toString().toLowerCase();
            if (extencion.contains(".doc") || extencion.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (extencion.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (extencion.contains(".ppt") || extencion.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (extencion.contains(".xls") || extencion.contains(".xlsx")||extencion.contains(".csv")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (extencion.contains(".zip") || extencion.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (extencion.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (extencion.contains(".wav") || extencion.contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (extencion.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (extencion.contains(".jpg") || extencion.contains(".jpeg") || extencion.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (extencion.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (extencion.contains(".3gp") || extencion.contains(".mpg") || extencion.contains(".mpeg") || extencion.contains(".mpe") || extencion.contains(".mp4") || extencion.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                // Other files
                intent.setDataAndType(uri, "*/*");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


    }
}
