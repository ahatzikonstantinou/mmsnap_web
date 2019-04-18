package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.AssessmentPhase;

/**
 * A HealthRisk.
 */
@Entity
@Table(name = "health_risk")
public class HealthRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "smoking")
    private Boolean smoking;

    @Column(name = "physical_activity")
    private Boolean physicalActivity;

    @Column(name = "diet")
    private Boolean diet;

    @Column(name = "alcohol")
    private Boolean alcohol;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
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

    public Boolean isSmoking() {
        return smoking;
    }

    public HealthRisk smoking(Boolean smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Boolean isPhysicalActivity() {
        return physicalActivity;
    }

    public HealthRisk physicalActivity(Boolean physicalActivity) {
        this.physicalActivity = physicalActivity;
        return this;
    }

    public void setPhysicalActivity(Boolean physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public Boolean isDiet() {
        return diet;
    }

    public HealthRisk diet(Boolean diet) {
        this.diet = diet;
        return this;
    }

    public void setDiet(Boolean diet) {
        this.diet = diet;
    }

    public Boolean isAlcohol() {
        return alcohol;
    }

    public HealthRisk alcohol(Boolean alcohol) {
        this.alcohol = alcohol;
        return this;
    }

    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public AssessmentPhase getPhase() {
        return phase;
    }

    public HealthRisk phase(AssessmentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(AssessmentPhase phase) {
        this.phase = phase;
    }

    public Instant getDate() {
        return date;
    }

    public HealthRisk date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public HealthRisk user(User user) {
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
        HealthRisk healthRisk = (HealthRisk) o;
        if (healthRisk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), healthRisk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HealthRisk{" +
            "id=" + getId() +
            ", smoking='" + isSmoking() + "'" +
            ", physicalActivity='" + isPhysicalActivity() + "'" +
            ", diet='" + isDiet() + "'" +
            ", alcohol='" + isAlcohol() + "'" +
            ", phase='" + getPhase() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
