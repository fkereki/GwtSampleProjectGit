package com.kereki.gwtsampleproject.server;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kereki.gwtsampleproject.client.GetWorldTimeService;

/**
 * The server side implementation of the RPC service. Returns a string in format
 * HH:MM:SS if the time zone is correct, or an empty string otherwise.
 */
@SuppressWarnings("serial")
public class GetWorldTimeServiceImpl extends RemoteServiceServlet implements
  GetWorldTimeService {

  @Override
  public String getWorldTime(String zone) {
    try {
      Calendar cal= new GregorianCalendar(TimeZone.getTimeZone(zone));
      return String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    } catch (Exception e) {
      return "";
    }
  }
}
