package gr.hua.dit.ds.group60.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_representative_id")
    private LegalRepresentative legalRepresentative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Application() {
    }

    public Application(Integer id, LegalRepresentative legalRepresentative, Company company) {
        this.id = id;
        this.legalRepresentative = legalRepresentative;
        this.company = company;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LegalRepresentative getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(LegalRepresentative legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", legalRepresentative=" + legalRepresentative +
                ", company=" + company +
                '}';
    }
}
