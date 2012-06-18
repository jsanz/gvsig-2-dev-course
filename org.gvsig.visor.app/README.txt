====
    gvSIG. Desktop Geographic Information System.

    Copyright (C) 2007-2012 gvSIG Association.

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
    MA  02110-1301, USA.

    For any additional information, do not hesitate to contact us
    at info AT gvsig.com, or visit our website www.gvsig.com.
====

The first time you checkout the current project to a new workspace, 
you have to prepare it to be able to work easily with maven from
eclipse itself.

Perform the following steps:

1.- Launch the *prepare-workspace.xml* ant build file. 
    You can do it by loading the file into the ant view, 
    and running the default task, or right-clicking the 
    file from the package explorer or the navigator and
    select the option: *Run as > Ant build*. 
    
2.- Restart eclipse.

3.- Import the subprojects of the project you have just checked out.

Those steps are only needed once per workspace.     

