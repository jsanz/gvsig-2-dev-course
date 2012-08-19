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
package org.gvsig.visor.app.mainplugin;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingUtilities;

import org.gvsig.about.AboutManager;
import org.gvsig.about.AboutParticipant;
import org.gvsig.andami.messages.NotificationManager;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.documents.view.ViewDocument;
import org.gvsig.app.project.documents.view.ViewManager;
import org.gvsig.app.project.documents.view.gui.IView;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.mapcontext.MapContext;
import org.gvsig.fmap.mapcontext.MapContextManager;
import org.gvsig.fmap.mapcontext.exceptions.LoadLayerException;
import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontrol.tools.Behavior.PointBehavior;
import org.gvsig.visor.VisorException;
import org.gvsig.visor.VisorLocator;
import org.gvsig.visor.VisorManager;
import org.gvsig.visor.swing.VisorSwingLocator;
import org.gvsig.visor.swing.VisorSwingManager;

/**
 * Andami extension to show Visor in the application.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class VisorExtension extends Extension {

    private VisorManager manager;
    private VisorSwingManager swingManager;

    /**
     * to create new layers
     */
    private MapContextManager mapContextManager;
    /**
     * to access the window management
     */
    private ApplicationManager applicationManager;
    private int createViewsCounter = 0;

    private static final String CREATE_VIEW_ACTION = "CREATE_VIEW";

    public void initialize() {
        // Do nothing
    }

    @Override
    public void postInitialize() {
        super.postInitialize();
        manager = VisorLocator.getManager();

        // Initialize VisorManager
        try {
            manager.initialize(getResource("data/blocks.shp"),
                getResource("data/properties.shp"),
                getResource("data/PNOA.tif"));
        } catch (VisorException e) {
            // TODO, manage this exception!!
            e.printStackTrace();
        }

        // Asignamos el locator e iniciamos la instancia
        swingManager = VisorSwingLocator.getSwingManager();

        applicationManager = ApplicationLocator.getManager();
        mapContextManager = applicationManager.getMapContextManager();

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    createAndShowView();
                } catch (LoadLayerException e) {
                    NotificationManager.addError("Error loading layers", e);
                }

            }
        });

        initializeAbout();

    }

    private void createAndShowView() throws LoadLayerException {
        // Get the view name
        createViewsCounter++;
        String myViewName =
            swingManager.getTranslation("block_viewer") + "_"
                + createViewsCounter;

        // Get the project manager and create a new View document
        ViewDocument myView =
            (ViewDocument) applicationManager.getProjectManager()
                .createDocument(ViewManager.TYPENAME, myViewName);

        // Create the layers
        String blockLayerName = swingManager.getTranslation("block_layer_name");
        String backgroundLayerName =
            swingManager.getTranslation("background_layer_name");

        FeatureStore blockFS = manager.getBlocks();
        DataStore backgroundDS = manager.getBackground();

        FLayer blocksLayer =
            mapContextManager.createLayer(blockLayerName, blockFS);
        FLayer backgroundLayer =
            mapContextManager.createLayer(backgroundLayerName, backgroundDS);

        // Add them to the view
        MapContext mapContext = myView.getMapContext();
        mapContext.beginAtomicEvent();
        mapContext.getLayers().addLayer(backgroundLayer);
        mapContext.getLayers().addLayer(blocksLayer);
        mapContext.endAtomicEvent();

        // Add the view to the current project
        applicationManager.getCurrentProject().add(myView);

        // Show it centered
        applicationManager.getUIManager().addCentredWindow(
            myView.getMainWindow());

        // Add the behavior
        IView iView = (IView) myView.getMainWindow();

        iView.getMapControl().addBehavior(
            PropertiesOfBlockListener.PROPERTIES_OF_BLOCK_TOOL,
            new PointBehavior(new PropertiesOfBlockListener()));

    }

    public void execute(String actionCommand) {
        if (actionCommand.equalsIgnoreCase(CREATE_VIEW_ACTION)) {
            try {
                createAndShowView();
            } catch (LoadLayerException e) {
                NotificationManager.addError("Error loading layers", e);
            }
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isVisible() {
        return true;
    }

    private File getResource(String pathname) {
        URL res = this.getClass().getClassLoader().getResource(pathname);
        return new File(res.getPath());
    }

    private void initializeAbout(){
        AboutManager aboutManager = applicationManager.getAbout();
        AboutParticipant dev = aboutManager.addDeveloper("Curso de desarrollo",
                this.getClass().getClassLoader().getResource("about/curso-dev.html"), 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date begin, end;
        try {
            begin = sdf.parse("24/04/2012");
            end = sdf.parse("27/04/2012");
            dev.addContribution("Visor", "Visor personalizado de cartografía",
                begin, end);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
