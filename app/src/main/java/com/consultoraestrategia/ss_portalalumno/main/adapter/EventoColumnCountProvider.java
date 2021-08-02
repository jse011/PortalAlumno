package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnStaggeredGridLayoutManager;

public class EventoColumnCountProvider implements AutoColumnStaggeredGridLayoutManager.ColumnCountProvider {
    @NonNull
    private final Context context;

    public EventoColumnCountProvider(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getColumnCount(int recyclerViewWidth) {
        return columnsForWidth(recyclerViewWidth);
    }

    public int columnsForWidth(int widthPx) {
        int widthDp = dpFromPx(widthPx);
        if (widthDp >= 900) {
            return 9;
        } else if (widthDp >= 720) {
            return 7;
        } else if (widthDp >= 600) {
            return 5;
        } else if (widthDp >= 480) {
            return 5;
        } else if (widthDp >= 320) {
            return 4;
        } else {
            return 4;
        }
    }

    public int dpFromPx(float px) {
        return (int)(px / context.getResources().getDisplayMetrics().density + 0.5f);
    }
}