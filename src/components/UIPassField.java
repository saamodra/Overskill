/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Dimension;  
import java.awt.Font;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Insets;  
import java.awt.Paint;  
import java.awt.RenderingHints;  
import java.awt.font.TextLayout;  
import java.awt.geom.Rectangle2D;  
import java.awt.geom.RoundRectangle2D;  
import javax.swing.JPasswordField;  
import javax.swing.border.EmptyBorder;  

/**
 *
 * @author ERROR404
 */

public class UIPassField extends JPasswordField {  
  
    private Boolean left = true;  
    protected float Ae_sizeBorder = 1.0F;  
    protected Color Ae_colorBorder = Color.decode("#ADADAD");  
    private String Ae_placeholder = "Type password here";  
    private Color Ae_txtColorPlaceholder = Color.decode("#808080");  
    private final Integer Ae_borderRadius = 8;  
    private final Color txtcolor = Color.decode("#000000");  
    private final Color bgcolor = Color.decode("#FFFFFF");  
  
    public UIPassField() {  
        setOpaque(false);  
        setBorder(new EmptyBorder(0, 10, 0, 4));  
        setPreferredSize(new Dimension(188, 30));  
        setFont(new Font("Segoe UI", 0, 12));  
        setForeground(txtcolor);  
        setBackground(bgcolor);  
    }  
  
    @Override  
    protected void paintComponent(Graphics g) {  
        Graphics2D g2 = (Graphics2D) g;  
        Paint oldPaint = g2.getPaint();  
  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
  
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0.0F, 0.0F, getWidth(), getHeight(), this.Ae_borderRadius, this.Ae_borderRadius);  
  
        g2.clip(r2d);  
  
        g2.setColor(getBackground());  
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.Ae_borderRadius, this.Ae_borderRadius);  
        if (getText().length() < 1) {  
            Insets insets = getInsets();  
            FontMetrics fm = getFontMetrics(getFont());  
            g2.setColor(getForeground());  
            if (getText() == null) {  
                setText("  ");  
            }  
            TextLayout layout = new TextLayout(this.Ae_placeholder == null ? " " : this.Ae_placeholder, getFont(), g2.getFontRenderContext());  
  
            Rectangle2D bounds = layout.getBounds();  
  
            int x = (int) (getWidth() - insets.left - insets.right - bounds.getWidth()) / 2;  
            if (getHorizontalAlignment() != 0) {  
                x = 0 + insets.left;  
            }  
            int y = (getHeight() - insets.top - insets.bottom - fm.getMaxAscent() - fm.getMaxDescent()) / 2;  
  
            y += fm.getAscent() - 1;  
            g2.setColor(this.Ae_txtColorPlaceholder);  
            layout.draw(g2, x, y);  
        }  
        g2.setPaint(oldPaint);  
        super.paintComponent(g);  
    }  
  
    @Override  
    protected void paintBorder(Graphics g) {  
        int x = 1;  
        int y = 1;  
        int w = getWidth() - 2;  
        int h = getHeight() - 2;  
        Graphics2D g2 = (Graphics2D) g.create();  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
  
        g2.setStroke(new BasicStroke(this.Ae_sizeBorder));  
        g2.setColor(this.Ae_colorBorder);  
        g2.drawRoundRect(x, y, w, h, this.Ae_borderRadius, this.Ae_borderRadius);  
        g2.dispose();  
    }  
  
    public Color getAe_TxtColorPlaceholder() {  
        return this.Ae_txtColorPlaceholder;  
    }  
  
    public void setAe_TxtColorPlaceholder(Color Ae_txtColorPlaceholder) {  
        this.Ae_txtColorPlaceholder = Ae_txtColorPlaceholder;  
    }  
  
    public Boolean getLeft() {  
        return this.left;  
    }  
  
    public void setLeft(Boolean left) {  
        this.left = left;  
        repaint();  
    }  
  
    public float getAe_SizeBorder() {  
        return this.Ae_sizeBorder;  
    }  
  
    public void setAe_SizeBorder(float Ae_sizeBorder) {  
        this.Ae_sizeBorder = Ae_sizeBorder;  
    }  
  
    public Color getAe_ColorBorder() {  
        return this.Ae_colorBorder;  
    }  
  
    public void setAe_ColorBorder(Color Ae_colorBorder) {  
        this.Ae_colorBorder = Ae_colorBorder;  
    }  
  
    public String getAe_Placeholder() {  
        return this.Ae_placeholder;  
    }  
  
    public void setAe_Placeholder(String Ae_placeholder) {  
        this.Ae_placeholder = Ae_placeholder;  
    }  
}
