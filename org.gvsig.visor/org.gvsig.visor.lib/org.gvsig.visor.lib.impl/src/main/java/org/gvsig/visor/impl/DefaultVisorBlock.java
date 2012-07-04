/* gvSIG. Desktop Geographic Information System.
 *
 * Copyright © 2007-2012 gvSIG Association
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.exception.InitializeException;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureQuery;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.tools.evaluator.Evaluator;
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.visitor.VisitCanceledException;
import org.gvsig.tools.visitor.Visitor;
import org.gvsig.visor.VisorBlock;
import org.gvsig.visor.VisorException;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorProperty;


/**
 * @author jsanz
 *
 */
public class DefaultVisorBlock implements VisorBlock {

    public static final String CODE_FIELD_NAME = "MASA";

    VisorManager manager;
    String code;
    Geometry shape;
    List<VisorProperty> properties = null;

    public DefaultVisorBlock(VisorManager manager, String code, Geometry shape) {
        this.manager = manager;
        this.code = code;
        this.shape = shape;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorBlock#getShape()
     */
    public Geometry getShape() {
        return this.shape;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorBlock#getProperties()
     */
    public List<VisorProperty> getProperties() throws VisorException {
        if (this.properties != null) {
            // if exists, return it
            return this.properties;
        } else {
            final ArrayList<VisorProperty> tmpProperties =
                new ArrayList<VisorProperty>();
            FeatureStore store = manager.getProperties();

            DataManager dataManager = DALLocator.getDataManager();
            FeatureSet set = null;
            try {
                // Create a new evaluator with the code block value
                Evaluator evaluator =
                    dataManager
                        .createExpresion(DefaultVisorProperty.CODEBLOCK_FIELD_NAME
                            + " = " + this.code);
                FeatureQuery featureQuery = store.createFeatureQuery();
                featureQuery.setFilter(evaluator);

                // Run over a feature set created with the evaluator
                set = store.getFeatureSet(featureQuery);
                set.accept(new Visitor() {

                    public void visit(Object obj)
                        throws VisitCanceledException, BaseException {
                        Feature feat = (Feature) obj;
                        // Create a property using feature values
                        DefaultVisorProperty prop =
                            new DefaultVisorProperty(
                                manager,
                                feat.getString(DefaultVisorProperty.CODE_FIELD_NAME),
                                feat.getString(DefaultVisorProperty.CODEBLOCK_FIELD_NAME),
                                feat.getDefaultGeometry(),
                                feat.getInt(DefaultVisorProperty.CREATIONDATE_FIELD_NAME),
                                feat.getInt(DefaultVisorProperty.MUNICODE_FIELD_NAME));
                        // Add it to the temporal List
                        tmpProperties.add(prop);

                    }
                });

                // Create a read-only list and return it
                this.properties = Collections.unmodifiableList(tmpProperties);
                return this.properties;

            } catch (InitializeException e) {
                throw new VisorException(e);
            } catch (BaseException e) {
                throw new VisorException(e);
            } finally {
                if (set != null) {
                    set.dispose();
                }
            }

        }
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorBlock#getManager()
     */
    public VisorManager getManager() {
        return this.manager;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorBlock#getCode()
     */
    public String getCode() {
        return this.code;
    }

}
