package com.kereki.gwtsampleproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.event.SearchButtonPressedEvent;
import com.googlecode.gwtphonegap.client.event.SearchButtonPressedHandler;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationOptions;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import com.googlecode.gwtphonegap.client.notification.ConfirmCallback;

public class GwtSampleProject implements EntryPoint {
  static final String MY_HOST= "http://192.168.1.200";
  static final String MY_PATH= "/GwtSampleProject";

  // Remote service proxy to talk to the server-side actual service.
  private final GetWorldTimeServiceAsync timeProxy= GWT.create(GetWorldTimeService.class);

  // Set up PhoneGap
  private final PhoneGap phoneGap= GWT.create(PhoneGap.class);

  static final boolean useGwtPhoneGap= true; // false implies using JSNI only


  @Override
  public void onModuleLoad() {
    if (!GWT.getHostPageBaseURL().startsWith("file://") || useGwtPhoneGap) {
      onModuleLoadGwtPhoneGap();
    } else {
      onModuleLoadJsni(new JsniEventsCallback() {
        @Override
        public void onEvent() {
          finishOnLoad();
        }
      });
    }
  }


  public native void onModuleLoadJsni(JsniEventsCallback callback) /*-{
    $doc
        .addEventListener(
            "deviceready",
            function() {
              $entry(callback.@com.kereki.gwtsampleproject.client.JsniEventsCallback::onEvent()());
            }, false);
  }-*/;


  public void onModuleLoadGwtPhoneGap() {
    /*
     * Configure PhoneGap
     */
    phoneGap.addHandler(new PhoneGapAvailableHandler() {
      @Override
      public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
        // PhoneGap is ready!
        finishOnLoad();
      }
    });

    phoneGap.addHandler(new PhoneGapTimeoutHandler() {
      @Override
      public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
        // PhoneGap won't work; alert the user, crash the app, whatever...
      }
    });

    phoneGap.initializePhoneGap();
  }

  /*
   * PhoneGap is ready; finish setting everything up
   */
  public void finishOnLoad() {
    /*
     * Adjust the RPC service if running on the smartphone
     */
    if (GWT.getHostPageBaseURL().startsWith("file://")) {
      final String newUrl= GWT.getModuleBaseURL().replace("file:///android_asset/www",
        MY_HOST + ":8080" + MY_PATH);

      ((ServiceDefTarget) timeProxy).setServiceEntryPoint(newUrl
        + ((ServiceDefTarget) timeProxy).getServiceEntryPoint().replace(
          GWT.getModuleBaseURL(), ""));

      ((ServiceDefTarget) timeProxy).setRpcRequestBuilder(new RpcRequestBuilder() {
        @Override
        protected void doFinish(RequestBuilder rb) {
          super.doFinish(rb);
          rb.setHeader(MODULE_BASE_HEADER, newUrl);
        }
      });
    }

    /*
     * Set up the UI and events
     */
    final TextBox timeZoneTextBox= new TextBox();
    RootPanel.get("timeZoneContainer").add(timeZoneTextBox);

    final Button getTimeRpcButton= new Button("Get Time through RPC");
    RootPanel.get("getTimeRpcContainer").add(getTimeRpcButton);
    getTimeRpcButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        timeProxy.getWorldTime(timeZoneTextBox.getValue(), new AsyncCallback<String>() {
          @Override
          public void onSuccess(String result) {
            Window.alert("Time by RPC is " + result);
          }

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Failure ... " + caught.getMessage());
          }
        });
      }
    });

    final Button getTimeJsonButton= new Button("Get Time through JSON");
    RootPanel.get("getTimeJsonContainer").add(getTimeJsonButton);
    getTimeJsonButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        final JsonpRequestBuilder jsonprb= new JsonpRequestBuilder();
        jsonprb.requestObject(MY_HOST + ":80/getjsontime.php?unga=bunga&zone="
          + timeZoneTextBox.getValue(), new AsyncCallback<JsonTime>() {
          @Override
          public void onSuccess(JsonTime result) {
            Window.alert("Time by JSONP is " + result.getTime());
          }

          @Override
          public void onFailure(final Throwable caught) {
            Window.alert("Failure ... " + caught.getMessage());
          }
        });
      }
    });

    final Button getPositionJsniButton= new Button("Get Position through JSNI");
    RootPanel.get("getPositionJsniContainer").add(getPositionJsniButton);
    getPositionJsniButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        JsniGeolocation.getCurrentPosition(new JsniGeolocationCallback() {
          @Override
          public void onSuccess(JsniPosition result) {
            Window.alert("Through JSNI, you are at coordinates "
              + result.getCoords().getLatitude() + " , "
              + result.getCoords().getLongitude() + " +/- "
              + result.getCoords().getAccuracy() + " meters. ");
          }

          @Override
          public void onFailure(String result) {
            Window.alert("Failure... " + result);
          }
        });
      }
    });

    final Button getPositionGpButton= new Button("Get Position through GWT-Phonegap");
    RootPanel.get("getPositionGpContainer").add(getPositionGpButton);
    if (useGwtPhoneGap) {
      getPositionGpButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          GeolocationOptions options= new GeolocationOptions();
          options.setEnableHighAccuracy(true);
          options.setMaximumAge(30000);
          options.setTimeout(10000);

          phoneGap.getGeolocation().getCurrentPosition(new GeolocationCallback() {
            @Override
            public void onSuccess(Position position) {
              Window.alert("Through GWT-Phonegap, you are at coordinates "
                + position.getCoordinates().getLatitude() + ","
                + position.getCoordinates().getLongitude() + " +/- "
                + position.getCoordinates().getAccuracy() + " meters.");
            }

            @Override
            public void onFailure(PositionError error) {
              Window.alert("Failure... " + error.getMessage());
            }
          }, options);
        }
      });
    } else {
      getPositionGpButton.setEnabled(false);
    }

    /*
     * Set up vibration and beep alerts
     */
    final Button jsniVibrateButton= new Button("Beep through JSNI");
    RootPanel.get("jsniBeepContainer").add(jsniVibrateButton);
    jsniVibrateButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        JsniNotification.beep(1);
        JsniNotification.vibrate(2209);
      }
    });

    final Button gpVibrateButton= new Button("Vibrate through GWT-Phonegap");
    RootPanel.get("gpVibrateContainer").add(gpVibrateButton);
    if (useGwtPhoneGap) {
      gpVibrateButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          phoneGap.getNotification().beep(1);
          phoneGap.getNotification().vibrate(2209);
        }
      });
    } else {
      gpVibrateButton.setEnabled(false);
    }

    /*
     * Confirm something through JSNI and GWT-Phonegap
     */
    final Button stdConfirmButton= new Button("JSNI PhoneGap Confirm");
    RootPanel.get("stdConfirmContainer").add(stdConfirmButton);
    stdConfirmButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        JsniNotification.confirm("Will you confirm this?", new JsniConfirmCallback() {
          @Override
          public void onConfirm(boolean confirmed) {
            Window.alert(confirmed? "OK, confirmed": "No, denied");
          }
        }, "Confirm title", "Yep,Nope");
      }
    });

    final Button gpConfirmButton= new Button("GWT-Phonegap Confirm");
    RootPanel.get("gpConfirmContainer").add(gpConfirmButton);
    if (useGwtPhoneGap) {
      gpConfirmButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          phoneGap.getNotification().confirm("Are you confirming this?",
            new ConfirmCallback() {
              @Override
              public void onConfirm(int button) {
                Window.alert((button == 1)? "OK, confirmed": "No, denied");
              }
            }, "gwt-phonegap", new String[]{"Yeah", "Nope"});
        }
      });
    } else {
      gpConfirmButton.setEnabled(false);
    }

    /*
     * Process some events
     */
    JsniEvents.setBackButtonCallback(new JsniEventsCallback() {
      @Override
      public void onEvent() {
        // Process Android "Back" button through JSNI
        Window.alert("Here we'd go back!");
      }
    });

    if (useGwtPhoneGap) {
      phoneGap.getEvent().getSearchButton()
        .addSearchButtonHandler(new SearchButtonPressedHandler() {
          @Override
          public void onSearchButtonPressed(SearchButtonPressedEvent event) {
            // Process Android's "search" button through PhoneGap
            Window.alert("Here we'd do a search!");
          }
        });
    }

    /*
     * Set up the phone, SMS, and e-Mail links
     */
    DOM.getElementById("phonelink").setAttribute("href", "tel:555-5555");

    DOM.getElementById("smslink").setAttribute("href",
      "sms:555-5555?body=Some SMS text...!");

    DOM.getElementById("maillink").setAttribute("href",
      "mailto:someone@server.com?subject=An email!&body=Some email text...");
  }
}
