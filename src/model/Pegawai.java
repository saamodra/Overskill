/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author samod
 */
public class Pegawai {
    private String ID_Pegawai;
    private String Nama_Pegawai;
    private String Jenis_Kelamin;
    private String Alamat;
    private String No_telp;
    private String Email;
    private String Username;
    private String Password;
    private char Role;
    private char Status;
    
    public Pegawai() {
    }

    public Pegawai(String ID_Pegawai, String Nama_Pegawai, String Jenis_Kelamin, String Alamat, String No_telp, String Email, String Username, String Password, char Role, char Status) {
        this.ID_Pegawai = ID_Pegawai;
        this.Nama_Pegawai = Nama_Pegawai;
        this.Jenis_Kelamin = Jenis_Kelamin;
        this.Alamat = Alamat;
        this.No_telp = No_telp;
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.Role = Role;
        this.Status = Status;
    }

    public String getID_Pegawai() {
        return ID_Pegawai;
    }

    public void setID_Pegawai(String ID_Pegawai) {
        this.ID_Pegawai = ID_Pegawai;
    }

    public String getNama_Pegawai() {
        return Nama_Pegawai;
    }

    public void setNama_Pegawai(String Nama_Pegawai) {
        this.Nama_Pegawai = Nama_Pegawai;
    }

    public String getJenis_Kelamin() {
        return Jenis_Kelamin;
    }

    public void setJenis_Kelamin(String Jenis_Kelamin) {
        this.Jenis_Kelamin = Jenis_Kelamin;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getNo_telp() {
        return No_telp;
    }

    public void setNo_telp(String No_telp) {
        this.No_telp = No_telp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public char getRole() {
        return Role;
    }

    public void setRole(char Role) {
        this.Role = Role;
    }

    public char getStatus() {
        return Status;
    }

    public void setStatus(char Status) {
        this.Status = Status;
    }
    
    
}
