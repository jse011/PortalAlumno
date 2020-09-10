package com.consultoraestrategia.ss_portalalumno.exoPlayer.util

import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.extractor.flv.FlvExtractor
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.extractor.ogg.OggExtractor
import com.google.android.exoplayer2.extractor.ts.PsExtractor
import com.google.android.exoplayer2.extractor.ts.TsExtractor
import com.google.android.exoplayer2.extractor.wav.WavExtractor

public class AllExtractorFactory : ExtractorsFactory {

    override fun createExtractors(): Array<Extractor> {
        return arrayOf(
            Mp4Extractor(),
            Mp3Extractor(),
            MatroskaExtractor(),
            FlvExtractor(),
            OggExtractor(),
            WavExtractor(),
            TsExtractor(),
            PsExtractor()
        )
    }
}