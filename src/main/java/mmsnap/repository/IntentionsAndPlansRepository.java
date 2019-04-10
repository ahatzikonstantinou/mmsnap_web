package mmsnap.repository;

import mmsnap.domain.IntentionsAndPlans;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the IntentionsAndPlans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntentionsAndPlansRepository extends JpaRepository<IntentionsAndPlans, Long> {

    @Query("select intentions_and_plans from IntentionsAndPlans intentions_and_plans where intentions_and_plans.user.login = ?#{principal.username}")
    List<IntentionsAndPlans> findByUserIsCurrentUser();

}
