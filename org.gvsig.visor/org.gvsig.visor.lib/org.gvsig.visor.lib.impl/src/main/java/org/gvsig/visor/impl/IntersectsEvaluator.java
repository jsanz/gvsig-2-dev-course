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

import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.operation.GeometryOperationException;
import org.gvsig.fmap.geom.operation.GeometryOperationNotSupportedException;
import org.gvsig.tools.evaluator.AbstractEvaluator;
import org.gvsig.tools.evaluator.EvaluatorData;
import org.gvsig.tools.evaluator.EvaluatorException;


/**
 * @author jsanz
 *
 */
public class IntersectsEvaluator extends AbstractEvaluator {

    private String fieldName;
    private Geometry geom;
    private String where;

    public IntersectsEvaluator(String fieldName, Geometry geom)
        throws GeometryOperationNotSupportedException,
        GeometryOperationException {
        this.fieldName = fieldName;
        this.geom = geom;
        this.where =
            "intersects(GeomFromText('" + geom.convertToWKT() + "',''),"
                + fieldName + ")";
    }

    /* (non-Javadoc)
     * @see org.gvsig.tools.evaluator.Evaluator#evaluate(org.gvsig.tools.evaluator.EvaluatorData)
     */
    public Object evaluate(EvaluatorData data) throws EvaluatorException {
        Geometry evalGeom = (Geometry) data.getDataValue(this.fieldName);
        try {
            return this.geom.intersects(evalGeom);
        } catch (Exception e) {
            throw new EvaluatorException(e);
        }
    }

    /* (non-Javadoc)
     * @see org.gvsig.tools.evaluator.Evaluator#getName()
     */
    public String getName() {
        return "intersects";
    }

    @Override
    public String getSQL() {
        return this.where;
    }
}
