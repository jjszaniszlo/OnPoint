package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.components.NavTree;
import com.ateam.onpoint.gui.content.IContent;

/**
 * Content view contains the panel where all the application's functionality will dynamically be made available through the sidebar.
 */
public class ContentView {
    private Class<? extends IContent> selectedContent;
    private final NavTree.NavItem navTreeRoot;

    public NavTree.NavItem getRoot() {
        return navTreeRoot;
    }

    /**
     * construct the content view of the application
     */
    public ContentView() {
        navTreeRoot = NavTree.NavItem.makeRoot();
        navTreeRoot.getChildren().setAll(
                NavTree.NavItem.makeContent("Tasks", null),
                NavTree.NavItem.makeContent("Schedule", null)
        );
    }
}
