package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,Integer> {




}
