package com.kereki.gwtsampleproject.client;

import com.google.gwt.core.client.JavaScriptObject;

public class JsniPosition extends JavaScriptObject {
    protected JsniPosition() {
    }

    /*
     * We could have a getLatitude() method to directly return
     * this.coords.latitude, but using JsniPosition is easier if it matches
     * PhoneGap's documentation.
     */

    public final native JsniPositionCoords getCoords()
    /*-{
		return this.coords;
    }-*/;

    public final native double getTimeStamp()
    /*-{
		return this.timestamp;
    }-*/;
}
