package mmsnap.domain;

import mmsnap.web.rest.ExportDataResource.DataExcelModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class DataExcelReportView extends AbstractXlsxView
{

    public DataExcelReportView()
    {
    }

    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        response.setHeader("Content-Disposition", "attachment;filename=\"mmsnap-data.xlsx\"");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding( "UTF-8" );
        DataExcelModel dataExcelModel = ( DataExcelModel) model.get( "dataExcelModel" );

        createEQVASSheet( workbook, dataExcelModel.eqVasList );
        createHealthRiskSheet( workbook, dataExcelModel.healthRiskList );
        createIntentionsAndPlansSheet( workbook, dataExcelModel.intentionsAndPlansList );
        createSelfEfficacySheet( workbook, dataExcelModel.selfEfficacyList );
        createSelfRatedHealthSheet( workbook, dataExcelModel.selfRatedHealthList );
        createDailyEvaluationsSheet( workbook, dataExcelModel.dailyEvaluationList );
        createWeeklyEvaluationsSheet( workbook, dataExcelModel.weeklyEvaluationList );

    }

    private void createWeeklyEvaluationsSheet( Workbook workbook, List<WeeklyEvaluation> weeklyEvaluationList )
    {
        Sheet sheet  = workbook.createSheet( "Weekly Evaluations" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "Diet" );
        header.createCell( 2 ).setCellValue( "Physical Activity" );
        header.createCell( 3 ).setCellValue( "Smoking" );
        header.createCell( 4 ).setCellValue( "Alcohol" );
        header.createCell( 5 ).setCellValue( "From" );
        header.createCell( 6 ).setCellValue( "To" );

        header.createCell( 7 ).setCellValue( "Date" );
        header.createCell( 8 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( WeeklyEvaluation data : weeklyEvaluationList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.getDiet() );
            row.createCell( 2 ).setCellValue( data.getPhysicalActivity() );
            row.createCell( 3 ).setCellValue( data.getSmoking() );
            row.createCell( 4 ).setCellValue( data.getAlcohol() );

            Calendar c = Calendar.getInstance();
            c.set( Calendar.YEAR, data.getYear() );
            c.set( Calendar.WEEK_OF_YEAR, data.getWeekOfYear() );
            c.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
            Cell f = row.createCell( 5 );
            f.setCellStyle( dateStyle );
            f.setCellValue( GregorianCalendar.from( c.toInstant().atZone( TimeZone.getDefault().toZoneId() ) ) );

            c.add( Calendar.DAY_OF_MONTH, 6 );
            Cell t = row.createCell( 6 );
            t.setCellStyle( dateStyle );
            t.setCellValue( GregorianCalendar.from( c.toInstant().atZone( TimeZone.getDefault().toZoneId() ) ) );

            Cell d = row.createCell( 7 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 8 ).setCellValue( data.getUser().getLogin() );
        }
    }

    private void createDailyEvaluationsSheet( Workbook workbook, List<DailyEvaluation> dailyEvaluationList )
    {
        Sheet sheet  = workbook.createSheet( "Daily Evaluations" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "Plan type" );
        header.createCell( 2 ).setCellValue( "Diet" );
        header.createCell( 3 ).setCellValue( "Physical Activity" );
        header.createCell( 4 ).setCellValue( "Smoking" );
        header.createCell( 5 ).setCellValue( "Alcohol" );
        header.createCell( 6 ).setCellValue( "If" );
        header.createCell( 7 ).setCellValue( "Then" );
        header.createCell( 8 ).setCellValue( "Coping If" );
        header.createCell( 9 ).setCellValue( "Coping Then" );
        header.createCell( 10 ).setCellValue( "Success" );

        header.createCell( 11 ).setCellValue( "Date" );
        header.createCell( 12 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( DailyEvaluation data : dailyEvaluationList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.getType().name() );
            row.createCell( 2 ).setCellValue( data.isDiet() );
            row.createCell( 3 ).setCellValue( data.isPhysicalActivity() );
            row.createCell( 4 ).setCellValue( data.isSmoking() );
            row.createCell( 5 ).setCellValue( data.isAlcohol() );
            row.createCell( 6 ).setCellValue( data.getIfStatement() );
            row.createCell( 7 ).setCellValue( data.getThenStatement() );
            row.createCell( 8 ).setCellValue( data.getCopingIfStatement() );
            row.createCell( 9 ).setCellValue( data.getCopingThenStatement() );
            row.createCell( 10 ).setCellValue( data.isSuccess() );

            Cell d = row.createCell( 11 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 12 ).setCellValue( data.getUser().getLogin() );
        }
    }

    private void createSelfRatedHealthSheet( Workbook workbook, List<SelfRatedHealth> selfRatedHealthList )
    {
        Sheet sheet  = workbook.createSheet( "Self Rated Health" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "One condition is more serious" );
        header.createCell( 2 ).setCellValue( "Time spent managing makes it difficult" );
        header.createCell( 3 ).setCellValue( "Feel overwhelmed" );
        header.createCell( 4 ).setCellValue( "Causes are linked" );
        header.createCell( 5 ).setCellValue( "Difficult to take all medications" );
        header.createCell( 6 ).setCellValue( "Limited activities" );
        header.createCell( 7 ).setCellValue( "Different medication problems" );
        header.createCell( 8 ).setCellValue( "Mixing medications" );
        header.createCell( 9 ).setCellValue( "Less effective treatments" );
        header.createCell( 10 ).setCellValue( "One causes another" );
        header.createCell( 11 ).setCellValue( "One condition dominates" );
        header.createCell( 12 ).setCellValue( "Conditions interact" );
        header.createCell( 13 ).setCellValue( "Difficult to get best treatment" );
        header.createCell( 14 ).setCellValue( "Reduced social life" );
        header.createCell( 15 ).setCellValue( "Unhappy" );
        header.createCell( 16 ).setCellValue( "Anxious" );
        header.createCell( 17 ).setCellValue( "Angry" );
        header.createCell( 18 ).setCellValue( "Sad" );
        header.createCell( 19 ).setCellValue( "Irritable" );
        header.createCell( 20 ).setCellValue( "When sad managing is struggle" );

        header.createCell( 21 ).setCellValue( "Phase" );
        header.createCell( 22 ).setCellValue( "Date" );
        header.createCell( 23 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( SelfRatedHealth data : selfRatedHealthList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.getOneConditionMoreSerious().name() );
            row.createCell( 2 ).setCellValue( data.getTimeSpentManaging().name() );
            row.createCell( 3 ).setCellValue( data.getFeelOverwhelmed().name() );
            row.createCell( 4 ).setCellValue( data.getCausesAreLinked().name() );
            row.createCell( 5 ).setCellValue( data.getDifficultAllMedications().name() );
            row.createCell( 6 ).setCellValue( data.getLimitedActivities().name() );
            row.createCell( 7 ).setCellValue( data.getDifferentMedicationsProblems().name() );
            row.createCell( 8 ).setCellValue( data.getMixingMedications().name() );
            row.createCell( 9 ).setCellValue( data.getLessEffectiveTreatments().name() );
            row.createCell( 10 ).setCellValue( data.getOneCauseAnother().name() );
            row.createCell( 11 ).setCellValue( data.getOneDominates().name() );
            row.createCell( 12 ).setCellValue( data.getConditionsInteract().name() );
            row.createCell( 13 ).setCellValue( data.getDifficultBestTreatment().name() );
            row.createCell( 14 ).setCellValue( data.getReducedSocialLife().name() );
            row.createCell( 15 ).setCellValue( data.getUnhappy().name() );
            row.createCell( 16 ).setCellValue( data.getAnxious().name() );
            row.createCell( 17 ).setCellValue( data.getAngry().name() );
            row.createCell( 18 ).setCellValue( data.getSad().name() );
            row.createCell( 19 ).setCellValue( data.getIrritable().name() );
            row.createCell( 20 ).setCellValue( data.getSadStruggle().name() );

            row.createCell( 21 ).setCellValue( data.getPhase().name() );
            Cell d = row.createCell( 22 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 23 ).setCellValue( data.getUser().getLogin() );

        }
    }

    private void createSelfEfficacySheet( Workbook workbook, List<SelfEfficacy> selfEfficacyList )
    {
        Sheet sheet  = workbook.createSheet( "Self Efficacy" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "Healthier Lifestyle" );
        header.createCell( 2 ).setCellValue( "Behaviour Goals per week" );
        header.createCell( 3 ).setCellValue( "Manage Multimorbidity" );

        header.createCell( 4 ).setCellValue( "Phase" );
        header.createCell( 5 ).setCellValue( "Date" );
        header.createCell( 6 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( SelfEfficacy data : selfEfficacyList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.isHealthierLifestyle() );
            row.createCell( 2 ).setCellValue( data.isCompleteBehaviourGoals() );
            row.createCell( 3 ).setCellValue( data.isManageMultimorbidity() );

            row.createCell( 4 ).setCellValue( data.getPhase().name() );
            Cell d = row.createCell( 5 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 6 ).setCellValue( data.getUser().getLogin() );

        }
    }

    private void createIntentionsAndPlansSheet( Workbook workbook, List<IntentionsAndPlans> intentionsAndPlansList )
    {
        Sheet sheet  = workbook.createSheet( "Intentions and Plans" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "When to exercise" );
        header.createCell( 2 ).setCellValue( "Past week exercise" );
        header.createCell( 3 ).setCellValue( "Where to exercise" );
        header.createCell( 4 ).setCellValue( "How to exercise" );
        header.createCell( 5 ).setCellValue( "How often to exercise" );
        header.createCell( 6 ).setCellValue( "With whom to exercise" );
        header.createCell( 7 ).setCellValue( "If something interferes" );
        header.createCell( 8 ).setCellValue( "Cope with setbacks" );
        header.createCell( 9 ).setCellValue( "What to do in difficult situations" );
        header.createCell( 10 ).setCellValue( "Which good opportunities to take" );
        header.createCell( 11 ).setCellValue( "Prevent lapses" );
        header.createCell( 12 ).setCellValue( "Exercise several times per week" );
        header.createCell( 13 ).setCellValue( "Work up sweat regularly" );
        header.createCell( 14 ).setCellValue( "Exercise regularly" );
        header.createCell( 15 ).setCellValue( "Minimum 30 minutes physical activity" );
        header.createCell( 16 ).setCellValue( "Increase leisure time activity" );
        header.createCell( 17 ).setCellValue( "Adhere to exercise regime" );
        header.createCell( 18 ).setCellValue( "Phase" );
        header.createCell( 19 ).setCellValue( "Date" );
        header.createCell( 20 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( IntentionsAndPlans data : intentionsAndPlansList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.isWhenToExercise() );
            row.createCell( 2 ).setCellValue( data.isPastWeekExercise() );
            row.createCell( 3 ).setCellValue( data.isExerciseWhere() );
            row.createCell( 4 ).setCellValue( data.isExerciseHow() );
            row.createCell( 5 ).setCellValue( data.isExerciseHowOften() );
            row.createCell( 6 ).setCellValue( data.isExerciseWithWhom() );
            row.createCell( 7 ).setCellValue( data.isPlansInterfere() );
            row.createCell( 8 ).setCellValue( data.isSetbacksCope() );
            row.createCell( 9 ).setCellValue( data.isDifficultSituations() );
            row.createCell( 10 ).setCellValue( data.isGoodOpportunities() );
            row.createCell( 11 ).setCellValue( data.isPreventLapses() );
            row.createCell( 12 ).setCellValue( data.isExerciseSeveralTimesPerWeek() );
            row.createCell( 13 ).setCellValue( data.isWorkUpSweat() );
            row.createCell( 14 ).setCellValue( data.isExerciseRegularly() );
            row.createCell( 15 ).setCellValue( data.isMinimumPhysicalActivity() );
            row.createCell( 16 ).setCellValue( data.isLeisureTimeActivity() );
            row.createCell( 17 ).setCellValue( data.isExerciseDuringRehabilitation() );

            row.createCell( 18 ).setCellValue( data.getPhase().name() );
            Cell d = row.createCell( 19 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 20 ).setCellValue( data.getUser().getLogin() );

        }
    }

    private void createHealthRiskSheet( Workbook workbook, List<HealthRisk> healthRiskList )
    {
        Sheet sheet  = workbook.createSheet( "Health Risk Behaviours" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "Diet" );
        header.createCell( 2 ).setCellValue( "Physical Activity" );
        header.createCell( 3 ).setCellValue( "Smoking" );
        header.createCell( 4 ).setCellValue( "Alcohol" );
        header.createCell( 5 ).setCellValue( "Phase" );
        header.createCell( 6 ).setCellValue( "Date" );
        header.createCell( 7 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( HealthRisk data : healthRiskList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.isDiet() );
            row.createCell( 2 ).setCellValue( data.isPhysicalActivity() );
            row.createCell( 3 ).setCellValue( data.isSmoking() );
            row.createCell( 4 ).setCellValue( data.isAlcohol() );

            row.createCell( 5 ).setCellValue( data.getPhase().name() );
            Cell d = row.createCell( 6 );
            d.setCellStyle( dateStyle );
            d.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 7 ).setCellValue( data.getUser().getLogin() );

        }
    }

    private void createEQVASSheet( Workbook workbook, List<EQVas> eqVasList )
    {
        Sheet sheet  = workbook.createSheet( "EQVAS" );
        Row   header = sheet.createRow( 0 );
        header.createCell( 0 ).setCellValue( "ID" );
        header.createCell( 1 ).setCellValue( "Score" );
        header.createCell( 2 ).setCellValue( "Phase" );
        header.createCell( 3 ).setCellValue( "Date" );
        header.createCell( 4 ).setCellValue( "User" );

        CellStyle      dateStyle    = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat( createHelper.createDataFormat().getFormat( "dd/mm/yyyy HH:MM:SS" ) );

        int rowNum = 1;
        for( EQVas data : eqVasList )
        {
            Row row = sheet.createRow( rowNum++ );
            row.createCell( 0 ).setCellValue( data.getId() );
            row.createCell( 1 ).setCellValue( data.getScore() );

            row.createCell( 2 ).setCellValue( data.getPhase().name() );
            Cell c3 = row.createCell( 3 );
            c3.setCellStyle( dateStyle );
            c3.setCellValue( GregorianCalendar.from( data.getDate().atZone( TimeZone.getDefault().toZoneId() ) ) );
            row.createCell( 4 ).setCellValue( data.getUser().getLogin() );

        }
    }
}
