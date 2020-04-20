package com.consultoraestrategia.ss_portalalumno.base;

/**
 * Created by @stevecampos on 15/09/2017.
 */

/**
 * Interface for schedulers, see {@link UseCaseThreadPoolScheduler}.
 */

public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                          final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(
            final UseCase.UseCaseCallback<V> useCaseCallback);
}

