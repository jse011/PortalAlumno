package com.consultoraestrategia.ss_portalalumno.previewDrive

import android.net.Uri
import android.os.Bundle
import com.consultoraestrategia.ss_portalalumno.BuildConfig
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu
import com.consultoraestrategia.ss_portalalumno.util.getFinalUriFromPath

abstract class BasePrevArchivoActivity :
    BaseActivity<PreviewArchivoView, PreviewArchivoPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getUriFromPath(path: String): Uri? {
        return getFinalUriFromPath(path, BuildConfig.APPLICATION_ID);
        //activity?.tryOpenPathIntent(path, false)
    }
}