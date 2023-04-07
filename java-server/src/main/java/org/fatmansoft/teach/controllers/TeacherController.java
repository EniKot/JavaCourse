package org.fatmansoft.teach.controllers;


import org.fatmansoft.teach.models.*;
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
import java.util.*;

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
        m.put("institute",t.getInstitute());
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
    //将获取到这个老师教的课程，转换成list<map>

    public List getTeacherCourseMapList(List<Course> courseList){
        List list=new ArrayList();
        if(courseList == null || courseList.size() == 0){
            return list;
        }
        Map map;
        List data=new ArrayList();
        for(Course c:courseList){
            map=new HashMap();
            map.put("courseNum",c.getNum());
            map.put("courseName",c.getName());
            map.put("duration","Week "+c.getStartWeek()+"~"+c.getEndWeek());
            map.put("credit",c.getCredit());
            data.add(map);
        }
        return data;

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
        Integer teacherId=dataRequest.getInteger("teacherId");
        Teacher t=null;
        Optional<Teacher> op;
        if(teacherId !=null){
            op=teacherRepository.findById(teacherId);
            if(op.isPresent()){
                t=op.get();
            }
        }

        if(t!=null){
            Person p=t.getPerson();
            Optional<User> uOp=userRepository.findByPersonPersonId(p.getPersonId());
            if(uOp.isPresent()){
                userRepository.delete(uOp.get());
            }
            teacherRepository.delete(t);
            personRepository.delete(p);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getTeacherInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getTeacherInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer teacherId=dataRequest.getInteger("teacherId");
        Teacher t=null;
        Optional<Teacher> op;
        if(teacherId !=null){
            op=teacherRepository.findById(teacherId);
            if(op.isPresent()){
                t=op.get();
            }
        }
        return CommonMethod.getReturnData(getMapFromTeacher(t));
    }

    @PostMapping("/teacherEditSave")
    @PreAuthorize(" hasRole('ADMIN')")
    public DataResponse teacherEditSave(@Valid @RequestBody DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        Map form = dataRequest.getMap("form"); //参数获取Map对象
        String num = CommonMethod.getString(form,"num");  //Map 获取属性的值
        Teacher t= null;
        Person p;
        User u;
        Optional<Teacher> op;
        Integer personId;
        if(teacherId != null) {
            op= teacherRepository.findById(teacherId);  //查询对应数据库中主键为id的值的实体对象
            if(op.isPresent()) {
                t = op.get();
            }
        }
        Optional<Person> nOp = personRepository.findByNum(num); //查询是否存在num的人员
        if(nOp.isPresent()) {
            if(t == null || !t.getPerson().getNum().equals(num)) {
                return CommonMethod.getReturnMessageError("新编号已经存在，不能添加或修改！");
            }
        }
        if(t == null) {
            personId = getNewPersonId();
            p = new Person();
            p.setPersonId(personId);
            p.setNum(num);
            p.setType("1");
            personRepository.saveAndFlush(p);
            String password = encoder.encode("123456");
            u= new User();
            u.setUserId(getNewUserId());
            u.setPerson(p);
            u.setUserName(num);
            u.setPassword(password);
            u.setUserType(userTypeRepository.findByName(EUserType.ROLE_TEACHER));
            userRepository.saveAndFlush(u);
            t = new Teacher();
            t.setTeacherId(getNewTeacherId());
            t.setPerson(p);
            teacherRepository.saveAndFlush(t);
        }else {
            p = t.getPerson();
            personId = p.getPersonId();
        }
        if(!num.equals(p.getNum())) {
            Optional<User>uOp = userRepository.findByPersonPersonId(personId);
            if(uOp.isPresent()) {
                u = uOp.get();
                u.setUserName(num);
                userRepository.saveAndFlush(u);
            }
            p.setNum(num);  //设置属性
        }
        p.setName((String)form.get("name"));
        p.setDept(CommonMethod.getString(form,"dept"));
        p.setCard(CommonMethod.getString(form,"card"));
        p.setGender(CommonMethod.getString(form,"gender"));
        p.setBirthday(CommonMethod.getString(form,"birthday"));
        p.setEmail(CommonMethod.getString(form,"email"));
        p.setPhone(CommonMethod.getString(form,"phone"));
        p.setAddress(CommonMethod.getString(form,"address"));
        personRepository.save(p);  // 修改保存人员信息
        t.setSchool(CommonMethod.getString(form,"school"));
        t.setInstitute(CommonMethod.getString(form,"institute"));
        teacherRepository.save(t);  //修改保存学生信息
        return CommonMethod.getReturnData(t.getTeacherId());
    }


    @PostMapping("/getTeacherIntroduceData")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public DataResponse getTeacherIntroduceData(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId = CommonMethod.getUserId();
        Optional<User> uOp = userRepository.findByUserId(userId);
        if(!uOp.isPresent()){
            return CommonMethod.getReturnMessageError("用户不存在！");
        }
        User u=uOp.get();
        Optional<Teacher> tOp= teacherRepository.findByPersonPersonId(u.getPerson().getPersonId());
        if(!tOp.isPresent()){
            return CommonMethod.getReturnMessageError("教师不存在！");
        }
        Teacher t=tOp.get();
        Map info=getMapFromTeacher(t);
        Map data=new HashMap();
        List<Course> cList=t.getTeaches();
        data.put("info",info);
        data.put("courseTeaches",getTeacherCourseMapList(cList));
        return  CommonMethod.getReturnData(data);
    }
    @PostMapping("/saveTeacherIntroduce")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public DataResponse saveTeacherIntroduce(@Valid @RequestBody DataRequest dataRequest) {
        Integer teacherId=dataRequest.getInteger("teacherId");
        String introduce = dataRequest.getString("introduce");
        Optional<Teacher> tOp=teacherRepository.findById(teacherId);
        if(!tOp.isPresent()){
            return CommonMethod.getReturnMessageError("老师不存在！");
        }
        Teacher t=tOp.get();
        Person p=t.getPerson();
        p.setIntroduce(introduce);
        personRepository.save(p);
        return CommonMethod.getReturnMessageOK();
    }
}
