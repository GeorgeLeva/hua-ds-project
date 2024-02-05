package gr.hua.dit.ds.group60.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "application_id")
    private int id;

    @Column
    private String company;

    @Column
    private String purpose;

    @Column
    private String operatingStatute;

    @Column
    private String HQ;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name="application_legal_representative",
            joinColumns = @JoinColumn(name="application_id"),
            inverseJoinColumns = @JoinColumn(name="legal_presentative_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"legal_representative_id", "application_id"})}
    )
    private List<LegalRepresentative> legalRepresentatives;

    public Application() {
    }

    public Application(String company, String purpose, String operatingStatute, String HQ) {
        this.company = company;
        this.purpose = purpose;
        this.operatingStatute = operatingStatute;
        this.HQ = HQ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOperatingStatute() {
        return operatingStatute;
    }

    public void setOperatingStatute(String operatingStatute) {
        this.operatingStatute = operatingStatute;
    }

    public String getHQ() {
        return HQ;
    }

    public void setHQ(String HQ) {
        this.HQ = HQ;
    }

    public List<LegalRepresentative> getLegalRepresentatives() {
        return legalRepresentatives;
    }

    public void setLegalRepresentatives(List<LegalRepresentative> legalRepresentatives) {
        this.legalRepresentatives = legalRepresentatives;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", purpose='" + purpose + '\'' +
                ", operatingStatute='" + operatingStatute + '\'' +
                ", HQ='" + HQ + '\'' +
                '}';
    }
}
