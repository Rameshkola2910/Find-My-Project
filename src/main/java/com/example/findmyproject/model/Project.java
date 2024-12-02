/*
 * You can use the following import statements
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * import javax.persistence.*;
 * import java.util.List;
 * 
 */
package com.example.findmyproject.model;

import com.example.findmyproject.model.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int projectId;

	@Column(name = "name")
	private String projectName;

	@Column(name = "budget")
	private Double budget;

	@ManyToMany
	@JoinTable(name = "researcher_project", joinColumns = @JoinColumn(name = "projectid"), inverseJoinColumns = @JoinColumn(name = "researcherid"))
	@JsonIgnoreProperties("projects")
	private List<Researcher> researchers;

	public Project() {
	}

	public Project(int projectId, String projectName, Double budget, List<Researcher> researchers) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.budget = budget;
		this.researchers = researchers;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public List<Researcher> getResearchers() {
		return researchers;
	}

	public void setResearchers(List<Researcher> researchers) {
		this.researchers = researchers;
	}

}