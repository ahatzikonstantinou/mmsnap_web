package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.EQVas;
import mmsnap.repository.EQVasRepository;
import mmsnap.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mmsnap.domain.enumeration.AssessmentPhase;
/**
 * Test class for the EQVasResource REST controller.
 *
 * @see EQVasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class EQVasResourceIntTest {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final AssessmentPhase DEFAULT_PHASE = AssessmentPhase.INITIAL;
    private static final AssessmentPhase UPDATED_PHASE = AssessmentPhase.FINAL;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EQVasRepository eQVasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEQVasMockMvc;

    private EQVas eQVas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EQVasResource eQVasResource = new EQVasResource(eQVasRepository);
        this.restEQVasMockMvc = MockMvcBuilders.standaloneSetup(eQVasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EQVas createEntity(EntityManager em) {
        EQVas eQVas = new EQVas()
            .score(DEFAULT_SCORE)
            .phase(DEFAULT_PHASE)
            .date(DEFAULT_DATE);
        return eQVas;
    }

    @Before
    public void initTest() {
        eQVas = createEntity(em);
    }

    @Test
    @Transactional
    public void createEQVas() throws Exception {
        int databaseSizeBeforeCreate = eQVasRepository.findAll().size();

        // Create the EQVas
        restEQVasMockMvc.perform(post("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eQVas)))
            .andExpect(status().isCreated());

        // Validate the EQVas in the database
        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeCreate + 1);
        EQVas testEQVas = eQVasList.get(eQVasList.size() - 1);
        assertThat(testEQVas.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testEQVas.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testEQVas.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEQVasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eQVasRepository.findAll().size();

        // Create the EQVas with an existing ID
        eQVas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEQVasMockMvc.perform(post("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eQVas)))
            .andExpect(status().isBadRequest());

        // Validate the EQVas in the database
        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = eQVasRepository.findAll().size();
        // set the field null
        eQVas.setScore(null);

        // Create the EQVas, which fails.

        restEQVasMockMvc.perform(post("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eQVas)))
            .andExpect(status().isBadRequest());

        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eQVasRepository.findAll().size();
        // set the field null
        eQVas.setDate(null);

        // Create the EQVas, which fails.

        restEQVasMockMvc.perform(post("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eQVas)))
            .andExpect(status().isBadRequest());

        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEQVas() throws Exception {
        // Initialize the database
        eQVasRepository.saveAndFlush(eQVas);

        // Get all the eQVasList
        restEQVasMockMvc.perform(get("/api/e-q-vas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eQVas.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEQVas() throws Exception {
        // Initialize the database
        eQVasRepository.saveAndFlush(eQVas);

        // Get the eQVas
        restEQVasMockMvc.perform(get("/api/e-q-vas/{id}", eQVas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eQVas.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEQVas() throws Exception {
        // Get the eQVas
        restEQVasMockMvc.perform(get("/api/e-q-vas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEQVas() throws Exception {
        // Initialize the database
        eQVasRepository.saveAndFlush(eQVas);
        int databaseSizeBeforeUpdate = eQVasRepository.findAll().size();

        // Update the eQVas
        EQVas updatedEQVas = eQVasRepository.findOne(eQVas.getId());
        updatedEQVas
            .score(UPDATED_SCORE)
            .phase(UPDATED_PHASE)
            .date(UPDATED_DATE);

        restEQVasMockMvc.perform(put("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEQVas)))
            .andExpect(status().isOk());

        // Validate the EQVas in the database
        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeUpdate);
        EQVas testEQVas = eQVasList.get(eQVasList.size() - 1);
        assertThat(testEQVas.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testEQVas.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testEQVas.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEQVas() throws Exception {
        int databaseSizeBeforeUpdate = eQVasRepository.findAll().size();

        // Create the EQVas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEQVasMockMvc.perform(put("/api/e-q-vas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eQVas)))
            .andExpect(status().isCreated());

        // Validate the EQVas in the database
        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEQVas() throws Exception {
        // Initialize the database
        eQVasRepository.saveAndFlush(eQVas);
        int databaseSizeBeforeDelete = eQVasRepository.findAll().size();

        // Get the eQVas
        restEQVasMockMvc.perform(delete("/api/e-q-vas/{id}", eQVas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EQVas> eQVasList = eQVasRepository.findAll();
        assertThat(eQVasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EQVas.class);
        EQVas eQVas1 = new EQVas();
        eQVas1.setId(1L);
        EQVas eQVas2 = new EQVas();
        eQVas2.setId(eQVas1.getId());
        assertThat(eQVas1).isEqualTo(eQVas2);
        eQVas2.setId(2L);
        assertThat(eQVas1).isNotEqualTo(eQVas2);
        eQVas1.setId(null);
        assertThat(eQVas1).isNotEqualTo(eQVas2);
    }
}
