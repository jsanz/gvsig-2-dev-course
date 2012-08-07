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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gvsig.andami.plugins.Extension;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.documents.view.gui.IView;

/**
 * @author jsanz
 * 
 */
public class PropertiesOfBlockToolExtension extends Extension {

    private static final Logger LOG = LoggerFactory
        .getLogger(PropertiesOfBlockToolExtension.class);

    public final static String SET_PROPERTIES_OF_BLOCK_TOOL_ACTION =
        "SET_PROPERTIES_OF_BLOCK_TOOL";
    private ApplicationManager applicationManager;

    @Override
    public void postInitialize() {
        super.postInitialize();
        applicationManager = ApplicationLocator.getManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gvsig.andami.plugins.IExtension#initialize()
     */
    public void initialize() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gvsig.andami.plugins.IExtension#execute(java.lang.String)
     */
    public void execute(String actionCommand) {
        IWindow aWindow = applicationManager.getActiveWindow();
        // if it's a view and the action command is correct, activate the tool
        if (aWindow instanceof IView
            && actionCommand
                .equalsIgnoreCase(SET_PROPERTIES_OF_BLOCK_TOOL_ACTION)) {
            ((IView) aWindow).getMapControl().setTool(
                PropertiesOfBlockListener.PROPERTIES_OF_BLOCK_TOOL);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gvsig.andami.plugins.IExtension#isEnabled()
     */
    public boolean isEnabled() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gvsig.andami.plugins.IExtension#isVisible()
     */
    public boolean isVisible() {
        /*
         * This seems to be accessed before post initialize is executed
         * so we need to check if the applicationManager is initialized
         */
        if (applicationManager == null) {
            return false;
        } else {
            // get the active window
            IWindow aWindow = applicationManager.getActiveWindow();
            // check if exists, it's a view and the view has the listener
            return aWindow != null
                && aWindow instanceof IView
                && ((IView) aWindow)
                    .getMapControl()
                    .getMapToolsKeySet()
                    .contains(
                        PropertiesOfBlockListener.PROPERTIES_OF_BLOCK_TOOL);
        }
    }

}
