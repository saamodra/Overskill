/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

/**
 *
 * @author samod
 */
public class OSSession {
    private static String id;
    private static String username;
    private static String nama;
    private static String role;

    public OSSession() {
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        OSSession.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        OSSession.username = username;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        OSSession.nama = nama;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        OSSession.role = role;
    }
    
    
}
