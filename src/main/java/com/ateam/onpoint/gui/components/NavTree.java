package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.gui.OnPointGUI;
import com.ateam.onpoint.gui.content.IContent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/*
 * The nav tree lets users select what content should be shown in the content view.
 */
public class NavTree extends TreeView<NavTree.ContentRecord> {
    /**
     * Builds the NavTree and the factor for the cell constructor.
     * @param view
     */
    public NavTree(OnPointGUI view) {
        super();

        getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            if (!(val instanceof NavItem item)) {
                return;
            }
            view.navigate(item.getContentClass());
        });

        this.setShowRoot(false);
        this.setStyle("-fx-border-width: 0;");
        this.setRoot(view.getNavTreeRoot());

        // pass in a callback with param TreeView
        this.setCellFactory((v) -> new NavCell());
    }

    /**
     * The content record holds the necessary information to link the name in the navtree with the content view.
     * @param name
     * @param content
     */
    public record ContentRecord(String name, Class<? extends  IContent> content) {
        public Class<? extends IContent> getContent() {
            return this.content;
        }
    }

    /**
     * the node used for the navtree structure.
     */
    public static class NavItem extends TreeItem<ContentRecord> {
        ContentRecord record;

        /**
         * construct a new navitem with the content record passed in.
         * @param record
         */
        private NavItem(ContentRecord record) {
            this.record = record;
            this.setValue(record);
        }

        /**
         * retrieve the content view associated with this navitem.
         * @return the content view
         */
        public Class<? extends IContent> getContentClass() {
            return this.record.content;
        }

        /**
         * construct a new root navitem.
         * @return the constructed navitem
         */
        public static NavItem makeRoot() {
            return new NavItem(new ContentRecord("ROOT", null));
        }

        /**
         * construct a new content navitem
         * @param name the name to associate with a given content view
         * @param content the content view to associate with the name
         * @return the constructed navitem
         */
        public static NavItem makeContent(String name, Class<? extends IContent> content) {
            return new NavItem(new ContentRecord(name, content));
        }
    }

    /**
     * the displayed structures for each navitem in the navtree.
     */
    public static class NavCell extends TreeCell<ContentRecord> {
        private final HBox root;
        private final Label name;

        /**
         * construct a new navcell
         */
        public NavCell() {
            super();

            this.name = new Label();
            this.name.setGraphicTextGap(5);
            this.name.setStyle("-fx-font-weight: 600;");

            this.root = new HBox();
            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.getChildren().add(this.name);

            this.getStyleClass().add("nav-tree-cell");
        }

        /**
         * this is the callback for updating a cell if an item is updated.
         * @param rec The new item for the cell.
         * @param empty whether or not this cell represents data from the list. If it
         *        is empty, then it does not represent any domain data, but is a cell
         *        being used to render an "empty" row.
         */
        @Override
        protected void updateItem(ContentRecord rec, boolean empty) {
            super.updateItem(rec, empty);

            if (rec == null || empty) {
                this.setGraphic(null);

                this.name.setText(null);
            } else {
                this.setGraphic(root);

                this.name.setText(rec.name);
            }
        }
    }
}
