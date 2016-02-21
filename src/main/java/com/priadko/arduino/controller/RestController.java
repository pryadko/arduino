package com.priadko.arduino.controller;

import com.priadko.arduino.entry.Mock;
import com.priadko.arduino.repository.SessionHandler;
import com.priadko.arduino.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Controller
@RequestMapping("/measure")
@ServerEndpoint(value = "/measureMock", configurator = SpringConfigurator.class)
public class RestController {

    @Autowired
    private DataService dataService;

    @Autowired
    private SessionHandler sessionHandler;

    /**
     * main method for change state Mock variable
     * @param mock value from client
     */
    @RequestMapping(value = "/mock", method = RequestMethod.POST)
    public
    @ResponseBody
    @Async
    void setMeasureMock(@RequestBody Mock mock) {
        sessionHandler.toggleMock();
        dataService.setMeasureMock(mock);
    }

    /**
     * simple example for get request
     * @return return value to client
     */
    @RequestMapping(value = "mock", method = RequestMethod.GET)
    public
    @ResponseBody
    Mock getMeasureMock() {
        return dataService.getMeasureMock();
    }

    /**
     * long pooling get
     * @return loon pooling answer
     */
    @RequestMapping(value = "mockLong", method=RequestMethod.GET)
    @ResponseBody
    public DeferredResult<Mock> getMessages() {

        return dataService.getDeferredMeasureMock();
    }

    @OnOpen
    public void onOpen(Session session){
        sessionHandler.addSession(session);
    }

    /**
     * example for web socket
     * @param measureMock input value from websooet
     * @param session user session
     */
    @OnMessage
    public void onMessage(String measureMock, Session session){
        sessionHandler.toggleMock();
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        sessionHandler.removeSession(session);
    }


}
