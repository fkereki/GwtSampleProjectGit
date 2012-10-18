package com.kereki.gwtsampleproject.client;

import com.google.gwt.core.client.JavaScriptObject;

public class JsniPositionCoords extends JavaScriptObject {
  protected JsniPositionCoords() {
  }

  /*
   * We should have more methods, to return the other fields in the coords
   * object, but for our example we won't be needing them.
   */
  public final native double getLatitude()
  /*-{
    return this.latitude;
  }-*/;

  public final native double getLongitude()
  /*-{
    return this.longitude;
  }-*/;

  public final native double getAccuracy()
  /*-{
    return this.accuracy;
  }-*/;

}
