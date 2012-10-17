package com.kereki.gwtsampleproject.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service
 */
@RemoteServiceRelativePath("getworldtime")
public interface GetWorldTimeService extends RemoteService {
  String getWorldTime(String zone);
}
