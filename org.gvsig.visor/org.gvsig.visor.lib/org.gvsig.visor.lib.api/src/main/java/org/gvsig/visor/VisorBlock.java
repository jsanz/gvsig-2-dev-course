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

import java.util.List;

import org.gvsig.fmap.geom.Geometry;

/**
 * A block of buildings for the viewer plugin.
 * 
 * @author jsanz
 * 
 */
public interface VisorBlock {

    /**
     * 
     * @return the {link {@link Geometry} that represents the block
     */
    public Geometry getShape();

    /**
     * 
     * @return the list of {@link VisorProperty} that are inside the block *
     * 
     * @throws VisorException
     */
    public List<VisorProperty> getProperties() throws VisorException;

    /**
     * 
     * @return a reference to the plugin manager
     * 
     * @see VisorManager
     */
    public VisorManager getManager();

    /**
     * 
     * @return an identificator of the block
     */
    public String getCode();
}
