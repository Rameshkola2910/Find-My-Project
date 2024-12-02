/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */
package com.example.findmyproject.repository;

import com.example.findmyproject.model.*;
import java.util.*;

public interface ProjectRepository {

    ArrayList<Project> getProjects();

    Project getProjectById(int projectId);

    Project addProject(Project project);

    Project updateProject(int projectId, Project project);

    void deleteProject(int projectId);

    List<Researcher> getResearchersProject(int projectId);

}