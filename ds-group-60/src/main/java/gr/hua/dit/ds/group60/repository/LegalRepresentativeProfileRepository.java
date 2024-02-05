package gr.hua.dit.ds.group60.repository;

import gr.hua.dit.ds.group60.entity.LegalRepresentativeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path= "legal_representative_profile")
public interface LegalRepresentativeProfileRepository extends JpaRepository<LegalRepresentativeProfile, Integer> {

}
