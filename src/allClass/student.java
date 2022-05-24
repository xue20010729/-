package allClass;

public class student extends borrower {
    int studentNumber;  //学号
    String college;

    public student(int id, int score, String name, int studentNumber, String college) {
        super(id, score, name);
        this.studentNumber = studentNumber;
        this.college = college;
    }
}
