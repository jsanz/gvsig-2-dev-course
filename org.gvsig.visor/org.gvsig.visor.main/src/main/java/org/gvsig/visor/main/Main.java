/**
 * gvSIG. Desktop Geographic Information System.
 *
 * Copyright (C) 2007-2012 gvSIG Association.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * For any additional information, do not hesitate to contact us
 * at info AT gvsig.com, or visit our website www.gvsig.com.
 */
package org.gvsig.visor.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.geom.primitive.Envelope;
import org.gvsig.fmap.mapcontext.MapContext;
import org.gvsig.fmap.mapcontext.MapContextLocator;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;
import org.gvsig.fmap.mapcontrol.MapControl;
import org.gvsig.fmap.mapcontrol.MapControlLocator;
import org.gvsig.fmap.mapcontrol.MapControlManager;
import org.gvsig.fmap.mapcontrol.tools.BehaviorException;
import org.gvsig.fmap.mapcontrol.tools.PanListenerImpl;
import org.gvsig.fmap.mapcontrol.tools.ZoomInListenerImpl;
import org.gvsig.fmap.mapcontrol.tools.ZoomOutRightButtonListener;
import org.gvsig.fmap.mapcontrol.tools.Behavior.Behavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.MoveBehavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.PointBehavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.RectangleBehavior;
import org.gvsig.fmap.mapcontrol.tools.Events.PointEvent;
import org.gvsig.fmap.mapcontrol.tools.Listeners.AbstractPointListener;
import org.gvsig.raster.fmap.layers.FLyrRaster;
import org.gvsig.tools.evaluator.sqljep.SQLJEPEvaluator;
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.library.impl.DefaultLibrariesInitializer;
import org.gvsig.tools.swing.api.ToolsSwingLocator;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorException;
import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.swing.VisorPanel;
import org.gvsig.visor.swing.VisorSwingLocator;
import org.gvsig.visor.swing.VisorSwingManager;

/**
 * Main executable class for testing the Visor library.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String SHOWINFO_TOOL_NAME = "Visor.infotool";

    private VisorManager manager;

    private MapControlManager mapControlManager;

    private MapControl mapControl;

    public static void main(String args[]) throws BaseException {
        new DefaultLibrariesInitializer().fullInitialize();
        Main main = new Main();
        try {
            main.show();
        } catch (Exception e) {
            LOG.error("Exception in show", e);
            System.exit(1000);
        }
    }

    @SuppressWarnings("serial")
    public void show() throws BaseException {
        mapControlManager = MapControlLocator.getMapControlManager();
        manager = VisorLocator.getManager();

        // Register default expression parser
        DALLocator.getDataManager().registerDefaultEvaluator(
            SQLJEPEvaluator.class);

        // Initialize VisorManager
        manager.initialize(getResource("data/blocks.shp"),
            getResource("data/properties.shp"), getResource("data/PNOA.tif"));


        mapControl = mapControlManager.createJMapControlPanel();
        mapControl.addBehavior("zoom", new Behavior[] {
            new RectangleBehavior(new ZoomInListenerImpl(mapControl)),
            new PointBehavior(new ZoomOutRightButtonListener(mapControl)) });
        mapControl.addBehavior("pan", new MoveBehavior(new PanListenerImpl(
            mapControl)));
        mapControl.setTool("pan");

        Action exit = new AbstractAction("Exit") {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        JFrame frame = new JFrame("Visor example app");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));

        // Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        // Build the menu.
        JMenu menuFile = new JMenu("File");
        menuFile.add(new JMenuItem(exit));

        menuBar.add(menuFile);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton(new AbstractAction("Pan") {

            public void actionPerformed(ActionEvent e) {
                mapControl.setTool("pan");
            }
        }));
        toolBar.add(new JButton(new AbstractAction("Zoom") {

            public void actionPerformed(ActionEvent e) {
                mapControl.setTool("zoom");
            }
        }));
        toolBar.add(new JButton(new AbstractAction("Zoom all") {

            public void actionPerformed(ActionEvent e) {
                zoomAll();
            }
        }));

        toolBar.add(new JButton(new AbstractAction("Info") {

            public void actionPerformed(ActionEvent e) {
                mapControl.setTool(SHOWINFO_TOOL_NAME);
                ;
            }
        }));

        toolBar.add(new JButton(exit));
        frame.setPreferredSize(new Dimension(400, 300));
        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(mapControl, BorderLayout.CENTER);

        // Display the window.
        frame.pack();
        frame.setVisible(true);

        LOG.info("Providers: "
            + DALLocator.getDataManager().getStoreProviders().toString());


        // Create layers for block and background
        FLyrVect layer =
            (FLyrVect) MapContextLocator.getMapContextManager().createLayer(
                "Blocks", manager.getBlocks());
        FLyrRaster lyrBackground =
            (FLyrRaster) MapContextLocator.getMapContextManager().createLayer(
                "Background", manager.getBackground());
        // Add layers to mapControl
        mapControl.getMapContext().beginAtomicEvent();
        mapControl.getMapContext().getLayers().addLayer(lyrBackground);
        mapControl.getMapContext().getLayers().addLayer(layer);
        mapControl.getMapContext().endAtomicEvent();

        PropertiesOfBlockListener listener = new PropertiesOfBlockListener();
        mapControl.addBehavior(SHOWINFO_TOOL_NAME, new PointBehavior(listener));

        zoomAll();
    }

    private void zoomAll() {
        MapContext mapContext = mapControl.getMapContext();
        Envelope all = mapContext.getLayers().getFullEnvelope();
        LOG.info("Full extdents " + all.toString());
        mapContext.getViewPort().setEnvelope(all);
        mapContext.invalidate();
    }

    private File getResource(String pathname) {
        URL res = this.getClass().getClassLoader().getResource(pathname);
        return new File(res.getPath());
    }

    public class PropertiesOfBlockListener extends AbstractPointListener {

        public void point(PointEvent event) throws BehaviorException {
            VisorSwingManager swingManager =
                VisorSwingLocator.getSwingManager();

            VisorBlock block;
            try {
                block = swingManager.getManager().getBlock(event.getMapPoint());
                if (block == null) {
                    return;
                }
                VisorPanel panel = swingManager.createVisorBlockPanel(block);
                ToolsSwingLocator.getWindowManager().showWindow(
                    panel.getComponent(), "Block information",
                    WindowManager.MODE.TOOL);
            } catch (VisorException e) {
                // FIXME: Process exception
                throw new RuntimeException(
                    "Can't show properties of selected block.", e);
            }
        }
    }
}
