package com.kereki.gwtsampleproject.client;

public class JsniEvents {
  static native void setBackButtonCallback(JsniEventsCallback callback)
  /*-{
    var myCallback= $entry(function() {
      callback.@com.kereki.gwtsampleproject.client.JsniEventsCallback::onEvent()();
    });

    $doc.addEventListener("backbutton", $entry(function() {
      alert("go back!");
      myCallback();
    }), false);
  }-*/;
}
