package mmsnap.repository;

import mmsnap.domain.SelfEfficacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SelfEfficacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelfEfficacyRepository extends JpaRepository<SelfEfficacy, Long> {

    @Query("select self_efficacy from SelfEfficacy self_efficacy where self_efficacy.user.login = ?#{principal.username}")
    List<SelfEfficacy> findByUserIsCurrentUser();

    @Query("select self_efficacy from SelfEfficacy self_efficacy where self_efficacy.user.login = ?#{principal.username}")
    Page<SelfEfficacy> findByUserIsCurrentUser( Pageable pageable );
}
