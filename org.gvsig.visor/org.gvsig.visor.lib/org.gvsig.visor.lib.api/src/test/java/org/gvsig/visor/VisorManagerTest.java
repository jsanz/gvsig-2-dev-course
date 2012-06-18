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

import org.gvsig.fmap.dal.DataSet;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.tools.junit.AbstractLibraryAutoInitTestCase;

/**
 * API compatibility tests for {@link VisorManager} implementations.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public abstract class VisorManagerTest extends
    AbstractLibraryAutoInitTestCase {

    protected VisorManager manager;

    @Override
    protected void doSetUp() throws Exception {
        manager = VisorLocator.getManager();

        manager.initialize(getBlockForTest(), getPropertiesForTest(),
            getBackgroundForTest());
    }

    public void testGetBlocks() throws Exception {
        FeatureStore store = null;
        FeatureSet set = null;
        store = manager.getBlocks();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check it's not empty
        set = store.getFeatureSet();
        assertFalse("The blocks FeatureStore has elements", set.isEmpty());

    }

    public void testGetProperties() throws Exception {
        FeatureStore store = null;
        FeatureSet set = null;

        store = manager.getProperties();
        // Check if the FeatureStore is null
        assertNotNull(store);
        // Get de Feature set and check it's not empty
        set = store.getFeatureSet();
        assertFalse("The blocks FeatureStore has elements", set.isEmpty());

    }

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
    protected abstract File getBlockForTest();

    protected abstract File getPropertiesForTest();

    protected abstract File getBackgroundForTest();
    
}
