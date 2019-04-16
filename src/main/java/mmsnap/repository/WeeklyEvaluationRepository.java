package mmsnap.repository;

import mmsnap.domain.WeeklyEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the WeeklyEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeeklyEvaluationRepository extends JpaRepository<WeeklyEvaluation, Long> {

    @Query("select weekly_evaluation from WeeklyEvaluation weekly_evaluation where weekly_evaluation.user.login = ?#{principal.username}")
    List<WeeklyEvaluation> findByUserIsCurrentUser();

    @Query("select weekly_evaluation from WeeklyEvaluation weekly_evaluation where weekly_evaluation.user.login = ?#{principal.username}")
    Page<WeeklyEvaluation> findByUserIsCurrentUser( Pageable pageable );
}
