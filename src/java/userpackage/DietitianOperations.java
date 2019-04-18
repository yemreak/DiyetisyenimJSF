/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author YEmre
 */
public class DietitianOperations implements DatabaseInfos {

    public static void main(String[] args) {
        System.out.println(getDietitianInfo(1, "url_image"));
    }
    
    /**
     * Diyetisyenin temel bilgilerini database'den alma
     *
     * @param id Diyetisyen ID'si
     * @param info Diyetisyenin istenen verisi ("full_name", "explanation")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public static String getDietitian(int id, String info) {
        String dietitian = "";

        if (id > 0) {
            try {
                String sql = "SELECT " + info + " FROM " + TABLE_DIETISIANS + " WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    dietitian = rs.getString(info);
                }

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return dietitian;
    }
    
    /**
     * Diyet bilgilerini database'den alma
     *
     * @param id Diyetin ID'si
     * @param info Diyetin istenen verisi ("morning", "after_morn")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public static String getDiet(int id, String info) {
        String diet = "";

        if (id > 0) {
            try {
                String sql = "SELECT " + info + " FROM " + TABLE_DIETS + " WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    diet = rs.getString(info);
                }

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return diet;
    }

    /**
     * Diyetisyenin bilgilerini database'den alma
     *
     * @param id Diyetisyen ID'si
     * @param info Diyetisyenin istenen verisi ("university", "department")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public static String getDietitianInfo(int id, String info) {
        String dietitianInfo = "";

        if (id > 0) {
            try {
                String sql = "SELECT " + info + " FROM " + TABLE_DIETITIANS_INFOS + " WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    dietitianInfo = rs.getString(info);
                }

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return dietitianInfo;
    }
}
