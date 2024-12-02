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

public interface ResearcherRepository {

    ArrayList<Researcher> getResearchers();

    Researcher getResearcherById(int researcherId);

    Researcher addResearcher(Researcher researcher);

    Researcher updateResearcher(int researcherId, Researcher researcher);

    void deleteResearcher(int researcherId);

    List<Project> getProjectsResearcher(int researcherId);

}