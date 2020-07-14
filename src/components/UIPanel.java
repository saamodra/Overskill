/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author ERROR404
 */

import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import javax.swing.JPanel;  
  
public class UIPanel extends JPanel 
{
    private Color WarnaBackground = new Color(255, 51, 0);  
  
    public UIPanel() {  
        setOpaque(false);  
    }  
  
    @Override  
    protected void paintComponent(Graphics g) {  
        int x = 2;  
        int y = 2;  
        int w = getWidth() - 4;  
        int h = getHeight() - 4;  
        int arc = 8;  
        Color warna = WarnaBackground;
        
        Graphics2D g2 = (Graphics2D) g.create();  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                RenderingHints.VALUE_ANTIALIAS_ON);  
        //warna background  
        g2.setColor(warna);  
        g2.fillRoundRect(x, y, w, h, arc, arc);  
  
        //ukuran border  
        g2.setStroke(new BasicStroke(3f));  
        //warna border  
        g2.setColor(new Color(213, 213, 213));  
        g2.drawRoundRect(x, y, w, h, arc, arc);  
  
        g2.dispose();  
    }
    
    public Color getWarnaBackground() {  
        return this.WarnaBackground;  
    }
    
    public void setWarnaBackground(Color bgColor) {  
        this.WarnaBackground = bgColor;  
    }
}