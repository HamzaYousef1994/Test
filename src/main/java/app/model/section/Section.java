package app.model.section;


import javax.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String titleEn;

    private String titleAr;

    @Column(columnDefinition = "TEXT")
    private String briefEn;

    @Column(columnDefinition = "TEXT")
    private String briefAr;


    public Section() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getBriefEn() {
        return briefEn;
    }

    public void setBriefEn(String briefEn) {
        this.briefEn = briefEn;
    }

    public String getBriefAr() {
        return briefAr;
    }

    public void setBriefAr(String briefAr) {
        this.briefAr = briefAr;
    }
}
