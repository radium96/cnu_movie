package com.naca.database_termproject.data;

// 로그인한 사용자의 정보를 저장하는 Model.
public class User {
    String name; // 사용자의 이름
    String email; // 사용자의 이메일
    String birth; // 사용자의 생년월일
    String sex; // 사용자의 성별

    public User(String name, String email, String birth, String sex) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        if(sex.equals("M")){
            this.sex = "남성";
        } else{
            this.sex = "여성";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
