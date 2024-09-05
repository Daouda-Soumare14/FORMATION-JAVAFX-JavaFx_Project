package rachid.javafx;

import java.sql.Date;

public class EmployeeData {
    
    private Integer employee_id;
    private String prenom;
    private String nom;
    private String genre;
    private String phoneNum;
    private String position;
    private String image;
    private Date membreDate;


    @SuppressWarnings("exports")
    public EmployeeData(Integer employee_id, String prenom, String nom, String genre, String phoneNum, String position, String image, Date membreDate) {
        this.employee_id = employee_id;
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.phoneNum = phoneNum;
        this.position = position;
        this.image = image;
        this.membreDate = membreDate;
    }


    public Integer getEmployee_id() {
        return this.employee_id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getPosition() {
        return this.position;
    }

    public String getImage() {
        return this.image;
    }

    @SuppressWarnings("exports")
    public Date getMembreDate() {
        return this.membreDate;
    }
}
