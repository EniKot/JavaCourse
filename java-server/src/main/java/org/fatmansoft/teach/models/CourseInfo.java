package org.fatmansoft.teach.models;

import javax.persistence.ManyToMany;
import java.util.List;

public class CourseInfo {

    private Course course;


    private String textBook;

    private String startWeek;

    private String endWeek;

    //第N周第M节
    @ManyToMany
    private List<String> absence;
}
