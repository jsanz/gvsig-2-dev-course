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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gvsig.fmap.dal.DataSet;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.GeometryManager;
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.junit.AbstractLibraryAutoInitTestCase;
import org.gvsig.tools.visitor.VisitCanceledException;
import org.gvsig.tools.visitor.Visitor;

/**
 * API compatibility tests for {@link VisorManager} implementations.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public abstract class VisorManagerTest extends
    AbstractLibraryAutoInitTestCase {

    static Logger LOG = LoggerFactory.getLogger(VisorManagerTest.class);

    protected VisorManager manager;

    @Override
    protected void doSetUp() throws Exception {
        manager = VisorLocator.getManager();

        manager.initialize(getBlockForTest(), getPropertiesForTest(),
            getBackgroundForTest());
    }

    /**
     * Try to open the store for the blocks and check if
     * it has features in it or not
     * 
     * @throws Exception
     */
    public void testGetBlocks() throws Exception {
        FeatureStore store = null;
        FeatureSet set = null;
        store = manager.getBlocks();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check it's not empty
        set = store.getFeatureSet();
        assertFalse("The blocks FeatureStore has not elements", set.isEmpty());
        set.dispose();

    }

    /**
     * Troy to open the properties store and check
     * if it has features or not
     * 
     * @throws Exception
     */
    public void testGetProperties() throws Exception {
        FeatureStore store = null;
        FeatureSet set = null;

        store = manager.getProperties();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check it's not empty
        set = store.getFeatureSet();
        assertFalse("The blocks FeatureStore has not elements", set.isEmpty());

        set.dispose();
    }

    /**
     * Try to opoen the background TIFF file and
     * get a data set assuring is not null
     * 
     * @throws Exception
     */
    public void testGetBackground() throws Exception {
        DataStore store = null;
        DataSet set = null;
        store = manager.getBackground();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check is not null
        set = store.getDataSet();
        assertNotNull(set);
        // We can dispose it safely as it's not null
        set.dispose();
    }

    /**
     * Test that the getBlock method works
     * 
     * @throws Exception
     */
    public void testGetBlock() throws Exception {
        // First store properties ids by block on a map
        final Map<String, List<String>> blockIds =
            new HashMap<String, List<String>>();

        FeatureStore store = null;
        store = manager.getProperties();
        // Check if the FeatureStore is null
        assertNotNull(store);

        // Iterate over the properties
        store.accept(new Visitor() {

            public void visit(Object obj) throws VisitCanceledException,
                BaseException {

                Feature property = (Feature) obj;
                // Get the block and property ids
                String blockId = property.getString("MASA");
                String propId = property.getString("PARCELA");

                // If the block was on the map, add the property id to its list
                if (blockIds.containsKey(blockId)) {
                    blockIds.get(blockId).add(propId);
                } else {
                    // Else, create a list and add a new item to the map
                    ArrayList<String> propIds = new ArrayList<String>();
                    propIds.add(propId);
                    blockIds.put(blockId, propIds);
                }
            }
        });
        
        // Get an instance of a geometry manager for geometry creation
        final GeometryManager geomManager =
            GeometryLocator.getGeometryManager();
        
        store = manager.getBlocks();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check it's not empty

        FeatureSet set = null;
        set = store.getFeatureSet();

        // Visit all block features, get the center and get check the getBlock
        // method
        set.accept(new Visitor() {
            public void visit(Object obj) throws VisitCanceledException, BaseException {
                Feature feat = (Feature) obj;

                Double x = (Double) feat.get("COORX");
                Double y = (Double) feat.get("COORY");
                
                VisorBlock block =
                    manager.getBlock(geomManager.createPoint(x, y,
                        Geometry.SUBTYPES.GEOM2D));

                String blockId = block.getCode();

                // First check block iterated id is the same with block
                // retrieved by geometry
                assertEquals(feat.getString("MASA"), blockId);
                LOG.debug("The block " + blockId
                    + " geometry intersects the coordinate fields geometry");

                // Second check the block id against the keys of the map created
                // from properties
                assertTrue(blockIds.containsKey(blockId));
                LOG.debug("The block " + blockId
                    + " exists on the map of properties");

                // Finally check the number of items on the list of the map
                // against the properties retrieved using the API
                List blockProps = block.getProperties();
                assertNotNull(blockProps);
                assertEquals(blockIds.get(blockId).size(), blockProps.size());
                LOG.debug("The block " + blockId + " has " + blockProps.size()
                    + " properties");

            }
        });
        set.dispose();
    }

    /**
     * Abstract method to retrieve an specific block
     * data set for testing
     * 
     * @return the File descriptor for the shapfile
     */
    protected abstract File getBlockForTest();

    /**
     * Abstract method to retrieve an specific properties
     * data set for testing
     * 
     * @return the File descriptor for the shapfile
     */
    protected abstract File getPropertiesForTest();

    /**
     * Abstract method to retrieve a raster dataset
     * as background
     * 
     * @return
     */
    protected abstract File getBackgroundForTest();
    
}
