/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */
package com.example.findmyproject.service;

import com.example.findmyproject.model.*;
import com.example.findmyproject.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class ProjectJpaService implements ProjectRepository {

	@Autowired
	private ProjectJpaRepository projectJpaRepository;

	@Autowired
	private ResearcherJpaRepository researcherJpaRepository;

	@Override
	public ArrayList<Project> getProjects() {
		return (ArrayList<Project>) projectJpaRepository.findAll();
	}

	@Override
	public Project getProjectById(int projectId) {
		try {
			return projectJpaRepository.findById(projectId).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Project addProject(Project project) {
		try {
			List<Integer> researcherIds = new ArrayList<>();
			for (Researcher researcher : project.getResearchers()) {
				researcherIds.add(researcher.getResearcherId());
			}
			List<Researcher> researchers = researcherJpaRepository.findAllById(researcherIds);
			if (researchers.size() != researcherIds.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some of researchers are found");
			}
			project.setResearchers(researchers);
			return projectJpaRepository.save(project);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Project updateProject(int projectId, Project project) {
		try {
			Project newproject = projectJpaRepository.findById(projectId).get();
			if (project.getProjectName() != null) {
				newproject.setProjectName(project.getProjectName());
			}
			if (project.getBudget() != 0) {
				newproject.setBudget(project.getBudget());
			}
			if (project.getResearchers() != null) {
				List<Integer> researcherIds = new ArrayList<>();
				for (Researcher researcher : project.getResearchers()) {
					researcherIds.add(researcher.getResearcherId());
				}
				List<Researcher> researchers = researcherJpaRepository.findAllById(researcherIds);
				if (researchers.size() != researcherIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some researchers not found");
				}
				newproject.setResearchers(researchers);
			}
			return projectJpaRepository.save(newproject);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteProject(int projectId) {
		try {
			projectJpaRepository.deleteById(projectId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);

	}

	@Override
	public List<Researcher> getResearchersProject(int projectId) {
		try {
			Project project = projectJpaRepository.findById(projectId).get();
			return project.getResearchers();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}