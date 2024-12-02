/*
 * You can use the following import statements
 *
 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 * 
 */
package com.example.findmyproject.repository;
 import com.example.findmyproject.model.*;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface ResearcherJpaRepository extends JpaRepository<Researcher, Integer>{

}