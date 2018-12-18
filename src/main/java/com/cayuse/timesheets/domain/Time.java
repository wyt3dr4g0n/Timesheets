package com.cayuse.timesheets.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Time.
 */
@Entity
@Table(name = "time")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "jhi_cost")
    private Float cost;

    @Column(name = "for_billing")
    private Float forBilling;

    @Column(name = "for_payroll")
    private Float forPayroll;

    @Column(name = "billing_description")
    private String billingDescription;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Project projectId;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Task taskId;

    @ManyToOne
    @JsonIgnoreProperties("")
    private SubCostCategory subCostCategory;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PayCode payCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getCost() {
        return cost;
    }

    public Time cost(Float cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getForBilling() {
        return forBilling;
    }

    public Time forBilling(Float forBilling) {
        this.forBilling = forBilling;
        return this;
    }

    public void setForBilling(Float forBilling) {
        this.forBilling = forBilling;
    }

    public Float getForPayroll() {
        return forPayroll;
    }

    public Time forPayroll(Float forPayroll) {
        this.forPayroll = forPayroll;
        return this;
    }

    public void setForPayroll(Float forPayroll) {
        this.forPayroll = forPayroll;
    }

    public String getBillingDescription() {
        return billingDescription;
    }

    public Time billingDescription(String billingDescription) {
        this.billingDescription = billingDescription;
        return this;
    }

    public void setBillingDescription(String billingDescription) {
        this.billingDescription = billingDescription;
    }

    public String getDescription() {
        return description;
    }

    public Time description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public Time attachments(byte[] attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public Time attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public String getNotes() {
        return notes;
    }

    public Time notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Time createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Project getProjectId() {
        return projectId;
    }

    public Time projectId(Project project) {
        this.projectId = project;
        return this;
    }

    public void setProjectId(Project project) {
        this.projectId = project;
    }

    public Task getTaskId() {
        return taskId;
    }

    public Time taskId(Task task) {
        this.taskId = task;
        return this;
    }

    public void setTaskId(Task task) {
        this.taskId = task;
    }

    public SubCostCategory getSubCostCategory() {
        return subCostCategory;
    }

    public Time subCostCategory(SubCostCategory subCostCategory) {
        this.subCostCategory = subCostCategory;
        return this;
    }

    public void setSubCostCategory(SubCostCategory subCostCategory) {
        this.subCostCategory = subCostCategory;
    }

    public PayCode getPayCode() {
        return payCode;
    }

    public Time payCode(PayCode payCode) {
        this.payCode = payCode;
        return this;
    }

    public void setPayCode(PayCode payCode) {
        this.payCode = payCode;
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
        Time time = (Time) o;
        if (time.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), time.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Time{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", cost=" + getCost() +
            ", forBilling=" + getForBilling() +
            ", forPayroll=" + getForPayroll() +
            ", billingDescription='" + getBillingDescription() + "'" +
            ", description='" + getDescription() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
