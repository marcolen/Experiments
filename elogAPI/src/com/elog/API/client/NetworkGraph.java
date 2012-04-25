package com.elog.API.client;

/*******************************************************************************
 * Copyright 2009, 2010 Lars Grammel 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.  
 *******************************************************************************/


import org.thechiselgroup.choosel.protovis.client.PV;
import org.thechiselgroup.choosel.protovis.client.PVDom;
import org.thechiselgroup.choosel.protovis.client.PVDomNode;
import org.thechiselgroup.choosel.protovis.client.PVEventHandler;
import org.thechiselgroup.choosel.protovis.client.PVForceLayout;
import org.thechiselgroup.choosel.protovis.client.PVLink;
import org.thechiselgroup.choosel.protovis.client.PVPanel;
import org.thechiselgroup.choosel.protovis.client.ProtovisWidget;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsArgs;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsArrayGeneric;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsDoubleFunction;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsFunction;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsStringFunction;
import org.thechiselgroup.choosel.protovis.client.jsutil.JsUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * Protovis/GWT implementation of <a href=
 * "http://gitorious.org/protovis/protovis/blobs/e90698ec7a4af638d871d5179e9c391c1f9c5597/tests/layout/force-toggle.html"
 * >Force Toggle Test</a>.
 * 
 * @author Lars Grammel
 */


public class NetworkGraph extends ProtovisWidget implements ProtovisExample {

    @Override
    public Widget asWidget() {
        return this;
    }

    private void createVisualization(FlareData.Unit flare) {
        final PVDomNode root = PVDom.create(flare,
                new FlareData.UnitDomAdapter());

        root.toggle(true);
        root.toggle();

        PVPanel vis = getPVPanel().width(900).height(900).fillStyle("white")
                .event(PV.Event.MOUSEDOWN, PV.Behavior.pan())
                .event(PV.Event.MOUSEWHEEL, PV.Behavior.zoom());
       
 
        
        final PVForceLayout layout = vis.add(PV.Layout.Force())
                .nodes(new JsFunction<JavaScriptObject>() {
                    @Override
                    public JavaScriptObject f(JsArgs args) {
                        return root.nodes();
                    }
                }).links(PV.Layout.HierarchyLinks());

        layout.link().add(PV.Line).lineWidth(10)
//        .add(PV.Dot).data(new JsFunction<JsArrayGeneric<Pair>>()
//		{
//			@Override
//			public JsArrayGeneric<Pair> f(JsArgs args)
//			{
//				PVLink l = args.getObject(); 
//				
//				 double x= l.targetNode().x() - 2.6 * Math.cos(Math.atan2(l.targetNode().y() - l.sourceNode().y(), l.targetNode().x() - l.sourceNode().x()));
//				 double y= l.targetNode().y() - 2.6 * Math.sin(Math.atan2(l.targetNode().y() - l.sourceNode().y(), l.targetNode().x() - l.sourceNode().x()));
//
//				 JsArrayGeneric<Pair> data = JsUtils.createJsArrayGeneric();
//			     data.push(new Pair(x, y));   
//				 return  data;
//			}
//		})
//		.angle(new JsDoubleFunction()
//		{
//			@Override
//			public double f(JsArgs args)
//			{
//				PVLink l = args.getObject(); 
//				return  Math.atan2(l.targetNode().y() - l.sourceNode().y(), l.targetNode().x() - l.sourceNode().x()) - Math.PI/2;
//			}
//		})
//		.shape("triangle").fillStyle("#999") .size(.5);

        ;
        
        layout.node()
                .add(PV.Dot)
                .title(new JsStringFunction() {
                    public String f(JsArgs args) {
                        PVDomNode d = args.getObject();
                        return d.nodeName();
                    }
                })
                .size(new JsDoubleFunction() {
                    public double f(JsArgs args) {
                        PVDomNode d = args.getObject();
                        return d.hasNodeValue() ? Math.max(
                                d.nodeValueInt() / 100, 10) : 10;
                    }
                })
                .fillStyle(new JsStringFunction() {
                    public String f(JsArgs args) {
                        PVDomNode n = args.getObject();
                        return n.toggled() ? "#1f77b4"
                                : (n.firstChild() != null ? "#aec7e8"
                                        : "#ff7f0e");
                    }
                }).lineWidth(3)
                .event(PV.Event.MOUSEDOWN, PV.Behavior.drag())
                .event(PV.Event.DRAG, layout)
                .event(PV.Event.DOUBLE_CLICK, new PVEventHandler() {
                    @Override
                    public void onEvent(Event e, String pvEventType, JsArgs args) {
                        PVDomNode n = args.getObject();
                        n.toggle(e.getAltKey());
                        layout.reset();
                    }
                });
        

        
        layout.label().add(PV.Label) 
        .text(new JsStringFunction() {
            public String f(JsArgs args) {
                PVDomNode d = args.getObject();
                return d.nodeName();
            }
        }); 
    
        getPVPanel().render();
    }

    @Override
    public String getDescription() {
        return "Use double-click to expand/collapse direct children. Use ALT + double-click to expand/collapse all children.";
    }

    public String getProtovisExampleURL() {
        return "http://gitorious.org/protovis/protovis/blobs/e90698ec7a4af638d871d5179e9c391c1f9c5597/tests/layout/force-toggle.html";
    }

    public String getSourceCodeFile() {
        return "ForceToggleExample.java";
    }

    protected void onAttach() {
        super.onAttach();
        initPVPanel();
        createVisualization(FlareData.data());
       
    }

    public String toString() {
        return "Force Toggle Test";
    }
    
    
    

}