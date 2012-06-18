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

import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorService;
import org.gvsig.tools.junit.AbstractLibraryAutoInitTestCase;
import org.gvsig.tools.service.ServiceException;

/**
 * API compatibility tests for {@link VisorSwingManager}
 * implementations.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public abstract class VisorSwingManagerTest extends
    AbstractLibraryAutoInitTestCase {

    protected VisorManager manager;
    protected VisorSwingManager swingManager;

    @Override
    protected void doSetUp() throws Exception {
        manager = VisorLocator.getManager();
        swingManager = VisorSwingLocator.getSwingManager();
    }

    public void testGetManager() {
        assertEquals(manager, swingManager.getManager());
    }

    public void testCreationJVisorServicePanel()
        throws ServiceException {
        VisorService cookie = manager.getVisorService();

        JVisorServicePanel panel1 =
            swingManager.createVisor(cookie);

        assertNotNull(panel1);
    }

}
