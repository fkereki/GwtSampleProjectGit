package com.kereki.gwtsampleproject.client;

public class JsniEvents {
  static native void setBackButtonCallback(JsniEventsCallback callback)
  /*-{
    $doc
        .addEventListener(
            "backbutton",
            function() {
              $entry(callback.@com.kereki.gwtsampleproject.client.JsniEventsCallback::onEvent()());
            }, false);
  }-*/;
}
