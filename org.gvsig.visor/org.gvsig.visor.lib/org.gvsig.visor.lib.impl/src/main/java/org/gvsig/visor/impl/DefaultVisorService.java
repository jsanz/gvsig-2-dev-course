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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

import org.gvsig.visor.VisorManager;
import org.gvsig.visor.VisorMessageException;
import org.gvsig.visor.VisorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link VisorService} implementation.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultVisorService implements VisorService {

    private static final Logger LOG =
        LoggerFactory.getLogger(DefaultVisorService.class);

    private static final String DEFAULT_FORTUNE_COOKIE_URL =
        "http://www.fullerdata.com/FortuneCookie/FortuneCookie.asmx/GetFortuneCookie";

    private String message = null;
    private VisorManager manager;
    private Date date = null;
    private String url;

    /**
     * {@link DefaultVisorService} constructor with a
     * {@link VisorManager}.
     * 
     * @param manager
     *            to use in the service
     */
    public DefaultVisorService(VisorManager manager) {
        this(manager, DEFAULT_FORTUNE_COOKIE_URL);
    }

    /**
     * {@link DefaultVisorService} constructor with a
     * {@link VisorManager}.
     * 
     * @param manager
     *            to use in the service
     */
    public DefaultVisorService(VisorManager manager, String url) {
        this.manager = manager;
        this.url = url;
    }

    public String getMessage() throws VisorMessageException {
        if (this.message == null) {
            try {
                this.message = getMessageFromUrl();
            } catch (IOException e) {
                throw new VisorMessageException(e);
            }
            this.date = new Date();
        }
        return this.message;
    }

    /**
     * Gets a Visor message from an external service through a URL.
     * 
     * @return the message
     */
    private String getMessageFromUrl() throws IOException {
        LOG.debug("Getting the Visor message from the url: {}",
            this.url);

        URL url = new URL(this.url);
        StringBuffer message = new StringBuffer();
        BufferedReader in =
            new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        // Create a pattern to match breaks
        Pattern p = Pattern.compile("\\<[^\\>]+\\>");

        StringBuffer data = null;

        if (LOG.isDebugEnabled()) {
            data = new StringBuffer();
        }

        while ((str = in.readLine()) != null) {
            if (LOG.isDebugEnabled()) {
                data.append(str).append('\n');
            }
            message.append(p.matcher(str).replaceAll("\n"));
        }
        in.close();

        LOG.debug("Visor message response:\n{}", data);

        return message.toString();
    }

    public VisorManager getManager() {
        return this.manager;
    }

    public Date getDate() {
        return this.date;
    }

}
