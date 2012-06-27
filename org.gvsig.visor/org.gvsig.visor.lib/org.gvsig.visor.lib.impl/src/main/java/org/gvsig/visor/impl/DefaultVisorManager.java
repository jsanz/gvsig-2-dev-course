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
package org.gvsig.visor.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.DataStoreParameters;
import org.gvsig.fmap.dal.exception.DataException;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureQuery;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.operation.GeometryOperationException;
import org.gvsig.fmap.geom.operation.GeometryOperationNotSupportedException;
import org.gvsig.tools.dispose.DisposableIterator;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorException;
import org.gvsig.visor.VisorManager;

/**
 * Default {@link VisorManager} implementation.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultVisorManager implements VisorManager {

    static Logger LOG = LoggerFactory.getLogger(DefaultVisorManager.class);
    private FeatureStore blocks;
    private FeatureStore properties;
    private DataStore background;

    public void initialize(FeatureStore blocks, FeatureStore properties,
        DataStore background) {
        this.blocks = blocks;
        this.properties = properties;
        this.background = background;

        LOG.info("Manager initialized by passed stores");
    }

    public void initialize(File blocks, File properties, File background)
        throws VisorException {
        LOG.info("Initialize manager using those files  \r\n\tBlocks : "
            + blocks.getAbsolutePath() + "\r\n\tProperties: "
            + properties.getAbsolutePath() + "\r\n\tBackground: "
            + background.getAbsolutePath());

        initialize(getShape(blocks), getShape(properties), getTiff(background));

    }

    public VisorBlock getBlock(Geometry point) throws VisorException {
        FeatureSet set = null;
        DisposableIterator it = null;

        try {
            // Get the defalut geometry name from the default feature type
            String geomFieldName =
                this.blocks.getDefaultFeatureType()
                    .getDefaultGeometryAttributeName();

            // Create the evaluator and a Query to apply on the store
            IntersectsEvaluator evaluator =
                new IntersectsEvaluator(geomFieldName, point);
            FeatureQuery query = this.blocks.createFeatureQuery();
            query.setFilter(evaluator);
            set = this.blocks.getFeatureSet(query);

            // Create an iterator and use it
            it = set.fastIterator();

            // if it has no elements then return null
            if (!it.hasNext())
                return null;

            while (it.hasNext()) {
                Feature feat = (Feature) it.next();
                // Create and return a new VisorBlock created from the feature
                VisorBlock block =
                    new DefaultVisorBlock(this,
                        feat.getString(DefaultVisorBlock.CODE_FIELD_NAME),
                        feat.getDefaultGeometry());
                return block;
            }

        } catch (DataException e) {
            throw new VisorException(e);
        } catch (GeometryOperationNotSupportedException e) {
            throw new VisorException(e);
        } catch (GeometryOperationException e) {
            throw new VisorException(e);
        } finally {
            if (set != null) {
                set.dispose();
            }
            if (it != null) {
                it.dispose();
            }
        }

        return null;
    }

    public FeatureStore getBlocks() {
        return this.blocks;
    }

    public FeatureStore getProperties() {
        return this.properties;
    }

    public DataStore getBackground() {
        return this.background;
    }

    private FeatureStore getShape(File shape) throws VisorException {
        FeatureStore store = null;
        DataManager manager = DALLocator.getDataManager();
        try {
            DataStoreParameters params = manager.createStoreParameters("Shape");
            params.setDynValue("shpfile", shape);
            params.setDynValue("crs", "EPSG:23030");
            store = (FeatureStore) manager.openStore("Shape", params);
        } catch (Exception e) {
            throw new VisorException(e);
        }
        return store;

    }

    private DataStore getTiff(File tiff) throws VisorException {
        DataStore store = null;
        DataManager manager = DALLocator.getDataManager();
        try {
            DataStoreParameters params =
                manager.createStoreParameters("Gdal Store");
            params.setDynValue("uri", tiff);
            params.setDynValue("srs", "EPSG:23030");
            store = manager.openStore("Gdal Store", params);
        } catch (Exception e) {
            throw new VisorException(e);
        }
        return store;
    }


}
