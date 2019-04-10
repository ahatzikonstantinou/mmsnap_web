package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.SelfRatedHealth;
import mmsnap.repository.SelfRatedHealthRepository;
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

import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.HealthScale;
import mmsnap.domain.enumeration.AssessmentPhase;
/**
 * Test class for the SelfRatedHealthResource REST controller.
 *
 * @see SelfRatedHealthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class SelfRatedHealthResourceIntTest {

    private static final HealthScale DEFAULT_ONE_CONDITION_MORE_SERIOUS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_ONE_CONDITION_MORE_SERIOUS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_TIME_SPENT_MANAGING = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_TIME_SPENT_MANAGING = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_FEEL_OVERWHELMED = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_FEEL_OVERWHELMED = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_CAUSES_ARE_LINKED = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_CAUSES_ARE_LINKED = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_DIFFICULT_ALL_MEDICATIONS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_DIFFICULT_ALL_MEDICATIONS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_LIMITED_ACTIVITIES = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_LIMITED_ACTIVITIES = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_DIFFERENT_MEDICATIONS_PROBLEMS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_DIFFERENT_MEDICATIONS_PROBLEMS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_MIXING_MEDICATIONS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_MIXING_MEDICATIONS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_LESS_EFFECTIVE_TREATMENTS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_LESS_EFFECTIVE_TREATMENTS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_ONE_CAUSE_ANOTHER = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_ONE_CAUSE_ANOTHER = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_ONE_DOMINATES = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_ONE_DOMINATES = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_CONDITIONS_INTERACT = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_CONDITIONS_INTERACT = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_DIFFICULT_BEST_TREATMENT = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_DIFFICULT_BEST_TREATMENT = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_REDUCED_SOCIAL_LIFE = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_REDUCED_SOCIAL_LIFE = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_UNHAPPY = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_UNHAPPY = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_ANXIOUS = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_ANXIOUS = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_ANGRY = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_ANGRY = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_SAD = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_SAD = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_IRRITABLE = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_IRRITABLE = HealthScale.DISAGREE;

    private static final HealthScale DEFAULT_SAD_STRUGGLE = HealthScale.STRONGLY_DISAGREE;
    private static final HealthScale UPDATED_SAD_STRUGGLE = HealthScale.DISAGREE;

    private static final AssessmentPhase DEFAULT_PHASE = AssessmentPhase.INITIAL;
    private static final AssessmentPhase UPDATED_PHASE = AssessmentPhase.FINAL;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SelfRatedHealthRepository selfRatedHealthRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSelfRatedHealthMockMvc;

    private SelfRatedHealth selfRatedHealth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelfRatedHealthResource selfRatedHealthResource = new SelfRatedHealthResource(selfRatedHealthRepository);
        this.restSelfRatedHealthMockMvc = MockMvcBuilders.standaloneSetup(selfRatedHealthResource)
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
    public static SelfRatedHealth createEntity(EntityManager em) {
        SelfRatedHealth selfRatedHealth = new SelfRatedHealth()
            .oneConditionMoreSerious(DEFAULT_ONE_CONDITION_MORE_SERIOUS)
            .timeSpentManaging(DEFAULT_TIME_SPENT_MANAGING)
            .feelOverwhelmed(DEFAULT_FEEL_OVERWHELMED)
            .causesAreLinked(DEFAULT_CAUSES_ARE_LINKED)
            .difficultAllMedications(DEFAULT_DIFFICULT_ALL_MEDICATIONS)
            .limitedActivities(DEFAULT_LIMITED_ACTIVITIES)
            .differentMedicationsProblems(DEFAULT_DIFFERENT_MEDICATIONS_PROBLEMS)
            .mixingMedications(DEFAULT_MIXING_MEDICATIONS)
            .lessEffectiveTreatments(DEFAULT_LESS_EFFECTIVE_TREATMENTS)
            .oneCauseAnother(DEFAULT_ONE_CAUSE_ANOTHER)
            .oneDominates(DEFAULT_ONE_DOMINATES)
            .conditionsInteract(DEFAULT_CONDITIONS_INTERACT)
            .difficultBestTreatment(DEFAULT_DIFFICULT_BEST_TREATMENT)
            .reducedSocialLife(DEFAULT_REDUCED_SOCIAL_LIFE)
            .unhappy(DEFAULT_UNHAPPY)
            .anxious(DEFAULT_ANXIOUS)
            .angry(DEFAULT_ANGRY)
            .sad(DEFAULT_SAD)
            .irritable(DEFAULT_IRRITABLE)
            .sadStruggle(DEFAULT_SAD_STRUGGLE)
            .phase(DEFAULT_PHASE)
            .date(DEFAULT_DATE);
        return selfRatedHealth;
    }

    @Before
    public void initTest() {
        selfRatedHealth = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelfRatedHealth() throws Exception {
        int databaseSizeBeforeCreate = selfRatedHealthRepository.findAll().size();

        // Create the SelfRatedHealth
        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isCreated());

        // Validate the SelfRatedHealth in the database
        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeCreate + 1);
        SelfRatedHealth testSelfRatedHealth = selfRatedHealthList.get(selfRatedHealthList.size() - 1);
        assertThat(testSelfRatedHealth.getOneConditionMoreSerious()).isEqualTo(DEFAULT_ONE_CONDITION_MORE_SERIOUS);
        assertThat(testSelfRatedHealth.getTimeSpentManaging()).isEqualTo(DEFAULT_TIME_SPENT_MANAGING);
        assertThat(testSelfRatedHealth.getFeelOverwhelmed()).isEqualTo(DEFAULT_FEEL_OVERWHELMED);
        assertThat(testSelfRatedHealth.getCausesAreLinked()).isEqualTo(DEFAULT_CAUSES_ARE_LINKED);
        assertThat(testSelfRatedHealth.getDifficultAllMedications()).isEqualTo(DEFAULT_DIFFICULT_ALL_MEDICATIONS);
        assertThat(testSelfRatedHealth.getLimitedActivities()).isEqualTo(DEFAULT_LIMITED_ACTIVITIES);
        assertThat(testSelfRatedHealth.getDifferentMedicationsProblems()).isEqualTo(DEFAULT_DIFFERENT_MEDICATIONS_PROBLEMS);
        assertThat(testSelfRatedHealth.getMixingMedications()).isEqualTo(DEFAULT_MIXING_MEDICATIONS);
        assertThat(testSelfRatedHealth.getLessEffectiveTreatments()).isEqualTo(DEFAULT_LESS_EFFECTIVE_TREATMENTS);
        assertThat(testSelfRatedHealth.getOneCauseAnother()).isEqualTo(DEFAULT_ONE_CAUSE_ANOTHER);
        assertThat(testSelfRatedHealth.getOneDominates()).isEqualTo(DEFAULT_ONE_DOMINATES);
        assertThat(testSelfRatedHealth.getConditionsInteract()).isEqualTo(DEFAULT_CONDITIONS_INTERACT);
        assertThat(testSelfRatedHealth.getDifficultBestTreatment()).isEqualTo(DEFAULT_DIFFICULT_BEST_TREATMENT);
        assertThat(testSelfRatedHealth.getReducedSocialLife()).isEqualTo(DEFAULT_REDUCED_SOCIAL_LIFE);
        assertThat(testSelfRatedHealth.getUnhappy()).isEqualTo(DEFAULT_UNHAPPY);
        assertThat(testSelfRatedHealth.getAnxious()).isEqualTo(DEFAULT_ANXIOUS);
        assertThat(testSelfRatedHealth.getAngry()).isEqualTo(DEFAULT_ANGRY);
        assertThat(testSelfRatedHealth.getSad()).isEqualTo(DEFAULT_SAD);
        assertThat(testSelfRatedHealth.getIrritable()).isEqualTo(DEFAULT_IRRITABLE);
        assertThat(testSelfRatedHealth.getSadStruggle()).isEqualTo(DEFAULT_SAD_STRUGGLE);
        assertThat(testSelfRatedHealth.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testSelfRatedHealth.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createSelfRatedHealthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selfRatedHealthRepository.findAll().size();

        // Create the SelfRatedHealth with an existing ID
        selfRatedHealth.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        // Validate the SelfRatedHealth in the database
        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOneConditionMoreSeriousIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setOneConditionMoreSerious(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeSpentManagingIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setTimeSpentManaging(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeelOverwhelmedIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setFeelOverwhelmed(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCausesAreLinkedIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setCausesAreLinked(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDifficultAllMedicationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setDifficultAllMedications(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLimitedActivitiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setLimitedActivities(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDifferentMedicationsProblemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setDifferentMedicationsProblems(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMixingMedicationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setMixingMedications(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLessEffectiveTreatmentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setLessEffectiveTreatments(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOneCauseAnotherIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setOneCauseAnother(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOneDominatesIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setOneDominates(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConditionsInteractIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setConditionsInteract(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDifficultBestTreatmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setDifficultBestTreatment(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReducedSocialLifeIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setReducedSocialLife(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnhappyIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setUnhappy(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnxiousIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setAnxious(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAngryIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setAngry(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSadIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setSad(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIrritableIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setIrritable(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSadStruggleIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setSadStruggle(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setPhase(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfRatedHealthRepository.findAll().size();
        // set the field null
        selfRatedHealth.setDate(null);

        // Create the SelfRatedHealth, which fails.

        restSelfRatedHealthMockMvc.perform(post("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isBadRequest());

        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSelfRatedHealths() throws Exception {
        // Initialize the database
        selfRatedHealthRepository.saveAndFlush(selfRatedHealth);

        // Get all the selfRatedHealthList
        restSelfRatedHealthMockMvc.perform(get("/api/self-rated-healths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selfRatedHealth.getId().intValue())))
            .andExpect(jsonPath("$.[*].oneConditionMoreSerious").value(hasItem(DEFAULT_ONE_CONDITION_MORE_SERIOUS.toString())))
            .andExpect(jsonPath("$.[*].timeSpentManaging").value(hasItem(DEFAULT_TIME_SPENT_MANAGING.toString())))
            .andExpect(jsonPath("$.[*].feelOverwhelmed").value(hasItem(DEFAULT_FEEL_OVERWHELMED.toString())))
            .andExpect(jsonPath("$.[*].causesAreLinked").value(hasItem(DEFAULT_CAUSES_ARE_LINKED.toString())))
            .andExpect(jsonPath("$.[*].difficultAllMedications").value(hasItem(DEFAULT_DIFFICULT_ALL_MEDICATIONS.toString())))
            .andExpect(jsonPath("$.[*].limitedActivities").value(hasItem(DEFAULT_LIMITED_ACTIVITIES.toString())))
            .andExpect(jsonPath("$.[*].differentMedicationsProblems").value(hasItem(DEFAULT_DIFFERENT_MEDICATIONS_PROBLEMS.toString())))
            .andExpect(jsonPath("$.[*].mixingMedications").value(hasItem(DEFAULT_MIXING_MEDICATIONS.toString())))
            .andExpect(jsonPath("$.[*].lessEffectiveTreatments").value(hasItem(DEFAULT_LESS_EFFECTIVE_TREATMENTS.toString())))
            .andExpect(jsonPath("$.[*].oneCauseAnother").value(hasItem(DEFAULT_ONE_CAUSE_ANOTHER.toString())))
            .andExpect(jsonPath("$.[*].oneDominates").value(hasItem(DEFAULT_ONE_DOMINATES.toString())))
            .andExpect(jsonPath("$.[*].conditionsInteract").value(hasItem(DEFAULT_CONDITIONS_INTERACT.toString())))
            .andExpect(jsonPath("$.[*].difficultBestTreatment").value(hasItem(DEFAULT_DIFFICULT_BEST_TREATMENT.toString())))
            .andExpect(jsonPath("$.[*].reducedSocialLife").value(hasItem(DEFAULT_REDUCED_SOCIAL_LIFE.toString())))
            .andExpect(jsonPath("$.[*].unhappy").value(hasItem(DEFAULT_UNHAPPY.toString())))
            .andExpect(jsonPath("$.[*].anxious").value(hasItem(DEFAULT_ANXIOUS.toString())))
            .andExpect(jsonPath("$.[*].angry").value(hasItem(DEFAULT_ANGRY.toString())))
            .andExpect(jsonPath("$.[*].sad").value(hasItem(DEFAULT_SAD.toString())))
            .andExpect(jsonPath("$.[*].irritable").value(hasItem(DEFAULT_IRRITABLE.toString())))
            .andExpect(jsonPath("$.[*].sadStruggle").value(hasItem(DEFAULT_SAD_STRUGGLE.toString())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSelfRatedHealth() throws Exception {
        // Initialize the database
        selfRatedHealthRepository.saveAndFlush(selfRatedHealth);

        // Get the selfRatedHealth
        restSelfRatedHealthMockMvc.perform(get("/api/self-rated-healths/{id}", selfRatedHealth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selfRatedHealth.getId().intValue()))
            .andExpect(jsonPath("$.oneConditionMoreSerious").value(DEFAULT_ONE_CONDITION_MORE_SERIOUS.toString()))
            .andExpect(jsonPath("$.timeSpentManaging").value(DEFAULT_TIME_SPENT_MANAGING.toString()))
            .andExpect(jsonPath("$.feelOverwhelmed").value(DEFAULT_FEEL_OVERWHELMED.toString()))
            .andExpect(jsonPath("$.causesAreLinked").value(DEFAULT_CAUSES_ARE_LINKED.toString()))
            .andExpect(jsonPath("$.difficultAllMedications").value(DEFAULT_DIFFICULT_ALL_MEDICATIONS.toString()))
            .andExpect(jsonPath("$.limitedActivities").value(DEFAULT_LIMITED_ACTIVITIES.toString()))
            .andExpect(jsonPath("$.differentMedicationsProblems").value(DEFAULT_DIFFERENT_MEDICATIONS_PROBLEMS.toString()))
            .andExpect(jsonPath("$.mixingMedications").value(DEFAULT_MIXING_MEDICATIONS.toString()))
            .andExpect(jsonPath("$.lessEffectiveTreatments").value(DEFAULT_LESS_EFFECTIVE_TREATMENTS.toString()))
            .andExpect(jsonPath("$.oneCauseAnother").value(DEFAULT_ONE_CAUSE_ANOTHER.toString()))
            .andExpect(jsonPath("$.oneDominates").value(DEFAULT_ONE_DOMINATES.toString()))
            .andExpect(jsonPath("$.conditionsInteract").value(DEFAULT_CONDITIONS_INTERACT.toString()))
            .andExpect(jsonPath("$.difficultBestTreatment").value(DEFAULT_DIFFICULT_BEST_TREATMENT.toString()))
            .andExpect(jsonPath("$.reducedSocialLife").value(DEFAULT_REDUCED_SOCIAL_LIFE.toString()))
            .andExpect(jsonPath("$.unhappy").value(DEFAULT_UNHAPPY.toString()))
            .andExpect(jsonPath("$.anxious").value(DEFAULT_ANXIOUS.toString()))
            .andExpect(jsonPath("$.angry").value(DEFAULT_ANGRY.toString()))
            .andExpect(jsonPath("$.sad").value(DEFAULT_SAD.toString()))
            .andExpect(jsonPath("$.irritable").value(DEFAULT_IRRITABLE.toString()))
            .andExpect(jsonPath("$.sadStruggle").value(DEFAULT_SAD_STRUGGLE.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSelfRatedHealth() throws Exception {
        // Get the selfRatedHealth
        restSelfRatedHealthMockMvc.perform(get("/api/self-rated-healths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelfRatedHealth() throws Exception {
        // Initialize the database
        selfRatedHealthRepository.saveAndFlush(selfRatedHealth);
        int databaseSizeBeforeUpdate = selfRatedHealthRepository.findAll().size();

        // Update the selfRatedHealth
        SelfRatedHealth updatedSelfRatedHealth = selfRatedHealthRepository.findOne(selfRatedHealth.getId());
        updatedSelfRatedHealth
            .oneConditionMoreSerious(UPDATED_ONE_CONDITION_MORE_SERIOUS)
            .timeSpentManaging(UPDATED_TIME_SPENT_MANAGING)
            .feelOverwhelmed(UPDATED_FEEL_OVERWHELMED)
            .causesAreLinked(UPDATED_CAUSES_ARE_LINKED)
            .difficultAllMedications(UPDATED_DIFFICULT_ALL_MEDICATIONS)
            .limitedActivities(UPDATED_LIMITED_ACTIVITIES)
            .differentMedicationsProblems(UPDATED_DIFFERENT_MEDICATIONS_PROBLEMS)
            .mixingMedications(UPDATED_MIXING_MEDICATIONS)
            .lessEffectiveTreatments(UPDATED_LESS_EFFECTIVE_TREATMENTS)
            .oneCauseAnother(UPDATED_ONE_CAUSE_ANOTHER)
            .oneDominates(UPDATED_ONE_DOMINATES)
            .conditionsInteract(UPDATED_CONDITIONS_INTERACT)
            .difficultBestTreatment(UPDATED_DIFFICULT_BEST_TREATMENT)
            .reducedSocialLife(UPDATED_REDUCED_SOCIAL_LIFE)
            .unhappy(UPDATED_UNHAPPY)
            .anxious(UPDATED_ANXIOUS)
            .angry(UPDATED_ANGRY)
            .sad(UPDATED_SAD)
            .irritable(UPDATED_IRRITABLE)
            .sadStruggle(UPDATED_SAD_STRUGGLE)
            .phase(UPDATED_PHASE)
            .date(UPDATED_DATE);

        restSelfRatedHealthMockMvc.perform(put("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSelfRatedHealth)))
            .andExpect(status().isOk());

        // Validate the SelfRatedHealth in the database
        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeUpdate);
        SelfRatedHealth testSelfRatedHealth = selfRatedHealthList.get(selfRatedHealthList.size() - 1);
        assertThat(testSelfRatedHealth.getOneConditionMoreSerious()).isEqualTo(UPDATED_ONE_CONDITION_MORE_SERIOUS);
        assertThat(testSelfRatedHealth.getTimeSpentManaging()).isEqualTo(UPDATED_TIME_SPENT_MANAGING);
        assertThat(testSelfRatedHealth.getFeelOverwhelmed()).isEqualTo(UPDATED_FEEL_OVERWHELMED);
        assertThat(testSelfRatedHealth.getCausesAreLinked()).isEqualTo(UPDATED_CAUSES_ARE_LINKED);
        assertThat(testSelfRatedHealth.getDifficultAllMedications()).isEqualTo(UPDATED_DIFFICULT_ALL_MEDICATIONS);
        assertThat(testSelfRatedHealth.getLimitedActivities()).isEqualTo(UPDATED_LIMITED_ACTIVITIES);
        assertThat(testSelfRatedHealth.getDifferentMedicationsProblems()).isEqualTo(UPDATED_DIFFERENT_MEDICATIONS_PROBLEMS);
        assertThat(testSelfRatedHealth.getMixingMedications()).isEqualTo(UPDATED_MIXING_MEDICATIONS);
        assertThat(testSelfRatedHealth.getLessEffectiveTreatments()).isEqualTo(UPDATED_LESS_EFFECTIVE_TREATMENTS);
        assertThat(testSelfRatedHealth.getOneCauseAnother()).isEqualTo(UPDATED_ONE_CAUSE_ANOTHER);
        assertThat(testSelfRatedHealth.getOneDominates()).isEqualTo(UPDATED_ONE_DOMINATES);
        assertThat(testSelfRatedHealth.getConditionsInteract()).isEqualTo(UPDATED_CONDITIONS_INTERACT);
        assertThat(testSelfRatedHealth.getDifficultBestTreatment()).isEqualTo(UPDATED_DIFFICULT_BEST_TREATMENT);
        assertThat(testSelfRatedHealth.getReducedSocialLife()).isEqualTo(UPDATED_REDUCED_SOCIAL_LIFE);
        assertThat(testSelfRatedHealth.getUnhappy()).isEqualTo(UPDATED_UNHAPPY);
        assertThat(testSelfRatedHealth.getAnxious()).isEqualTo(UPDATED_ANXIOUS);
        assertThat(testSelfRatedHealth.getAngry()).isEqualTo(UPDATED_ANGRY);
        assertThat(testSelfRatedHealth.getSad()).isEqualTo(UPDATED_SAD);
        assertThat(testSelfRatedHealth.getIrritable()).isEqualTo(UPDATED_IRRITABLE);
        assertThat(testSelfRatedHealth.getSadStruggle()).isEqualTo(UPDATED_SAD_STRUGGLE);
        assertThat(testSelfRatedHealth.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testSelfRatedHealth.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSelfRatedHealth() throws Exception {
        int databaseSizeBeforeUpdate = selfRatedHealthRepository.findAll().size();

        // Create the SelfRatedHealth

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSelfRatedHealthMockMvc.perform(put("/api/self-rated-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selfRatedHealth)))
            .andExpect(status().isCreated());

        // Validate the SelfRatedHealth in the database
        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSelfRatedHealth() throws Exception {
        // Initialize the database
        selfRatedHealthRepository.saveAndFlush(selfRatedHealth);
        int databaseSizeBeforeDelete = selfRatedHealthRepository.findAll().size();

        // Get the selfRatedHealth
        restSelfRatedHealthMockMvc.perform(delete("/api/self-rated-healths/{id}", selfRatedHealth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SelfRatedHealth> selfRatedHealthList = selfRatedHealthRepository.findAll();
        assertThat(selfRatedHealthList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelfRatedHealth.class);
        SelfRatedHealth selfRatedHealth1 = new SelfRatedHealth();
        selfRatedHealth1.setId(1L);
        SelfRatedHealth selfRatedHealth2 = new SelfRatedHealth();
        selfRatedHealth2.setId(selfRatedHealth1.getId());
        assertThat(selfRatedHealth1).isEqualTo(selfRatedHealth2);
        selfRatedHealth2.setId(2L);
        assertThat(selfRatedHealth1).isNotEqualTo(selfRatedHealth2);
        selfRatedHealth1.setId(null);
        assertThat(selfRatedHealth1).isNotEqualTo(selfRatedHealth2);
    }
}
