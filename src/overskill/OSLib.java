/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import java.awt.Color;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.BorderHighlighter;

/**
 *
 * @author samod
 */
public class OSLib {
    public static String AutoNumber(String table, String id_table, String kode) {
        
        String id = "";
        DBConnect connection = new DBConnect();
        try {
            int tempId, countRow = 0;
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM " + table;
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                id = connection.result.getString(id_table);
                countRow++;
            }
            
            if(countRow > 0) {
                tempId = Integer.parseInt(id.substring(2)) + 1;
                id = String.format (kode + "%03d", tempId);
            } else {
                id = kode + "001";
            }
            connection.stat.close();
            connection.result.close();
            
        } catch(Exception e) {
            System.out.println("Terjadi error saat load permintaan pengiriman: " + e);
        }
        
        return id;
    }
    
    public static int deleteData(String table, String idtable, String id) {
        int returnVar = 0;
        try {
            DBConnect connection = new DBConnect();
            String query = "UPDATE " + table + " SET Status = 0 WHERE " + idtable + "=?";
            
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            p.setString(1, id);
            
            p.executeUpdate();
            p.close();
            returnVar = 1;
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah user : " + e);
        }
        
        return returnVar;
    }
    
    
    public static String getNumberCurrencyComma(String currency) {
        String numberOnly = currency.replaceAll("[^0-9]", "");
        
        return numberOnly.substring(0, numberOnly.length() - 2);
    }
    
    public static String getNumberCurrency(String currency) {
        String numberOnly = currency.replaceAll("[^0-9]", "");
        
        return numberOnly;
    }
    
    public static void textFieldCurrencyFormat(JTextField textfield) {
        NumberFormat formatter = new DecimalFormat("#,###,###");
        
        double text = Double.parseDouble(getNumberCurrency(textfield.getText().equals("") ? "0" : textfield.getText()));
        
        String formattedNumber = formatter.format(text);
        
        textfield.setText(formattedNumber.replace(',', '.'));
    }
    
    public static boolean fieldRequired(Object text, JLabel label)
    {
        boolean valid = false;
        if (text.equals("") || text == null)
        {
            label.setVisible(true);
            label.setText("Wajib diisi.");
        }
        else
        {
            label.setVisible(false);
            valid = true;
        }

        return valid;
    }
    
    public static boolean toggleRequired(JLabel label, boolean... arguments)
    {
        boolean result = false;

        for(boolean r : arguments) {
            if(r) {
                result = true;
            }
        }

        if (!result)
        {
            label.setVisible(true);
            label.setText("Wajib diisi.");
        }
        else
        {
            label.setVisible(false);
        }

        return result;
    }
    
    public static boolean emailRequired(String emailStr, JLabel label) {
        boolean valid = false;
        Pattern ptr = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = ptr.matcher(emailStr);
        boolean validEmail = matcher.find();
        if (emailStr.equals(""))
        {
            label.setVisible(true);
            label.setText("Wajib diisi.");
        } else if(validEmail) {
            label.setVisible(true);
            label.setText("Email tidak valid.");
        }
        else
        {
            label.setVisible(false);
            valid = true;
        }

        return valid;
    }
    
    public static void tableSettings(JXTable table) {
        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(255, 89, 61));
        header.setForeground(Color.WHITE);
        header.setFont(table.getFont().deriveFont(Font.BOLD));
        BorderHighlighter hl = new BorderHighlighter(
            BorderFactory.createEmptyBorder(10, 5, 10, 5));
        hl.setInner(true);
        table.addHighlighter(hl);
        table.setSelectionBackground(new Color(22, 43, 70));
        table.setHorizontalScrollEnabled(true);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        
        table.packAll();
    }
}
