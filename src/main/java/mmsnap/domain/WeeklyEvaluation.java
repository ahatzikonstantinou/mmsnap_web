package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A WeeklyEvaluation.
 */
@Entity
@Table(name = "weekly_evaluation")
public class WeeklyEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diet")
    private Integer diet;

    @Column(name = "smoking")
    private Integer smoking;

    @Column(name = "physical_activity")
    private Integer physicalActivity;

    @Column(name = "alcohol")
    private Integer alcohol;

    @NotNull
    @Column(name = "jhi_year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "week_of_year", nullable = false)
    private Integer weekOfYear;

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

    public Integer getDiet() {
        return diet;
    }

    public WeeklyEvaluation diet(Integer diet) {
        this.diet = diet;
        return this;
    }

    public void setDiet(Integer diet) {
        this.diet = diet;
    }

    public Integer getSmoking() {
        return smoking;
    }

    public WeeklyEvaluation smoking(Integer smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(Integer smoking) {
        this.smoking = smoking;
    }

    public Integer getPhysicalActivity() {
        return physicalActivity;
    }

    public WeeklyEvaluation physicalActivity(Integer physicalActivity) {
        this.physicalActivity = physicalActivity;
        return this;
    }

    public void setPhysicalActivity(Integer physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public Integer getAlcohol() {
        return alcohol;
    }

    public WeeklyEvaluation alcohol(Integer alcohol) {
        this.alcohol = alcohol;
        return this;
    }

    public void setAlcohol(Integer alcohol) {
        this.alcohol = alcohol;
    }

    public Integer getYear() {
        return year;
    }

    public WeeklyEvaluation year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public WeeklyEvaluation weekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
        return this;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Instant getDate() {
        return date;
    }

    public WeeklyEvaluation date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public WeeklyEvaluation user(User user) {
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
        WeeklyEvaluation weeklyEvaluation = (WeeklyEvaluation) o;
        if (weeklyEvaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weeklyEvaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeeklyEvaluation{" +
            "id=" + getId() +
            ", diet='" + getDiet() + "'" +
            ", smoking='" + getSmoking() + "'" +
            ", physicalActivity='" + getPhysicalActivity() + "'" +
            ", alcohol='" + getAlcohol() + "'" +
            ", year='" + getYear() + "'" +
            ", weekOfYear='" + getWeekOfYear() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
