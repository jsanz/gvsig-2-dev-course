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
package org.gvsig.visor.app.mainplugin;

import java.util.HashSet;
import java.util.Set;

import org.gvsig.andami.PluginServices;
import org.gvsig.andami.plugins.ExclusiveUIExtension;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.andami.plugins.IExtension;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.documents.view.gui.IView;


/**
 * @author jsanz
 *
 */
public class VisorToolsManagerExtension extends Extension implements
    ExclusiveUIExtension {

    private static final String[] EXTENSIONSTOHIDE = {
        "org.gvsig.app.extension.AddLayer",
        "org.gvsig.newlayer.app.extension.NewLayerExtension" };
    private ExclusiveUIExtension delegatedUIExtension;
    private ApplicationManager applicationManager;
    private boolean lastCheckedDelegated = false;
    private IWindow lastWindow = null;
    private Set<String> setOfExtensionsToHide;


    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.IExtension#initialize()
     */
    public void initialize() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.IExtension#execute(java.lang.String)
     */
    public void execute(String actionCommand) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.IExtension#isEnabled()
     */
    public boolean isEnabled() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.IExtension#isVisible()
     */
    public boolean isVisible() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.ExclusiveUIExtension#isEnabled(org.gvsig.andami.plugins.IExtension)
     */
    public boolean isEnabled(IExtension extension) {        
        IWindow window = applicationManager.getActiveWindow();

        // if there is no window, set the flags and continue
        if (window == null) {
            lastWindow = null;
            lastCheckedDelegated = false;
            return delegateEnabled(extension);
        } else if (window.equals(lastWindow) && lastCheckedDelegated) {
            // if it's the same window and the flag is checked, continue
            return delegateEnabled(extension);
        }

        // at this point we have to check the window
        lastWindow = window;
        lastCheckedDelegated = false;
        // if it's not a view change flag and continue
        if (!(window instanceof IView)) {
            lastCheckedDelegated = true;
            return delegateEnabled(extension);
        }

        // if it's not a visor view continue
        if (!isVisorView((IView) window)) {
            return delegateEnabled(extension);
        }
        
        // on our visor views, hide the desired extensions
        if (hideExtension(extension)) {
            return false;
        }

        // at the end, delegate the logic
        return delegateEnabled(extension);
    }

    /* (non-Javadoc)
     * @see org.gvsig.andami.plugins.ExclusiveUIExtension#isVisible(org.gvsig.andami.plugins.IExtension)
     */
    public boolean isVisible(IExtension extension) {
        IWindow window = applicationManager.getActiveWindow();

        // if there is no window, set the flags and continue
        if (window == null) {
            lastWindow = null;
            lastCheckedDelegated = false;
            return delegateVisible(extension);
        } else if (window.equals(lastWindow) && lastCheckedDelegated) {
            // if it's the same window and the flag is checked, continue
            return delegateVisible(extension);
        }

        // at this point we have to check the window
        lastWindow = window;
        lastCheckedDelegated = false;
        // if it's not a view change flag and continue
        if (!(window instanceof IView)) {
            lastCheckedDelegated = true;
            return delegateVisible(extension);
        }

        // if it's not a visor view continue
        if (!isVisorView((IView) window)) {
            return delegateVisible(extension);
        }

        // on our visor views, hide the desired extensions
        if (hideExtension(extension)) {
            return false;
        }

        // at the end, delegate the logic
        return delegateVisible(extension);
    }

    @Override
    public void postInitialize() {
        super.postInitialize();

        applicationManager = ApplicationLocator.getManager();
        initializeExtensionSet();
    }

    /**
     * This method initializes the set of extensions to hide
     */
    private void initializeExtensionSet() {
        // Initialize the set
        setOfExtensionsToHide = new HashSet<String>();
        for (int i = 0; i < EXTENSIONSTOHIDE.length; i++) {
            setOfExtensionsToHide.add(EXTENSIONSTOHIDE[i]);
        }

        // Store a reference for a previous exclusive ui extension
        delegatedUIExtension = PluginServices.getExclusiveUIExtension();

        // Register at plugin services itself
        PluginServices.setExclusiveUIExtension(this);
    }

    /**
     * @param view
     * @return if the view has the tool to view properties
     */
    private boolean isVisorView(IView view) {
        return view.getMapControl().hasTool(
            PropertiesOfBlockListener.PROPERTIES_OF_BLOCK_TOOL);
    }

    /**
     * 
     * @param extension
     * @return
     */
    private boolean delegateEnabled(IExtension extension) {
        if (delegatedUIExtension != null) {
            return delegatedUIExtension.isEnabled(extension);
        } else {
            return extension.isEnabled();
        }
    }

    /**
     * 
     * @param extension
     * @return
     */
    private boolean delegateVisible(IExtension extension) {
        if (delegatedUIExtension != null) {
            return delegatedUIExtension.isVisible(extension);
        } else {
            return extension.isVisible();
        }
    }

    /**
     * Controls if an extension should be hidden
     * 
     * @param extension
     * @return
     */
    public boolean hideExtension(IExtension extension) {
        String name = extension.getClass().getName();

        return name.startsWith("org.gvsig.editing")
            || setOfExtensionsToHide.contains(name);

    }

}
