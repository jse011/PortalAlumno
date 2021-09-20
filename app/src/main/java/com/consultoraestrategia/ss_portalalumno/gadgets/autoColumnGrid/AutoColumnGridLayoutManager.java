package com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AutoColumnGridLayoutManager extends GridLayoutManager {

    // Dummy column count just to supply some value to the super constructor
    private static final int FAKE_COUNT = 1;

    @Nullable
    private ColumnCountProvider columnCountProvider;

    public interface ColumnCountProvider {
        int getColumnCount(int recyclerViewWidth);
    }

    private int spanCount;
    public static class DefaultColumnCountProvider implements ColumnCountProvider {
        @NonNull
        private final Context context;

        public DefaultColumnCountProvider(@NonNull Context context) {
            this.context = context;
        }

        @Override
        public int getColumnCount(int recyclerViewWidth) {
            return columnsForWidth(recyclerViewWidth);
        }

        public int columnsForWidth(int widthPx) {
            int widthDp = dpFromPx(widthPx);
            if (widthDp >= 900) {
                return 5;
            } else if (widthDp >= 720) {
                return 4;
            } else if (widthDp >= 600) {
                return 3;
            } else if (widthDp >= 480) {
                return 2;
            } else if (widthDp >= 320) {
                return 2;
            } else {
                return 2;
            }
        }

        public int dpFromPx(float px) {
            return (int)(px / context.getResources().getDisplayMetrics().density + 0.5f);
        }
    }

    public AutoColumnGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoColumnGridLayoutManager(Context context) {
        super(context, FAKE_COUNT);
    }

    public AutoColumnGridLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, FAKE_COUNT, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler,
                                 RecyclerView.State state) {
        updateSpanCount(getWidth());
        super.onLayoutChildren(recycler, state);
    }

    private void updateSpanCount(int width) {
        if (columnCountProvider != null) {
            spanCount = columnCountProvider.getColumnCount(width);
            setSpanCount(spanCount > 0 ? spanCount : 1);
        }
    }

    public void setColumnCountProvider(@Nullable ColumnCountProvider provider) {
        this.columnCountProvider = provider;
    }


    @Override
    public int getSpanCount() {
        return spanCount;
    }
}
