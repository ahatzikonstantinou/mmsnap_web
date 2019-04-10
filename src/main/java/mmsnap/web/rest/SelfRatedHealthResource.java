package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.SelfRatedHealth;

import mmsnap.repository.SelfRatedHealthRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SelfRatedHealth.
 */
@RestController
@RequestMapping("/api")
public class SelfRatedHealthResource {

    private final Logger log = LoggerFactory.getLogger(SelfRatedHealthResource.class);

    private static final String ENTITY_NAME = "selfRatedHealth";

    private final SelfRatedHealthRepository selfRatedHealthRepository;

    public SelfRatedHealthResource(SelfRatedHealthRepository selfRatedHealthRepository) {
        this.selfRatedHealthRepository = selfRatedHealthRepository;
    }

    /**
     * POST  /self-rated-healths : Create a new selfRatedHealth.
     *
     * @param selfRatedHealth the selfRatedHealth to create
     * @return the ResponseEntity with status 201 (Created) and with body the new selfRatedHealth, or with status 400 (Bad Request) if the selfRatedHealth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/self-rated-healths")
    @Timed
    public ResponseEntity<SelfRatedHealth> createSelfRatedHealth(@Valid @RequestBody SelfRatedHealth selfRatedHealth) throws URISyntaxException {
        log.debug("REST request to save SelfRatedHealth : {}", selfRatedHealth);
        if (selfRatedHealth.getId() != null) {
            throw new BadRequestAlertException("A new selfRatedHealth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelfRatedHealth result = selfRatedHealthRepository.save(selfRatedHealth);
        return ResponseEntity.created(new URI("/api/self-rated-healths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /self-rated-healths : Updates an existing selfRatedHealth.
     *
     * @param selfRatedHealth the selfRatedHealth to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated selfRatedHealth,
     * or with status 400 (Bad Request) if the selfRatedHealth is not valid,
     * or with status 500 (Internal Server Error) if the selfRatedHealth couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/self-rated-healths")
    @Timed
    public ResponseEntity<SelfRatedHealth> updateSelfRatedHealth(@Valid @RequestBody SelfRatedHealth selfRatedHealth) throws URISyntaxException {
        log.debug("REST request to update SelfRatedHealth : {}", selfRatedHealth);
        if (selfRatedHealth.getId() == null) {
            return createSelfRatedHealth(selfRatedHealth);
        }
        SelfRatedHealth result = selfRatedHealthRepository.save(selfRatedHealth);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selfRatedHealth.getId().toString()))
            .body(result);
    }

    /**
     * GET  /self-rated-healths : get all the selfRatedHealths.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of selfRatedHealths in body
     */
    @GetMapping("/self-rated-healths")
    @Timed
    public ResponseEntity<List<SelfRatedHealth>> getAllSelfRatedHealths(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SelfRatedHealths");
        Page<SelfRatedHealth> page = selfRatedHealthRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/self-rated-healths");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /self-rated-healths/:id : get the "id" selfRatedHealth.
     *
     * @param id the id of the selfRatedHealth to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the selfRatedHealth, or with status 404 (Not Found)
     */
    @GetMapping("/self-rated-healths/{id}")
    @Timed
    public ResponseEntity<SelfRatedHealth> getSelfRatedHealth(@PathVariable Long id) {
        log.debug("REST request to get SelfRatedHealth : {}", id);
        SelfRatedHealth selfRatedHealth = selfRatedHealthRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(selfRatedHealth));
    }

    /**
     * DELETE  /self-rated-healths/:id : delete the "id" selfRatedHealth.
     *
     * @param id the id of the selfRatedHealth to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/self-rated-healths/{id}")
    @Timed
    public ResponseEntity<Void> deleteSelfRatedHealth(@PathVariable Long id) {
        log.debug("REST request to delete SelfRatedHealth : {}", id);
        selfRatedHealthRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
