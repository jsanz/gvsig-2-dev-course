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

import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorService;
import org.gvsig.visor.swing.VisorSwingLocator;
import org.gvsig.visor.swing.VisorSwingManager;
import org.gvsig.visor.swing.JVisorServicePanel;
import org.gvsig.tools.library.impl.DefaultLibrariesInitializer;
import org.gvsig.tools.service.ServiceException;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;

/**
 * Main executable class for testing the Visor library.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private VisorManager manager;
    private VisorSwingManager swingManager;

    public static void main(String args[]) {
        new DefaultLibrariesInitializer().fullInitialize();
        Main main = new Main();
        main.show();
    }

    @SuppressWarnings("serial")
    public void show() {
        manager = VisorLocator.getManager();
        swingManager = VisorSwingLocator.getSwingManager();

        Action showCookie = new AbstractAction("Get a Visor") {

            public void actionPerformed(ActionEvent e) {
                showVisor(manager);
            }
        };

        Action exit = new AbstractAction("Exit") {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        JFrame frame = new JFrame("Visor example app");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        // Build the menu.
        JMenu menuFile = new JMenu("File");
        menuFile.add(new JMenuItem(showCookie));
        menuFile.add(new JMenuItem(exit));

        menuBar.add(menuFile);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton(showCookie));
        toolBar.add(new JButton(exit));

        frame.setPreferredSize(new Dimension(200, 100));
        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.PAGE_START);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void showVisor(VisorManager manager) {
        try {
            VisorService service =
                (VisorService) manager.getVisorService();
            JVisorServicePanel panel =
                swingManager.createVisor(service);
            swingManager.getWindowManager().showWindow(panel, "Visor",
                WindowManager.MODE.WINDOW);

        } catch (ServiceException e) {
            LOG.error("Error showing a Visor", e);
        }
    }

}
