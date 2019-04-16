package mmsnap.repository;

import mmsnap.domain.EQVas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the EQVas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EQVasRepository extends JpaRepository<EQVas, Long> {

    @Query("select eq_vas from EQVas eq_vas where eq_vas.user.login = ?#{principal.username}")
    List<EQVas> findByUserIsCurrentUser();

    @Query("select eq_vas from EQVas eq_vas where eq_vas.user.login = ?#{principal.username}")
    Page<EQVas> findByUserIsCurrentUser( Pageable pageable );
}
