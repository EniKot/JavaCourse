package org.fatmansoft.teach.controllers;


import org.fatmansoft.teach.models.Course;
import org.fatmansoft.teach.models.Person;
import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.models.Teacher;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.*;
import org.fatmansoft.teach.service.BaseService;
import org.fatmansoft.teach.util.ComDataUtil;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private BaseService baseService;
    public synchronized Integer getNewPersonId(){  //synchronized 同步方法
        Integer  id = personRepository.getMaxId();  // 查询最大的id
        if(id == null)
            id = 1;
        else
            id = id+1;
        return id;
    };
    public synchronized Integer getNewUserId(){
        Integer  id = userRepository.getMaxId();  // 查询最大的id
        if(id == null)
            id = 1;
        else
            id = id+1;
        return id;
    };
    public synchronized Integer getNewTeacherId(){
        Integer  id = teacherRepository.getMaxId();  // 查询最大的id
        if(id == null)
            id = 1;
        else
            id = id+1;
        return id;
    }
    public Map getMapFromTeacher(Teacher t) {
        Map m = new HashMap();
        Person p;
        if(t == null)
            return m;

        m.put("school",t.getSchool());
        m.put("institute",t.getInsititute());
        p = t.getPerson();
        if(p == null)
            return m;
        m.put("teacherId", t.getTeacherId());
        m.put("personId", p.getPersonId());
        m.put("num",p.getNum());
        m.put("name",p.getName());
        m.put("dept",p.getDept());
        m.put("card",p.getCard());
        String gender = p.getGender();
        m.put("gender",gender);
        m.put("genderName", ComDataUtil.getInstance().getDictionaryLabelByValue("XBM", gender)); //性别类型的值转换成数据类型名
        m.put("birthday", p.getBirthday());  //时间格式转换字符串
        m.put("email",p.getEmail());
        m.put("phone",p.getPhone());
        m.put("address",p.getAddress());
        m.put("introduce",p.getIntroduce());
        return m;
    }
    public List getTeacherMapList(String numName){
        List dataList = new ArrayList<HashMap>();
        List<Teacher> tList = teacherRepository.findTeacherListByNumName(numName);  //数据库查询操作
        if(tList == null || tList.size() == 0)
            return dataList;
        for(int i = 0; i < tList.size();i++) {
            dataList.add(getMapFromTeacher(tList.get(i)));
        }
        return dataList;
    }

    @PostMapping("/getTeacherList")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getTeacherList(@Valid @RequestBody DataRequest dataRequest){
        String numName= dataRequest.getString("numName");
        List dataList = getTeacherMapList(numName);
        return CommonMethod.getReturnData(dataList);  //按照测试框架规范会送Map的list
    }

    @PostMapping("/teacherDelete")
    @PreAuthorize(" hasRole('ADMIN')")
    public  DataResponse teacherDelete(@Valid @RequestBody DataRequest dataRequest){

    }

    @PostMapping("/getTeacherInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getTeacherInfo(@Valid @RequestBody DataRequest dataRequest) {

    }

    @PostMapping("/teacherEditSave")
    @PreAuthorize(" hasRole('ADMIN')")
    public DataResponse teacherEditSave(@Valid @RequestBody DataRequest dataRequest) {

    }

    public List getTeacherCourseList(List<Course> courseList){

    }
    @PostMapping("/getTeacherIntroduceData")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public DataResponse getTeacherIntroduceData(@Valid @RequestBody DataRequest dataRequest) {

    }
    @PostMapping("/saveTeacherIntroduce")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public DataResponse saveTeacherIntroduce(@Valid @RequestBody DataRequest dataRequest) {

    }
}
