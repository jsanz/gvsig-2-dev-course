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
package org.gvsig.visor.swing.impl;

import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.gvsig.tools.swing.api.ToolsSwingLocator;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.swing.VisorBlockPanel;
import org.gvsig.visor.swing.VisorSwingManager;

/**
 * Default implementation of the {@link VisorSwingManager}.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultVisorSwingManager implements
    VisorSwingManager {

    private VisorManager manager;
    private I18nManager i18nmanager = null;
    private WindowManager windowManager;

    public DefaultVisorSwingManager() {
        this.manager = VisorLocator.getManager();
        this.i18nmanager = ToolsLocator.getI18nManager();
        this.windowManager = ToolsSwingLocator.getWindowManager();
    }

    public VisorManager getManager() {
        return this.manager;
    }

    public String getTranslation(String key) {
        return this.i18nmanager.getTranslation(key);
    }

    public WindowManager getWindowManager() {
        return this.windowManager;
    }

    public VisorBlockPanel createVisorBlockPanel(VisorBlock visorBlock) {
        return new DefaultVisorBlockPanel(visorBlock, this);
    }
}
