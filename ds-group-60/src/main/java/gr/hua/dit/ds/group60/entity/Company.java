package gr.hua.dit.ds.group60.entity;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Integer id;

    @Column
    private String name;

    @Column
    private String purpose;

    @Column
    private String operatingStatute;

    @Column
    private String HQ;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "legal_representative_id")
    private LegalRepresentative legalRepresentative;

    public Company() {
    }

    public Company(Integer id, String name, String purpose, String operatingStatute, String HQ, LegalRepresentative legalRepresentative) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
        this.operatingStatute = operatingStatute;
        this.HQ = HQ;
        this.legalRepresentative = legalRepresentative;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LegalRepresentative getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(LegalRepresentative legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purpose='" + purpose + '\'' +
                ", operatingStatute='" + operatingStatute + '\'' +
                ", HQ='" + HQ + '\'' +
                ", legalRepresentative=" + legalRepresentative +
                '}';
    }
}
