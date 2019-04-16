package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.SelfEfficacy;
import mmsnap.repository.SelfEfficacyRepository;
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
 * Test class for the SelfEfficacyResource REST controller.
 *
 * @see SelfEfficacyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class SelfEfficacyResourceIntTest {

    private static final Boolean DEFAULT_HEALTHIER_LIFESTYLE = false;
    private static final Boolean UPDATED_HEALTHIER_LIFESTYLE = true;

    private static final Boolean DEFAULT_COMPLETE_BEHAVIOUR_GOALS = false;
    private static final Boolean UPDATED_COMPLETE_BEHAVIOUR_GOALS = true;

    private static final Boolean DEFAULT_MANAGE_MULTIMORBIDITY = false;
    private static final Boolean UPDATED_MANAGE_MULTIMORBIDITY = true;

    private static final AssessmentPhase DEFAULT_PHASE = AssessmentPhase.INITIAL;
    private static final AssessmentPhase UPDATED_PHASE = AssessmentPhase.FINAL;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SelfEfficacyRepository selfEfficacyRepository;

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

    private MockMvc restSelfEfficacyMockMvc;

    private SelfEfficacy selfEfficacy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelfEfficacyResource selfEfficacyResource = new SelfEfficacyResource( selfEfficacyRepository, userRepository );
        this.restSelfEfficacyMockMvc = MockMvcBuilders.standaloneSetup(selfEfficacyResource)
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
    public static SelfEfficacy createEntity(EntityManager em) {
        SelfEfficacy selfEfficacy = new SelfEfficacy()
            .healthierLifestyle(DEFAULT_HEALTHIER_LIFESTYLE)
            .completeBehaviourGoals(DEFAULT_COMPLETE_BEHAVIOUR_GOALS)
            .manageMultimorbidity(DEFAULT_MANAGE_MULTIMORBIDITY)
            .phase(DEFAULT_PHASE)
            .date(DEFAULT_DATE);
        return selfEfficacy;
    }

    @Before
    public void initTest() {
        selfEfficacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelfEfficacy() throws Exception {
        int databaseSizeBeforeCreate = selfEfficacyRepository.findAll().size();

        // Create the SelfEfficacy
        restSelfEfficacyMockMvc.perform(post("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfEfficacy)))
            .andExpect(status().isCreated());

        // Validate the SelfEfficacy in the database
        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeCreate + 1);
        SelfEfficacy testSelfEfficacy = selfEfficacyList.get(selfEfficacyList.size() - 1);
        assertThat(testSelfEfficacy.isHealthierLifestyle()).isEqualTo(DEFAULT_HEALTHIER_LIFESTYLE);
        assertThat(testSelfEfficacy.isCompleteBehaviourGoals()).isEqualTo(DEFAULT_COMPLETE_BEHAVIOUR_GOALS);
        assertThat(testSelfEfficacy.isManageMultimorbidity()).isEqualTo(DEFAULT_MANAGE_MULTIMORBIDITY);
        assertThat(testSelfEfficacy.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testSelfEfficacy.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createSelfEfficacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selfEfficacyRepository.findAll().size();

        // Create the SelfEfficacy with an existing ID
        selfEfficacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelfEfficacyMockMvc.perform(post("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfEfficacy)))
            .andExpect(status().isBadRequest());

        // Validate the SelfEfficacy in the database
        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfEfficacyRepository.findAll().size();
        // set the field null
        selfEfficacy.setPhase(null);

        // Create the SelfEfficacy, which fails.

        restSelfEfficacyMockMvc.perform(post("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfEfficacy)))
            .andExpect(status().isBadRequest());

        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfEfficacyRepository.findAll().size();
        // set the field null
        selfEfficacy.setDate(null);

        // Create the SelfEfficacy, which fails.

        restSelfEfficacyMockMvc.perform(post("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfEfficacy)))
            .andExpect(status().isBadRequest());

        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSelfEfficacies() throws Exception {
        // Initialize the database
        selfEfficacyRepository.saveAndFlush(selfEfficacy);

        // Get all the selfEfficacyList
        restSelfEfficacyMockMvc.perform(get("/api/self-efficacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selfEfficacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].healthierLifestyle").value(hasItem(DEFAULT_HEALTHIER_LIFESTYLE.booleanValue())))
            .andExpect(jsonPath("$.[*].completeBehaviourGoals").value(hasItem(DEFAULT_COMPLETE_BEHAVIOUR_GOALS.booleanValue())))
            .andExpect(jsonPath("$.[*].manageMultimorbidity").value(hasItem(DEFAULT_MANAGE_MULTIMORBIDITY.booleanValue())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSelfEfficacy() throws Exception {
        // Initialize the database
        selfEfficacyRepository.saveAndFlush(selfEfficacy);

        // Get the selfEfficacy
        restSelfEfficacyMockMvc.perform(get("/api/self-efficacies/{id}", selfEfficacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selfEfficacy.getId().intValue()))
            .andExpect(jsonPath("$.healthierLifestyle").value(DEFAULT_HEALTHIER_LIFESTYLE.booleanValue()))
            .andExpect(jsonPath("$.completeBehaviourGoals").value(DEFAULT_COMPLETE_BEHAVIOUR_GOALS.booleanValue()))
            .andExpect(jsonPath("$.manageMultimorbidity").value(DEFAULT_MANAGE_MULTIMORBIDITY.booleanValue()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSelfEfficacy() throws Exception {
        // Get the selfEfficacy
        restSelfEfficacyMockMvc.perform(get("/api/self-efficacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelfEfficacy() throws Exception {
        // Initialize the database
        selfEfficacyRepository.saveAndFlush(selfEfficacy);
        int databaseSizeBeforeUpdate = selfEfficacyRepository.findAll().size();

        // Update the selfEfficacy
        SelfEfficacy updatedSelfEfficacy = selfEfficacyRepository.findOne(selfEfficacy.getId());
        updatedSelfEfficacy
            .healthierLifestyle(UPDATED_HEALTHIER_LIFESTYLE)
            .completeBehaviourGoals(UPDATED_COMPLETE_BEHAVIOUR_GOALS)
            .manageMultimorbidity(UPDATED_MANAGE_MULTIMORBIDITY)
            .phase(UPDATED_PHASE)
            .date(UPDATED_DATE);

        restSelfEfficacyMockMvc.perform(put("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSelfEfficacy)))
            .andExpect(status().isOk());

        // Validate the SelfEfficacy in the database
        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeUpdate);
        SelfEfficacy testSelfEfficacy = selfEfficacyList.get(selfEfficacyList.size() - 1);
        assertThat(testSelfEfficacy.isHealthierLifestyle()).isEqualTo(UPDATED_HEALTHIER_LIFESTYLE);
        assertThat(testSelfEfficacy.isCompleteBehaviourGoals()).isEqualTo(UPDATED_COMPLETE_BEHAVIOUR_GOALS);
        assertThat(testSelfEfficacy.isManageMultimorbidity()).isEqualTo(UPDATED_MANAGE_MULTIMORBIDITY);
        assertThat(testSelfEfficacy.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testSelfEfficacy.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSelfEfficacy() throws Exception {
        int databaseSizeBeforeUpdate = selfEfficacyRepository.findAll().size();

        // Create the SelfEfficacy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSelfEfficacyMockMvc.perform(put("/api/self-efficacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfEfficacy)))
            .andExpect(status().isCreated());

        // Validate the SelfEfficacy in the database
        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSelfEfficacy() throws Exception {
        // Initialize the database
        selfEfficacyRepository.saveAndFlush(selfEfficacy);
        int databaseSizeBeforeDelete = selfEfficacyRepository.findAll().size();

        // Get the selfEfficacy
        restSelfEfficacyMockMvc.perform(delete("/api/self-efficacies/{id}", selfEfficacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SelfEfficacy> selfEfficacyList = selfEfficacyRepository.findAll();
        assertThat(selfEfficacyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelfEfficacy.class);
        SelfEfficacy selfEfficacy1 = new SelfEfficacy();
        selfEfficacy1.setId(1L);
        SelfEfficacy selfEfficacy2 = new SelfEfficacy();
        selfEfficacy2.setId(selfEfficacy1.getId());
        assertThat(selfEfficacy1).isEqualTo(selfEfficacy2);
        selfEfficacy2.setId(2L);
        assertThat(selfEfficacy1).isNotEqualTo(selfEfficacy2);
        selfEfficacy1.setId(null);
        assertThat(selfEfficacy1).isNotEqualTo(selfEfficacy2);
    }
}
