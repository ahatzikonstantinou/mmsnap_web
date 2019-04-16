package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.WeeklyEvaluation;

import mmsnap.repository.UserRepository;
import mmsnap.repository.WeeklyEvaluationRepository;
import mmsnap.security.AuthoritiesConstants;
import mmsnap.security.SecurityUtils;
import mmsnap.web.rest.errors.BadRequestAlertException;
import mmsnap.web.rest.util.HeaderUtil;
import mmsnap.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WeeklyEvaluation.
 */
@RestController
@RequestMapping("/api")
public class WeeklyEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(WeeklyEvaluationResource.class);

    private static final String ENTITY_NAME = "weeklyEvaluation";

    private final WeeklyEvaluationRepository weeklyEvaluationRepository;
    private final UserRepository             userRepository;

    public WeeklyEvaluationResource( WeeklyEvaluationRepository weeklyEvaluationRepository, UserRepository userRepository ) {
        this.weeklyEvaluationRepository = weeklyEvaluationRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /weekly-evaluations : Create a new weeklyEvaluation.
     *
     * @param weeklyEvaluation the weeklyEvaluation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weeklyEvaluation, or with status 400 (Bad Request) if the weeklyEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weekly-evaluations")
    @Timed
    public ResponseEntity<WeeklyEvaluation> createWeeklyEvaluation(@Valid @RequestBody WeeklyEvaluation weeklyEvaluation) throws URISyntaxException {
        log.debug("REST request to save WeeklyEvaluation : {}", weeklyEvaluation);
        if (weeklyEvaluation.getId() != null) {
            throw new BadRequestAlertException("A new weeklyEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) )
        {
            weeklyEvaluation.setUser( userRepository.findOneByLogin( SecurityUtils.getCurrentUserLogin() ).get() );
        }
        WeeklyEvaluation result = weeklyEvaluationRepository.save(weeklyEvaluation);
        return ResponseEntity.created(new URI("/api/weekly-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weekly-evaluations : Updates an existing weeklyEvaluation.
     *
     * @param weeklyEvaluation the weeklyEvaluation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weeklyEvaluation,
     * or with status 400 (Bad Request) if the weeklyEvaluation is not valid,
     * or with status 500 (Internal Server Error) if the weeklyEvaluation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weekly-evaluations")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<WeeklyEvaluation> updateWeeklyEvaluation(@Valid @RequestBody WeeklyEvaluation weeklyEvaluation) throws URISyntaxException {
        log.debug("REST request to update WeeklyEvaluation : {}", weeklyEvaluation);
        if (weeklyEvaluation.getId() == null) {
            return createWeeklyEvaluation(weeklyEvaluation);
        }
        WeeklyEvaluation result = weeklyEvaluationRepository.save(weeklyEvaluation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weeklyEvaluation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weekly-evaluations : get all the weeklyEvaluations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weeklyEvaluations in body
     */
    @GetMapping("/weekly-evaluations")
    @Timed
    public ResponseEntity<List<WeeklyEvaluation>> getAllWeeklyEvaluations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WeeklyEvaluations");
        Page<WeeklyEvaluation> page = SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) ? weeklyEvaluationRepository.findAll( pageable ) : weeklyEvaluationRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weekly-evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /weekly-evaluations/:id : get the "id" weeklyEvaluation.
     *
     * @param id the id of the weeklyEvaluation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weeklyEvaluation, or with status 404 (Not Found)
     */
    @GetMapping("/weekly-evaluations/{id}")
    @Timed
    public ResponseEntity<WeeklyEvaluation> getWeeklyEvaluation(@PathVariable Long id) {
        log.debug("REST request to get WeeklyEvaluation : {}", id);
        WeeklyEvaluation weeklyEvaluation = weeklyEvaluationRepository.findOne(id);
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) &&
            !weeklyEvaluation.getUser().getLogin().contentEquals( SecurityUtils.getCurrentUserLogin() )
            )
        {
            weeklyEvaluation = null;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weeklyEvaluation));
    }

    /**
     * DELETE  /weekly-evaluations/:id : delete the "id" weeklyEvaluation.
     *
     * @param id the id of the weeklyEvaluation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weekly-evaluations/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteWeeklyEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete WeeklyEvaluation : {}", id);
        weeklyEvaluationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
