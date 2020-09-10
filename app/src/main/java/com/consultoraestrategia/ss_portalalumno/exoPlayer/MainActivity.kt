package com.consultoraestrategia.ss_portalalumno.exoPlayer

import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.consultoraestrategia.ss_portalalumno.R
import com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments.PageViewModel
import com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments.SecondsViewFragment
import com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments.SectionsPagerAdapter
import com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments.ShapeFragment
import com.github.vkay94.dtpv.youtube.YouTubeOverlay
import kotlinx.android.synthetic.main.activity_exoplayer.*
import kotlinx.android.synthetic.main.exo_playback_control_view_yt.*


class MainActivity : BaseVideoActivity() {

    private var isVideoFullscreen = false
    private var currentVideoId = -1

    private lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exoplayer)
        setSupportActionBar(toolbar)

        this.videoPlayer = previewPlayerView
        initDoubleTapPlayerView()
        initViewModel()
        startNextVideo()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.addFragment(ShapeFragment.newInstance())
        sectionsPagerAdapter.addFragment(SecondsViewFragment.newInstance())

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        fullscreen_button.setOnClickListener {
            toggleFullscreen()
        }
    }

    private fun initDoubleTapPlayerView() {
        ytOverlay
            // Uncomment this line if the DoubleTapPlayerView is not set via XML
            //.playerView(previewPlayerView)
            .performListener(object : YouTubeOverlay.PerformListener {
                override fun onAnimationStart() {
                    previewPlayerView.useController = false
                    ytOverlay.visibility = View.VISIBLE
                }
                override fun onAnimationEnd() {
                    ytOverlay.visibility = View.GONE
                    previewPlayerView.useController = true
                }
            })

        previewPlayerView.doubleTapDelay = 800
        // Uncomment this line if the PlayerDoubleTapListener is not set via XML
         //previewPlayerView.controller(ytOverlay)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            tapCircleColor.value = ytOverlay.tapCircleColor
            arcSize.value = DataAndUtils.pxToDp(this@MainActivity, ytOverlay.arcSize)
            circleBackgroundColor.value = ytOverlay.circleBackgroundColor
            circleExpandDuration.value = ytOverlay.animationDuration
            fontSize.value = DataAndUtils.pxToSp(this@MainActivity, ytOverlay.secondsTextView.textSize)
            typeFace.value = Typeface.NORMAL
            iconSpeed.value = ytOverlay.iconAnimationDuration
        }

        viewModel.circleExpandDuration.observe(this, Observer {
            ytOverlay.animationDuration(it)
        })
        viewModel.arcSize.observe(this, Observer {
            ytOverlay.arcSize(DataAndUtils.dpToPx(this@MainActivity, it.toFloat()))
        })
        viewModel.fontSize.observe(this, Observer {
            TextViewStyler().textSize(it).applyTo(ytOverlay.secondsTextView)
        })
        viewModel.typeFace.observe(this, Observer {
            TextViewStyler().textStyle(it).applyTo(ytOverlay.secondsTextView)
        })
        viewModel.tapCircleColor.observe(this, Observer {
            ytOverlay.tapCircleColorInt(it)
        })
        viewModel.circleBackgroundColor.observe(this, Observer {
            ytOverlay.circleBackgroundColorInt(it)
        })
        viewModel.secondsIcon.observe(this, Observer {
            ytOverlay.icon(it)
        })
        viewModel.iconSpeed.observe(this, Observer {
            ytOverlay.iconAnimationDuration(it)
        })
    }

    private fun startNextVideo() {
        releasePlayer()
        initializePlayer()
        ytOverlay.player(player!!)

        currentVideoId = (currentVideoId + 1).rem(DataAndUtils.videoList.size)
        buildMediaSource(Uri.parse(DataAndUtils.videoList[currentVideoId]))
    }

    private fun toggleFullscreen() {
        if (isVideoFullscreen) {
            setFullscreen(false)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;
            if(supportActionBar != null){
                supportActionBar?.show();
            }
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            isVideoFullscreen = false
        } else {
            setFullscreen(true)
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.decorView.systemUiVisibility = flags

                    /*(View.SYSTEM_UI_FLAG_FULLSCREEN
                    and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)*/

            if(supportActionBar != null){
                supportActionBar?.hide();
            }
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            isVideoFullscreen = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_explayer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_main_action_github -> {
                openInBrowser(DataAndUtils.GITHUB_LINK)
                true
            }
            R.id.menu_main_action_change_video -> {
                startNextVideo()
                Toast.makeText(this, "Video has changed", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() {
        if (isVideoFullscreen) {
            toggleFullscreen()
            return
        }
        super.onBackPressed()
    }

}