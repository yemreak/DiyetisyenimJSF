package userpackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author YEmre
 */
@ManagedBean(name = "user")
@SessionScoped
public final class UserBean {

    static int id;
    String email;
    String username;
    String password;
    String repassword;

    String err_email; // Email hatası
    String err_username; // Username hatası
    String err_password; // Şifre hatası

    String visibility_logs; // Kayıt ol ve giriş yap butonları için
    String visibility_user;

    static String msg; // Olası exceptionlar için

    static boolean logged;

    public UserBean() {
        logged = false;
        hideErr("all");
    }

    public void refreshEmail() {
        if (UserOperations.isNewEmail(email)) {
            UserOperations.changeUser(email, "email");
        } else {
            email = UserOperations.getUser("email");
        }
    }

    public void refreshUsername() {
        if (UserOperations.isNewUsername(username)) {
            UserOperations.changeUser(username, "username");
        } else {
            username = UserOperations.getUser("username");
        }
    }

    public void refreshPassword() {
        if (UserOperations.checkPassword(password, repassword)) {
            UserOperations.changeUser(password, "password");
        } else {
            password = UserOperations.getUser("password");
        }
    }

    public String getHiddenPassword() {
        String hiddenPass = "";
        
        if (password != null && !password.isEmpty()) {
            for (int i = 0; i < password.length(); i++) {
                hiddenPass += "*";
            }
        }
        return hiddenPass;
    }

    public String logOut() {
        if (logged) {
            logged = false;
            UserOperations.resetData(this);
        }

        return "index?faces-redirect=true";
    }

    public void checkUser() {
        boolean error = false;

        if (!UserOperations.checkUserName(username)) {
            showErr("username");
            error = true;
        } else if (!UserOperations.checkPassword(password)) {
            showErr("password");
            error = true;
        }

        if (!error) {
            UserOperations.refreshUser(this);
            logged = true;
            //return "index?faces-redirect=true"; // Sayfaya bağlanma
        }

        //return "javascript:void(0)";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        UserBean.msg = msg;
    }

    public void reset() {
        id = 0;
        email = null;
        username = null;
        password = null;
        repassword = null;
    }

    /**
     * Kayıl ol butonu eylemi
     *
     * @return Yönlendirilecek sayfa
     */
    public String register() {
        boolean error = false;

        if (!UserOperations.isNewEmail(email)) {
            showErr("email");

            error = true;
        }
        if (!UserOperations.isNewUsername(username)) {
            showErr("username");

            error = true;
        }
        if (!UserOperations.checkPassword(password, repassword)) {
            showErr("password");

            error = true;
        }

        if (!error) {
            UserOperations.createUser(username, password, email);

            id = UserOperations.getId();

            logged = true;

            return "profil?faces-redirect=true"; // Sayfaya bağlanma
        }

        return "javascript:void(0)";
    }

    /**
     * Hata mesajlarını görünmez kılar.
     *
     * @param type Gizlenmek istenen hata mesajı ("username", "password" ...
     * "all")
     */
    public void hideErr(String type) {
        if (type.equalsIgnoreCase("username")) {
            err_username = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
        } else if (type.equalsIgnoreCase("password")) {
            err_password = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
        } else if (type.equalsIgnoreCase("email")) {
            err_email = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
        } else if (type.equalsIgnoreCase("all")) {
            err_email = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
            err_username = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
            err_password = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: none;";
        }
    }

    /**
     * Hata mesajlarını görünür kılar.
     *
     * @param type Gösterilmek istenen hata mesajı ("username", "password" ...
     * "all")
     */
    public void showErr(String type) {
        if (type.equalsIgnoreCase("username")) {
            err_username = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
        } else if (type.equalsIgnoreCase("password")) {
            err_password = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
        } else if (type.equalsIgnoreCase("email")) {
            err_email = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
        } else if (type.equalsIgnoreCase("all")) {
            err_username = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
            err_password = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
            err_email = "color: orangered;\n"
                    + "    font-size: 15px;\n"
                    + "    font-style: initial;\n"
                    + "    display: block;";
        }
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean login) {
        this.logged = login;
    }

    public String getErr_email() {
        return err_email;
    }

    public void setErr_email(String err_email) {
        this.err_email = err_email;
    }

    public String getErr_username() {
        return err_username;
    }

    public void setErr_username(String err_username) {
        this.err_username = err_username;
    }

    public String getErr_password() {
        return err_password;
    }

    public void setErr_password(String err_password) {
        this.err_password = err_password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        UserBean.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getVisibility_logs() {
        if (logged) {
            visibility_logs = "invisible";
        } else {
            visibility_logs = "visible";
        }
        return visibility_logs;
    }

    public void setVisibility_logs(String visiblity_logs) {
        this.visibility_logs = visiblity_logs;
    }

    public String getVisibility_user() {
        if (logged) {
            visibility_user = "visible";
        } else {
            visibility_user = "invisible";
        }
        return visibility_user;
    }

    public void setVisibility_user(String visibility_userPic) {
        this.visibility_user = visibility_userPic;
    }

}
