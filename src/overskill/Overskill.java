/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.IntelliJTheme;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author samod
 */
public class Overskill {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf() );
        } catch( UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize LaF : " + ex );
        }
        
        IntelliJTheme.install( Overskill.class.getResourceAsStream(
            "./../components/arc-theme-orange.theme.json" ) );
        
        UIManager.put( "ScrollBar.thumbArc", 999 );
        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
        UIManager.put( "ScrollBar.width", 13 );
        UIManager.put( "ScrollBar.trackInsets", new Insets( 2, 4, 2, 4 ) );
        UIManager.put( "ScrollBar.track", new Color( 0xe0e0e0 ) );
        
        Login l = new Login();
        l.setVisible(true);
    }
    
}
