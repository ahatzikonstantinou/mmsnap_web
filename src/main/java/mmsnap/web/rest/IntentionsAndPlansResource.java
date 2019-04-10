package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.IntentionsAndPlans;

import mmsnap.repository.IntentionsAndPlansRepository;
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
 * REST controller for managing IntentionsAndPlans.
 */
@RestController
@RequestMapping("/api")
public class IntentionsAndPlansResource {

    private final Logger log = LoggerFactory.getLogger(IntentionsAndPlansResource.class);

    private static final String ENTITY_NAME = "intentionsAndPlans";

    private final IntentionsAndPlansRepository intentionsAndPlansRepository;

    public IntentionsAndPlansResource(IntentionsAndPlansRepository intentionsAndPlansRepository) {
        this.intentionsAndPlansRepository = intentionsAndPlansRepository;
    }

    /**
     * POST  /intentions-and-plans : Create a new intentionsAndPlans.
     *
     * @param intentionsAndPlans the intentionsAndPlans to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intentionsAndPlans, or with status 400 (Bad Request) if the intentionsAndPlans has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intentions-and-plans")
    @Timed
    public ResponseEntity<IntentionsAndPlans> createIntentionsAndPlans(@Valid @RequestBody IntentionsAndPlans intentionsAndPlans) throws URISyntaxException {
        log.debug("REST request to save IntentionsAndPlans : {}", intentionsAndPlans);
        if (intentionsAndPlans.getId() != null) {
            throw new BadRequestAlertException("A new intentionsAndPlans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntentionsAndPlans result = intentionsAndPlansRepository.save(intentionsAndPlans);
        return ResponseEntity.created(new URI("/api/intentions-and-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intentions-and-plans : Updates an existing intentionsAndPlans.
     *
     * @param intentionsAndPlans the intentionsAndPlans to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intentionsAndPlans,
     * or with status 400 (Bad Request) if the intentionsAndPlans is not valid,
     * or with status 500 (Internal Server Error) if the intentionsAndPlans couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intentions-and-plans")
    @Timed
    public ResponseEntity<IntentionsAndPlans> updateIntentionsAndPlans(@Valid @RequestBody IntentionsAndPlans intentionsAndPlans) throws URISyntaxException {
        log.debug("REST request to update IntentionsAndPlans : {}", intentionsAndPlans);
        if (intentionsAndPlans.getId() == null) {
            return createIntentionsAndPlans(intentionsAndPlans);
        }
        IntentionsAndPlans result = intentionsAndPlansRepository.save(intentionsAndPlans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intentionsAndPlans.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intentions-and-plans : get all the intentionsAndPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of intentionsAndPlans in body
     */
    @GetMapping("/intentions-and-plans")
    @Timed
    public ResponseEntity<List<IntentionsAndPlans>> getAllIntentionsAndPlans(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IntentionsAndPlans");
        Page<IntentionsAndPlans> page = intentionsAndPlansRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/intentions-and-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /intentions-and-plans/:id : get the "id" intentionsAndPlans.
     *
     * @param id the id of the intentionsAndPlans to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intentionsAndPlans, or with status 404 (Not Found)
     */
    @GetMapping("/intentions-and-plans/{id}")
    @Timed
    public ResponseEntity<IntentionsAndPlans> getIntentionsAndPlans(@PathVariable Long id) {
        log.debug("REST request to get IntentionsAndPlans : {}", id);
        IntentionsAndPlans intentionsAndPlans = intentionsAndPlansRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(intentionsAndPlans));
    }

    /**
     * DELETE  /intentions-and-plans/:id : delete the "id" intentionsAndPlans.
     *
     * @param id the id of the intentionsAndPlans to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intentions-and-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntentionsAndPlans(@PathVariable Long id) {
        log.debug("REST request to delete IntentionsAndPlans : {}", id);
        intentionsAndPlansRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
