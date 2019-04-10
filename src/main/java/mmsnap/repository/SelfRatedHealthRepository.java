package mmsnap.repository;

import mmsnap.domain.SelfRatedHealth;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SelfRatedHealth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelfRatedHealthRepository extends JpaRepository<SelfRatedHealth, Long> {

    @Query("select self_rated_health from SelfRatedHealth self_rated_health where self_rated_health.user.login = ?#{principal.username}")
    List<SelfRatedHealth> findByUserIsCurrentUser();

}
