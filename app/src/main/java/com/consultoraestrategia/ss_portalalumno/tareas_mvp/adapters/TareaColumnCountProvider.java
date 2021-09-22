package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnGridLayoutManager;

public class TareaColumnCountProvider implements AutoColumnGridLayoutManager.ColumnCountProvider {
    @NonNull
    private final Context context;

    public TareaColumnCountProvider(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getColumnCount(int recyclerViewWidth) {
        return columnsForWidth(context,recyclerViewWidth);
    }

    public static int columnsForWidth(Context context, int widthPx) {
        int widthDp = dpFromPx(context, widthPx);
        if (widthDp >= 900) {
            return 6;
        } else if (widthDp >= 720) {
            return 5;
        } else if (widthDp >= 600) {
            return 4;
        } else if (widthDp >= 480) {
            return 3;
        } else if (widthDp >= 320) {
            return 2;
        } else {
            return 2;
        }
    }

    private static int dpFromPx(Context context, float px) {
        if(context==null)return 0;
        else return (int)(px / context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
