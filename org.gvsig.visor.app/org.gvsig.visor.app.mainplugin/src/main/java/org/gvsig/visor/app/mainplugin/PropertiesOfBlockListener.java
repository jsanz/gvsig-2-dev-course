/* gvSIG. Desktop Geographic Information System.
 *
 * Copyright © 2007-2012 gvSIG Association
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
package org.gvsig.visor.app.mainplugin;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.gvsig.andami.messages.NotificationManager;
import org.gvsig.fmap.mapcontrol.tools.BehaviorException;
import org.gvsig.fmap.mapcontrol.tools.Events.PointEvent;
import org.gvsig.fmap.mapcontrol.tools.Listeners.AbstractPointListener;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorException;
import org.gvsig.visor.swing.VisorBlockPanel;
import org.gvsig.visor.swing.VisorSwingLocator;
import org.gvsig.visor.swing.VisorSwingManager;

/**
 * @author jsanz
 * 
 */
public class PropertiesOfBlockListener extends AbstractPointListener {

    public static final String PROPERTIES_OF_BLOCK_TOOL = "Visor.info";

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.gvsig.fmap.mapcontrol.tools.Listeners.PointListener#point(org.gvsig
     * .fmap.mapcontrol.tools.Events.PointEvent)
     */
    public void point(PointEvent event) throws BehaviorException {
        final VisorSwingManager visorSwingManager =
            VisorSwingLocator.getSwingManager();
        VisorBlock block = null;

        // Get the block using the event map point
        try {
            block =
                visorSwingManager.getManager().getBlock(event.getMapPoint());
            if (block == null) {
                return;
            }
            // Create the panel associated to the block
            final VisorBlockPanel visorBlockPanel =
                visorSwingManager.createVisorBlockPanel(block);
            // Show the panel on the main thread
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    // Show it
                    String blockPanelTitle =
                        visorSwingManager.getTranslation("block_panel_title");
                    visorSwingManager.getWindowManager().showWindow(
                        (JComponent) visorBlockPanel, blockPanelTitle,
                        WindowManager.MODE.TOOL);
                }
            });
        } catch (VisorException e) {
            NotificationManager.addError("Error showing block information", e);
        }

    }

}
