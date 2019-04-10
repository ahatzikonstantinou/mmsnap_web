package mmsnap.web.rest;

import mmsnap.MmsnapWebApp;

import mmsnap.domain.WeeklyEvaluation;
import mmsnap.repository.WeeklyEvaluationRepository;
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

/**
 * Test class for the WeeklyEvaluationResource REST controller.
 *
 * @see WeeklyEvaluationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MmsnapWebApp.class)
public class WeeklyEvaluationResourceIntTest {

    private static final Integer DEFAULT_DIET = 1;
    private static final Integer UPDATED_DIET = 2;

    private static final Integer DEFAULT_SMOKING = 1;
    private static final Integer UPDATED_SMOKING = 2;

    private static final Integer DEFAULT_PHYSICAL_ACTIVITY = 1;
    private static final Integer UPDATED_PHYSICAL_ACTIVITY = 2;

    private static final Integer DEFAULT_ALCOHOL = 1;
    private static final Integer UPDATED_ALCOHOL = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_WEEK_OF_YEAR = 1;
    private static final Integer UPDATED_WEEK_OF_YEAR = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WeeklyEvaluationRepository weeklyEvaluationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeeklyEvaluationMockMvc;

    private WeeklyEvaluation weeklyEvaluation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeeklyEvaluationResource weeklyEvaluationResource = new WeeklyEvaluationResource(weeklyEvaluationRepository);
        this.restWeeklyEvaluationMockMvc = MockMvcBuilders.standaloneSetup(weeklyEvaluationResource)
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
    public static WeeklyEvaluation createEntity(EntityManager em) {
        WeeklyEvaluation weeklyEvaluation = new WeeklyEvaluation()
            .diet(DEFAULT_DIET)
            .smoking(DEFAULT_SMOKING)
            .physicalActivity(DEFAULT_PHYSICAL_ACTIVITY)
            .alcohol(DEFAULT_ALCOHOL)
            .year(DEFAULT_YEAR)
            .weekOfYear(DEFAULT_WEEK_OF_YEAR)
            .date(DEFAULT_DATE);
        return weeklyEvaluation;
    }

    @Before
    public void initTest() {
        weeklyEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeeklyEvaluation() throws Exception {
        int databaseSizeBeforeCreate = weeklyEvaluationRepository.findAll().size();

        // Create the WeeklyEvaluation
        restWeeklyEvaluationMockMvc.perform(post("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isCreated());

        // Validate the WeeklyEvaluation in the database
        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        WeeklyEvaluation testWeeklyEvaluation = weeklyEvaluationList.get(weeklyEvaluationList.size() - 1);
        assertThat(testWeeklyEvaluation.getDiet()).isEqualTo(DEFAULT_DIET);
        assertThat(testWeeklyEvaluation.getSmoking()).isEqualTo(DEFAULT_SMOKING);
        assertThat(testWeeklyEvaluation.getPhysicalActivity()).isEqualTo(DEFAULT_PHYSICAL_ACTIVITY);
        assertThat(testWeeklyEvaluation.getAlcohol()).isEqualTo(DEFAULT_ALCOHOL);
        assertThat(testWeeklyEvaluation.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testWeeklyEvaluation.getWeekOfYear()).isEqualTo(DEFAULT_WEEK_OF_YEAR);
        assertThat(testWeeklyEvaluation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createWeeklyEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weeklyEvaluationRepository.findAll().size();

        // Create the WeeklyEvaluation with an existing ID
        weeklyEvaluation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeeklyEvaluationMockMvc.perform(post("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isBadRequest());

        // Validate the WeeklyEvaluation in the database
        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = weeklyEvaluationRepository.findAll().size();
        // set the field null
        weeklyEvaluation.setYear(null);

        // Create the WeeklyEvaluation, which fails.

        restWeeklyEvaluationMockMvc.perform(post("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isBadRequest());

        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeekOfYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = weeklyEvaluationRepository.findAll().size();
        // set the field null
        weeklyEvaluation.setWeekOfYear(null);

        // Create the WeeklyEvaluation, which fails.

        restWeeklyEvaluationMockMvc.perform(post("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isBadRequest());

        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = weeklyEvaluationRepository.findAll().size();
        // set the field null
        weeklyEvaluation.setDate(null);

        // Create the WeeklyEvaluation, which fails.

        restWeeklyEvaluationMockMvc.perform(post("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isBadRequest());

        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeeklyEvaluations() throws Exception {
        // Initialize the database
        weeklyEvaluationRepository.saveAndFlush(weeklyEvaluation);

        // Get all the weeklyEvaluationList
        restWeeklyEvaluationMockMvc.perform(get("/api/weekly-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weeklyEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].diet").value(hasItem(DEFAULT_DIET)))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING)))
            .andExpect(jsonPath("$.[*].physicalActivity").value(hasItem(DEFAULT_PHYSICAL_ACTIVITY)))
            .andExpect(jsonPath("$.[*].alcohol").value(hasItem(DEFAULT_ALCOHOL)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].weekOfYear").value(hasItem(DEFAULT_WEEK_OF_YEAR)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getWeeklyEvaluation() throws Exception {
        // Initialize the database
        weeklyEvaluationRepository.saveAndFlush(weeklyEvaluation);

        // Get the weeklyEvaluation
        restWeeklyEvaluationMockMvc.perform(get("/api/weekly-evaluations/{id}", weeklyEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weeklyEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.diet").value(DEFAULT_DIET))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING))
            .andExpect(jsonPath("$.physicalActivity").value(DEFAULT_PHYSICAL_ACTIVITY))
            .andExpect(jsonPath("$.alcohol").value(DEFAULT_ALCOHOL))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.weekOfYear").value(DEFAULT_WEEK_OF_YEAR))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeeklyEvaluation() throws Exception {
        // Get the weeklyEvaluation
        restWeeklyEvaluationMockMvc.perform(get("/api/weekly-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeeklyEvaluation() throws Exception {
        // Initialize the database
        weeklyEvaluationRepository.saveAndFlush(weeklyEvaluation);
        int databaseSizeBeforeUpdate = weeklyEvaluationRepository.findAll().size();

        // Update the weeklyEvaluation
        WeeklyEvaluation updatedWeeklyEvaluation = weeklyEvaluationRepository.findOne(weeklyEvaluation.getId());
        updatedWeeklyEvaluation
            .diet(UPDATED_DIET)
            .smoking(UPDATED_SMOKING)
            .physicalActivity(UPDATED_PHYSICAL_ACTIVITY)
            .alcohol(UPDATED_ALCOHOL)
            .year(UPDATED_YEAR)
            .weekOfYear(UPDATED_WEEK_OF_YEAR)
            .date(UPDATED_DATE);

        restWeeklyEvaluationMockMvc.perform(put("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeeklyEvaluation)))
            .andExpect(status().isOk());

        // Validate the WeeklyEvaluation in the database
        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeUpdate);
        WeeklyEvaluation testWeeklyEvaluation = weeklyEvaluationList.get(weeklyEvaluationList.size() - 1);
        assertThat(testWeeklyEvaluation.getDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testWeeklyEvaluation.getSmoking()).isEqualTo(UPDATED_SMOKING);
        assertThat(testWeeklyEvaluation.getPhysicalActivity()).isEqualTo(UPDATED_PHYSICAL_ACTIVITY);
        assertThat(testWeeklyEvaluation.getAlcohol()).isEqualTo(UPDATED_ALCOHOL);
        assertThat(testWeeklyEvaluation.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testWeeklyEvaluation.getWeekOfYear()).isEqualTo(UPDATED_WEEK_OF_YEAR);
        assertThat(testWeeklyEvaluation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWeeklyEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = weeklyEvaluationRepository.findAll().size();

        // Create the WeeklyEvaluation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeeklyEvaluationMockMvc.perform(put("/api/weekly-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyEvaluation)))
            .andExpect(status().isCreated());

        // Validate the WeeklyEvaluation in the database
        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeeklyEvaluation() throws Exception {
        // Initialize the database
        weeklyEvaluationRepository.saveAndFlush(weeklyEvaluation);
        int databaseSizeBeforeDelete = weeklyEvaluationRepository.findAll().size();

        // Get the weeklyEvaluation
        restWeeklyEvaluationMockMvc.perform(delete("/api/weekly-evaluations/{id}", weeklyEvaluation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WeeklyEvaluation> weeklyEvaluationList = weeklyEvaluationRepository.findAll();
        assertThat(weeklyEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeeklyEvaluation.class);
        WeeklyEvaluation weeklyEvaluation1 = new WeeklyEvaluation();
        weeklyEvaluation1.setId(1L);
        WeeklyEvaluation weeklyEvaluation2 = new WeeklyEvaluation();
        weeklyEvaluation2.setId(weeklyEvaluation1.getId());
        assertThat(weeklyEvaluation1).isEqualTo(weeklyEvaluation2);
        weeklyEvaluation2.setId(2L);
        assertThat(weeklyEvaluation1).isNotEqualTo(weeklyEvaluation2);
        weeklyEvaluation1.setId(null);
        assertThat(weeklyEvaluation1).isNotEqualTo(weeklyEvaluation2);
    }
}
