package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
}
