package com.priadko.arduino.services;

import com.priadko.arduino.entry.Mock;
import org.springframework.web.context.request.async.DeferredResult;

public interface DataService {

    void setMeasureMock(Mock value);

    Mock getMeasureMock();

    DeferredResult<Mock> getDeferredMeasureMock();
}
