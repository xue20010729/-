package allClass;

public class teacher extends borrower {
    public int jobNumber;
    public String positon;

    public teacher(int id, int score, String name, int jobNumber, String positon) {
        super(id, score, name);
        this.jobNumber = jobNumber;
        this.positon = positon;
    }
}
