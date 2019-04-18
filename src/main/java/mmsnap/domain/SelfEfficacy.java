package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.AssessmentPhase;

/**
 * A SelfEfficacy.
 */
@Entity
@Table(name = "self_efficacy")
public class SelfEfficacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "healthier_lifestyle")
    private Boolean healthierLifestyle;

    @Column(name = "complete_behaviour_goals")
    private Boolean completeBehaviourGoals;

    @Column(name = "manage_multimorbidity")
    private Boolean manageMultimorbidity;

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

    public Boolean isHealthierLifestyle() {
        return healthierLifestyle;
    }

    public SelfEfficacy healthierLifestyle(Boolean healthierLifestyle) {
        this.healthierLifestyle = healthierLifestyle;
        return this;
    }

    public void setHealthierLifestyle(Boolean healthierLifestyle) {
        this.healthierLifestyle = healthierLifestyle;
    }

    public Boolean isCompleteBehaviourGoals() {
        return completeBehaviourGoals;
    }

    public SelfEfficacy completeBehaviourGoals(Boolean completeBehaviourGoals) {
        this.completeBehaviourGoals = completeBehaviourGoals;
        return this;
    }

    public void setCompleteBehaviourGoals(Boolean completeBehaviourGoals) {
        this.completeBehaviourGoals = completeBehaviourGoals;
    }

    public Boolean isManageMultimorbidity() {
        return manageMultimorbidity;
    }

    public SelfEfficacy manageMultimorbidity(Boolean manageMultimorbidity) {
        this.manageMultimorbidity = manageMultimorbidity;
        return this;
    }

    public void setManageMultimorbidity(Boolean manageMultimorbidity) {
        this.manageMultimorbidity = manageMultimorbidity;
    }

    public AssessmentPhase getPhase() {
        return phase;
    }

    public SelfEfficacy phase(AssessmentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(AssessmentPhase phase) {
        this.phase = phase;
    }

    public Instant getDate() {
        return date;
    }

    public SelfEfficacy date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public SelfEfficacy user(User user) {
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
        SelfEfficacy selfEfficacy = (SelfEfficacy) o;
        if (selfEfficacy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selfEfficacy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelfEfficacy{" +
            "id=" + getId() +
            ", healthierLifestyle='" + isHealthierLifestyle() + "'" +
            ", completeBehaviourGoals='" + isCompleteBehaviourGoals() + "'" +
            ", manageMultimorbidity='" + isManageMultimorbidity() + "'" +
            ", phase='" + getPhase() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
