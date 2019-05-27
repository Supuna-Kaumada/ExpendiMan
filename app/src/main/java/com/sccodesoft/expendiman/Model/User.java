package com.sccodesoft.expendiman.Model;

public class User {
        private int uid;
        private String name;
        private String email;
        private String password;
        private double Income;
        private double Expenditure;

    public User() {
    }

    public User(int uid, String name, String email, String password, double income, double expenditure) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        Income = income;
        Expenditure = expenditure;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getIncome() {
        return Income;
    }

    public void setIncome(double income) {
        Income = income;
    }

    public double getExpenditure() {
        return Expenditure;
    }

    public void setExpenditure(double expenditure) {
        Expenditure = expenditure;
    }
}
