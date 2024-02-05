package gr.hua.dit.ds.group60.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class LegalRepresentative{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legal_representative_id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="legal_representative_profile_id")
    private LegalRepresentativeProfile legalRepresentativeProfile;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name="application_legal_representative",
            joinColumns = @JoinColumn(name="legal_representative_id"),
            inverseJoinColumns = @JoinColumn(name="application_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"legal_representative_id", "application_id"})}
    )
    private List<Application> applications;

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public LegalRepresentative() {
    }

    public LegalRepresentative(Integer legalRepresentativeId, User user, String company) {
        this.Id = legalRepresentativeId;
        this.user = user;
        this.company = company;
    }

    public Integer getLegalRepresentativeId() {
        return Id;
    }

    public void setLegalRepresentativeId(Integer legalRepresentativeId) {
        this.Id = legalRepresentativeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LegalRepresentativeProfile getLegalRepresentativeProfile() {
        return legalRepresentativeProfile;
    }

    public void setLegalRepresentativeProfile(LegalRepresentativeProfile legalRepresentativeProfile) {
        this.legalRepresentativeProfile = legalRepresentativeProfile;
    }
}
