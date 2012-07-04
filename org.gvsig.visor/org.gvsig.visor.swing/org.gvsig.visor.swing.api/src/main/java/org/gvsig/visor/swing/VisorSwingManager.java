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
package org.gvsig.visor.swing;

import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorManager;

/**
 * This class is responsible of the management of the library's swing user
 * interface. It is the swing library's main entry point, and provides all the
 * services to manage library swing components.
 * 
 * @see JVisorServicePanel
 * @author gvSIG team
 * @version $Id$
 */
public interface VisorSwingManager {

    /**
     * 
     * Produce a GUI for a given block
     * 
     * @param The
     *            {@link VisorBlock} to represent on a panel
     * @return {@link VisorBlockPanel}
     */
    public VisorBlockPanel createVisorBlockPanel(VisorBlock visorBlock);

    /**
     * Returns the {@link VisorManager}.
     * 
     * @return {@link VisorManager}
     * @see {@link VisorManager}
     */
    public VisorManager getManager();

    /**
     * Returns the translation of a string.
     * 
     * @param key
     *            String to translate
     * @return a String with the translation of the string passed by parameter
     */
    public String getTranslation(String key);

    /**
     * Returns the {@link WindowManager}.
     * 
     * @return {@link WindowManager}
     */
    public WindowManager getWindowManager();
}
