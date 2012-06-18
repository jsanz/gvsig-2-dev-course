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
package org.gvsig.visor;

import org.gvsig.tools.locator.BaseLocator;
import org.gvsig.tools.locator.Locator;
import org.gvsig.tools.locator.LocatorException;

/**
 * This locator is the entry point for the Visor library, providing
 * access to all Visor services through the {@link VisorManager}
 * .
 * 
 * @author gvSIG team
 * @version $Id$
 */
public class VisorLocator extends BaseLocator {

    /**
     * Visor manager name.
     */
    public static final String MANAGER_NAME = "Visor.manager";

    /**
     * Visor manager description.
     */
    public static final String MANAGER_DESCRIPTION = "Visor Manager";

    private static final String LOCATOR_NAME = "Visor.locator";

    /**
     * Unique instance.
     */
    private static final VisorLocator INSTANCE =
        new VisorLocator();

    /**
     * Return the singleton instance.
     * 
     * @return the singleton instance
     */
    public static VisorLocator getInstance() {
        return INSTANCE;
    }

    /**
     * Return the Locator's name.
     * 
     * @return a String with the Locator's name
     */
    public final String getLocatorName() {
        return LOCATOR_NAME;
    }

    /**
     * Return a reference to the VisorManager.
     * 
     * @return a reference to the VisorManager
     * @throws LocatorException
     *             if there is no access to the class or the class cannot be
     *             instantiated
     * @see Locator#get(String)
     */
    public static VisorManager getManager() throws LocatorException {
        return (VisorManager) getInstance().get(MANAGER_NAME);
    }

    /**
     * Registers the Class implementing the VisorManager interface.
     * 
     * @param clazz
     *            implementing the VisorManager interface
     */
    public static void registerManager(
        Class<? extends VisorManager> clazz) {
        getInstance().register(MANAGER_NAME, MANAGER_DESCRIPTION, clazz);
    }

}
