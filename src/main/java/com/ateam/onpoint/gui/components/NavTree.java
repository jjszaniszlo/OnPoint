package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.gui.ContentView;
import com.ateam.onpoint.gui.content.IContent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/*
 * The nav tree acts as an interactable component where the user is able to select what content is shown.
 */
public class NavTree extends TreeView<NavTree.ContentRecord> {
    public NavTree(ContentView view) {
        super();

        this.setShowRoot(false);
        this.setStyle("-fx-border-width: 0;");
        this.setRoot(view.getRoot());
        // pass in a callback with param TreeView
        this.setCellFactory((v) -> new NavCell());
    }

    /*
     * the ContentRecord keeps track of the association between the name, icon and ContentView.
     */
    record ContentRecord(String name, Class<? extends  IContent> content) {}

    public static class NavItem extends TreeItem<ContentRecord> {
        ContentRecord record;
        private NavItem(ContentRecord record) {
            this.record = record;
            this.setValue(record);
        }

        public Class<? extends IContent> getContentView() {
            return this.record.content;
        }

        public static NavItem makeRoot() {
            return new NavItem(new ContentRecord("ROOT", null));
        }

        public static NavItem makeContent(String title, Class<? extends IContent> content) {
            return new NavItem(new ContentRecord(title, content));
        }
    }

    public static class NavCell extends TreeCell<ContentRecord> {
        private final HBox root;
        private final Label name;
        public NavCell() {
            super();

            this.name = new Label();
            this.name.setGraphicTextGap(5);

            this.root = new HBox();
            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.getChildren().add(this.name);

            this.getStyleClass().add("nav-tree-cell");
        }

        @Override
        protected void updateItem(ContentRecord rec, boolean empty) {
            super.updateItem(rec, empty);

            if (rec == null || empty) {
                this.setGraphic(null);
            } else {
                this.setGraphic(root);
                this.name.setText(rec.name);
            }
        }
    }
}
