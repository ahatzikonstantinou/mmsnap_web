package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.domain.EQVas;

import mmsnap.repository.EQVasRepository;
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
 * REST controller for managing EQVas.
 */
@RestController
@RequestMapping("/api")
public class EQVasResource {

    private final Logger log = LoggerFactory.getLogger(EQVasResource.class);

    private static final String ENTITY_NAME = "eQVas";

    private final EQVasRepository eQVasRepository;
    private final UserRepository  userRepository;

    public EQVasResource( EQVasRepository eQVasRepository, UserRepository userRepository ) {
        this.eQVasRepository = eQVasRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /e-q-vas : Create a new eQVas.
     *
     * @param eQVas the eQVas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eQVas, or with status 400 (Bad Request) if the eQVas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-q-vas")
    @Timed
    public ResponseEntity<EQVas> createEQVas(@Valid @RequestBody EQVas eQVas) throws URISyntaxException {
        log.debug("REST request to save EQVas : {}", eQVas);
        if (eQVas.getId() != null) {
            throw new BadRequestAlertException("A new eQVas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) )
        {
            eQVas.setUser( userRepository.findOneByLogin( SecurityUtils.getCurrentUserLogin() ).get() );
        }
        EQVas result = eQVasRepository.save(eQVas);
        return ResponseEntity.created(new URI("/api/e-q-vas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-q-vas : Updates an existing eQVas.
     *
     * @param eQVas the eQVas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eQVas,
     * or with status 400 (Bad Request) if the eQVas is not valid,
     * or with status 500 (Internal Server Error) if the eQVas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-q-vas")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<EQVas> updateEQVas(@Valid @RequestBody EQVas eQVas) throws URISyntaxException {
        log.debug("REST request to update EQVas : {}", eQVas);
        if (eQVas.getId() == null) {
            return createEQVas(eQVas);
        }
        EQVas result = eQVasRepository.save(eQVas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eQVas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-q-vas : get all the eQVas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eQVas in body
     */
    @GetMapping("/e-q-vas")
    @Timed
    public ResponseEntity<List<EQVas>> getAllEQVas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EQVas");
        Page<EQVas> page = SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) ? eQVasRepository.findAll( pageable ) : eQVasRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/e-q-vas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /e-q-vas/:id : get the "id" eQVas.
     *
     * @param id the id of the eQVas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eQVas, or with status 404 (Not Found)
     */
    @GetMapping("/e-q-vas/{id}")
    @Timed
    public ResponseEntity<EQVas> getEQVas(@PathVariable Long id) {
        log.debug("REST request to get EQVas : {}", id);
        EQVas eQVas = eQVasRepository.findOne(id);
        if( !SecurityUtils.isCurrentUserInRole( AuthoritiesConstants.ADMIN ) &&
            !eQVas.getUser().getLogin().contentEquals( SecurityUtils.getCurrentUserLogin() )
            )
        {
            eQVas = null;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eQVas));
    }

    /**
     * DELETE  /e-q-vas/:id : delete the "id" eQVas.
     *
     * @param id the id of the eQVas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-q-vas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteEQVas(@PathVariable Long id) {
        log.debug("REST request to delete EQVas : {}", id);
        eQVasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
