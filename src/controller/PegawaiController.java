/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.util.List;
import model.Pegawai;

/**
 *
 * @author samod
 */
public class PegawaiController {
    public List<Pegawai> getAll(Connection c) {
        c.stat = c.conn.createStatement();
        String sql = "SELECT * FROM Pegawai WHERE Status='1' AND (Nama_Pegawai LIKE '%" + cari + "%' "
                + "OR ID_Pegawai LIKE '%" + cari + "%' OR No_Telp LIKE '%" + cari + "%')";

        c.result = c.stat.executeQuery(sql);
        int no = 1;

        while(c.result.next()) {
            ResultSet r = c.result;
            Object obj[] = new Object[9];
            obj[0] = no++;
            obj[1] = r.getString("ID_Pegawai");
            obj[2] = r.getString("Nama_Pegawai");
            obj[3] = (r.getString("Jenis_Kelamin").equals("L") ? "Laki-Laki" : "Perempuan");
            obj[4] = r.getString("Alamat");
            obj[5] = r.getString("No_Telp");
            obj[6] = r.getString("Email");
            obj[7] = r.getString("Username");
            obj[8] = (r.getInt("Role") == 1 ? "Admin" : r.getInt("Role") == 2 ? "Kasir" : "Manager");


            model.addRow(obj);
        }

        c.stat.close();
        c.result.close();
    }
}
