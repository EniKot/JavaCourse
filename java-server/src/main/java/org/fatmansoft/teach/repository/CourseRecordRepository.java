package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Course;
import org.fatmansoft.teach.models.CourseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 实现CourseRecord的查询操作
 */
@Repository
public interface CourseRecordRepository extends JpaRepository<CourseRecord,Integer> {


}
