package com.cayuse.timesheets.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PayCode.
 */
@Entity
@Table(name = "pay_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PayCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pay_code_description")
    private String payCodeDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayCodeDescription() {
        return payCodeDescription;
    }

    public PayCode payCodeDescription(String payCodeDescription) {
        this.payCodeDescription = payCodeDescription;
        return this;
    }

    public void setPayCodeDescription(String payCodeDescription) {
        this.payCodeDescription = payCodeDescription;
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
        PayCode payCode = (PayCode) o;
        if (payCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayCode{" +
            "id=" + getId() +
            ", payCodeDescription='" + getPayCodeDescription() + "'" +
            "}";
    }
}
