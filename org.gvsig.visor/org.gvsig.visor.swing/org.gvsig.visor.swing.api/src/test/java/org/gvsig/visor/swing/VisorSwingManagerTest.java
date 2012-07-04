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

import java.io.File;

import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.tools.dispose.DisposableIterator;
import org.gvsig.tools.junit.AbstractLibraryAutoInitTestCase;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;

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
        manager.initialize(getBlocksForTest(), getPropertiesForTest(),
            getBackgroundForTest());
        swingManager = VisorSwingLocator.getSwingManager();
    }

    public void testGetManager() {
        assertEquals(manager, swingManager.getManager());
    }

    public void testCreateVisorBlockPanel() throws Exception{
        FeatureStore store = null;
        DisposableIterator it = null;
        try{
            // get the iterator from the dataset
            store = manager.getBlocks();
            it = store.getFeatureSet().fastIterator();
            assertTrue(it.hasNext());
            // get the first feature
            Feature feat = (Feature) it.next();
            // Create a geometry from COORD attributes
            Geometry geom =
                GeometryLocator.getGeometryManager().createPoint(
                    feat.getDouble("COORX"), feat.getDouble("COORY"),
                Geometry.TYPES.POINT);

            // Get the block that intersects the geom and a panel from it
            VisorBlock block = manager.getBlock(geom);
            assertNotNull(block);
            VisorBlockPanel panel = swingManager.createVisorBlockPanel(block);

            // check panel and associated block values

            assertNotNull(panel);
            assertNotNull(panel.getComponent());
            assertNotNull(panel.getVisorBlock());
            assertEquals(block.getCode(), panel.getVisorBlock().getCode());

        }catch(Exception e){
            throw new Exception(e);
        }finally{
            if (store != null) {
                store.dispose();
            }
            if (it != null) {
                it.dispose();
            }
        }
        
    }

    /**
     * 
     * @return the File descriptor of a shapefile with
     *         some blocks for testing the GUI
     */
    protected abstract File getBlocksForTest();

    /**
     * 
     * @return the File descriptor of a shapefile with
     *         some properties for testing the GUI
     */
    protected abstract File getPropertiesForTest();

    /**
     * 
     * @return the File descriptor of a TIFF
     *         for testing the GUI
     */
    protected abstract File getBackgroundForTest();

}
