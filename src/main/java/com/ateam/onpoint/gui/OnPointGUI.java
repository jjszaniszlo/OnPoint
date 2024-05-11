package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.components.NavTree;
import com.ateam.onpoint.gui.content.IContent;
import com.ateam.onpoint.gui.content.ScheduleView;
import com.ateam.onpoint.gui.content.TaskView;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * ApplicationView contains the panel where all the application's functionality will dynamically be made available through the sidebar.
 */
public class OnPointGUI {
    public static final Class<? extends IContent> DEFAULT_CONTENT = TaskView.class;
    private final NavTree.NavItem navTreeRoot;
    private final ReadOnlyObjectWrapper<Class<? extends IContent>> selectedContent = new ReadOnlyObjectWrapper<>();

    public NavTree.NavItem getNavTreeRoot() {
        return navTreeRoot;
    }

    /**
     * construct the content view of the application
     */
    public OnPointGUI() {
        navTreeRoot = NavTree.NavItem.makeRoot();
        navTreeRoot.getChildren().setAll(
                NavTree.NavItem.makeContent("Tasks", TaskView.class, new Image(Objects.requireNonNull(getClass().getResource("/img/inbox_52.png")).toString())),
                NavTree.NavItem.makeContent("Schedule", ScheduleView.class, new Image(Objects.requireNonNull(getClass().getResource("/img/calandar_64.png")).toString()))
        );
    }

    public ReadOnlyObjectProperty<Class<? extends IContent>> getContentProperty() {
        return selectedContent.getReadOnlyProperty();
    }

    public void navigate(Class<? extends IContent> content) {
        selectedContent.set(content);
    }
}
