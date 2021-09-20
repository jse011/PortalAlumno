package com.consultoraestrategia.ss_portalalumno.previewDrive

import android.net.Uri
import com.consultoraestrategia.ss_portalalumno.BuildConfig
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity
import com.consultoraestrategia.ss_portalalumno.util.getFinalUriFromPath

abstract class BasePrevArchivoActivity :
    BaseActivity<PreviewArchivoView, PreviewArchivoPresenter>() {

    fun getUriFromPath(path: String): Uri? {
        return getFinalUriFromPath(path, BuildConfig.APPLICATION_ID);
        //activity?.tryOpenPathIntent(path, false)
    }
}