package com.example.android.miwok;

public class ReportCard {
    private int graduateYear;
    private String studentFirstName;
    private String studentLastName;

    public ReportCard (int graduateYear, String studentLastName, String studentFirstName) {
        graduateYear = this.graduateYear;
        studentLastName = this.studentLastName;
        studentFirstName = this.studentFirstName;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "graduateYear=" + graduateYear +
                ", studentFirstName='" + studentFirstName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", studentGrade=" + studentGrade +
                ", classTaken='" + classTaken + '\'' +
                ", teacherLastname='" + teacherLastname + '\'' +
                ", attandanceDays=" + attandanceDays +
                '}';
    }

    private int studentGrade;
    private String classTaken;
    private String teacherLastname;
    private int attandanceDays;

    public int getGraduateYear() {
        return graduateYear;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getClassTaken() {
        return classTaken;
    }

    public void setClassTaken(String classTaken) {
        this.classTaken = classTaken;
    }

    public String getTeacherLastname() {
        return teacherLastname;
    }

    public void setTeacherLastname(String teacherLastname) {
        this.teacherLastname = teacherLastname;
    }

    public int getAttandanceDays() {
        return attandanceDays;
    }

    public void setAttandanceDays(int attandanceDays) {
        this.attandanceDays = attandanceDays;
    }
}
