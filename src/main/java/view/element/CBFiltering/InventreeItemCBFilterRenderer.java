package view.element.CBFiltering;

import Inventree.item.InventreeItem;
import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class InventreeItemCBFilterRenderer extends DefaultListCellRenderer {
    public static final Color background = new Color(225, 240, 255);
    private static final Color defaultBackground = (Color) UIManager.get("List.background");
    private static final Color defaultForeground = (Color) UIManager.get("List.foreground");
    private Supplier<String> highlightTextSupplier;

    public InventreeItemCBFilterRenderer(Supplier<String> highlightTextSupplier) {
        this.highlightTextSupplier = highlightTextSupplier;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        InventreeItem emp = (InventreeItem) value;
        if (emp == null) {
            return this;
        }
        String text = emp.getDisplayName();
        text = HtmlHighlighter.highlightText(text, highlightTextSupplier.get());
        this.setText(text);
        if (!isSelected) {
            this.setBackground(index % 2 == 0 ? background : defaultBackground);
        }
        this.setForeground(defaultForeground);
        return this;
    }

    public static String getItemDisplayText(InventreeItem ivI) {
        if (ivI == null) {
            return "";
        }
        return ivI.getDisplayName();
    }
}