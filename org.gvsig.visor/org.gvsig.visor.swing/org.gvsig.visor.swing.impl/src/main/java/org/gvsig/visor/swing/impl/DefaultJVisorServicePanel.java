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
package org.gvsig.visor.swing.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gvsig.visor.VisorMessageException;
import org.gvsig.visor.VisorService;
import org.gvsig.visor.swing.VisorSwingManager;
import org.gvsig.visor.swing.JVisorServicePanel;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;

/**
 * Default implementation for the {@link JVisorServicePanel}.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultJVisorServicePanel extends
    JVisorServicePanel {

    private static final Logger LOG = LoggerFactory
        .getLogger(DefaultJVisorServicePanel.class);

    private static final long serialVersionUID = 2965442763236823977L;

    private VisorService cookie;
    private VisorSwingManager uimanager;

    protected JButton accept = null;
    protected JButton info = null;

    public DefaultJVisorServicePanel(
        DefaultVisorSwingManager uimanager, VisorService cookie) {

        this.cookie = cookie;
        this.uimanager = uimanager;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(550, 150));

        JTextArea text = new JTextArea();
        try {
            text.setText(cookie.getMessage());
        } catch (VisorMessageException e) {
            text.setText(this.uimanager
                .getTranslation("Error obtaining message....."));
            LOG.error("Error getting the Visor message", e);
        }
        text.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setPreferredSize(new Dimension(550, 150));

        // TODO: replace with the UsabilitySwingManager.createJButton()
        accept = new JButton(this.uimanager.getTranslation("Accept"));
        info = new JButton(this.uimanager.getTranslation("Info"));

        JPanel optionsPane = new JPanel();
        optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.LINE_AXIS));
        optionsPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        optionsPane.add(Box.createHorizontalGlue());

        accept.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        info.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                showInfo();
            }
        });

        optionsPane.add(accept);
        optionsPane.add(info);
        optionsPane.add(Box.createRigidArea(new Dimension(10, 0)));

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(optionsPane, BorderLayout.SOUTH);
    }

    private void showInfo() {
        JPanel infoPanel =
            new DefaultJVisorServiceInfoPanel(this.uimanager,
                this.cookie);
        this.uimanager.getWindowManager().showWindow(infoPanel,
            this.uimanager.getTranslation("Information"),
            WindowManager.MODE.DIALOG);
    }

    @Override
    public VisorService getVisor() {
        return cookie;
    }
}
