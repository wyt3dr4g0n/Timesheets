package com.cayuse.timesheets.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SubCostCategory.
 */
@Entity
@Table(name = "sub_cost_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubCostCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sub_cost_category_description")
    private String subCostCategoryDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCostCategoryDescription() {
        return subCostCategoryDescription;
    }

    public SubCostCategory subCostCategoryDescription(String subCostCategoryDescription) {
        this.subCostCategoryDescription = subCostCategoryDescription;
        return this;
    }

    public void setSubCostCategoryDescription(String subCostCategoryDescription) {
        this.subCostCategoryDescription = subCostCategoryDescription;
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
        SubCostCategory subCostCategory = (SubCostCategory) o;
        if (subCostCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subCostCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubCostCategory{" +
            "id=" + getId() +
            ", subCostCategoryDescription='" + getSubCostCategoryDescription() + "'" +
            "}";
    }
}
