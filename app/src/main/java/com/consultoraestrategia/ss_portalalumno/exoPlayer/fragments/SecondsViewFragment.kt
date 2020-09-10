package com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.consultoraestrategia.ss_portalalumno.R
import com.consultoraestrategia.ss_portalalumno.exoPlayer.TextViewStyler
import kotlinx.android.synthetic.main.fragment_seconds_view_exoplayer.*


class SecondsViewFragment : Fragment() {

    private lateinit var viewModel: PageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seconds_view_exoplayer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seconds_view.seconds = 10
        seconds_view.textView.setTextColor(Color.WHITE)

        seconds_view.start()

        activity?.let { fa ->
            viewModel = ViewModelProvider(fa).get(PageViewModel::class.java)

            textsize_picker.apply {
                minValue = 10
                maxValue = 20
                value = 13
                setFormatter { "$it sp" }
                setOnValueChangedListener { picker, _, _ ->
                    seconds_view.textView.textSize = picker.value.toFloat()
                    viewModel.fontSize.value = picker.value.toFloat()
                }
            }

            typeface_picker.apply {
                minValue = 0
                maxValue = 3
                value = 0
                setFormatter {
                    when (it) {
                        0 -> "normal"
                        1 -> "bold"
                        2 -> "italic"
                        3 -> "bold italic"
                        else -> "undefined"
                    }
                }
                setOnValueChangedListener { picker, _, _ ->
                    val tf = when (picker.value) {
                        0 -> Typeface.NORMAL
                        1 -> Typeface.BOLD
                        2 -> Typeface.ITALIC
                        3 -> Typeface.BOLD_ITALIC
                        else -> 0
                    }

                    TextViewStyler()
                        .textStyle(tf).applyTo(seconds_view.textView)
                    viewModel.typeFace.value = tf
                }
            }

            seconds_picker.apply {
                minValue = 0
                maxValue = 3
                value = 0
                setFormatter {
                    when (it) {
                        0 -> "default"
                        1 -> "24dp"
                        2 -> "smaller"
                        3 -> "No space"
                        else -> "undefined"
                    }
                }
                setOnValueChangedListener { picker, _, _ ->
                    val res = when (picker.value) {
                        0 -> R.drawable.seconds_icon_default
                        1 -> R.drawable.seconds_icon_24dp
                        2 -> R.drawable.seconds_icon_smaller
                        3 -> R.drawable.seconds_icon_no_space
                        else -> 0
                    }

                    if (res > 0) {
                        seconds_view.icon = res
                        viewModel.secondsIcon.value = res
                    }
                }
            }
        }

        initTriangleSpeedSeekbar(
            seekbar_youtube_drawable_animation_duration,
            tv_youtube_drawable_animation_duration,
            3000,
            500,
            seconds_view.cycleDuration.toInt() ?: 800
        ) {
            seconds_view.cycleDuration = it.toLong()
            viewModel.iconSpeed.value = it.toLong()
        }
    }

    private fun initTriangleSpeedSeekbar(
        seekBar: AppCompatSeekBar? = null, textView: TextView? = null,
        maxValue: Int = 0, minValue: Int = 0, currentValue: Int = 0,
        progressChanged: (progress: Int) -> Unit
    ) {
        val textInitial = "$currentValue ms"
        textView?.text = textInitial

        seekBar?.apply {
            max = (maxValue - minValue) / 50
            progress = (currentValue - minValue) / 50
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = minValue + (progress * 50)
                    val text = "$value ms"
                    textView?.text = text

                    progressChanged(value)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): SecondsViewFragment {
            return SecondsViewFragment()
        }
    }
}