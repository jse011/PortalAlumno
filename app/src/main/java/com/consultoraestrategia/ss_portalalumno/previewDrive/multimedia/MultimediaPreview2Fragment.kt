package com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.consultoraestrategia.ss_portalalumno.R
import com.consultoraestrategia.ss_portalalumno.exoPlayer.util.*
import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoPresenter
import com.github.vkay94.dtpv.DoubleTapPlayerView
import com.github.vkay94.dtpv.SeekListener
import com.github.vkay94.dtpv.youtube.YouTubeOverlay
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.exo_playback_control_view_yt.*
import kotlinx.android.synthetic.main.fragment_player2.*


class MultimediaPreview2Fragment : Fragment(), Player.EventListener, MultimediaPreviewView {
    private var isVideoFullscreen = false
    private var disabledOrientation = false

    private var onRestar: Boolean = false;
    var videoPlayer: DoubleTapPlayerView? = null
    var player: SimpleExoPlayer? = null
    var previewArchivoPresenter: PreviewArchivoPresenter? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_player2, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.videoPlayer = previewPlayerTareaView
        initDoubleTapPlayerView()
        fullscreen_button.setOnClickListener {
            disabledOrientation = true
            toggleFullscreen()
        }
        exo_replay.setOnClickListener {
            previewArchivoPresenter?.onClickReload();
        }
        exo_reload.setOnClickListener {
            previewArchivoPresenter?.onClickReload();
        }
    }


    private fun startNextVideo(mediaSource: ProgressiveMediaSource) {
        releasePlayer()
        initializePlayer()
        ytOverlayTarea.player(player!!)
        player?.prepare(mediaSource)
        player?.playWhenReady = true
    }

    fun initializePlayer() {
        if (player == null) {
            val loadControl: LoadControl = DefaultLoadControl.Builder()
                    .setBufferDurationsMs(
                            MIN_BUFFER_DURATION,
                            MAX_BUFFER_DURATION,
                            MIN_PLAYBACK_START_BUFFER,
                            MIN_PLAYBACK_RESUME_BUFFER
                    )
                    .createDefaultLoadControl()

            player = SimpleExoPlayer.Builder(root.context)
                    .setLoadControl(loadControl)
                    .build()

            player?.addListener(this)
            videoPlayer?.player = player

        }
    }

    private fun initDoubleTapPlayerView() {
        ytOverlayTarea
                // Uncomment this line if the DoubleTapPlayerView is not set via XML
                .playerView(previewPlayerTareaView)
                .seekListener(object : SeekListener{
                    override fun onVideoEndReached() {
                        super.onVideoEndReached()
                    }

                    override fun onVideoStartReached() {
                        super.onVideoStartReached()
                    }
                })
                .performListener(object : YouTubeOverlay.PerformListener {
                    override fun onAnimationStart() {
                        previewPlayerTareaView.useController = false
                        ytOverlayTarea.visibility = View.VISIBLE
                    }
                    override fun onAnimationEnd() {
                        ytOverlayTarea.visibility = View.GONE
                        previewPlayerTareaView.useController = true
                    }
                })

        previewPlayerTareaView.doubleTapDelay = 800
        // Uncomment this line if the PlayerDoubleTapListener is not set via XML
        previewPlayerTareaView.controller(ytOverlayTarea)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
           setupControllLandscape()
        } else {
            // In portrait
            setupControllPortrait()
        }
    }

    private fun setupControllPortrait() {
        val params = exo_ll_timebar.layoutParams as ConstraintLayout.LayoutParams
        params.verticalBias = 1f // here is one modification for example. modify anything else you want :)
        exo_ll_timebar.setLayoutParams(params)
        exo_ll_timebar.requestLayout()
        fullscreen_button.setImageResource(R.drawable.ic_fullscreen_expand)
    }

    private fun setupControllLandscape() {
        val params = exo_ll_timebar.layoutParams as ConstraintLayout.LayoutParams
        params.verticalBias = 0.8f // here is one modification for example. modify anything else you want :)
        exo_ll_timebar.setLayoutParams(params)
        exo_ll_timebar.requestLayout()
        fullscreen_button.setImageResource(R.drawable.ic_fullscreen_exit)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            isVideoFullscreen = false;
            toggleFullscreen()
        } else {
            isVideoFullscreen = true;
            toggleFullscreen()
        }
    }

    // Player Lifecycle
    fun releasePlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
    }

    fun pausePlayer() {
        if (player != null) {
            player?.playWhenReady = false
            player?.playbackState
        }
    }

    fun resumePlayer() {
        if (player != null) {
            player?.playWhenReady = true
            player?.playbackState
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onStart() {
        super.onStart()
        if(onRestar){
            if(isVideoFullscreen){
                isVideoFullscreen = false;
                toggleFullscreen()
            }else{
                isVideoFullscreen = true;
                toggleFullscreen()
            }

            if (player?.playbackState == Player.STATE_READY && player?.playWhenReady!!)
                resumePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        onRestar = true;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (player != null) {
            player?.release()
            player = null
        }
    }

    fun setFullscreen(fullscreen: Boolean) {
        if (fullscreen) {
            activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                !playWhenReady) {
            this.videoPlayer?.setKeepScreenOn(false)
        } else { // STATE_IDLE, STATE_ENDED
            // This prevents the screen from getting dim/lock
            this.videoPlayer?.setKeepScreenOn(true)
        }

      if(playbackState == ExoPlayer.STATE_IDLE){
          this.previewArchivoPresenter?.onCancelPlayer()
          exo_replay.visibility = View.VISIBLE
          exo_reload.visibility = View.GONE
      }else{
          exo_replay.visibility = View.GONE
          exo_reload.visibility = View.GONE
      }

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun toggleFullscreen() {
        if (isVideoFullscreen) {
            setFullscreen(false)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (View.SYSTEM_UI_FLAG_VISIBLE
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                (View.SYSTEM_UI_FLAG_VISIBLE)
            }

            activity?.window?.decorView?.systemUiVisibility = flags

            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            isVideoFullscreen = false
            disabledOrientation = false
           setupControllPortrait()
        } else {
            setFullscreen(true)
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            activity?.window?.decorView?.systemUiVisibility = flags

            /*(View.SYSTEM_UI_FLAG_FULLSCREEN
            and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)*/

            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            if(!disabledOrientation){
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            }

            isVideoFullscreen = true
            setupControllLandscape()

        }
    }

    override fun onBackPressed():Boolean{
        if (isVideoFullscreen) {
            toggleFullscreen()
            return false;
        }
        return true;
    }

    override fun dimensionRatio(a: Int, b: Int) {
        if(a == 0 && b == 0){
            (frameLayout.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =  null;
        }else{
            (frameLayout.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "$a:$b";
        }

        frameLayout.requestLayout();
    }

    override fun uploadMkv(idDrive: String?) {
        val mUri = Uri.parse("https://drive.google.com/uc?export=download&id=$idDrive")
        val dataSourceFactory = DefaultDataSourceFactory(
                root.context,
                Util.getUserAgent(root.context, resources.getString(R.string.app_name)),
                DefaultBandwidthMeter.Builder(context).build()
        )

        val mediaSource = ProgressiveMediaSource.Factory(CacheDataSourceFactory(SimpleCacheUtils.getInstance(context), dataSourceFactory), MkvExtractorFactory())
                .createMediaSource(mUri)

        startNextVideo(mediaSource)
    }

    override fun uploadAudio(idDrive: String?) {
        val mUri = Uri.parse("https://drive.google.com/uc?export=download&id=$idDrive")
        val dataSourceFactory = DefaultDataSourceFactory(
                root.context,
                Util.getUserAgent(root.context, resources.getString(R.string.app_name)),
                DefaultBandwidthMeter.Builder(context).build()
        )

        val mediaSource = ProgressiveMediaSource.Factory(CacheDataSourceFactory(SimpleCacheUtils.getInstance(context), dataSourceFactory), AudioExtractorFactory())
                .createMediaSource(mUri)

        startNextVideo(mediaSource)
    }

    override fun setPresenter(presenter: PreviewArchivoPresenter?) {
       this.previewArchivoPresenter = presenter
    }


    override fun uploadArchivo(idDrive: String?) {

        val mUri = Uri.parse("https://drive.google.com/uc?export=download&id=$idDrive")
        val dataSourceFactory = DefaultDataSourceFactory(
                root.context,
                Util.getUserAgent(root.context, resources.getString(R.string.app_name)),
                DefaultBandwidthMeter.Builder(context).build()
        )

        val mediaSource = ProgressiveMediaSource.Factory(CacheDataSourceFactory(SimpleCacheUtils.getInstance(context), dataSourceFactory), AllExtractorFactory())
                .createMediaSource(mUri)

        startNextVideo(mediaSource)
    }

    override fun showBtnReload() {
        exo_reload.visibility = View.VISIBLE
    }

    override fun hideProgress() {
       progressBar12.visibility = View.GONE
    }

    override fun uploadFlv(idDrive: String?) {
        val mUri = Uri.parse("https://drive.google.com/uc?export=download&id=$idDrive")
        val dataSourceFactory = DefaultDataSourceFactory(
                root.context,
                Util.getUserAgent(root.context, resources.getString(R.string.app_name)),
                DefaultBandwidthMeter.Builder(context).build()
        )

        val mediaSource = ProgressiveMediaSource.Factory(CacheDataSourceFactory(SimpleCacheUtils.getInstance(context), dataSourceFactory), FlvExtractorFactory())
                .createMediaSource(mUri)

        startNextVideo(mediaSource)
    }

    override fun uploadMp4(idDrive: String?) {
        val mUri = Uri.parse("https://drive.google.com/uc?export=download&id=$idDrive")
        val dataSourceFactory = DefaultDataSourceFactory(
                root.context,
                Util.getUserAgent(root.context, resources.getString(R.string.app_name)),
                DefaultBandwidthMeter.Builder(context).build()
        )

        val mediaSource = ProgressiveMediaSource.Factory(CacheDataSourceFactory(SimpleCacheUtils.getInstance(context), dataSourceFactory), Mp4ExtractorFactory())
                .createMediaSource(mUri)

        startNextVideo(mediaSource)
    }

    override fun resumePreview(idDrive: String?) {

    }

    override fun showProgress() {
        progressBar12.visibility = View.VISIBLE
    }

    companion object {
        const val MIN_BUFFER_DURATION = 15000
        const val MAX_BUFFER_DURATION = 60000
        const val MIN_PLAYBACK_START_BUFFER = 2500
        const val MIN_PLAYBACK_RESUME_BUFFER = 5000
    }

}