package com.kereki.gwtsampleproject.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetWorldTimeServiceAsync {
    void getWorldTime(String zone, AsyncCallback<String> callback);
}
