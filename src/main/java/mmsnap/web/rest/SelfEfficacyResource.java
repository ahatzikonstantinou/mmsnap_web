package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.SelfEfficacy;

import mmsnap.repository.SelfEfficacyRepository;
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
 * REST controller for managing SelfEfficacy.
 */
@RestController
@RequestMapping("/api")
public class SelfEfficacyResource {

    private final Logger log = LoggerFactory.getLogger(SelfEfficacyResource.class);

    private static final String ENTITY_NAME = "selfEfficacy";

    private final SelfEfficacyRepository selfEfficacyRepository;

    public SelfEfficacyResource(SelfEfficacyRepository selfEfficacyRepository) {
        this.selfEfficacyRepository = selfEfficacyRepository;
    }

    /**
     * POST  /self-efficacies : Create a new selfEfficacy.
     *
     * @param selfEfficacy the selfEfficacy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new selfEfficacy, or with status 400 (Bad Request) if the selfEfficacy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/self-efficacies")
    @Timed
    public ResponseEntity<SelfEfficacy> createSelfEfficacy(@Valid @RequestBody SelfEfficacy selfEfficacy) throws URISyntaxException {
        log.debug("REST request to save SelfEfficacy : {}", selfEfficacy);
        if (selfEfficacy.getId() != null) {
            throw new BadRequestAlertException("A new selfEfficacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelfEfficacy result = selfEfficacyRepository.save(selfEfficacy);
        return ResponseEntity.created(new URI("/api/self-efficacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /self-efficacies : Updates an existing selfEfficacy.
     *
     * @param selfEfficacy the selfEfficacy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated selfEfficacy,
     * or with status 400 (Bad Request) if the selfEfficacy is not valid,
     * or with status 500 (Internal Server Error) if the selfEfficacy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/self-efficacies")
    @Timed
    public ResponseEntity<SelfEfficacy> updateSelfEfficacy(@Valid @RequestBody SelfEfficacy selfEfficacy) throws URISyntaxException {
        log.debug("REST request to update SelfEfficacy : {}", selfEfficacy);
        if (selfEfficacy.getId() == null) {
            return createSelfEfficacy(selfEfficacy);
        }
        SelfEfficacy result = selfEfficacyRepository.save(selfEfficacy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selfEfficacy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /self-efficacies : get all the selfEfficacies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of selfEfficacies in body
     */
    @GetMapping("/self-efficacies")
    @Timed
    public ResponseEntity<List<SelfEfficacy>> getAllSelfEfficacies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SelfEfficacies");
        Page<SelfEfficacy> page = selfEfficacyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/self-efficacies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /self-efficacies/:id : get the "id" selfEfficacy.
     *
     * @param id the id of the selfEfficacy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the selfEfficacy, or with status 404 (Not Found)
     */
    @GetMapping("/self-efficacies/{id}")
    @Timed
    public ResponseEntity<SelfEfficacy> getSelfEfficacy(@PathVariable Long id) {
        log.debug("REST request to get SelfEfficacy : {}", id);
        SelfEfficacy selfEfficacy = selfEfficacyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(selfEfficacy));
    }

    /**
     * DELETE  /self-efficacies/:id : delete the "id" selfEfficacy.
     *
     * @param id the id of the selfEfficacy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/self-efficacies/{id}")
    @Timed
    public ResponseEntity<Void> deleteSelfEfficacy(@PathVariable Long id) {
        log.debug("REST request to delete SelfEfficacy : {}", id);
        selfEfficacyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
