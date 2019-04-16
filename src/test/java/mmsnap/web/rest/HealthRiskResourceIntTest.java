package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.HealthRisk;
import mmsnap.repository.HealthRiskRepository;
import mmsnap.repository.UserRepository;
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
 * Test class for the HealthRiskResource REST controller.
 *
 * @see HealthRiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class HealthRiskResourceIntTest {

    private static final Boolean DEFAULT_SMOKING = false;
    private static final Boolean UPDATED_SMOKING = true;

    private static final Boolean DEFAULT_PHYSICAL_ACTIVITY = false;
    private static final Boolean UPDATED_PHYSICAL_ACTIVITY = true;

    private static final Boolean DEFAULT_DIET = false;
    private static final Boolean UPDATED_DIET = true;

    private static final Boolean DEFAULT_ALCOHOL = false;
    private static final Boolean UPDATED_ALCOHOL = true;

    private static final AssessmentPhase DEFAULT_PHASE = AssessmentPhase.INITIAL;
    private static final AssessmentPhase UPDATED_PHASE = AssessmentPhase.FINAL;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HealthRiskRepository healthRiskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHealthRiskMockMvc;

    private HealthRisk healthRisk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthRiskResource healthRiskResource = new HealthRiskResource( healthRiskRepository, userRepository );
        this.restHealthRiskMockMvc = MockMvcBuilders.standaloneSetup(healthRiskResource)
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
    public static HealthRisk createEntity(EntityManager em) {
        HealthRisk healthRisk = new HealthRisk()
            .smoking(DEFAULT_SMOKING)
            .physicalActivity(DEFAULT_PHYSICAL_ACTIVITY)
            .diet(DEFAULT_DIET)
            .alcohol(DEFAULT_ALCOHOL)
            .phase(DEFAULT_PHASE)
            .date(DEFAULT_DATE);
        return healthRisk;
    }

    @Before
    public void initTest() {
        healthRisk = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthRisk() throws Exception {
        int databaseSizeBeforeCreate = healthRiskRepository.findAll().size();

        // Create the HealthRisk
        restHealthRiskMockMvc.perform(post("/api/health-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRisk)))
            .andExpect(status().isCreated());

        // Validate the HealthRisk in the database
        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeCreate + 1);
        HealthRisk testHealthRisk = healthRiskList.get(healthRiskList.size() - 1);
        assertThat(testHealthRisk.isSmoking()).isEqualTo(DEFAULT_SMOKING);
        assertThat(testHealthRisk.isPhysicalActivity()).isEqualTo(DEFAULT_PHYSICAL_ACTIVITY);
        assertThat(testHealthRisk.isDiet()).isEqualTo(DEFAULT_DIET);
        assertThat(testHealthRisk.isAlcohol()).isEqualTo(DEFAULT_ALCOHOL);
        assertThat(testHealthRisk.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testHealthRisk.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createHealthRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthRiskRepository.findAll().size();

        // Create the HealthRisk with an existing ID
        healthRisk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthRiskMockMvc.perform(post("/api/health-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRisk)))
            .andExpect(status().isBadRequest());

        // Validate the HealthRisk in the database
        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthRiskRepository.findAll().size();
        // set the field null
        healthRisk.setDate(null);

        // Create the HealthRisk, which fails.

        restHealthRiskMockMvc.perform(post("/api/health-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRisk)))
            .andExpect(status().isBadRequest());

        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHealthRisks() throws Exception {
        // Initialize the database
        healthRiskRepository.saveAndFlush(healthRisk);

        // Get all the healthRiskList
        restHealthRiskMockMvc.perform(get("/api/health-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthRisk.getId().intValue())))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].physicalActivity").value(hasItem(DEFAULT_PHYSICAL_ACTIVITY.booleanValue())))
            .andExpect(jsonPath("$.[*].diet").value(hasItem(DEFAULT_DIET.booleanValue())))
            .andExpect(jsonPath("$.[*].alcohol").value(hasItem(DEFAULT_ALCOHOL.booleanValue())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getHealthRisk() throws Exception {
        // Initialize the database
        healthRiskRepository.saveAndFlush(healthRisk);

        // Get the healthRisk
        restHealthRiskMockMvc.perform(get("/api/health-risks/{id}", healthRisk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthRisk.getId().intValue()))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING.booleanValue()))
            .andExpect(jsonPath("$.physicalActivity").value(DEFAULT_PHYSICAL_ACTIVITY.booleanValue()))
            .andExpect(jsonPath("$.diet").value(DEFAULT_DIET.booleanValue()))
            .andExpect(jsonPath("$.alcohol").value(DEFAULT_ALCOHOL.booleanValue()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthRisk() throws Exception {
        // Get the healthRisk
        restHealthRiskMockMvc.perform(get("/api/health-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthRisk() throws Exception {
        // Initialize the database
        healthRiskRepository.saveAndFlush(healthRisk);
        int databaseSizeBeforeUpdate = healthRiskRepository.findAll().size();

        // Update the healthRisk
        HealthRisk updatedHealthRisk = healthRiskRepository.findOne(healthRisk.getId());
        updatedHealthRisk
            .smoking(UPDATED_SMOKING)
            .physicalActivity(UPDATED_PHYSICAL_ACTIVITY)
            .diet(UPDATED_DIET)
            .alcohol(UPDATED_ALCOHOL)
            .phase(UPDATED_PHASE)
            .date(UPDATED_DATE);

        restHealthRiskMockMvc.perform(put("/api/health-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthRisk)))
            .andExpect(status().isOk());

        // Validate the HealthRisk in the database
        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeUpdate);
        HealthRisk testHealthRisk = healthRiskList.get(healthRiskList.size() - 1);
        assertThat(testHealthRisk.isSmoking()).isEqualTo(UPDATED_SMOKING);
        assertThat(testHealthRisk.isPhysicalActivity()).isEqualTo(UPDATED_PHYSICAL_ACTIVITY);
        assertThat(testHealthRisk.isDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testHealthRisk.isAlcohol()).isEqualTo(UPDATED_ALCOHOL);
        assertThat(testHealthRisk.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testHealthRisk.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthRisk() throws Exception {
        int databaseSizeBeforeUpdate = healthRiskRepository.findAll().size();

        // Create the HealthRisk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHealthRiskMockMvc.perform(put("/api/health-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRisk)))
            .andExpect(status().isCreated());

        // Validate the HealthRisk in the database
        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHealthRisk() throws Exception {
        // Initialize the database
        healthRiskRepository.saveAndFlush(healthRisk);
        int databaseSizeBeforeDelete = healthRiskRepository.findAll().size();

        // Get the healthRisk
        restHealthRiskMockMvc.perform(delete("/api/health-risks/{id}", healthRisk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HealthRisk> healthRiskList = healthRiskRepository.findAll();
        assertThat(healthRiskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthRisk.class);
        HealthRisk healthRisk1 = new HealthRisk();
        healthRisk1.setId(1L);
        HealthRisk healthRisk2 = new HealthRisk();
        healthRisk2.setId(healthRisk1.getId());
        assertThat(healthRisk1).isEqualTo(healthRisk2);
        healthRisk2.setId(2L);
        assertThat(healthRisk1).isNotEqualTo(healthRisk2);
        healthRisk1.setId(null);
        assertThat(healthRisk1).isNotEqualTo(healthRisk2);
    }
}
