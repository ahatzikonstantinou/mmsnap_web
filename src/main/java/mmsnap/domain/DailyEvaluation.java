package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.PlanType;

/**
 * A DailyEvaluation.
 */
@Entity
@Table(name = "daily_evaluation")
public class DailyEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private PlanType type;

    @Column(name = "diet")
    private Boolean diet;

    @Column(name = "smoking")
    private Boolean smoking;

    @Column(name = "physical_activity")
    private Boolean physicalActivity;

    @Column(name = "alcohol")
    private Boolean alcohol;

    @Column(name = "if_statement")
    private String ifStatement;

    @Column(name = "then_statement")
    private String thenStatement;

    @Column(name = "coping_if_statement")
    private String copingIfStatement;

    @Column(name = "coping_then_statement")
    private String copingThenStatement;

    @NotNull
    @Column(name = "success", nullable = false)
    private Boolean success;

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

    public PlanType getType() {
        return type;
    }

    public DailyEvaluation type(PlanType type) {
        this.type = type;
        return this;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public Boolean isDiet() {
        return diet;
    }

    public DailyEvaluation diet(Boolean diet) {
        this.diet = diet;
        return this;
    }

    public void setDiet(Boolean diet) {
        this.diet = diet;
    }

    public Boolean isSmoking() {
        return smoking;
    }

    public DailyEvaluation smoking(Boolean smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Boolean isPhysicalActivity() {
        return physicalActivity;
    }

    public DailyEvaluation physicalActivity(Boolean physicalActivity) {
        this.physicalActivity = physicalActivity;
        return this;
    }

    public void setPhysicalActivity(Boolean physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public Boolean isAlcohol() {
        return alcohol;
    }

    public DailyEvaluation alcohol(Boolean alcohol) {
        this.alcohol = alcohol;
        return this;
    }

    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public String getIfStatement() {
        return ifStatement;
    }

    public DailyEvaluation ifStatement(String ifStatement) {
        this.ifStatement = ifStatement;
        return this;
    }

    public void setIfStatement(String ifStatement) {
        this.ifStatement = ifStatement;
    }

    public String getThenStatement() {
        return thenStatement;
    }

    public DailyEvaluation thenStatement(String thenStatement) {
        this.thenStatement = thenStatement;
        return this;
    }

    public void setThenStatement(String thenStatement) {
        this.thenStatement = thenStatement;
    }

    public String getCopingIfStatement() {
        return copingIfStatement;
    }

    public DailyEvaluation copingIfStatement(String copingIfStatement) {
        this.copingIfStatement = copingIfStatement;
        return this;
    }

    public void setCopingIfStatement(String copingIfStatement) {
        this.copingIfStatement = copingIfStatement;
    }

    public String getCopingThenStatement() {
        return copingThenStatement;
    }

    public DailyEvaluation copingThenStatement(String copingThenStatement) {
        this.copingThenStatement = copingThenStatement;
        return this;
    }

    public void setCopingThenStatement(String copingThenStatement) {
        this.copingThenStatement = copingThenStatement;
    }

    public Boolean isSuccess() {
        return success;
    }

    public DailyEvaluation success(Boolean success) {
        this.success = success;
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Instant getDate() {
        return date;
    }

    public DailyEvaluation date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public DailyEvaluation user(User user) {
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
        DailyEvaluation dailyEvaluation = (DailyEvaluation) o;
        if (dailyEvaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dailyEvaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DailyEvaluation{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", diet='" + isDiet() + "'" +
            ", smoking='" + isSmoking() + "'" +
            ", physicalActivity='" + isPhysicalActivity() + "'" +
            ", alcohol='" + isAlcohol() + "'" +
            ", ifStatement='" + getIfStatement() + "'" +
            ", thenStatement='" + getThenStatement() + "'" +
            ", copingIfStatement='" + getCopingIfStatement() + "'" +
            ", copingThenStatement='" + getCopingThenStatement() + "'" +
            ", success='" + isSuccess() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
