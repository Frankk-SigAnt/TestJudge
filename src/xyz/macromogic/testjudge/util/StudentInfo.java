package xyz.macromogic.testjudge.util;

public class StudentInfo {
    private String sid;
    private String lastName;
    private String firstName;
    private String grade;
    private String submissionDate;
    private String lateSubmission;

    public static StudentInfo newStudentInfo(String[] line) {
        StudentInfo info = new StudentInfo();
        info.sid = line[1];
        info.lastName = line[2];
        info.firstName = line[3];
        info.grade = line[4];
        info.submissionDate = line[5];
        info.lateSubmission = line[6];
        return info;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPath() {
        return String.format("%s, %s(%s)/", lastName, firstName, sid);
    }

    public String toCsvLine() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", sid, sid, lastName, firstName, grade, submissionDate, lateSubmission);
    }
}
