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
			navigator.notification
					.vibrate(duration);
		}
  }-*/;
}
