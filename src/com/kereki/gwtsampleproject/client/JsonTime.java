package com.kereki.gwtsampleproject.client;

import com.google.gwt.core.client.JavaScriptObject;

public class JsonTime extends JavaScriptObject {
    protected JsonTime() {
    }

    public final native String getTime()
    /*-{
		return this.time;
    }-*/;
}
