package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.DailyEvaluation;

import mmsnap.repository.DailyEvaluationRepository;
import mmsnap.repository.UserRepository;
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
 * REST controller for managing DailyEvaluation.
 */
@RestController
@RequestMapping("/api")
public class DailyEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(DailyEvaluationResource.class);

    private static final String ENTITY_NAME = "dailyEvaluation";

    private final DailyEvaluationRepository dailyEvaluationRepository;
    private final UserRepository            userRepository;

    public DailyEvaluationResource( DailyEvaluationRepository dailyEvaluationRepository, UserRepository userRepository ) {
        this.dailyEvaluationRepository = dailyEvaluationRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /daily-evaluations : Create a new dailyEvaluation.
     *
     * @param dailyEvaluation the dailyEvaluation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dailyEvaluation, or with status 400 (Bad Request) if the dailyEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/daily-evaluations")
    @Timed
    public ResponseEntity<DailyEvaluation> createDailyEvaluation(@Valid @RequestBody DailyEvaluation dailyEvaluation) throws URISyntaxException {
        log.debug("REST request to save DailyEvaluation : {}", dailyEvaluation);
        if (dailyEvaluation.getId() != null) {
            throw new BadRequestAlertException("A new dailyEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) )
        {
            dailyEvaluation.setUser( userRepository.findOneByLogin( SecurityUtils.getCurrentUserLogin() ).get() );
        }
        DailyEvaluation result = dailyEvaluationRepository.save(dailyEvaluation);
        return ResponseEntity.created(new URI("/api/daily-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /daily-evaluations : Updates an existing dailyEvaluation.
     *
     * @param dailyEvaluation the dailyEvaluation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dailyEvaluation,
     * or with status 400 (Bad Request) if the dailyEvaluation is not valid,
     * or with status 500 (Internal Server Error) if the dailyEvaluation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/daily-evaluations")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<DailyEvaluation> updateDailyEvaluation(@Valid @RequestBody DailyEvaluation dailyEvaluation) throws URISyntaxException {
        log.debug("REST request to update DailyEvaluation : {}", dailyEvaluation);
        if (dailyEvaluation.getId() == null) {
            return createDailyEvaluation(dailyEvaluation);
        }
        DailyEvaluation result = dailyEvaluationRepository.save(dailyEvaluation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dailyEvaluation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /daily-evaluations : get all the dailyEvaluations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dailyEvaluations in body
     */
    @GetMapping("/daily-evaluations")
    @Timed
    public ResponseEntity<List<DailyEvaluation>> getAllDailyEvaluations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DailyEvaluations");
        Page<DailyEvaluation> page = SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) ? dailyEvaluationRepository.findAll( pageable ) : dailyEvaluationRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/daily-evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /daily-evaluations/:id : get the "id" dailyEvaluation.
     *
     * @param id the id of the dailyEvaluation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dailyEvaluation, or with status 404 (Not Found)
     */
    @GetMapping("/daily-evaluations/{id}")
    @Timed
    public ResponseEntity<DailyEvaluation> getDailyEvaluation(@PathVariable Long id) {
        log.debug("REST request to get DailyEvaluation : {}", id);
        DailyEvaluation dailyEvaluation = dailyEvaluationRepository.findOne(id);
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) &&
            !dailyEvaluation.getUser().getLogin().contentEquals( SecurityUtils.getCurrentUserLogin() )
            )
        {
            dailyEvaluation = null;
        }return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dailyEvaluation));
    }

    /**
     * DELETE  /daily-evaluations/:id : delete the "id" dailyEvaluation.
     *
     * @param id the id of the dailyEvaluation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/daily-evaluations/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteDailyEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete DailyEvaluation : {}", id);
        dailyEvaluationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
