package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.IntentionsAndPlans;
import mmsnap.repository.IntentionsAndPlansRepository;
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
 * Test class for the IntentionsAndPlansResource REST controller.
 *
 * @see IntentionsAndPlansResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class IntentionsAndPlansResourceIntTest {

    private static final Boolean DEFAULT_WHEN_TO_EXERCISE = false;
    private static final Boolean UPDATED_WHEN_TO_EXERCISE = true;

    private static final Boolean DEFAULT_PAST_WEEK_EXERCISE = false;
    private static final Boolean UPDATED_PAST_WEEK_EXERCISE = true;

    private static final Boolean DEFAULT_EXERCISE_WHERE = false;
    private static final Boolean UPDATED_EXERCISE_WHERE = true;

    private static final Boolean DEFAULT_EXERCISE_HOW = false;
    private static final Boolean UPDATED_EXERCISE_HOW = true;

    private static final Boolean DEFAULT_EXERCISE_HOW_OFTER = false;
    private static final Boolean UPDATED_EXERCISE_HOW_OFTER = true;

    private static final Boolean DEFAULT_EXERCISE_WITH_WHOM = false;
    private static final Boolean UPDATED_EXERCISE_WITH_WHOM = true;

    private static final Boolean DEFAULT_PLANS_INTERFERE = false;
    private static final Boolean UPDATED_PLANS_INTERFERE = true;

    private static final Boolean DEFAULT_SETBACKS_COPE = false;
    private static final Boolean UPDATED_SETBACKS_COPE = true;

    private static final Boolean DEFAULT_DIFFICULT_SITUATIONS = false;
    private static final Boolean UPDATED_DIFFICULT_SITUATIONS = true;

    private static final Boolean DEFAULT_GOOD_OPPORTUNITIES = false;
    private static final Boolean UPDATED_GOOD_OPPORTUNITIES = true;

    private static final Boolean DEFAULT_PREVENT_LAPSES = false;
    private static final Boolean UPDATED_PREVENT_LAPSES = true;

    private static final Boolean DEFAULT_EXERCISE_SEVERAL_TIMES_PER_WEEK = false;
    private static final Boolean UPDATED_EXERCISE_SEVERAL_TIMES_PER_WEEK = true;

    private static final Boolean DEFAULT_WORK_UP_SWEAT = false;
    private static final Boolean UPDATED_WORK_UP_SWEAT = true;

    private static final Boolean DEFAULT_EXERCISE_REGULARLY = false;
    private static final Boolean UPDATED_EXERCISE_REGULARLY = true;

    private static final Boolean DEFAULT_MINIMUM_PHYSICAL_ACTIVITY = false;
    private static final Boolean UPDATED_MINIMUM_PHYSICAL_ACTIVITY = true;

    private static final Boolean DEFAULT_LEISURE_TIME_ACTIVITY = false;
    private static final Boolean UPDATED_LEISURE_TIME_ACTIVITY = true;

    private static final Boolean DEFAULT_EXERCISE_DURING_REHABILITATION = false;
    private static final Boolean UPDATED_EXERCISE_DURING_REHABILITATION = true;

    private static final AssessmentPhase DEFAULT_PHASE = AssessmentPhase.INITIAL;
    private static final AssessmentPhase UPDATED_PHASE = AssessmentPhase.FINAL;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntentionsAndPlansRepository intentionsAndPlansRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntentionsAndPlansMockMvc;

    private IntentionsAndPlans intentionsAndPlans;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntentionsAndPlansResource intentionsAndPlansResource = new IntentionsAndPlansResource(intentionsAndPlansRepository);
        this.restIntentionsAndPlansMockMvc = MockMvcBuilders.standaloneSetup(intentionsAndPlansResource)
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
    public static IntentionsAndPlans createEntity(EntityManager em) {
        IntentionsAndPlans intentionsAndPlans = new IntentionsAndPlans()
            .whenToExercise(DEFAULT_WHEN_TO_EXERCISE)
            .pastWeekExercise(DEFAULT_PAST_WEEK_EXERCISE)
            .exerciseWhere(DEFAULT_EXERCISE_WHERE)
            .exerciseHow(DEFAULT_EXERCISE_HOW)
            .exerciseHowOfter(DEFAULT_EXERCISE_HOW_OFTER)
            .exerciseWithWhom(DEFAULT_EXERCISE_WITH_WHOM)
            .plansInterfere(DEFAULT_PLANS_INTERFERE)
            .setbacksCope(DEFAULT_SETBACKS_COPE)
            .difficultSituations(DEFAULT_DIFFICULT_SITUATIONS)
            .goodOpportunities(DEFAULT_GOOD_OPPORTUNITIES)
            .preventLapses(DEFAULT_PREVENT_LAPSES)
            .exerciseSeveralTimesPerWeek(DEFAULT_EXERCISE_SEVERAL_TIMES_PER_WEEK)
            .workUpSweat(DEFAULT_WORK_UP_SWEAT)
            .exerciseRegularly(DEFAULT_EXERCISE_REGULARLY)
            .minimumPhysicalActivity(DEFAULT_MINIMUM_PHYSICAL_ACTIVITY)
            .leisureTimeActivity(DEFAULT_LEISURE_TIME_ACTIVITY)
            .exerciseDuringRehabilitation(DEFAULT_EXERCISE_DURING_REHABILITATION)
            .phase(DEFAULT_PHASE)
            .date(DEFAULT_DATE);
        return intentionsAndPlans;
    }

    @Before
    public void initTest() {
        intentionsAndPlans = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntentionsAndPlans() throws Exception {
        int databaseSizeBeforeCreate = intentionsAndPlansRepository.findAll().size();

        // Create the IntentionsAndPlans
        restIntentionsAndPlansMockMvc.perform(post("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionsAndPlans)))
            .andExpect(status().isCreated());

        // Validate the IntentionsAndPlans in the database
        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeCreate + 1);
        IntentionsAndPlans testIntentionsAndPlans = intentionsAndPlansList.get(intentionsAndPlansList.size() - 1);
        assertThat(testIntentionsAndPlans.isWhenToExercise()).isEqualTo(DEFAULT_WHEN_TO_EXERCISE);
        assertThat(testIntentionsAndPlans.isPastWeekExercise()).isEqualTo(DEFAULT_PAST_WEEK_EXERCISE);
        assertThat(testIntentionsAndPlans.isExerciseWhere()).isEqualTo(DEFAULT_EXERCISE_WHERE);
        assertThat(testIntentionsAndPlans.isExerciseHow()).isEqualTo(DEFAULT_EXERCISE_HOW);
        assertThat(testIntentionsAndPlans.isExerciseHowOfter()).isEqualTo(DEFAULT_EXERCISE_HOW_OFTER);
        assertThat(testIntentionsAndPlans.isExerciseWithWhom()).isEqualTo(DEFAULT_EXERCISE_WITH_WHOM);
        assertThat(testIntentionsAndPlans.isPlansInterfere()).isEqualTo(DEFAULT_PLANS_INTERFERE);
        assertThat(testIntentionsAndPlans.isSetbacksCope()).isEqualTo(DEFAULT_SETBACKS_COPE);
        assertThat(testIntentionsAndPlans.isDifficultSituations()).isEqualTo(DEFAULT_DIFFICULT_SITUATIONS);
        assertThat(testIntentionsAndPlans.isGoodOpportunities()).isEqualTo(DEFAULT_GOOD_OPPORTUNITIES);
        assertThat(testIntentionsAndPlans.isPreventLapses()).isEqualTo(DEFAULT_PREVENT_LAPSES);
        assertThat(testIntentionsAndPlans.isExerciseSeveralTimesPerWeek()).isEqualTo(DEFAULT_EXERCISE_SEVERAL_TIMES_PER_WEEK);
        assertThat(testIntentionsAndPlans.isWorkUpSweat()).isEqualTo(DEFAULT_WORK_UP_SWEAT);
        assertThat(testIntentionsAndPlans.isExerciseRegularly()).isEqualTo(DEFAULT_EXERCISE_REGULARLY);
        assertThat(testIntentionsAndPlans.isMinimumPhysicalActivity()).isEqualTo(DEFAULT_MINIMUM_PHYSICAL_ACTIVITY);
        assertThat(testIntentionsAndPlans.isLeisureTimeActivity()).isEqualTo(DEFAULT_LEISURE_TIME_ACTIVITY);
        assertThat(testIntentionsAndPlans.isExerciseDuringRehabilitation()).isEqualTo(DEFAULT_EXERCISE_DURING_REHABILITATION);
        assertThat(testIntentionsAndPlans.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testIntentionsAndPlans.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createIntentionsAndPlansWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intentionsAndPlansRepository.findAll().size();

        // Create the IntentionsAndPlans with an existing ID
        intentionsAndPlans.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentionsAndPlansMockMvc.perform(post("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionsAndPlans)))
            .andExpect(status().isBadRequest());

        // Validate the IntentionsAndPlans in the database
        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentionsAndPlansRepository.findAll().size();
        // set the field null
        intentionsAndPlans.setPhase(null);

        // Create the IntentionsAndPlans, which fails.

        restIntentionsAndPlansMockMvc.perform(post("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionsAndPlans)))
            .andExpect(status().isBadRequest());

        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentionsAndPlansRepository.findAll().size();
        // set the field null
        intentionsAndPlans.setDate(null);

        // Create the IntentionsAndPlans, which fails.

        restIntentionsAndPlansMockMvc.perform(post("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionsAndPlans)))
            .andExpect(status().isBadRequest());

        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntentionsAndPlans() throws Exception {
        // Initialize the database
        intentionsAndPlansRepository.saveAndFlush(intentionsAndPlans);

        // Get all the intentionsAndPlansList
        restIntentionsAndPlansMockMvc.perform(get("/api/intentions-and-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intentionsAndPlans.getId().intValue())))
            .andExpect(jsonPath("$.[*].whenToExercise").value(hasItem(DEFAULT_WHEN_TO_EXERCISE.booleanValue())))
            .andExpect(jsonPath("$.[*].pastWeekExercise").value(hasItem(DEFAULT_PAST_WEEK_EXERCISE.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseWhere").value(hasItem(DEFAULT_EXERCISE_WHERE.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseHow").value(hasItem(DEFAULT_EXERCISE_HOW.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseHowOfter").value(hasItem(DEFAULT_EXERCISE_HOW_OFTER.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseWithWhom").value(hasItem(DEFAULT_EXERCISE_WITH_WHOM.booleanValue())))
            .andExpect(jsonPath("$.[*].plansInterfere").value(hasItem(DEFAULT_PLANS_INTERFERE.booleanValue())))
            .andExpect(jsonPath("$.[*].setbacksCope").value(hasItem(DEFAULT_SETBACKS_COPE.booleanValue())))
            .andExpect(jsonPath("$.[*].difficultSituations").value(hasItem(DEFAULT_DIFFICULT_SITUATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].goodOpportunities").value(hasItem(DEFAULT_GOOD_OPPORTUNITIES.booleanValue())))
            .andExpect(jsonPath("$.[*].preventLapses").value(hasItem(DEFAULT_PREVENT_LAPSES.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseSeveralTimesPerWeek").value(hasItem(DEFAULT_EXERCISE_SEVERAL_TIMES_PER_WEEK.booleanValue())))
            .andExpect(jsonPath("$.[*].workUpSweat").value(hasItem(DEFAULT_WORK_UP_SWEAT.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseRegularly").value(hasItem(DEFAULT_EXERCISE_REGULARLY.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumPhysicalActivity").value(hasItem(DEFAULT_MINIMUM_PHYSICAL_ACTIVITY.booleanValue())))
            .andExpect(jsonPath("$.[*].leisureTimeActivity").value(hasItem(DEFAULT_LEISURE_TIME_ACTIVITY.booleanValue())))
            .andExpect(jsonPath("$.[*].exerciseDuringRehabilitation").value(hasItem(DEFAULT_EXERCISE_DURING_REHABILITATION.booleanValue())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getIntentionsAndPlans() throws Exception {
        // Initialize the database
        intentionsAndPlansRepository.saveAndFlush(intentionsAndPlans);

        // Get the intentionsAndPlans
        restIntentionsAndPlansMockMvc.perform(get("/api/intentions-and-plans/{id}", intentionsAndPlans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intentionsAndPlans.getId().intValue()))
            .andExpect(jsonPath("$.whenToExercise").value(DEFAULT_WHEN_TO_EXERCISE.booleanValue()))
            .andExpect(jsonPath("$.pastWeekExercise").value(DEFAULT_PAST_WEEK_EXERCISE.booleanValue()))
            .andExpect(jsonPath("$.exerciseWhere").value(DEFAULT_EXERCISE_WHERE.booleanValue()))
            .andExpect(jsonPath("$.exerciseHow").value(DEFAULT_EXERCISE_HOW.booleanValue()))
            .andExpect(jsonPath("$.exerciseHowOfter").value(DEFAULT_EXERCISE_HOW_OFTER.booleanValue()))
            .andExpect(jsonPath("$.exerciseWithWhom").value(DEFAULT_EXERCISE_WITH_WHOM.booleanValue()))
            .andExpect(jsonPath("$.plansInterfere").value(DEFAULT_PLANS_INTERFERE.booleanValue()))
            .andExpect(jsonPath("$.setbacksCope").value(DEFAULT_SETBACKS_COPE.booleanValue()))
            .andExpect(jsonPath("$.difficultSituations").value(DEFAULT_DIFFICULT_SITUATIONS.booleanValue()))
            .andExpect(jsonPath("$.goodOpportunities").value(DEFAULT_GOOD_OPPORTUNITIES.booleanValue()))
            .andExpect(jsonPath("$.preventLapses").value(DEFAULT_PREVENT_LAPSES.booleanValue()))
            .andExpect(jsonPath("$.exerciseSeveralTimesPerWeek").value(DEFAULT_EXERCISE_SEVERAL_TIMES_PER_WEEK.booleanValue()))
            .andExpect(jsonPath("$.workUpSweat").value(DEFAULT_WORK_UP_SWEAT.booleanValue()))
            .andExpect(jsonPath("$.exerciseRegularly").value(DEFAULT_EXERCISE_REGULARLY.booleanValue()))
            .andExpect(jsonPath("$.minimumPhysicalActivity").value(DEFAULT_MINIMUM_PHYSICAL_ACTIVITY.booleanValue()))
            .andExpect(jsonPath("$.leisureTimeActivity").value(DEFAULT_LEISURE_TIME_ACTIVITY.booleanValue()))
            .andExpect(jsonPath("$.exerciseDuringRehabilitation").value(DEFAULT_EXERCISE_DURING_REHABILITATION.booleanValue()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIntentionsAndPlans() throws Exception {
        // Get the intentionsAndPlans
        restIntentionsAndPlansMockMvc.perform(get("/api/intentions-and-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntentionsAndPlans() throws Exception {
        // Initialize the database
        intentionsAndPlansRepository.saveAndFlush(intentionsAndPlans);
        int databaseSizeBeforeUpdate = intentionsAndPlansRepository.findAll().size();

        // Update the intentionsAndPlans
        IntentionsAndPlans updatedIntentionsAndPlans = intentionsAndPlansRepository.findOne(intentionsAndPlans.getId());
        updatedIntentionsAndPlans
            .whenToExercise(UPDATED_WHEN_TO_EXERCISE)
            .pastWeekExercise(UPDATED_PAST_WEEK_EXERCISE)
            .exerciseWhere(UPDATED_EXERCISE_WHERE)
            .exerciseHow(UPDATED_EXERCISE_HOW)
            .exerciseHowOfter(UPDATED_EXERCISE_HOW_OFTER)
            .exerciseWithWhom(UPDATED_EXERCISE_WITH_WHOM)
            .plansInterfere(UPDATED_PLANS_INTERFERE)
            .setbacksCope(UPDATED_SETBACKS_COPE)
            .difficultSituations(UPDATED_DIFFICULT_SITUATIONS)
            .goodOpportunities(UPDATED_GOOD_OPPORTUNITIES)
            .preventLapses(UPDATED_PREVENT_LAPSES)
            .exerciseSeveralTimesPerWeek(UPDATED_EXERCISE_SEVERAL_TIMES_PER_WEEK)
            .workUpSweat(UPDATED_WORK_UP_SWEAT)
            .exerciseRegularly(UPDATED_EXERCISE_REGULARLY)
            .minimumPhysicalActivity(UPDATED_MINIMUM_PHYSICAL_ACTIVITY)
            .leisureTimeActivity(UPDATED_LEISURE_TIME_ACTIVITY)
            .exerciseDuringRehabilitation(UPDATED_EXERCISE_DURING_REHABILITATION)
            .phase(UPDATED_PHASE)
            .date(UPDATED_DATE);

        restIntentionsAndPlansMockMvc.perform(put("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntentionsAndPlans)))
            .andExpect(status().isOk());

        // Validate the IntentionsAndPlans in the database
        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeUpdate);
        IntentionsAndPlans testIntentionsAndPlans = intentionsAndPlansList.get(intentionsAndPlansList.size() - 1);
        assertThat(testIntentionsAndPlans.isWhenToExercise()).isEqualTo(UPDATED_WHEN_TO_EXERCISE);
        assertThat(testIntentionsAndPlans.isPastWeekExercise()).isEqualTo(UPDATED_PAST_WEEK_EXERCISE);
        assertThat(testIntentionsAndPlans.isExerciseWhere()).isEqualTo(UPDATED_EXERCISE_WHERE);
        assertThat(testIntentionsAndPlans.isExerciseHow()).isEqualTo(UPDATED_EXERCISE_HOW);
        assertThat(testIntentionsAndPlans.isExerciseHowOfter()).isEqualTo(UPDATED_EXERCISE_HOW_OFTER);
        assertThat(testIntentionsAndPlans.isExerciseWithWhom()).isEqualTo(UPDATED_EXERCISE_WITH_WHOM);
        assertThat(testIntentionsAndPlans.isPlansInterfere()).isEqualTo(UPDATED_PLANS_INTERFERE);
        assertThat(testIntentionsAndPlans.isSetbacksCope()).isEqualTo(UPDATED_SETBACKS_COPE);
        assertThat(testIntentionsAndPlans.isDifficultSituations()).isEqualTo(UPDATED_DIFFICULT_SITUATIONS);
        assertThat(testIntentionsAndPlans.isGoodOpportunities()).isEqualTo(UPDATED_GOOD_OPPORTUNITIES);
        assertThat(testIntentionsAndPlans.isPreventLapses()).isEqualTo(UPDATED_PREVENT_LAPSES);
        assertThat(testIntentionsAndPlans.isExerciseSeveralTimesPerWeek()).isEqualTo(UPDATED_EXERCISE_SEVERAL_TIMES_PER_WEEK);
        assertThat(testIntentionsAndPlans.isWorkUpSweat()).isEqualTo(UPDATED_WORK_UP_SWEAT);
        assertThat(testIntentionsAndPlans.isExerciseRegularly()).isEqualTo(UPDATED_EXERCISE_REGULARLY);
        assertThat(testIntentionsAndPlans.isMinimumPhysicalActivity()).isEqualTo(UPDATED_MINIMUM_PHYSICAL_ACTIVITY);
        assertThat(testIntentionsAndPlans.isLeisureTimeActivity()).isEqualTo(UPDATED_LEISURE_TIME_ACTIVITY);
        assertThat(testIntentionsAndPlans.isExerciseDuringRehabilitation()).isEqualTo(UPDATED_EXERCISE_DURING_REHABILITATION);
        assertThat(testIntentionsAndPlans.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testIntentionsAndPlans.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingIntentionsAndPlans() throws Exception {
        int databaseSizeBeforeUpdate = intentionsAndPlansRepository.findAll().size();

        // Create the IntentionsAndPlans

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntentionsAndPlansMockMvc.perform(put("/api/intentions-and-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionsAndPlans)))
            .andExpect(status().isCreated());

        // Validate the IntentionsAndPlans in the database
        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIntentionsAndPlans() throws Exception {
        // Initialize the database
        intentionsAndPlansRepository.saveAndFlush(intentionsAndPlans);
        int databaseSizeBeforeDelete = intentionsAndPlansRepository.findAll().size();

        // Get the intentionsAndPlans
        restIntentionsAndPlansMockMvc.perform(delete("/api/intentions-and-plans/{id}", intentionsAndPlans.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IntentionsAndPlans> intentionsAndPlansList = intentionsAndPlansRepository.findAll();
        assertThat(intentionsAndPlansList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntentionsAndPlans.class);
        IntentionsAndPlans intentionsAndPlans1 = new IntentionsAndPlans();
        intentionsAndPlans1.setId(1L);
        IntentionsAndPlans intentionsAndPlans2 = new IntentionsAndPlans();
        intentionsAndPlans2.setId(intentionsAndPlans1.getId());
        assertThat(intentionsAndPlans1).isEqualTo(intentionsAndPlans2);
        intentionsAndPlans2.setId(2L);
        assertThat(intentionsAndPlans1).isNotEqualTo(intentionsAndPlans2);
        intentionsAndPlans1.setId(null);
        assertThat(intentionsAndPlans1).isNotEqualTo(intentionsAndPlans2);
    }
}
