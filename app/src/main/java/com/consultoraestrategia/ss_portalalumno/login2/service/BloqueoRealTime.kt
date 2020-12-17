package com.consultoraestrategia.ss_portalalumno.login2.service

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.consultoraestrategia.ss_portalalumno.estadocuenta2.EstadoCuenta2Activity
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu
import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepositoryImpl
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.IsHabilitado
import com.consultoraestrategia.ss_portalalumno.userbloqueo.UserBloqueoActivity
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno
import kotlinx.coroutines.channels.ticker
import java.util.concurrent.TimeUnit

class BloqueoRealTime private constructor(private val context: Context) {
    private var firebaseCancel: FirebaseCancel? = null
    private val TAG = "BloqueoRealtimeTag"
    private var countdown_timer: CountDownTimer? = null
    private var isRunning: Boolean = false;
    private var time_in_milli_seconds = 0L
    private var isShow: Boolean = false;

    companion object {

        @Volatile
        private var instance: BloqueoRealTime? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: BloqueoRealTime(context).also { instance = it }
                }
    }


    init {
        Log.d(TAG, "init")
        val isHabilitado = IsHabilitado(LoginDataRepositoryImpl())
        firebaseCancel = isHabilitado.ishabilitadoAcceso { sucess, habilitarAccesoUi ->
            if (sucess) {
                Log.d(TAG, "onLoad sucess $sucess")
            }

            if(habilitarAccesoUi.isModifiado){
                iCRMEdu.variblesGlobales.bloqueoAcceso = false
                if (habilitarAccesoUi.isHabilitar) {
                    pauseTimer()
                }else{
                    if(!isRunning) startTimer()
                }
            }else{
                iCRMEdu.variblesGlobales.bloqueoAcceso = true
            }

            if (!habilitarAccesoUi.isHabilitar) {
                showActivty();
            }

            iCRMEdu.variblesGlobales.habilitarAcceso = habilitarAccesoUi.isHabilitar
            val bcIntent = Intent()
            bcIntent.action = iCRMEdu.ACTION_START_ALERT_BLOQUEO
            LocalBroadcastManager.getInstance(context).sendBroadcast(bcIntent)
        }
    }

    private fun showActivty() {
        if (!UtilsPortalAlumno.isActivityOnTop(context, EstadoCuenta2Activity::class.java)) {
            val intent = Intent(context, UserBloqueoActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
           // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(intent)
        }

    }


    private fun startTimer() {
        time_in_milli_seconds = TimeUnit.HOURS.toMillis(12)
        countdown_timer = object : CountDownTimer(time_in_milli_seconds, 1000) {
            override fun onFinish() {
                iCRMEdu.variblesGlobales.bloqueoAcceso = true
                showActivty();
                isRunning = false
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                Log.d(TAG, "count: p0 " + p0);
            }
        }
        countdown_timer?.start()

        isRunning = true
    }


    private fun pauseTimer() {
        countdown_timer?.cancel()
        countdown_timer?.onFinish()
        isRunning = false
    }


    fun onCancel() {
        Log.d(TAG, "cancel")
        firebaseCancel!!.cancel()
    }

}