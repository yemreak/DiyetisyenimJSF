/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author YEmre & Okan Uslu
 */
@ManagedBean(name = "userInfo")
@SessionScoped
public final class UserInfoBean {

    int id_diet;
    int id_dietitian;

    String name;
    String surname;
    String age;
    String height;
    String weight;
    String gender;

    String neck;
    String waist;
    String hip;

    String basalRate;
    String idealWeight;
    String bodyMassIndex;
    String bodyFatRate;

    String txt_basalMetabolic;
    String txt_idealWeight;
    String txt_bodyMassIndex;
    String txt_bodyFatRate;

    String explanationB;
    String activity;

    String explanationn;
    String explanationn2;
    String explanation;
    String explanation2;

    public void refreshWeight() {
        UserOperations.changeUserInfo(weight, "weight");
    }

    public void refreshHeight() {
        UserOperations.changeUserInfo(height, "height");
    }

    public void refreshAge() {
        UserOperations.changeUserInfo(age, "age");
    }

    public void saveBodyFatRate() {
        UserOperations.saveRateInfo(bodyFatRate, "body_fat_rate");
    }

    public void saveBasalRate() {
        UserOperations.saveRateInfo(basalRate, "basal_rate");
    }

    public void saveIdealWeight() {
        UserOperations.saveRateInfo(idealWeight, "ideal_weight");
    }

    public void saveBodyMassIndex() {
        UserOperations.saveRateInfo(bodyMassIndex, "body_mass_index");
    }

    public void logOut() {
        UserOperations.resetData(this);
    }

    /**
     * Diyetisyenin temel bilgilerini database'den alma
     *
     * @param id Diyetisyen ID'si
     * @param info Diyetisyenin istenen verisi ("full_name", "explanation")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public String getDietition(int id, String info) {
        return DietitianOperations.getDietitian(id, info);
    }

    /**
     * Diyet bilgilerini database'den alma
     *
     * @param id Diyetin ID'si
     * @param info Diyetin istenen verisi ("morning", "after_morn")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public String getDiet(int id, String info) {
        return DietitianOperations.getDiet(id, info);
    }

    /**
     * Diyetisyenin bilgilerini database'den alma
     *
     * @param id Diyetisyen ID'si
     * @param info Diyetisyenin istenen verisi ("university", "department")
     * @return Varsa aranan bilgi yoksa boş string
     */
    public String getDietitionInfo(int id, String info) {
        return DietitianOperations.getDietitianInfo(id, info);
    }

    public String selectDiet(int id) {
        id_diet = id;

        UserOperations.changeDiet(id);
        return "index.xhtml?faces-redirect=true";
    }

    public String selectDietitian(int id) {
        id_dietitian = id;

        UserOperations.changeDietitian(id);
        return "diet.xhtml?faces-redirect=true";
    }

    public String saveInfo() {
        UserOperations.changeInfos(name, surname, age, height, weight, neck, waist, hip, gender);
        return "dietician.xhtml?faces-redirect=true";
    }

    public String loginAndRefreshInfos() {
        if (UserBean.logged) {
            UserOperations.refreshUserInfo(this);
            return "index?faces-redirect=true"; // Sayfaya bağlanma
        }

        return "javascript:void(0)";
    }

    public void reset() {
        id_diet = 0;
        id_dietitian = 0;

        name = null;
        surname = null;
        age = null;
        height = null;
        weight = null;
        gender = null;

        neck = null;
        waist = null;
        hip = null;

        basalRate = null;
        idealWeight = null;
        bodyFatRate = null;
        txt_bodyMassIndex = null;
    }

    public int getId_diet() {
        return id_diet;
    }

    public void setId_diet(int id_diet) {
        this.id_diet = id_diet;
    }

    public int getId_dietitian() {
        return id_dietitian;
    }

    public void setId_dietitian(int id_dietitian) {
        this.id_dietitian = id_dietitian;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTxt_basalMetabolic() {
        return txt_basalMetabolic;
    }

    public void setTxt_basalMetabolic(String txt_basalMetabolic) {
        this.txt_basalMetabolic = txt_basalMetabolic;
    }

    public String getExplanationB() {
        return explanationB;
    }

    public void setExplanationB(String explanationB) {
        this.explanationB = explanationB;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void calculateBasalMetabolicRate() {
        double doubleWeight, doubleHeight, doubleAge;
        doubleWeight = Double.valueOf(weight);
        doubleHeight = Double.valueOf(height);
        doubleAge = Double.valueOf(age);
        if (gender != null && doubleWeight != 0 && doubleHeight != 0 && doubleAge != 0) {
            if (gender.equals("erkek")) {
                txt_basalMetabolic = "Bazal Metabolizma Hızınız " + String.valueOf(66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) + " Kalori";
                basalRate = String.valueOf(66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)).substring(0, 6);
            } else if (gender.equals("kadın")) {
                txt_basalMetabolic = "Bazal Metabolizma Hızınız " + String.valueOf(655 + (9.6 * doubleWeight) + (1.7 * doubleHeight) - (4.7 * doubleAge)) + " Kalori";
                basalRate = String.valueOf(655 + (9.6 * doubleWeight) + (1.7 * doubleHeight) - (4.7 * doubleAge)).substring(0, 6);
            }
            if (activity.equals("never")) {
                explanationB = "Hiç ya da çok az egzersiz yapanlar için günlük kalori ihtiyacı " + String.valueOf((66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) * 1.2) + " kalori";
            } else if (activity.equals("rarely")) {
                explanationB = "Hafif egzersiz yapanlar (Haftada 1-2 kere) için günlük kalori ihtiyacı " + String.valueOf((66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) * 1.375) + " kalori";
            } else if (activity.equals("often")) {
                explanationB = "Orta seviyede egzersiz yapanlar (Haftada 3-5 kere) için günlük kalori ihtiyacı " + String.valueOf((66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) * 1.55) + " kalori";
            } else if (activity.equals("usually")) {
                explanationB = "Ağır egzersiz yapanlar (Haftada 6-7 kere) için  günlük kalori ihtiyacı " + String.valueOf((66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) * 1.725) + " kalori";
            } else if (activity.equals("always")) {
                explanationB = "Çok ağır egzersiz yapanlar (Günde 2 kere, çok ağır egzersizler) için günlük kalori ihtiyacı " + String.valueOf((66 + (13.75 * doubleWeight) + (5 * doubleHeight) - (6.8 * doubleAge)) * 1.9) + " kalori";
            }
            txt_basalMetabolic = txt_basalMetabolic.substring(0, (txt_basalMetabolic.indexOf(".") + 3));
        } else {
            txt_basalMetabolic = "";
        }
    }

    public void calculateIdealWeight() {
        double doubleWeight, doubleHeight;
        doubleWeight = Double.valueOf(weight);
        doubleHeight = Double.valueOf(height);
        if (doubleWeight != 0 && doubleHeight != 0) {
            if (gender.equals("erkek")) {
                txt_idealWeight = "İdeal Kilonuz " + String.valueOf(50 + (2.3 / 2.54) * (doubleHeight - 152.4));
                idealWeight = String.valueOf(50 + (2.3 / 2.54) * (doubleHeight - 152.4)).substring(0, 5);
            } else if (gender.equals("kadın")) {
                txt_idealWeight = "İdeal Kilonuz " + String.valueOf((45.5) + (2.3 / 2.54) * (doubleHeight - 152.4));
                idealWeight = String.valueOf((45.5) + (2.3 / 2.54) * (doubleHeight - 152.4)).substring(0, 5);
            }
            txt_idealWeight = txt_idealWeight.substring(0, (txt_idealWeight.indexOf(".") + 3)) + " kg";
        } else {
            txt_idealWeight = "";
        }
    }

    public String getTxt_idealWeight() {
        return txt_idealWeight;
    }

    public void setTxt_idealWeight(String txt_idealWeight) {
        this.txt_idealWeight = txt_idealWeight;
    }

    public String getTxt_bodyMassIndex() {
        return txt_bodyMassIndex;
    }

    public void setTxt_bodyMassIndex(String txt_bodyMassIndex) {
        this.txt_bodyMassIndex = txt_bodyMassIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation2() {
        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }

    public void calculateBodyMassIndex() {
        double doubleWeight, doubleHeight;
        doubleWeight = Double.valueOf(weight);
        doubleHeight = Double.valueOf(height) / 100;
        if (doubleWeight != 0 && doubleHeight != 0) {
            txt_bodyMassIndex = "Vücut Kitle Indexiniz " + String.valueOf((doubleWeight / (doubleHeight * doubleHeight)));
            bodyMassIndex = String.valueOf((doubleWeight / (doubleHeight * doubleHeight))).substring(0, 4);
            txt_bodyMassIndex = txt_bodyMassIndex.substring(0, (txt_bodyMassIndex.indexOf(".") + 3));
            if ((doubleWeight / (doubleHeight * doubleHeight)) >= 0 && (doubleWeight / (doubleHeight * doubleHeight)) <= 18.4) {
                explanation = "0 - 18.4: Zayıf";
                explanation2 = "Boyunuza göre uygun ağırlıkta olmadığınızı, zayıf olduğunuzu gösterir. Zayıflık, bazı hastalıklar için risk oluşturan ve istenmeyen bir durumdur.Boyunuza uygun ağırlığa erişmeniz için yeterli ve dengeli beslenmeli, beslenme alışkanlıklarınızı geliştirmeye özen göstermelisiniz.";
            } else if ((doubleWeight / (doubleHeight * doubleHeight)) >= 18.5 && (doubleWeight / (doubleHeight * doubleHeight)) <= 24.9) {
                explanation = "18.5 - 24.9: Normal";
                explanation2 = "Boyunuza göre uygun ağırlıkta olduğunuzu gösterir. Yeterli ve dengeli beslenerek ve düzenli fiziksel aktivite yaparak bu ağırlığınızı korumaya özen gösteriniz.";
            } else if ((doubleWeight / (doubleHeight * doubleHeight)) >= 25 && (doubleWeight / (doubleHeight * doubleHeight)) <= 29.9) {
                explanation = "25.0 - 29.9: Fazla Kilolu";
                explanation2 = "Boyunuza göre vücut ağırlığınızın fazla olduğunu gösterir. Fazla kilolu olma durumu gerekli önlemler alınmadığı takdirde pek çok hastalık için risk faktörü olan obeziteye (şişmanlık) yol açar.";
            } else if ((doubleWeight / (doubleHeight * doubleHeight)) >= 30 && (doubleWeight / (doubleHeight * doubleHeight)) <= 34.9) {
                explanation = "30.0 - 34.9: Şişman (Obez) - I. Sınıf";
                explanation2 = "Boyunuza göre vücut ağırlığınızın fazla olduğunu bir başka deyişle şişman olduğunuzun bir göstergesidir. Şişmanlık, kalp-damar hastalıkları, diyabet, hipertansiyon v.b. kronik hastalıklar için risk faktörüdür. Bir sağlık kuruluşuna başvurarak hekim / diyetisyen kontrolünde zayıflayarak normal ağırlığa inmeniz sağlığınız açısından çok önemlidir. Lütfen, sağlık kuruluşuna başvurunuz.";
            } else if ((doubleWeight / (doubleHeight * doubleHeight)) >= 35 && (doubleWeight / (doubleHeight * doubleHeight)) <= 44.9) {
                explanation = "35.0 - 44.9: Şişman (Obez) - II. Sınıf";
                explanation2 = "Boyunuza göre vücut ağırlığınızın fazla olduğunu bir başka deyişle şişman olduğunuzun bir göstergesidir. Şişmanlık, kalp-damar hastalıkları, diyabet, hipertansiyon v.b. kronik hastalıklar için risk faktörüdür. Bir sağlık kuruluşuna başvurarak hekim / diyetisyen kontrolünde zayıflayarak normal ağırlığa inmeniz sağlığınız açısından çok önemlidir. Lütfen, sağlık kuruluşuna başvurunuz.";
            } else if ((doubleWeight / (doubleHeight * doubleHeight)) >= 45) {
                explanation = "45.0 ve üstü: Aşırı Şişman (Aşırı Obez) - III. Sınıf";
                explanation2 = "Boyunuza göre vücut ağırlığınızın fazla olduğunu bir başka deyişle şişman olduğunuzun bir göstergesidir. Şişmanlık, kalp-damar hastalıkları, diyabet, hipertansiyon v.b. kronik hastalıklar için risk faktörüdür. Bir sağlık kuruluşuna başvurarak hekim / diyetisyen kontrolünde zayıflayarak normal ağırlığa inmeniz sağlığınız açısından çok önemlidir. Lütfen, sağlık kuruluşuna başvurunuz.";
            }
        } else {
            txt_bodyMassIndex = "";
            explanation = "";
            explanation2 = "";
        }
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(String bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
    }

    public String getExplanationn() {
        return explanationn;
    }

    public void setExplanationn(String explanationn) {
        this.explanationn = explanationn;
    }

    public String getExplanationn2() {
        return explanationn2;
    }

    public void setExplanationn2(String explanationn2) {
        this.explanationn2 = explanationn2;
    }

    public String getBasalRate() {
        return basalRate;
    }

    public void setBasalRate(String basalRate) {
        this.basalRate = basalRate;
    }

    public String getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(String idealWeight) {
        this.idealWeight = idealWeight;
    }

    public String getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(String bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex;
    }

    public void calculateBodyFatRate() {
        if (weight != null && !weight.isEmpty() && height != null && !height.isEmpty() && neck != null && !neck.isEmpty() && waist != null && !waist.isEmpty() && hip != null && !hip.isEmpty()) {
            double doubleWeight, doubleHeight, doubleNeck, doubleWaist, doubleHip;
            doubleWeight = Double.valueOf(weight);
            doubleHeight = Double.valueOf(height) / 100;
            doubleNeck = Double.valueOf(neck);
            doubleWaist = Double.valueOf(waist);
            doubleHip = Double.valueOf(hip);
            if (gender != null && doubleWeight != 0 && doubleHeight != 0 && doubleNeck != 0 && doubleWaist != 0 && doubleHip != 0) {
                if (gender.equals("erkek")) {
                    txt_bodyFatRate = "Vücut Yağ Oranınız % " + String.valueOf(((495) / (1.0324 - (0.19077 * Math.log10(doubleWaist - doubleNeck)) + (0.15456 * Math.log10(doubleHeight)))) - 450);
                    bodyFatRate = String.valueOf(((495) / (1.0324 - (0.19077 * Math.log10(doubleWaist - doubleNeck)) + (0.15456 * Math.log10(doubleHeight)))) - 450);
                } else if (gender.equals("kadın")) {
                    txt_bodyFatRate = "Vücut Yağ Oranınız % " + String.valueOf(((495) / (1.29579 - (0.35004 * Math.log10(doubleWaist + doubleHip - doubleNeck)) + (0.22100 * Math.log10(doubleHeight)))) - 450);
                    bodyFatRate = String.valueOf(((495) / (1.29579 - (0.35004 * Math.log10(doubleWaist + doubleHip - doubleNeck)) + (0.22100 * Math.log10(doubleHeight)))) - 450);
                }
            } else {
                txt_bodyFatRate = "";
            }
        }
    }

    public String getTxt_bodyFatRate() {
        return txt_bodyFatRate;
    }

    public void setTxt_bodyFatRate(String txt_bodyFatRate) {
        this.txt_bodyFatRate = txt_bodyFatRate;
    }
}
