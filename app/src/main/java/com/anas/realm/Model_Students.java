package com.anas.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Model_Students extends RealmObject {

    @PrimaryKey
    int Student_serial;

    @Required
    String Student_name;

    @Required
    String Student_uid;


    public Model_Students() {
    }

    public Model_Students(int student_serial, String student_name, String student_uid) {
        Student_serial = student_serial;
        Student_name = student_name;
        Student_uid = student_uid;
    }


    public int getStudent_serial() {
        return Student_serial;
    }

    public void setStudent_serial(int student_serial) {
        Student_serial = student_serial;
    }

    public String getStudent_name() {
        return Student_name;
    }

    public void setStudent_name(String student_name) {
        Student_name = student_name;
    }

    public String getStudent_uid() {
        return Student_uid;
    }

    public void setStudent_uid(String student_uid) {
        Student_uid = student_uid;
    }
}
