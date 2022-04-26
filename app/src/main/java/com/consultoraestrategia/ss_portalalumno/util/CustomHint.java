package com.consultoraestrategia.ss_portalalumno.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomHint extends SpannableString {
    public CustomHint(final CharSequence source, final int style)
    {
        this(null, source, style, null);
    }

    public CustomHint(final CharSequence source, final Float size)
    {
        this(null, source, size);
    }

    public CustomHint(final CharSequence source, final int style, final Float size)
    {
        this(null, source, style, size);
    }

    public CustomHint(final Typeface typeface, final CharSequence source, final int style)
    {
        this(typeface, source, style, null);
    }

    public CustomHint(final Typeface typeface, final CharSequence source, final Float size)
    {
        this(typeface, source, null, size);
    }

    public CustomHint(final Typeface typeface, final CharSequence source, final Integer style, final Float size)
    {
        super(source);

        MetricAffectingSpan typefaceSpan = new CustomMetricAffectingSpan(typeface, style, size);
        setSpan(typefaceSpan, 0, source.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }


    public static class CustomMetricAffectingSpan extends MetricAffectingSpan
    {
        private final Typeface _typeface;
        private final Float    _newSize;
        private final Integer  _newStyle;

        public CustomMetricAffectingSpan(Float size)
        {
            this(null, null, size);
        }

        public CustomMetricAffectingSpan(Float size, Integer style)
        {
            this(null, style, size);
        }

        public CustomMetricAffectingSpan(Typeface type, Integer style, Float size)
        {
            this._typeface = type;
            this._newStyle = style;
            this._newSize = size;
        }

        @Override
        public void updateDrawState(TextPaint ds)
        {
            applyNewSize(ds);
        }

        @Override
        public void updateMeasureState(TextPaint paint)
        {
            applyNewSize(paint);
        }

        private void applyNewSize(TextPaint paint)
        {
            if (this._newStyle != null)
                paint.setTypeface(Typeface.create(this._typeface, this._newStyle));
            else
                paint.setTypeface(this._typeface);

            if (this._newSize != null)
                paint.setTextSize(this._newSize);
        }
    }

}
