package com.priadko.arduino.services.impl;

import com.priadko.arduino.entry.Mock;
import com.priadko.arduino.services.DataService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class DataServiceImpl implements DataService {

    private static Mock mockValue;

    static {
        mockValue = new Mock();
        mockValue.setValue(false);
    }

    private final ArrayBlockingQueue<DeferredResult<Mock>> messageDeferredResultList
            = new ArrayBlockingQueue<>(100);

    @Override
    @Async
    public void setMeasureMock(Mock value) {
        mockValue = value;
        for (DeferredResult<Mock> deferredResult : this.messageDeferredResultList) {
            deferredResult.setResult(mockValue);
        }
    }

    @Override
    public DeferredResult<Mock> getDeferredMeasureMock() {

        final DeferredResult<Mock> deferredResult =
                new DeferredResult<>(10_000L, mockValue);

        deferredResult.onCompletion(() -> messageDeferredResultList.remove(deferredResult));

        deferredResult.onTimeout(() -> messageDeferredResultList.remove(deferredResult));

        messageDeferredResultList.add(deferredResult);

        return deferredResult;
    }

    @Override
    public Mock getMeasureMock() {
        return mockValue;
    }
}
