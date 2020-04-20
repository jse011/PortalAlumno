package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioTipoFileU;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MoverArchivosAlaCarpetaTarea {
    private static final String TAG = MoverArchivosAlaCarpetaTarea.class.getSimpleName();
    private TareasMvpRepository repository;

    public MoverArchivosAlaCarpetaTarea(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public void execute(String curso, String titulo, List<RepositorioFileUi> repositorioFileUis){
        if(!TextUtils.isEmpty(titulo)){
            titulo = titulo.length()>20?titulo.substring(0,20):titulo;
            titulo = titulo.replace(' ' , '_');
        }else {
            titulo = "";
        }

        if(!TextUtils.isEmpty(curso)){
            curso = curso.replace(' ' , '_');
        }

        String sdCard = Environment.getExternalStorageDirectory().toString();
        String rutaNueva = sdCard + "/TareaDm3.0/"+curso + "/" + titulo +"/";
        crearCarpeta(rutaNueva);

        for (final RepositorioFileUi repositorioFileUi : repositorioFileUis){

            if(repositorioFileUi.getTipoFileU()!= RepositorioTipoFileU.VINCULO&&
                    repositorioFileUi.getTipoFileU()!= RepositorioTipoFileU.YOUTUBE){

                String sourceLocationPath = repositorioFileUi.getPath();
                String extencion = "";
                String inputPath = "";

                int i = sourceLocationPath.lastIndexOf('.');
                int p = Math.max(sourceLocationPath.lastIndexOf('/'), sourceLocationPath.lastIndexOf('\\'));
                String inputFile = sourceLocationPath.substring(p + 1);

                if (i > p) {
                    extencion = sourceLocationPath.substring(i+1);
                    inputPath = sourceLocationPath.substring(0,p+1);
                }

                Log.d(TAG, "extencion: " +extencion);
                Log.d(TAG, "nombre: " +inputFile);
                Log.d(TAG, "inputPath: " +inputPath);

                // your sd card

                String targetLocationPath = rutaNueva + inputFile;

                if(copyFile(sourceLocationPath, targetLocationPath)){
                    repositorioFileUi.setPath(targetLocationPath);
                }
            }

        }

        repository.updateArchivosTarea(repositorioFileUis);

    }

    private void crearCarpeta(String outputPath){
        File dir = new File(outputPath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
    }

    private boolean copyFile(String sourceLocationPath, String targetLocationPath) {
        boolean success= false;
        // the file to be moved or copied
        File sourceLocation = new File(sourceLocationPath);
        // make sure your target location folder exists!
        File targetLocation = new File(targetLocationPath);

        try {
            // just to take note of the location sources
            Log.v(TAG, "sourceLocation: " + sourceLocation);
            Log.v(TAG, "targetLocation: " + targetLocation);
            // make sure the target file exists
            if(sourceLocation.exists()){

                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(targetLocation);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
                success = true;
                Log.v(TAG, "Copy file successful.");

            }else{
                Log.v(TAG, "Copy file failed. Source file missing.");
            }

        }catch (Exception e){
            success = false;
            e.printStackTrace();
        }

        return success;

    }


    private boolean moveFile(String sourceLocationPath, String inputFile, int actionChoice) {
        boolean success= false;
        // your sd card
        String sdCard = Environment.getExternalStorageDirectory().toString();

        // the file to be moved or copied
        File sourceLocation = new File(sourceLocationPath);

        // make sure your target location folder exists!
        File targetLocation = new File(sdCard + "/TAREA:)/"+inputFile);

        // just to take note of the location sources
        Log.v(TAG, "sourceLocation: " + sourceLocation);
        Log.v(TAG, "targetLocation: " + targetLocation);
        try {

            if(sourceLocation.renameTo(targetLocation)){
                Log.v(TAG, "Move file successful.");
                success =true;
            }else{
                Log.v(TAG, "Move file failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }



}
