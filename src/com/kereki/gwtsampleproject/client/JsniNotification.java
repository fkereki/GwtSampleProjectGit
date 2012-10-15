package com.kereki.gwtsampleproject.client;

public class JsniNotification {
  static native void beep(int count)
  /*-{
		navigator.notification.beep(count);
  }-*/;

  static native void vibrate(int duration)
  /*-{
		navigator.notification.vibrate(duration);
  }-*/;
}
