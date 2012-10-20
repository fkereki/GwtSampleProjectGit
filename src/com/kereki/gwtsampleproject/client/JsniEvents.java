package com.kereki.gwtsampleproject.client;

public class JsniEvents {
  static void setBackButtonCallback(JsniEventsCallback callback) {
    addListener("backbutton", callback);
  }

  private static native void addListener(String eventName, JsniEventsCallback callback) /*-{
    $doc
        .addEventListener(
            eventName,
            function() {
              $entry(callback.@com.kereki.gwtsampleproject.client.JsniEventsCallback::onEvent()());
            }, false);
  }-*/;
}
