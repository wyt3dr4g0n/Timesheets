package com.cayuse.timesheets.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "project")
    private Long project;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Project projectId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public Task taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Task taskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        return this;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Long getProject() {
        return project;
    }

    public Task project(Long project) {
        this.project = project;
        return this;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public Project getProjectId() {
        return projectId;
    }

    public Task projectId(Project project) {
        this.projectId = project;
        return this;
    }

    public void setProjectId(Project project) {
        this.projectId = project;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", taskId='" + getTaskId() + "'" +
            ", taskDescription='" + getTaskDescription() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
