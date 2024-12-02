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
public class ResearcherJpaService implements ResearcherRepository {

	@Autowired
	private ResearcherJpaRepository researcherJpaRepository;

	@Autowired
	private ProjectJpaRepository projectJpaRepository;

	@Override
	public ArrayList<Researcher> getResearchers() {
		return (ArrayList<Researcher>) researcherJpaRepository.findAll();
	}

	@Override
	public Researcher getResearcherById(int researcherId) {
		try {
			return researcherJpaRepository.findById(researcherId).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Researcher addResearcher(Researcher researcher) {
		try {
			List<Integer> projectIds = new ArrayList<>();
			for (Project project : researcher.getProjects()) {
				projectIds.add(project.getProjectId());
			}
			List<Project> projects = projectJpaRepository.findAllById(projectIds);
			researcher.setProjects(projects);
			for (Project project : projects) {
				project.getResearchers().add(researcher);
			}
			Researcher saveResearcher = researcherJpaRepository.save(researcher);
			projectJpaRepository.saveAll(projects);
			return saveResearcher;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Researcher updateResearcher(int researcherId, Researcher researcher) {
		try {
			Researcher newresearcher = researcherJpaRepository.findById(researcherId).get();
			if (researcher.getResearcherName() != null) {
				newresearcher.setResearcherName(researcher.getResearcherName());
			}
			if (researcher.getSpecialization() != null) {
				newresearcher.setSpecialization(researcher.getSpecialization());
			}
			if (researcher.getProjects() != null) {
				List<Project> projects = newresearcher.getProjects();
				for (Project project : projects) {
					project.getResearchers().remove(newresearcher);
				}
				projectJpaRepository.saveAll(projects);
				List<Integer> newprojectIds = new ArrayList<>();
				for (Project project : researcher.getProjects()) {
					newprojectIds.add(project.getProjectId());
				}
				List<Project> newprojects = projectJpaRepository.findAllById(newprojectIds);
				for (Project project : newprojects) {
					project.getResearchers().add(newresearcher);
				}
				projectJpaRepository.saveAll(newprojects);
				newresearcher.setProjects(newprojects);
			}
			return researcherJpaRepository.save(newresearcher);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteResearcher(int researcherId) {
		try {
			Researcher researcher = researcherJpaRepository.findById(researcherId).get();

			List<Project> projects = researcher.getProjects();
			for (Project project : projects) {
				project.getResearchers().remove(researcher);
			}
			projectJpaRepository.saveAll(projects);
			researcherJpaRepository.deleteById(researcherId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Project> getProjectsResearcher(int researcherId) {
		try {
			Researcher researcher = researcherJpaRepository.findById(researcherId).get();
			return researcher.getProjects();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}