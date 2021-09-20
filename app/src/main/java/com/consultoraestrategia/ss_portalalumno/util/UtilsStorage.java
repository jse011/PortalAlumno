package com.consultoraestrategia.ss_portalalumno.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

public class UtilsStorage {
    public static String getMimeType(String url) {
        url = !TextUtils.isEmpty(url)? "archivo"+"." + UtilsStorage.getExtencion(url):"";//se le reduce el nombre del archivo no afecta que tengo otro nombre
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String getMimeExtension(String url) {
        url = !TextUtils.isEmpty(url)?url:"";
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    public static String getNombre(String nameFile) {
        nameFile = !TextUtils.isEmpty(nameFile)?nameFile:"";
        return nameFile.substring(0, nameFile.lastIndexOf('.'));
    }

    public static String getExtencion(String nameFile){
        String extension = "";
        nameFile = !TextUtils.isEmpty(nameFile)?nameFile:"";
        int i = nameFile.lastIndexOf('.');
        int p = Math.max(nameFile.lastIndexOf('/'), nameFile.lastIndexOf('\\'));

        if (i > p) {
            extension = nameFile.substring(i+1);
        }

        return extension;
    }

    public static String makeid(int length) { //Id al radom con limite de tamaño
        StringBuilder result = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt((int) Math.floor(Math.random() * charactersLength)));
        }
        return result.toString();
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }
}
