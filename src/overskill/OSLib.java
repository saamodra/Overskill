/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.validator.routines.EmailValidator;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;
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
    
    public static boolean fieldRequired(String text, JLabel label)
    {
        boolean valid = false;
        if (text.equals(""))
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
    
    public static boolean dateRequired(Object obj, JLabel label) {
        boolean valid = false;
        if (obj == null)
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
    
    public static boolean comboRequired(String text, String placeholder, JLabel label) {
        boolean valid = false;
        if (text.equals(placeholder))
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
    
    public static boolean emailRequired(String emailStr, JLabel label) {
        boolean valid = false;
        boolean isValidEmail = EmailValidator.getInstance().isValid(emailStr);
        if (emailStr.equals(""))
        {
            label.setVisible(true);
            label.setText("Wajib diisi.");
        } else if(!isValidEmail) {
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
    
    public static void alphabetTextField(KeyEvent evt) {
        if(!Character.isAlphabetic(evt.getKeyChar()) && evt.getKeyChar() != 32){
            evt.consume();
        }
    }
    
    public static void numericMaxTextField(KeyEvent evt, int maxLength) {
        boolean max = ((JXTextField)evt.getSource()).getText().length() >= maxLength;
        if(!Character.isDigit(evt.getKeyChar()) || max){
            evt.consume();
        } 
    }
    
    public static void numericTextField(KeyEvent evt) {
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        } 
    }
    
    public static void setDefaultDateFilter(JDateChooser tglAkhir, JDateChooser tglAwal, int year) {
        
        tglAkhir.setDate(new Date());
        
        java.util.Date date2;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-01-01");
            tglAwal.setDate(date2);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
    }
    
    public static void tableSettings(JXTable table) {
        table.setHorizontalScrollEnabled(true);
        table.getColumnModel().getColumn(0).setMaxWidth(70);
        
        table.packAll();
    }
    
}
