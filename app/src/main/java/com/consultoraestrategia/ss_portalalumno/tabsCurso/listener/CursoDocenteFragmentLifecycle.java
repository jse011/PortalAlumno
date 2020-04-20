package com.consultoraestrategia.ss_portalalumno.tabsCurso.listener;

import android.os.Bundle;

/**
 * Created by SCIEV on 21/02/2018.
 */

public interface CursoDocenteFragmentLifecycle {
    void onPauseFragment();
    void onResumeFragment();
    void onResumeFragment(Bundle bundle);
}
