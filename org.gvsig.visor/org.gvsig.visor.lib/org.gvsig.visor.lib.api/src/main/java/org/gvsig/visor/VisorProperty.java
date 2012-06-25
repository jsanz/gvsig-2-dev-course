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

import org.gvsig.fmap.geom.Geometry;

/**
 * Class that represents a property that is contained inside a
 * {@link VisorBlock}
 * 
 * @author jsanz
 * 
 */
public interface VisorProperty {

    /**
     * 
     * @return the identificator of the property
     */
    public String getCode();

    /**
     * 
     * @return the date the property was created (as an Integer)
     */
    public Integer getCreationDate();

    /**
     * 
     * @return the geometry that contains the property
     */
    public Geometry getShape();

    /**
     * 
     * @return the identificator of the municipality where the property belongs
     */
    public Integer getMunicipalityCode();

    /**
     * 
     * @return a reference to the manager
     */
    public VisorManager getManager();

    /**
     * 
     * @return the code of the block that the property belongs
     */
    public String getBlockCode();
}
