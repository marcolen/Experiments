package com.elog.API.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElogAPI implements EntryPoint {

	public void onModuleLoad() {


		NetworkGraph sample = new NetworkGraph();
		RootPanel.get("container").add(sample);

	}
}
