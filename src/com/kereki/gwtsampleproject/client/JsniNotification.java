package com.kereki.gwtsampleproject.client;

public class JsniNotification {
  static native void beep(int count)
  /*-{
    if (typeof navigator.notification === "undefined") {
      alert("beep!");
    } else {
      navigator.notification.beep(count);
    }
  }-*/;

  static native void vibrate(int duration)
  /*-{
    if (typeof navigator.notification === "undefined") {
      alert("bzzz!");
    } else {
      navigator.notification.vibrate(duration);
    }
  }-*/;

  static native void confirm(
    String message,
    JsniConfirmCallback callback,
    String title,
    String buttonLabels)
  /*-{
    if (typeof navigator.notification === "undefined") {
      var result= confirm(message);
      $entry(callback.@com.kereki.gwtsampleproject.client.JsniConfirmCallback::onConfirm(Z)(result));
    } else {
      navigator.notification
          .confirm(
              message,
              function(result) {
                $entry(callback.@com.kereki.gwtsampleproject.client.JsniConfirmCallback::onConfirm(Z)(result==2));
              }, title, buttonLabels);
    }
  }-*/;
}
