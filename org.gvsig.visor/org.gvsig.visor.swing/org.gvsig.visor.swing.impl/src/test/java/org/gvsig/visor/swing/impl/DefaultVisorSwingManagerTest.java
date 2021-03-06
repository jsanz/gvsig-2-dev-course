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

import java.io.File;
import java.net.URL;

import org.gvsig.visor.swing.VisorSwingManager;
import org.gvsig.visor.swing.VisorSwingManagerTest;

/**
 * {@link VisorSwingManager} API compatibility tests for the
 * {@link DefaultVisorSwingManager} implementation.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultVisorSwingManagerTest extends
    VisorSwingManagerTest {

    @Override
    protected File getPropertiesForTest() {
        return getResource("data/properties.shp");
    }

    @Override
    protected File getBackgroundForTest() {
        return getResource("data/PNOA.tif");
    }

    @Override
    protected File getBlocksForTest() {
        return getResource("data/blocks.shp");
    }

    private File getResource(String pathname) {
        URL res = this.getClass().getClassLoader().getResource(pathname);
        return new File(res.getPath());
    }

}

