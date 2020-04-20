package com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class AutoColumnStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    // Dummy column count just to supply some value to the super constructor
    private static final int FAKE_COUNT = 2;
    private Context context;

    @Nullable
    private ColumnCountProvider columnCountProvider;

    public interface ColumnCountProvider {
        int getColumnCount(int recyclerViewWidth);
    }

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

    public AutoColumnStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoColumnStaggeredGridLayoutManager(int orientation, Context context) {
        super(FAKE_COUNT, orientation);
        this.context = context;
    }

    public AutoColumnStaggeredGridLayoutManager(int orientation,int spanCount, Context context) {
        super(spanCount, orientation);
        this.context = context;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler,
                                 RecyclerView.State state) {
        Handler uiHandler = new Handler(context.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                updateSpanCount(getWidth());
            }
        });

        super.onLayoutChildren(recycler, state);
    }

    public void updateSpanCount(int width) {
        if (columnCountProvider != null) {
            int spanCount = columnCountProvider.getColumnCount(width);
            setSpanCount(spanCount > 0 ? spanCount : 1);
        }
    }

    public void setColumnCountProvider(@Nullable ColumnCountProvider provider) {
        this.columnCountProvider = provider;
    }
}
