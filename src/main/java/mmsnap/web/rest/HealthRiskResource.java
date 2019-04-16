package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.HealthRisk;

import mmsnap.repository.HealthRiskRepository;
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
 * REST controller for managing HealthRisk.
 */
@RestController
@RequestMapping("/api")
public class HealthRiskResource {

    private final Logger log = LoggerFactory.getLogger(HealthRiskResource.class);

    private static final String ENTITY_NAME = "healthRisk";

    private final HealthRiskRepository healthRiskRepository;
    private final UserRepository       userRepository;

    public HealthRiskResource( HealthRiskRepository healthRiskRepository, UserRepository userRepository ) {
        this.healthRiskRepository = healthRiskRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /health-risks : Create a new healthRisk.
     *
     * @param healthRisk the healthRisk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new healthRisk, or with status 400 (Bad Request) if the healthRisk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/health-risks")
    @Timed
    public ResponseEntity<HealthRisk> createHealthRisk(@Valid @RequestBody HealthRisk healthRisk) throws URISyntaxException {
        log.debug("REST request to save HealthRisk : {}", healthRisk);
        if (healthRisk.getId() != null) {
            throw new BadRequestAlertException("A new healthRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) )
        {
            healthRisk.setUser( userRepository.findOneByLogin( SecurityUtils.getCurrentUserLogin() ).get() );
        }
        HealthRisk result = healthRiskRepository.save(healthRisk);
        return ResponseEntity.created(new URI("/api/health-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /health-risks : Updates an existing healthRisk.
     *
     * @param healthRisk the healthRisk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated healthRisk,
     * or with status 400 (Bad Request) if the healthRisk is not valid,
     * or with status 500 (Internal Server Error) if the healthRisk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/health-risks")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<HealthRisk> updateHealthRisk(@Valid @RequestBody HealthRisk healthRisk) throws URISyntaxException {
        log.debug("REST request to update HealthRisk : {}", healthRisk);
        if (healthRisk.getId() == null) {
            return createHealthRisk(healthRisk);
        }
        HealthRisk result = healthRiskRepository.save(healthRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, healthRisk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /health-risks : get all the healthRisks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of healthRisks in body
     */
    @GetMapping("/health-risks")
    @Timed
    public ResponseEntity<List<HealthRisk>> getAllHealthRisks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HealthRisks");
        Page<HealthRisk> page = SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) ? healthRiskRepository.findAll( pageable ) : healthRiskRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/health-risks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /health-risks/:id : get the "id" healthRisk.
     *
     * @param id the id of the healthRisk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the healthRisk, or with status 404 (Not Found)
     */
    @GetMapping("/health-risks/{id}")
    @Timed
    public ResponseEntity<HealthRisk> getHealthRisk(@PathVariable Long id) {
        log.debug("REST request to get HealthRisk : {}", id);
        HealthRisk healthRisk = healthRiskRepository.findOne(id);
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) &&
            !healthRisk.getUser().getLogin().contentEquals( SecurityUtils.getCurrentUserLogin() )
            )
        {
            healthRisk = null;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthRisk));
    }

    /**
     * DELETE  /health-risks/:id : delete the "id" healthRisk.
     *
     * @param id the id of the healthRisk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/health-risks/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteHealthRisk(@PathVariable Long id) {
        log.debug("REST request to delete HealthRisk : {}", id);
        healthRiskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
