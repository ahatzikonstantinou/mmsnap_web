package mmsnap.repository;

import mmsnap.domain.DailyEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select daily_evaluation from DailyEvaluation daily_evaluation where daily_evaluation.user.login = ?#{principal.username}")
    Page<DailyEvaluation> findByUserIsCurrentUser( Pageable pageable );
}
