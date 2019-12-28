package com.qstpss.victimmockapplication.webclient;

import com.qstpss.victimmockapplication.model.MockEvent;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IClient {
    String serverControllerEndpoint = "mockServer/rest";

    @PUT(serverControllerEndpoint + "/start")
    Call<Void> startMockEvent(@Body MockEvent mockEvent) throws IOException;

    @PUT(serverControllerEndpoint + "/end")
    Call<Void> endMockEvent(@Body MockEvent mockEvent) throws IOException;

    @GET(serverControllerEndpoint + "/actualEventByType={type}")
    Call<MockEvent> getActiveEventByType(@Path("type") String type) throws IOException;

    @GET(serverControllerEndpoint + "/startedEvents")
    Call<List<MockEvent>> getStartedEvents() throws IOException;

    Response getResponse();
}
