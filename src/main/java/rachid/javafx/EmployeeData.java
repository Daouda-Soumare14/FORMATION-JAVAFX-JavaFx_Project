package rachid.javafx;

import java.sql.Date;

public class EmployeeData {
    
    private int employee_id;
    private String prenom;
    private String nom;
    private String genre;
    private String phoneNum;
    private String position;
    private String image;
    private Date membreDate;


    // @SuppressWarnings("exports")
    // public EmployeeData(Integer employee_id, String prenom, String nom, String genre, String phoneNum, String position, String image, Date membreDate) {
    //     this.employee_id = employee_id;
    //     this.prenom = prenom;
    //     this.nom = nom;
    //     this.genre = genre;
    //     this.phoneNum = phoneNum;
    //     this.position = position;
    //     this.image = image;
    //     this.membreDate = membreDate;
    // }

    public int getEmployee_id() {
        return this.employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public void setMembreDate(Date membreDate) {
        this.membreDate = membreDate;
    }

    @SuppressWarnings("exports")
    public Date getMembreDate() {
        return this.membreDate;
    }
}
