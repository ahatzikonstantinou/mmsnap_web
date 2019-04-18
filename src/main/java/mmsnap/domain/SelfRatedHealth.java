package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.HealthScale;

import mmsnap.domain.enumeration.AssessmentPhase;

/**
 * A SelfRatedHealth.
 */
@Entity
@Table(name = "self_rated_health")
public class SelfRatedHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "one_condition_more_serious", nullable = false)
    private HealthScale oneConditionMoreSerious;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_spent_managing", nullable = false)
    private HealthScale timeSpentManaging;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "feel_overwhelmed", nullable = false)
    private HealthScale feelOverwhelmed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "causes_are_linked", nullable = false)
    private HealthScale causesAreLinked;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "difficult_all_medications", nullable = false)
    private HealthScale difficultAllMedications;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "limited_activities", nullable = false)
    private HealthScale limitedActivities;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "different_medications_problems", nullable = false)
    private HealthScale differentMedicationsProblems;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mixing_medications", nullable = false)
    private HealthScale mixingMedications;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "less_effective_treatments", nullable = false)
    private HealthScale lessEffectiveTreatments;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "one_cause_another", nullable = false)
    private HealthScale oneCauseAnother;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "one_dominates", nullable = false)
    private HealthScale oneDominates;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "conditions_interact", nullable = false)
    private HealthScale conditionsInteract;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "difficult_best_treatment", nullable = false)
    private HealthScale difficultBestTreatment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reduced_social_life", nullable = false)
    private HealthScale reducedSocialLife;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unhappy", nullable = false)
    private HealthScale unhappy;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "anxious", nullable = false)
    private HealthScale anxious;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "angry", nullable = false)
    private HealthScale angry;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sad", nullable = false)
    private HealthScale sad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "irritable", nullable = false)
    private HealthScale irritable;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sad_struggle", nullable = false)
    private HealthScale sadStruggle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false)
    private AssessmentPhase phase;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @ManyToOne
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HealthScale getOneConditionMoreSerious() {
        return oneConditionMoreSerious;
    }

    public SelfRatedHealth oneConditionMoreSerious(HealthScale oneConditionMoreSerious) {
        this.oneConditionMoreSerious = oneConditionMoreSerious;
        return this;
    }

    public void setOneConditionMoreSerious(HealthScale oneConditionMoreSerious) {
        this.oneConditionMoreSerious = oneConditionMoreSerious;
    }

    public HealthScale getTimeSpentManaging() {
        return timeSpentManaging;
    }

    public SelfRatedHealth timeSpentManaging(HealthScale timeSpentManaging) {
        this.timeSpentManaging = timeSpentManaging;
        return this;
    }

    public void setTimeSpentManaging(HealthScale timeSpentManaging) {
        this.timeSpentManaging = timeSpentManaging;
    }

    public HealthScale getFeelOverwhelmed() {
        return feelOverwhelmed;
    }

    public SelfRatedHealth feelOverwhelmed(HealthScale feelOverwhelmed) {
        this.feelOverwhelmed = feelOverwhelmed;
        return this;
    }

    public void setFeelOverwhelmed(HealthScale feelOverwhelmed) {
        this.feelOverwhelmed = feelOverwhelmed;
    }

    public HealthScale getCausesAreLinked() {
        return causesAreLinked;
    }

    public SelfRatedHealth causesAreLinked(HealthScale causesAreLinked) {
        this.causesAreLinked = causesAreLinked;
        return this;
    }

    public void setCausesAreLinked(HealthScale causesAreLinked) {
        this.causesAreLinked = causesAreLinked;
    }

    public HealthScale getDifficultAllMedications() {
        return difficultAllMedications;
    }

    public SelfRatedHealth difficultAllMedications(HealthScale difficultAllMedications) {
        this.difficultAllMedications = difficultAllMedications;
        return this;
    }

    public void setDifficultAllMedications(HealthScale difficultAllMedications) {
        this.difficultAllMedications = difficultAllMedications;
    }

    public HealthScale getLimitedActivities() {
        return limitedActivities;
    }

    public SelfRatedHealth limitedActivities(HealthScale limitedActivities) {
        this.limitedActivities = limitedActivities;
        return this;
    }

    public void setLimitedActivities(HealthScale limitedActivities) {
        this.limitedActivities = limitedActivities;
    }

    public HealthScale getDifferentMedicationsProblems() {
        return differentMedicationsProblems;
    }

    public SelfRatedHealth differentMedicationsProblems(HealthScale differentMedicationsProblems) {
        this.differentMedicationsProblems = differentMedicationsProblems;
        return this;
    }

    public void setDifferentMedicationsProblems(HealthScale differentMedicationsProblems) {
        this.differentMedicationsProblems = differentMedicationsProblems;
    }

    public HealthScale getMixingMedications() {
        return mixingMedications;
    }

    public SelfRatedHealth mixingMedications(HealthScale mixingMedications) {
        this.mixingMedications = mixingMedications;
        return this;
    }

    public void setMixingMedications(HealthScale mixingMedications) {
        this.mixingMedications = mixingMedications;
    }

    public HealthScale getLessEffectiveTreatments() {
        return lessEffectiveTreatments;
    }

    public SelfRatedHealth lessEffectiveTreatments(HealthScale lessEffectiveTreatments) {
        this.lessEffectiveTreatments = lessEffectiveTreatments;
        return this;
    }

    public void setLessEffectiveTreatments(HealthScale lessEffectiveTreatments) {
        this.lessEffectiveTreatments = lessEffectiveTreatments;
    }

    public HealthScale getOneCauseAnother() {
        return oneCauseAnother;
    }

    public SelfRatedHealth oneCauseAnother(HealthScale oneCauseAnother) {
        this.oneCauseAnother = oneCauseAnother;
        return this;
    }

    public void setOneCauseAnother(HealthScale oneCauseAnother) {
        this.oneCauseAnother = oneCauseAnother;
    }

    public HealthScale getOneDominates() {
        return oneDominates;
    }

    public SelfRatedHealth oneDominates(HealthScale oneDominates) {
        this.oneDominates = oneDominates;
        return this;
    }

    public void setOneDominates(HealthScale oneDominates) {
        this.oneDominates = oneDominates;
    }

    public HealthScale getConditionsInteract() {
        return conditionsInteract;
    }

    public SelfRatedHealth conditionsInteract(HealthScale conditionsInteract) {
        this.conditionsInteract = conditionsInteract;
        return this;
    }

    public void setConditionsInteract(HealthScale conditionsInteract) {
        this.conditionsInteract = conditionsInteract;
    }

    public HealthScale getDifficultBestTreatment() {
        return difficultBestTreatment;
    }

    public SelfRatedHealth difficultBestTreatment(HealthScale difficultBestTreatment) {
        this.difficultBestTreatment = difficultBestTreatment;
        return this;
    }

    public void setDifficultBestTreatment(HealthScale difficultBestTreatment) {
        this.difficultBestTreatment = difficultBestTreatment;
    }

    public HealthScale getReducedSocialLife() {
        return reducedSocialLife;
    }

    public SelfRatedHealth reducedSocialLife(HealthScale reducedSocialLife) {
        this.reducedSocialLife = reducedSocialLife;
        return this;
    }

    public void setReducedSocialLife(HealthScale reducedSocialLife) {
        this.reducedSocialLife = reducedSocialLife;
    }

    public HealthScale getUnhappy() {
        return unhappy;
    }

    public SelfRatedHealth unhappy(HealthScale unhappy) {
        this.unhappy = unhappy;
        return this;
    }

    public void setUnhappy(HealthScale unhappy) {
        this.unhappy = unhappy;
    }

    public HealthScale getAnxious() {
        return anxious;
    }

    public SelfRatedHealth anxious(HealthScale anxious) {
        this.anxious = anxious;
        return this;
    }

    public void setAnxious(HealthScale anxious) {
        this.anxious = anxious;
    }

    public HealthScale getAngry() {
        return angry;
    }

    public SelfRatedHealth angry(HealthScale angry) {
        this.angry = angry;
        return this;
    }

    public void setAngry(HealthScale angry) {
        this.angry = angry;
    }

    public HealthScale getSad() {
        return sad;
    }

    public SelfRatedHealth sad(HealthScale sad) {
        this.sad = sad;
        return this;
    }

    public void setSad(HealthScale sad) {
        this.sad = sad;
    }

    public HealthScale getIrritable() {
        return irritable;
    }

    public SelfRatedHealth irritable(HealthScale irritable) {
        this.irritable = irritable;
        return this;
    }

    public void setIrritable(HealthScale irritable) {
        this.irritable = irritable;
    }

    public HealthScale getSadStruggle() {
        return sadStruggle;
    }

    public SelfRatedHealth sadStruggle(HealthScale sadStruggle) {
        this.sadStruggle = sadStruggle;
        return this;
    }

    public void setSadStruggle(HealthScale sadStruggle) {
        this.sadStruggle = sadStruggle;
    }

    public AssessmentPhase getPhase() {
        return phase;
    }

    public SelfRatedHealth phase(AssessmentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(AssessmentPhase phase) {
        this.phase = phase;
    }

    public Instant getDate() {
        return date;
    }

    public SelfRatedHealth date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public SelfRatedHealth user(User user) {
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
        SelfRatedHealth selfRatedHealth = (SelfRatedHealth) o;
        if (selfRatedHealth.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selfRatedHealth.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelfRatedHealth{" +
            "id=" + getId() +
            ", oneConditionMoreSerious='" + getOneConditionMoreSerious() + "'" +
            ", timeSpentManaging='" + getTimeSpentManaging() + "'" +
            ", feelOverwhelmed='" + getFeelOverwhelmed() + "'" +
            ", causesAreLinked='" + getCausesAreLinked() + "'" +
            ", difficultAllMedications='" + getDifficultAllMedications() + "'" +
            ", limitedActivities='" + getLimitedActivities() + "'" +
            ", differentMedicationsProblems='" + getDifferentMedicationsProblems() + "'" +
            ", mixingMedications='" + getMixingMedications() + "'" +
            ", lessEffectiveTreatments='" + getLessEffectiveTreatments() + "'" +
            ", oneCauseAnother='" + getOneCauseAnother() + "'" +
            ", oneDominates='" + getOneDominates() + "'" +
            ", conditionsInteract='" + getConditionsInteract() + "'" +
            ", difficultBestTreatment='" + getDifficultBestTreatment() + "'" +
            ", reducedSocialLife='" + getReducedSocialLife() + "'" +
            ", unhappy='" + getUnhappy() + "'" +
            ", anxious='" + getAnxious() + "'" +
            ", angry='" + getAngry() + "'" +
            ", sad='" + getSad() + "'" +
            ", irritable='" + getIrritable() + "'" +
            ", sadStruggle='" + getSadStruggle() + "'" +
            ", phase='" + getPhase() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
