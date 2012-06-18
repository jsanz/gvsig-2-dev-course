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

/**
 * Exception thrown when there is an error getting a Visor message.
 * 
 * @author gvSIG team
 * @version $Id$
 */
public class VisorMessageException extends VisorException {

    private static final long serialVersionUID = -4051458353306878010L;

    private static final String MESSAGE =
        "An error has been produced " + "getting the Visor message";

    private static final String KEY = "_VisorMessageException";

    /**
     * Creates a new {@link VisorMessageException}.
     * 
     * @param cause
     *            the original cause
     */
    public VisorMessageException(Throwable cause) {
        super(MESSAGE, cause, KEY, serialVersionUID);
    }
}
