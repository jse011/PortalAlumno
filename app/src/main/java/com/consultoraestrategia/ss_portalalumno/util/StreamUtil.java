package com.consultoraestrategia.ss_portalalumno.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
    public static final String PREFIX = "stream2file";

    public static File stream2file (Uri uri, Context context) throws IOException {
        FileOutputStream outputStream = null;
        File file = null;
        try {

            String nombreArchivo = getNombre(uri, context);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            file = File.createTempFile(PREFIX, nombreArchivo);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return file;
    }

    public static File stream2file (InputStream inputStream, String tempFileSuffix) throws IOException {
        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = File.createTempFile(PREFIX, tempFileSuffix);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return file;
    }

    public static String getNombre(Uri uri, Context context) {
        String displayName = null;
        try {
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null, null);
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
                cursor.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return displayName;
    }
}
