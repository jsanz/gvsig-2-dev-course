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
package org.gvsig.visor.app.mainplugin;

import org.gvsig.andami.messages.NotificationManager;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorService;
import org.gvsig.visor.swing.VisorSwingLocator;
import org.gvsig.visor.swing.VisorSwingManager;
import org.gvsig.visor.swing.JVisorServicePanel;
import org.gvsig.tools.service.ServiceException;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;

/**
 * Andami extension to show Visor in the application.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class VisorExtension extends Extension {

    private VisorManager manager;
    private VisorSwingManager swingManager;

    public void initialize() {
        // Do nothing
    }

    @Override
    public void postInitialize() {
        super.postInitialize();
        manager = VisorLocator.getManager();
        // Asignamos el locator e iniciamos la instancia
        swingManager = VisorSwingLocator.getSwingManager();
    }

    public void execute(String actionCommand) {
        showVisor(manager);
    }

    public void showVisor(VisorManager manager) {
        try {
            VisorService service =
                (VisorService) manager.getVisorService();
            JVisorServicePanel panel =
                swingManager.createVisor(service);
            swingManager.getWindowManager().showWindow(panel, "Visor",
                WindowManager.MODE.DIALOG);

        } catch (ServiceException e) {
            NotificationManager.addError(e);
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isVisible() {
        return true;
    }

}
