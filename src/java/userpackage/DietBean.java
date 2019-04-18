/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author YEmre
 */
@ManagedBean(name = "diet")
public class DietBean {

    int id;
    String morning;
    String after_morn;
    String noon;
    String after_noon;
    String evening;
    String after_eve;

    private static DietBean instance;

    private DietBean() {
    }
    
    /* 
    public static void main(String[] args) { // Test
        System.out.println(DietBean.getRandom());
    }
    */

    public static DietBean getInstance(int i) {
        instance = new DietBean();
        
        try {
            String sql = "SELECT * FROM diets";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            for (int count = 0; count < i; count++) {
                rs.next();
            }

            if (rs.next()) {              
                instance.id = rs.getInt("id");
                instance.morning = rs.getString("morning");
                instance.after_morn = rs.getString("after_morn");
                instance.noon = rs.getString("noon");
                instance.after_noon = rs.getString("after_noon");
                instance.evening = rs.getString("evening");
                instance.after_eve = rs.getString("after_eve");
            }
            
            Database.close();

        } catch (SQLException e) {
            UserBean.msg = e.toString(); // hata mesajı
        }
        
        return instance;
    }
    
    /**
     * Tablodaki en son (yüksek) id'i döndürür.
     * @return ID
     */
    public static int getId(){
        int maxId = -1;
        
        try {
            String sql = "SELECT Max(ID) FROM diets";
            PreparedStatement state= Database.getConnection().prepareStatement(sql);
            ResultSet set = state.executeQuery();
            
            if (set.next())
                maxId = set.getInt(1);
       
        } catch (SQLException e){
            UserBean.msg = e.toString();
        }
        
        return maxId;
    }

    /**
     * Database'den rastgele diyet listesi verisi alır döndürür.
     * @return Rastgele diyet listesi
     */
    public static DietBean getRandom() {
        if (getId() > 0)
            return getInstance(new Random().nextInt(getId()));
        
        return null;
    } 
    
    @Override
    public String toString(){
        return "Sabah:\n" + morning + "\nAra:\n" + after_morn + "\nÖğlen:\n" + noon + "\nAra:\n" + after_noon + "\nAkşam:\n" + evening + "\nAra:\n" + after_eve;
    }
}
