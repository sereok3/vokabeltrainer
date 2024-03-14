package net.tfobz.vokabeltrainer.utils;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {

    public CustomTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Allow editing only if it's not the third column (index 2)
        return column != 2; // Adjust index if needed
    }
}
