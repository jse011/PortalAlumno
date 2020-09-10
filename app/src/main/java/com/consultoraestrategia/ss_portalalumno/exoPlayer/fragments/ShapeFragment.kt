package com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments

import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_shape_exoplayer.*

/**
 * A placeholder fragment containing a simple view.
 */
class ShapeFragment : Fragment() {

    private lateinit var viewModel: PageViewModel

    private val minAnimationDuration = 500
    private val minArcSize = 0
    private val maxAnimationDuration = 3000
    private val maxArcSize = 100

    val durationSteps = 50
    val arcSteps = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shape_exoplayer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { fa ->
            viewModel = ViewModelProvider(fa).get(PageViewModel::class.java)

            initializeDurationSeekBar(
                seekbar_youtube_animation_duration,
                tv_youtube_animation_duration,
                maxAnimationDuration,
                minAnimationDuration,
                viewModel.circleExpandDuration.value?.toInt() ?: 800
            ) {
                viewModel.circleExpandDuration.value = it.toLong()
            }

            initializeDimensSeekBar(
                seekbar_arc_size,
                tv_arc_size,
                maxArcSize,
                minArcSize,
                viewModel.arcSize.value?.toInt() ?: 40
            ) {
                viewModel.arcSize.value = it.toFloat()
            }

            initializeAlphaSeekbar(
                seekbar_tap_circle_alpha,
                tv_tap_circle_alpha,
                100, 0,
                (Color.alpha(viewModel.tapCircleColor.value ?: 10) * (100 / 255f)).toInt()
            ) {
                viewModel.tapCircleColor.value = it
            }

            initializeAlphaSeekbar(
                seekbar_background_circle_alpha,
                tv_background_circle_alpha,
                100, 0,
                (Color.alpha(viewModel.circleBackgroundColor.value ?: 15) * (100 / 255f)).toInt()
            ) {
                viewModel.circleBackgroundColor.value = it
            }
        }
    }

    private fun initializeDurationSeekBar(seekBar: AppCompatSeekBar? = null, textView: TextView? = null,
                                          maxValue: Int = 0, minValue: Int = 0, currentValue: Int = 0,
                                          progressChanged: (progress: Int) -> Unit) {

        val textInitial = "$currentValue ms"
        textView?.text = textInitial

        seekBar?.apply {
            max = (maxValue - minValue) / durationSteps
            progress = (currentValue - minValue) / durationSteps
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = minValue + (progress * durationSteps)
                    val text = "$value ms"
                    textView?.text = text

                    progressChanged(value)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

            })
        }
    }

    private fun initializeDimensSeekBar(seekBar: AppCompatSeekBar? = null, textView: TextView? = null,
                                        maxValue: Int = 0, minValue: Int = 0, currentValue: Int = 0,
                                        progressChanged: (progress: Int) -> Unit) {

        val textInitial = "$currentValue dp"
        textView?.text = textInitial

        seekBar?.apply {
            max = (maxValue - minValue) / arcSteps
            progress = (currentValue - minValue) / arcSteps
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = minValue + (progress * arcSteps)
                    val text = "$value dp"
                    textView?.text = text

                    progressChanged(value)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

            })
        }
    }

    private fun initializeAlphaSeekbar(seekBar: AppCompatSeekBar? = null, textView: TextView? = null,
                                                  maxValue: Int = 100, minValue: Int = 0, currentValue: Int,
                                                  progressChanged: (progress: Int) -> Unit) {

        val textInitial = "$currentValue %"
        textView?.text = textInitial

        seekBar?.apply {
            max = 100
            progress = currentValue
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val text = "$progress %"
                    textView?.text = text

                    val color = Color.argb(((progress / 100f) * 255).toInt(), 255, 255, 255)
                    progressChanged(color)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ShapeFragment {
            return ShapeFragment()
        }
    }
}