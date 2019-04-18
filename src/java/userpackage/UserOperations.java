package userpackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * , open the template in the editor.
 */
/**
 *
 * @author YEmre
 */
public class UserOperations implements DatabaseInfos {

    /**
     * Tablodaki en son (yüksek) id'i döndürür.
     *
     * @return ID
     */
    public static int getId() {
        int maxId = -1;

        try {

            String sql = "SELECT Max(ID) FROM " + TABLE_USERS + "";
            PreparedStatement state = Database.getConnection().prepareStatement(sql);
            ResultSet set = state.executeQuery();

            if (set.next()) {
                maxId = set.getInt(1);
            }

        } catch (SQLException e) {
            UserBean.msg = e.toString();
        }

        return maxId;
    }
    
    public static void main(String args[]) { // TEST
        UserInfoBean user = new UserInfoBean();
        UserBean.id = 12;
        System.out.println(getUser("username"));

    }
    
    public static String getUser(String info_type) {
        String info = "";
        try {

            String sql = "SELECT " + info_type + " FROM " + TABLE_USERS + " WHERE id = ?";
            PreparedStatement state = Database.getConnection().prepareStatement(sql);
            state.setInt(1, UserBean.id);
            ResultSet set = state.executeQuery();

            if (set.next()) {
                info = set.getString(1);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return info;
    }

    /**
     * Kullanıcı adı kontrolü
     *
     * @param username Kullanıcı adı
     * @return Uygunluk
     */
    public static boolean isNewUsername(String username) {
        if (username != null && username.length() > 0) {
            try {

                String sql = "SELECT username FROM " + TABLE_USERS + " WHERE username = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);

                statement.setString(1, username);
                ResultSet set = statement.executeQuery();

                if (!set.next()) {
                    Database.close();
                    return true;
                }

                Database.close();

            } catch (SQLException e) {
                UserBean.msg = e.toString();
            }
        }
        return false;
    }

    /**
     * Şifre kontrolü
     *
     * @param password Şifre
     * @param repassword Şifre tekrarı
     * @return Uygunluk
     */
    public static boolean checkPassword(String password, String repassword) {
        return password != null && password.length() > 0 && password.equals(repassword);
    }

    /**
     * Mail kontrolü
     *
     * @param email E-mail
     * @return Uygunluk
     */
    public static boolean isNewEmail(String email) {
        if (email != null && email.length() > 0) {
            try {
                String sql = "SELECT username FROM " + TABLE_USERS + " WHERE email = ?";
                PreparedStatement statement = Database.getConnection().prepareStatement(sql);

                statement.setString(1, email);
                ResultSet set = statement.executeQuery();

                if (!set.next()) {
                    return true;
                }

                Database.close();

            } catch (SQLException e) {
                UserBean.msg = e.toString();
            }
        }
        return false;
    }

    /**
     * Üye kaydı oluşturma
     *
     * @param username Kullanıcı Adı
     * @param password Şifre
     * @param email Mail
     */
    public static void createUser(String username, String password, String email) {
        if (username.length() > 0 && email.length() > 0 && password.length() > 0) {
            try {

                String sql = "INSERT INTO " + TABLE_USERS + " (id, username, password, email) VALUES (?,?,?,?)";

                int id = getId() + 1;

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);

                statement.setInt(1, id);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, email);
                statement.executeUpdate();

                sql = "insert into " + TABLE_USERS_INFOS + " (id) values (?)";

                statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                sql = "insert into " + TABLE_RATES_INFOS + " (id) values (?)";

                statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                sql = "insert into " + TABLE_BODY_INFOS + " (id) values (?)";

                statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                UserBean.msg = e.toString();
            }
        }
    }

    public static void refreshUser(UserBean user) {
        try {
            String sql = "SELECT * FROM " + TABLE_USERS + " WHERE username = ?";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.setString(1, user.username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserBean.id = rs.getInt("id");
                // user.username = rs.getString("username"); Zaten alındı
                // user.password = rs.getString("password"); Zaten alındı
                user.email = rs.getString("email");
            }

            Database.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Kullanıcı verilerini temizleme
     *
     * @param user Sıfırlanacak veri
     */
    public static void resetData(Object user) {
        if (user instanceof UserBean) {
            ((UserBean) user).reset();
        } else if (user instanceof UserInfoBean) {
            ((UserInfoBean) user).reset();
        }
    }

    /**
     * Kullanıcı verilerini databaseden alma
     *
     * @param user Güncellenecek kullanıcı verileri
     */
    public static void refreshUserInfo(UserInfoBean user) {
        if (UserBean.id > 0) {
            try {
                String sql = "SELECT * FROM " + TABLE_USERS_INFOS + " WHERE id = ?";
                PreparedStatement ps = Database.getConnection().prepareStatement(sql);
                ps.setInt(1, UserBean.id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    user.name = rs.getString("name");
                    user.surname = rs.getString("surname");
                    user.age = rs.getString("age");
                    user.height = rs.getString("height");
                    user.weight = rs.getString("weight");
                    user.gender = rs.getString("gender");
                    user.id_diet = rs.getInt("id_diet");
                    user.id_dietitian = rs.getInt("id_dietitian");
                }

                sql = "SELECT * FROM " + TABLE_BODY_INFOS + " WHERE id = ?";
                ps = Database.getConnection().prepareStatement(sql);
                ps.setInt(1, UserBean.id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    user.neck = rs.getString("neck");
                    user.waist = rs.getString("waist");
                    user.hip = rs.getString("hip");
                }

                sql = "SELECT * FROM " + TABLE_RATES_INFOS + " WHERE id = ?";
                ps = Database.getConnection().prepareStatement(sql);
                ps.setInt(1, UserBean.id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    user.basalRate = rs.getString("basal_rate");
                    user.idealWeight = rs.getString("ideal_weight");
                    user.bodyMassIndex = rs.getString("body_mass_index");
                    user.bodyFatRate = rs.getString("body_fat_rate");
                }

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            UserBean.msg = "ID: " + UserBean.id;
        }
    }

    public static void changeDietitian(int id_dietitian) {
        if (UserBean.id > 0 && id_dietitian > 0) {
            try {
                String sql = "UPDATE " + TABLE_USERS_INFOS + " SET id_dietitian = ? WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id_dietitian);
                statement.setInt(2, UserBean.id);

                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void changeDiet(int id_diet) {
        if (UserBean.id > 0 && id_diet > 0) {
            try {
                String sql = "UPDATE " + TABLE_USERS_INFOS + " SET id_diet = ? WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, id_diet);
                statement.setInt(2, UserBean.id);

                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void saveRateInfo(String info, String info_type) {
        if (UserBean.id > 0 && info_type != null && info != null) {
            try {
                String sql = "UPDATE " + TABLE_RATES_INFOS + " SET " + info_type + " = ? WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setDouble(1, Double.valueOf(info));
                statement.setInt(2, UserBean.id);

                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void changeUser(String info, String info_type) {
        if (UserBean.id > 0 && info_type != null && info != null) {
            try {
                String sql = "UPDATE " + TABLE_USERS + " SET " + info_type + " = ? WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, info);
                statement.setInt(2, UserBean.id);

                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void changeUserInfo(String info, String info_type) {
        if (UserBean.id > 0 && info_type != null && info != null) {
            try {
                String sql = "UPDATE " + TABLE_USERS_INFOS + " SET " + info_type + " = ? WHERE id = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setInt(1, Integer.valueOf(info));
                statement.setInt(2, UserBean.id);

                statement.executeUpdate();

                Database.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void changeInfos(String name, String surname, String age, String height, String weight, String neck, String waist, String hip, String gender) {
        try {
            boolean first = true;

            String sql = "";

            List list_userInfos = new ArrayList();

            if (name != null && !name.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " name = ?";
                list_userInfos.add(name);
            }

            if (surname != null && !surname.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " surname = ?";
                list_userInfos.add(surname);
            }

            if (age != null && !age.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " age = ?";
                list_userInfos.add(Integer.parseInt(age));
            }

            if (height != null && !height.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " height = ?";
                list_userInfos.add(Integer.parseInt(height));
            }

            if (weight != null && !weight.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " weight = ?";
                list_userInfos.add(Integer.parseInt(weight));
            }

            if (gender != null && !gender.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                // first = false; Gerekli değil
                sql += " gender = ?";
                list_userInfos.add(gender);
            }

            if (!sql.isEmpty()) {
                sql = "UPDATE " + TABLE_USERS_INFOS + " SET" + sql + " WHERE id = ?";
            }

            if (list_userInfos.size() > 0) {
                int i = 1;

                PreparedStatement ps = Database.getConnection().prepareStatement(sql);

                for (Object data : list_userInfos) {
                    ps.setObject(i++, data);
                }
                ps.setInt(i, UserBean.id);

                ps.executeUpdate();
            }

            first = true;
            sql = "";
            List list_bodyInfos = new ArrayList();

            if (neck != null && !neck.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " neck = ?";
                list_bodyInfos.add(Integer.parseInt(neck));
            }

            if (waist != null && !waist.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                first = false;
                sql += " waist = ?";
                list_bodyInfos.add(Integer.parseInt(waist));
            }

            if (hip != null && !hip.isEmpty()) {
                if (!first) {
                    sql += " ,";
                }
                // first = false; Gerekli değil
                sql += " hip = ?";
                list_bodyInfos.add(Integer.parseInt(hip));
            }

            if (!sql.isEmpty()) {
                sql = "UPDATE " + TABLE_BODY_INFOS + " SET" + sql + " WHERE id = ?";
            }

            if (list_bodyInfos.size() > 0) {
                int i = 1;

                PreparedStatement ps = Database.getConnection().prepareStatement(sql);

                for (Object data : list_bodyInfos) {
                    ps.setObject(i++, data);
                }
                ps.setInt(i, UserBean.id);

                ps.executeUpdate();
            }

            Database.close();

        } catch (SQLException e) {

            System.out.println(e.toString());
            UserBean.msg = e.toString();
        }
    }

    /**
     * Kullanıcı adı database'de aratır.
     * @param username Kullanıcı adı
     * @return Eğer bulunursa true
     */
    public static boolean checkUserName(String username) {
        if (username != null && username.length() > 0) {
            try {
                String sql = "SELECT username FROM " + TABLE_USERS + " WHERE username = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, username);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Database.close();
                    return true;
                }

                Database.close();

            } catch (SQLException e) {
                UserBean.msg = e.toString();
            }
        }
        return false;
    }

    public static boolean checkPassword(String password) {
        if (password != null && password.length() > 0) {
            try {
                String sql = "SELECT password FROM " + TABLE_USERS + " WHERE password = ?";

                PreparedStatement statement = Database.getConnection().prepareStatement(sql);
                statement.setString(1, password);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Database.close();
                    return true;
                }

                Database.close();

            } catch (SQLException e) {
                UserBean.msg = e.toString();
            }
        }
        return false;
    }
}
