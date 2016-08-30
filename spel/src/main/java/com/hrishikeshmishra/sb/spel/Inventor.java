package com.hrishikeshmishra.sb.spel;

import java.util.Date;

/**
 * Created by hrishikesh.mishra on 30/08/16.
 */
public class Inventor {

    private String name;
    private Date birthday;
    private String nationality;

    public Inventor(String name, Date birthday, String nationality) {
        this.name = name;
        this.birthday = birthday;
        this.nationality = nationality;
    }

    public Inventor(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }


    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Inventor{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
