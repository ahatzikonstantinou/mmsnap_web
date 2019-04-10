package mmsnap.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mmsnap.domain.enumeration.AssessmentPhase;

/**
 * A EQVas.
 */
@Entity
@Table(name = "eq_vas")
public class EQVas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
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

    public Integer getScore() {
        return score;
    }

    public EQVas score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public AssessmentPhase getPhase() {
        return phase;
    }

    public EQVas phase(AssessmentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(AssessmentPhase phase) {
        this.phase = phase;
    }

    public Instant getDate() {
        return date;
    }

    public EQVas date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public EQVas user(User user) {
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
        EQVas eQVas = (EQVas) o;
        if (eQVas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eQVas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EQVas{" +
            "id=" + getId() +
            ", score='" + getScore() + "'" +
            ", phase='" + getPhase() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
