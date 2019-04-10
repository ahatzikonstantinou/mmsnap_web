package mmsnap.repository;

import mmsnap.domain.HealthRisk;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the HealthRisk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthRiskRepository extends JpaRepository<HealthRisk, Long> {

    @Query("select health_risk from HealthRisk health_risk where health_risk.user.login = ?#{principal.username}")
    List<HealthRisk> findByUserIsCurrentUser();

}
