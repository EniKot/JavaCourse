package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Award;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 实现Award的查询
 */
@Repository
public interface AwardRepository extends JpaRepository<Award,Integer> {



}
