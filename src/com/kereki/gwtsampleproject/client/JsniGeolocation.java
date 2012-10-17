package com.kereki.gwtsampleproject.client;

public class JsniGeolocation {
  static native void getCurrentPosition(JsniGeolocationCallback callback)
  /*-{
		navigator.geolocation
				.getCurrentPosition(
						function(position) {
							$entry(callback.@com.kereki.gwtsampleproject.client.JsniGeolocationCallback::onSuccess(Lcom/kereki/gwtsampleproject/client/JsniPosition;)(position));
						},

						function(error) {
							$entry(callback.@com.kereki.gwtsampleproject.client.JsniGeolocationCallback::onFailure(Ljava/lang/String;)(error.message));
						},

						{
							maximumAge : 30000,
							timeout : 10000,
							enableHighAccuracy : true
						});
  }-*/;
}
