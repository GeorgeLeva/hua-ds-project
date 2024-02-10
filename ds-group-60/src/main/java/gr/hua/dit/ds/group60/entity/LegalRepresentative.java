package gr.hua.dit.ds.group60.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class LegalRepresentative{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legal_representative_id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="legal_representative_profile_id")
    private LegalRepresentativeProfile legalRepresentativeProfile;

    @OneToMany(mappedBy = "legalRepresentative", cascade = CascadeType.ALL)
    private List<Application> applications;

    @OneToMany(mappedBy = "legalRepresentative", cascade = CascadeType.PERSIST)
    private Set<Company> companies = new HashSet<>();

    @Transient
    private String companyName;

    public LegalRepresentative() {
    }

    public LegalRepresentative(Integer legalRepresentativeId, User user) {
        this.Id = legalRepresentativeId;
        this.user = user;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LegalRepresentativeProfile getLegalRepresentativeProfile() {
        return legalRepresentativeProfile;
    }

    public void setLegalRepresentativeProfile(LegalRepresentativeProfile legalRepresentativeProfile) {
        this.legalRepresentativeProfile = legalRepresentativeProfile;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addCompanyByName(String companyName) {
        Company company = new Company();
        company.setName(companyName);
        company.setLegalRepresentative(this); // Set the LegalRepresentative instance
        companies.add(company);
    }

}

