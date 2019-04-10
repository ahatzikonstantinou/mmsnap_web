package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.DailyEvaluation;
import mmsnap.repository.DailyEvaluationRepository;
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

import mmsnap.domain.enumeration.PlanType;
/**
 * Test class for the DailyEvaluationResource REST controller.
 *
 * @see DailyEvaluationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class DailyEvaluationResourceIntTest {

    private static final PlanType DEFAULT_TYPE = PlanType.ACTION;
    private static final PlanType UPDATED_TYPE = PlanType.COPING;

    private static final Boolean DEFAULT_DIET = false;
    private static final Boolean UPDATED_DIET = true;

    private static final Boolean DEFAULT_SMOKING = false;
    private static final Boolean UPDATED_SMOKING = true;

    private static final Boolean DEFAULT_PHYSICAL_ACTIVITY = false;
    private static final Boolean UPDATED_PHYSICAL_ACTIVITY = true;

    private static final Boolean DEFAULT_ALCOHOL = false;
    private static final Boolean UPDATED_ALCOHOL = true;

    private static final String DEFAULT_IF_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_IF_STATEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_THEN_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_THEN_STATEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_COPING_IF_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COPING_IF_STATEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_COPING_THEN_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COPING_THEN_STATEMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DailyEvaluationRepository dailyEvaluationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDailyEvaluationMockMvc;

    private DailyEvaluation dailyEvaluation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DailyEvaluationResource dailyEvaluationResource = new DailyEvaluationResource(dailyEvaluationRepository);
        this.restDailyEvaluationMockMvc = MockMvcBuilders.standaloneSetup(dailyEvaluationResource)
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
    public static DailyEvaluation createEntity(EntityManager em) {
        DailyEvaluation dailyEvaluation = new DailyEvaluation()
            .type(DEFAULT_TYPE)
            .diet(DEFAULT_DIET)
            .smoking(DEFAULT_SMOKING)
            .physicalActivity(DEFAULT_PHYSICAL_ACTIVITY)
            .alcohol(DEFAULT_ALCOHOL)
            .ifStatement(DEFAULT_IF_STATEMENT)
            .thenStatement(DEFAULT_THEN_STATEMENT)
            .copingIfStatement(DEFAULT_COPING_IF_STATEMENT)
            .copingThenStatement(DEFAULT_COPING_THEN_STATEMENT)
            .success(DEFAULT_SUCCESS)
            .date(DEFAULT_DATE);
        return dailyEvaluation;
    }

    @Before
    public void initTest() {
        dailyEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyEvaluation() throws Exception {
        int databaseSizeBeforeCreate = dailyEvaluationRepository.findAll().size();

        // Create the DailyEvaluation
        restDailyEvaluationMockMvc.perform(post("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isCreated());

        // Validate the DailyEvaluation in the database
        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        DailyEvaluation testDailyEvaluation = dailyEvaluationList.get(dailyEvaluationList.size() - 1);
        assertThat(testDailyEvaluation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDailyEvaluation.isDiet()).isEqualTo(DEFAULT_DIET);
        assertThat(testDailyEvaluation.isSmoking()).isEqualTo(DEFAULT_SMOKING);
        assertThat(testDailyEvaluation.isPhysicalActivity()).isEqualTo(DEFAULT_PHYSICAL_ACTIVITY);
        assertThat(testDailyEvaluation.isAlcohol()).isEqualTo(DEFAULT_ALCOHOL);
        assertThat(testDailyEvaluation.getIfStatement()).isEqualTo(DEFAULT_IF_STATEMENT);
        assertThat(testDailyEvaluation.getThenStatement()).isEqualTo(DEFAULT_THEN_STATEMENT);
        assertThat(testDailyEvaluation.getCopingIfStatement()).isEqualTo(DEFAULT_COPING_IF_STATEMENT);
        assertThat(testDailyEvaluation.getCopingThenStatement()).isEqualTo(DEFAULT_COPING_THEN_STATEMENT);
        assertThat(testDailyEvaluation.isSuccess()).isEqualTo(DEFAULT_SUCCESS);
        assertThat(testDailyEvaluation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDailyEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyEvaluationRepository.findAll().size();

        // Create the DailyEvaluation with an existing ID
        dailyEvaluation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyEvaluationMockMvc.perform(post("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isBadRequest());

        // Validate the DailyEvaluation in the database
        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyEvaluationRepository.findAll().size();
        // set the field null
        dailyEvaluation.setType(null);

        // Create the DailyEvaluation, which fails.

        restDailyEvaluationMockMvc.perform(post("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isBadRequest());

        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSuccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyEvaluationRepository.findAll().size();
        // set the field null
        dailyEvaluation.setSuccess(null);

        // Create the DailyEvaluation, which fails.

        restDailyEvaluationMockMvc.perform(post("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isBadRequest());

        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyEvaluationRepository.findAll().size();
        // set the field null
        dailyEvaluation.setDate(null);

        // Create the DailyEvaluation, which fails.

        restDailyEvaluationMockMvc.perform(post("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isBadRequest());

        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDailyEvaluations() throws Exception {
        // Initialize the database
        dailyEvaluationRepository.saveAndFlush(dailyEvaluation);

        // Get all the dailyEvaluationList
        restDailyEvaluationMockMvc.perform(get("/api/daily-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].diet").value(hasItem(DEFAULT_DIET.booleanValue())))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].physicalActivity").value(hasItem(DEFAULT_PHYSICAL_ACTIVITY.booleanValue())))
            .andExpect(jsonPath("$.[*].alcohol").value(hasItem(DEFAULT_ALCOHOL.booleanValue())))
            .andExpect(jsonPath("$.[*].ifStatement").value(hasItem(DEFAULT_IF_STATEMENT.toString())))
            .andExpect(jsonPath("$.[*].thenStatement").value(hasItem(DEFAULT_THEN_STATEMENT.toString())))
            .andExpect(jsonPath("$.[*].copingIfStatement").value(hasItem(DEFAULT_COPING_IF_STATEMENT.toString())))
            .andExpect(jsonPath("$.[*].copingThenStatement").value(hasItem(DEFAULT_COPING_THEN_STATEMENT.toString())))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDailyEvaluation() throws Exception {
        // Initialize the database
        dailyEvaluationRepository.saveAndFlush(dailyEvaluation);

        // Get the dailyEvaluation
        restDailyEvaluationMockMvc.perform(get("/api/daily-evaluations/{id}", dailyEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dailyEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.diet").value(DEFAULT_DIET.booleanValue()))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING.booleanValue()))
            .andExpect(jsonPath("$.physicalActivity").value(DEFAULT_PHYSICAL_ACTIVITY.booleanValue()))
            .andExpect(jsonPath("$.alcohol").value(DEFAULT_ALCOHOL.booleanValue()))
            .andExpect(jsonPath("$.ifStatement").value(DEFAULT_IF_STATEMENT.toString()))
            .andExpect(jsonPath("$.thenStatement").value(DEFAULT_THEN_STATEMENT.toString()))
            .andExpect(jsonPath("$.copingIfStatement").value(DEFAULT_COPING_IF_STATEMENT.toString()))
            .andExpect(jsonPath("$.copingThenStatement").value(DEFAULT_COPING_THEN_STATEMENT.toString()))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDailyEvaluation() throws Exception {
        // Get the dailyEvaluation
        restDailyEvaluationMockMvc.perform(get("/api/daily-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyEvaluation() throws Exception {
        // Initialize the database
        dailyEvaluationRepository.saveAndFlush(dailyEvaluation);
        int databaseSizeBeforeUpdate = dailyEvaluationRepository.findAll().size();

        // Update the dailyEvaluation
        DailyEvaluation updatedDailyEvaluation = dailyEvaluationRepository.findOne(dailyEvaluation.getId());
        updatedDailyEvaluation
            .type(UPDATED_TYPE)
            .diet(UPDATED_DIET)
            .smoking(UPDATED_SMOKING)
            .physicalActivity(UPDATED_PHYSICAL_ACTIVITY)
            .alcohol(UPDATED_ALCOHOL)
            .ifStatement(UPDATED_IF_STATEMENT)
            .thenStatement(UPDATED_THEN_STATEMENT)
            .copingIfStatement(UPDATED_COPING_IF_STATEMENT)
            .copingThenStatement(UPDATED_COPING_THEN_STATEMENT)
            .success(UPDATED_SUCCESS)
            .date(UPDATED_DATE);

        restDailyEvaluationMockMvc.perform(put("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDailyEvaluation)))
            .andExpect(status().isOk());

        // Validate the DailyEvaluation in the database
        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeUpdate);
        DailyEvaluation testDailyEvaluation = dailyEvaluationList.get(dailyEvaluationList.size() - 1);
        assertThat(testDailyEvaluation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDailyEvaluation.isDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testDailyEvaluation.isSmoking()).isEqualTo(UPDATED_SMOKING);
        assertThat(testDailyEvaluation.isPhysicalActivity()).isEqualTo(UPDATED_PHYSICAL_ACTIVITY);
        assertThat(testDailyEvaluation.isAlcohol()).isEqualTo(UPDATED_ALCOHOL);
        assertThat(testDailyEvaluation.getIfStatement()).isEqualTo(UPDATED_IF_STATEMENT);
        assertThat(testDailyEvaluation.getThenStatement()).isEqualTo(UPDATED_THEN_STATEMENT);
        assertThat(testDailyEvaluation.getCopingIfStatement()).isEqualTo(UPDATED_COPING_IF_STATEMENT);
        assertThat(testDailyEvaluation.getCopingThenStatement()).isEqualTo(UPDATED_COPING_THEN_STATEMENT);
        assertThat(testDailyEvaluation.isSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testDailyEvaluation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = dailyEvaluationRepository.findAll().size();

        // Create the DailyEvaluation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDailyEvaluationMockMvc.perform(put("/api/daily-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyEvaluation)))
            .andExpect(status().isCreated());

        // Validate the DailyEvaluation in the database
        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDailyEvaluation() throws Exception {
        // Initialize the database
        dailyEvaluationRepository.saveAndFlush(dailyEvaluation);
        int databaseSizeBeforeDelete = dailyEvaluationRepository.findAll().size();

        // Get the dailyEvaluation
        restDailyEvaluationMockMvc.perform(delete("/api/daily-evaluations/{id}", dailyEvaluation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DailyEvaluation> dailyEvaluationList = dailyEvaluationRepository.findAll();
        assertThat(dailyEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyEvaluation.class);
        DailyEvaluation dailyEvaluation1 = new DailyEvaluation();
        dailyEvaluation1.setId(1L);
        DailyEvaluation dailyEvaluation2 = new DailyEvaluation();
        dailyEvaluation2.setId(dailyEvaluation1.getId());
        assertThat(dailyEvaluation1).isEqualTo(dailyEvaluation2);
        dailyEvaluation2.setId(2L);
        assertThat(dailyEvaluation1).isNotEqualTo(dailyEvaluation2);
        dailyEvaluation1.setId(null);
        assertThat(dailyEvaluation1).isNotEqualTo(dailyEvaluation2);
    }
}
