package mmsnap.repository;

import mmsnap.domain.DailyEvaluation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the DailyEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyEvaluationRepository extends JpaRepository<DailyEvaluation, Long> {

    @Query("select daily_evaluation from DailyEvaluation daily_evaluation where daily_evaluation.user.login = ?#{principal.username}")
    List<DailyEvaluation> findByUserIsCurrentUser();

}
