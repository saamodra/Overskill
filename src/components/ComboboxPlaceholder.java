/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author samod
 */
@SuppressWarnings("serial")
public class ComboboxPlaceholder extends JComboBox<Object> {

    public ComboboxPlaceholder() {
    }
    
    
    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        setRenderer(new MyComboBoxRenderer(placeholder));
    }
    
    private String placeholder;
    
    public void setPlaceholder(final String s) {
        placeholder = s;
    }
    
    static class MyComboBoxRenderer extends JLabel implements ListCellRenderer
    {
        private String _title;

        public MyComboBoxRenderer(String title)
        {
            _title = title;
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean hasFocus)
        {
            if (index == -1 && value == null) setText(_title);
            else setText(value.toString());
            return this;
        }
    }
    
    
}
