package com.talentounido.front_retrofit;

public class Person {
    private String _id;
    private String fullName;
    private String name;
    private String address;
    private String phone;
    private int age;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String _id, String fullName, String name, String address, String phone, int age) {
        this._id = _id;
        this.fullName = fullName;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
    }
    public Person(String fullName, String name, String address, String phone, int age) {
        this.fullName = fullName;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
    }
}
