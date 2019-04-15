package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.AssessmentPhase;

/**
 * A IntentionsAndPlans.
 */
@Entity
@Table(name = "intentions_and_plans")
public class IntentionsAndPlans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "when_to_exercise")
    private Boolean whenToExercise;

    @Column(name = "past_week_exercise")
    private Boolean pastWeekExercise;

    @Column(name = "exercise_where")
    private Boolean exerciseWhere;

    @Column(name = "exercise_how")
    private Boolean exerciseHow;

    @Column(name = "exercise_how_often")
    private Boolean exerciseHowOften;

    @Column(name = "exercise_with_whom")
    private Boolean exerciseWithWhom;

    @Column(name = "plans_interfere")
    private Boolean plansInterfere;

    @Column(name = "setbacks_cope")
    private Boolean setbacksCope;

    @Column(name = "difficult_situations")
    private Boolean difficultSituations;

    @Column(name = "good_opportunities")
    private Boolean goodOpportunities;

    @Column(name = "prevent_lapses")
    private Boolean preventLapses;

    @Column(name = "exercise_several_times_per_week")
    private Boolean exerciseSeveralTimesPerWeek;

    @Column(name = "work_up_sweat")
    private Boolean workUpSweat;

    @Column(name = "exercise_regularly")
    private Boolean exerciseRegularly;

    @Column(name = "minimum_physical_activity")
    private Boolean minimumPhysicalActivity;

    @Column(name = "leisure_time_activity")
    private Boolean leisureTimeActivity;

    @Column(name = "exercise_during_rehabilitation")
    private Boolean exerciseDuringRehabilitation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false)
    private AssessmentPhase phase;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isWhenToExercise() {
        return whenToExercise;
    }

    public IntentionsAndPlans whenToExercise(Boolean whenToExercise) {
        this.whenToExercise = whenToExercise;
        return this;
    }

    public void setWhenToExercise(Boolean whenToExercise) {
        this.whenToExercise = whenToExercise;
    }

    public Boolean isPastWeekExercise() {
        return pastWeekExercise;
    }

    public IntentionsAndPlans pastWeekExercise(Boolean pastWeekExercise) {
        this.pastWeekExercise = pastWeekExercise;
        return this;
    }

    public void setPastWeekExercise(Boolean pastWeekExercise) {
        this.pastWeekExercise = pastWeekExercise;
    }

    public Boolean isExerciseWhere() {
        return exerciseWhere;
    }

    public IntentionsAndPlans exerciseWhere(Boolean exerciseWhere) {
        this.exerciseWhere = exerciseWhere;
        return this;
    }

    public void setExerciseWhere(Boolean exerciseWhere) {
        this.exerciseWhere = exerciseWhere;
    }

    public Boolean isExerciseHow() {
        return exerciseHow;
    }

    public IntentionsAndPlans exerciseHow(Boolean exerciseHow) {
        this.exerciseHow = exerciseHow;
        return this;
    }

    public void setExerciseHow(Boolean exerciseHow) {
        this.exerciseHow = exerciseHow;
    }

    public Boolean isExerciseHowOften() {
        return exerciseHowOften;
    }

    public IntentionsAndPlans exerciseHowOften(Boolean exerciseHowOften) {
        this.exerciseHowOften = exerciseHowOften;
        return this;
    }

    public void setExerciseHowOften(Boolean exerciseHowOften) {
        this.exerciseHowOften = exerciseHowOften;
    }

    public Boolean isExerciseWithWhom() {
        return exerciseWithWhom;
    }

    public IntentionsAndPlans exerciseWithWhom(Boolean exerciseWithWhom) {
        this.exerciseWithWhom = exerciseWithWhom;
        return this;
    }

    public void setExerciseWithWhom(Boolean exerciseWithWhom) {
        this.exerciseWithWhom = exerciseWithWhom;
    }

    public Boolean isPlansInterfere() {
        return plansInterfere;
    }

    public IntentionsAndPlans plansInterfere(Boolean plansInterfere) {
        this.plansInterfere = plansInterfere;
        return this;
    }

    public void setPlansInterfere(Boolean plansInterfere) {
        this.plansInterfere = plansInterfere;
    }

    public Boolean isSetbacksCope() {
        return setbacksCope;
    }

    public IntentionsAndPlans setbacksCope(Boolean setbacksCope) {
        this.setbacksCope = setbacksCope;
        return this;
    }

    public void setSetbacksCope(Boolean setbacksCope) {
        this.setbacksCope = setbacksCope;
    }

    public Boolean isDifficultSituations() {
        return difficultSituations;
    }

    public IntentionsAndPlans difficultSituations(Boolean difficultSituations) {
        this.difficultSituations = difficultSituations;
        return this;
    }

    public void setDifficultSituations(Boolean difficultSituations) {
        this.difficultSituations = difficultSituations;
    }

    public Boolean isGoodOpportunities() {
        return goodOpportunities;
    }

    public IntentionsAndPlans goodOpportunities(Boolean goodOpportunities) {
        this.goodOpportunities = goodOpportunities;
        return this;
    }

    public void setGoodOpportunities(Boolean goodOpportunities) {
        this.goodOpportunities = goodOpportunities;
    }

    public Boolean isPreventLapses() {
        return preventLapses;
    }

    public IntentionsAndPlans preventLapses(Boolean preventLapses) {
        this.preventLapses = preventLapses;
        return this;
    }

    public void setPreventLapses(Boolean preventLapses) {
        this.preventLapses = preventLapses;
    }

    public Boolean isExerciseSeveralTimesPerWeek() {
        return exerciseSeveralTimesPerWeek;
    }

    public IntentionsAndPlans exerciseSeveralTimesPerWeek(Boolean exerciseSeveralTimesPerWeek) {
        this.exerciseSeveralTimesPerWeek = exerciseSeveralTimesPerWeek;
        return this;
    }

    public void setExerciseSeveralTimesPerWeek(Boolean exerciseSeveralTimesPerWeek) {
        this.exerciseSeveralTimesPerWeek = exerciseSeveralTimesPerWeek;
    }

    public Boolean isWorkUpSweat() {
        return workUpSweat;
    }

    public IntentionsAndPlans workUpSweat(Boolean workUpSweat) {
        this.workUpSweat = workUpSweat;
        return this;
    }

    public void setWorkUpSweat(Boolean workUpSweat) {
        this.workUpSweat = workUpSweat;
    }

    public Boolean isExerciseRegularly() {
        return exerciseRegularly;
    }

    public IntentionsAndPlans exerciseRegularly(Boolean exerciseRegularly) {
        this.exerciseRegularly = exerciseRegularly;
        return this;
    }

    public void setExerciseRegularly(Boolean exerciseRegularly) {
        this.exerciseRegularly = exerciseRegularly;
    }

    public Boolean isMinimumPhysicalActivity() {
        return minimumPhysicalActivity;
    }

    public IntentionsAndPlans minimumPhysicalActivity(Boolean minimumPhysicalActivity) {
        this.minimumPhysicalActivity = minimumPhysicalActivity;
        return this;
    }

    public void setMinimumPhysicalActivity(Boolean minimumPhysicalActivity) {
        this.minimumPhysicalActivity = minimumPhysicalActivity;
    }

    public Boolean isLeisureTimeActivity() {
        return leisureTimeActivity;
    }

    public IntentionsAndPlans leisureTimeActivity(Boolean leisureTimeActivity) {
        this.leisureTimeActivity = leisureTimeActivity;
        return this;
    }

    public void setLeisureTimeActivity(Boolean leisureTimeActivity) {
        this.leisureTimeActivity = leisureTimeActivity;
    }

    public Boolean isExerciseDuringRehabilitation() {
        return exerciseDuringRehabilitation;
    }

    public IntentionsAndPlans exerciseDuringRehabilitation(Boolean exerciseDuringRehabilitation) {
        this.exerciseDuringRehabilitation = exerciseDuringRehabilitation;
        return this;
    }

    public void setExerciseDuringRehabilitation(Boolean exerciseDuringRehabilitation) {
        this.exerciseDuringRehabilitation = exerciseDuringRehabilitation;
    }

    public AssessmentPhase getPhase() {
        return phase;
    }

    public IntentionsAndPlans phase(AssessmentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(AssessmentPhase phase) {
        this.phase = phase;
    }

    public Instant getDate() {
        return date;
    }

    public IntentionsAndPlans date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public IntentionsAndPlans user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntentionsAndPlans intentionsAndPlans = (IntentionsAndPlans) o;
        if (intentionsAndPlans.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intentionsAndPlans.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntentionsAndPlans{" +
            "id=" + getId() +
            ", whenToExercise='" + isWhenToExercise() + "'" +
            ", pastWeekExercise='" + isPastWeekExercise() + "'" +
            ", exerciseWhere='" + isExerciseWhere() + "'" +
            ", exerciseHow='" + isExerciseHow() + "'" +
            ", exerciseHowOften='" + isExerciseHowOften() + "'" +
            ", exerciseWithWhom='" + isExerciseWithWhom() + "'" +
            ", plansInterfere='" + isPlansInterfere() + "'" +
            ", setbacksCope='" + isSetbacksCope() + "'" +
            ", difficultSituations='" + isDifficultSituations() + "'" +
            ", goodOpportunities='" + isGoodOpportunities() + "'" +
            ", preventLapses='" + isPreventLapses() + "'" +
            ", exerciseSeveralTimesPerWeek='" + isExerciseSeveralTimesPerWeek() + "'" +
            ", workUpSweat='" + isWorkUpSweat() + "'" +
            ", exerciseRegularly='" + isExerciseRegularly() + "'" +
            ", minimumPhysicalActivity='" + isMinimumPhysicalActivity() + "'" +
            ", leisureTimeActivity='" + isLeisureTimeActivity() + "'" +
            ", exerciseDuringRehabilitation='" + isExerciseDuringRehabilitation() + "'" +
            ", phase='" + getPhase() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
