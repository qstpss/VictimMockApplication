package com.qstpss.victimmockapplication.webclient;

import com.google.gson.GsonBuilder;
import com.qstpss.victimmockapplication.model.MockEvent;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientImpl implements IClient {

    private static final String hostAddress = "http://185.204.2.124:8080/";
    private Retrofit retrofit;
    private Response response;

    public ClientImpl() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(hostAddress)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .serializeNulls()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                                .build();
    }

    @Override
    public Call<Void> startMockEvent(MockEvent mockEvent) throws IOException {
        IClient client = retrofit.create(IClient.class);
        Call<Void> call = client.startMockEvent(mockEvent);
        this.response = call.execute();
        return call;
    }

    @Override
    public Call<Void> endMockEvent(MockEvent mockEvent) throws IOException {
        IClient client = retrofit.create(IClient.class);
        Call<Void> call = client.endMockEvent(mockEvent);
        this.response = call.execute();
        return call;
    }

    @Override
    public Call<MockEvent> getActiveEventByType(String type) throws IOException {
        IClient client = retrofit.create(IClient.class);
        Call<MockEvent> activeMockEvent = client.getActiveEventByType(type);
        this.response = activeMockEvent.execute();
        return activeMockEvent;
    }

    @Override
    public Call<List<MockEvent>> getStartedEvents() throws IOException {
        IClient client = retrofit.create(IClient.class);
        Call<List<MockEvent>> call = client.getStartedEvents();
        this.response = call.execute();
        return call;
    }


    @Override
    public Response getResponse() {
        return response;
    }
}
