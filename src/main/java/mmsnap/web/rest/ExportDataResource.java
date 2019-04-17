package mmsnap.web.rest;

import com.codahale.metrics.annotation.Timed;
import mmsnap.config.ApplicationProperties;
import mmsnap.domain.*;
import mmsnap.repository.*;
import mmsnap.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Resource to export data to an excel file
 */
@RestController
@RequestMapping("/api")
public class ExportDataResource
{
    private final Logger log = LoggerFactory.getLogger( ExportDataResource.class );
    private final EQVasRepository              eQVasRepository;
    private final HealthRiskRepository         healthRiskRepository;
    private final IntentionsAndPlansRepository intentionsAndPlansRepository;
    private final SelfEfficacyRepository       selfEfficacyRepository;
    private final SelfRatedHealthRepository    selfRatedHealthRepository;
    private final DailyEvaluationRepository    dailyEvaluationRepository;
    private final WeeklyEvaluationRepository   weeklyEvaluationRepository;

    private ApplicationProperties applicationProperties;

    public ExportDataResource( EQVasRepository eQVasRepository, HealthRiskRepository healthRiskRepository, IntentionsAndPlansRepository intentionsAndPlansRepository,
                               SelfEfficacyRepository selfEfficacyRepository, SelfRatedHealthRepository selfRatedHealthRepository,
                               DailyEvaluationRepository dailyEvaluationRepository, WeeklyEvaluationRepository weeklyEvaluationRepository, ApplicationProperties applicationProperties )
    {
        this.eQVasRepository = eQVasRepository;
        this.healthRiskRepository = healthRiskRepository;
        this.intentionsAndPlansRepository = intentionsAndPlansRepository;
        this.selfEfficacyRepository = selfEfficacyRepository;
        this.selfRatedHealthRepository = selfRatedHealthRepository;
        this.dailyEvaluationRepository = dailyEvaluationRepository;
        this.weeklyEvaluationRepository = weeklyEvaluationRepository;
        this.applicationProperties = applicationProperties;
    }

    public class DataExcelModel
    {
        public List<EQVas>              eqVasList;
        public List<HealthRisk>         healthRiskList;
        public List<IntentionsAndPlans> intentionsAndPlansList;
        public List<SelfEfficacy>       selfEfficacyList;
        public List<SelfRatedHealth>    selfRatedHealthList;
        public List<DailyEvaluation>    dailyEvaluationList;
        public List<WeeklyEvaluation>   weeklyEvaluationList;

        public DataExcelModel( List<EQVas> eqVasList, List<HealthRisk> healthRiskList, List<IntentionsAndPlans> intentionsAndPlansList, List<SelfEfficacy> selfEfficacyList,
                               List<SelfRatedHealth> selfRatedHealthList, List<DailyEvaluation> dailyEvaluationList, List<WeeklyEvaluation> weeklyEvaluationList )
        {
            this.eqVasList = eqVasList;
            this.healthRiskList = healthRiskList;
            this.intentionsAndPlansList = intentionsAndPlansList;
            this.selfEfficacyList = selfEfficacyList;
            this.selfRatedHealthList = selfRatedHealthList;
            this.dailyEvaluationList = dailyEvaluationList;
            this.weeklyEvaluationList = weeklyEvaluationList;
        }
    }

    @GetMapping( "/_export" )
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ModelAndView export()
    {
        log.debug( "REST request to export data to Excel" );

        DataExcelModel dataExcelModel = new DataExcelModel(
            eQVasRepository.findAll(),
            healthRiskRepository.findAll(),
            intentionsAndPlansRepository.findAll(),
            selfEfficacyRepository.findAll(),
            selfRatedHealthRepository.findAll(),
            dailyEvaluationRepository.findAll(),
            weeklyEvaluationRepository.findAll()
        );

        return new ModelAndView( new DataExcelReportView(), "dataExcelModel", dataExcelModel);
    }
}
