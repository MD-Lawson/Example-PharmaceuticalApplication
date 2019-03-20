package PharmaceuticalApplication;
//Source of most of this code is taken from stackoverflow and can be found at https://stackoverflow.com/questions/37768335/how-to-word-wrap-inside-a-jtable-row
//Created by user ck1 https://stackoverflow.com/users/6243024/ck1
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
    WordWrapCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        //Following statements are modifications I have done
        if(isSelected){
        	setForeground(table.getSelectionForeground());
        	setBackground(table.getSelectionBackground());
        }else {
        	setForeground(table.getForeground());
        	setBackground(table.getBackground());
        }
        return this;
    }
}
