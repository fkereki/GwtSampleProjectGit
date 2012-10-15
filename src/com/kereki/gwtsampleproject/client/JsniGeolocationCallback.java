package com.kereki.gwtsampleproject.client;

abstract class JsniGeolocationCallback {
    public abstract void onFailure(String result);

    public abstract void onSuccess(JsniPosition result);
}
