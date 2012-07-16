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
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorProperty;


/**
 * @author jsanz
 *
 */
public class DefaultVisorProperty implements VisorProperty {

    public static final String CODE_FIELD_NAME = "PARCELA";
    public static final String CODEBLOCK_FIELD_NAME = "MASA";
    public static final String CREATIONDATE_FIELD_NAME = "FECHAALTA";
    public static final String MUNICODE_FIELD_NAME = "MUNICIPIO";

    VisorManager manager;
    String code;
    String blockCode;
    Geometry shape;
    int creationDate;
    int municipalityCode;

    public DefaultVisorProperty(VisorManager manager, String code,
        String blockCode, Geometry shape, int creationDate, int municipalityCode) {
        super();
        this.manager = manager;
        this.code = code;
        this.blockCode = blockCode;
        this.shape = shape;
        this.creationDate = creationDate;
        this.municipalityCode = municipalityCode;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getCode()
     */
    public String getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getCreationDate()
     */
    public Integer getCreationDate() {
        return creationDate;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getShape()
     */
    public Geometry getShape() {
        return shape;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getMunicipalityCode()
     */
    public Integer getMunicipalityCode() {
        return municipalityCode;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getManager()
     */
    public VisorManager getManager() {
        return manager;
    }

    /* (non-Javadoc)
     * @see org.gvsig.visor.VisorProperty#getBlockCode()
     */
    public String getBlockCode() {
        return blockCode;
    }

}
