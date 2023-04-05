package com.teach.javafxclient.controller;

import com.teach.javafxclient.controller.base.MessageDialog;
import com.teach.javafxclient.controller.base.ToolController;
import com.teach.javafxclient.request.DataRequest;
import com.teach.javafxclient.request.DataResponse;
import com.teach.javafxclient.request.HttpRequestUtil;
import com.teach.javafxclient.request.OptionItem;
import com.teach.javafxclient.util.CommonMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentTeamController extends ToolController {

    private ObservableList<Map> teamObservableList= FXCollections.observableArrayList();
    private ObservableList<Map> workObservableList= FXCollections.observableArrayList();
    @FXML
    private Label num;  //学号标签
    @FXML
    private Label name;//姓名标签
    @FXML
    private Label dept; //学院标签
    @FXML
    private Label teamScore; //专业标签
    @FXML
    private Label className; //班级标签
    @FXML
    private Label teamNo; //班级标签
    @FXML
    private Label teachNo;
    @FXML
    private Label uploader;  //证件号码标签
    @FXML
    private Label uploadTime; //性别标签
    @FXML
    private Label birthday; //出生日期标签
    @FXML
    private Label email; //邮箱标签
    @FXML
    private Label phone; //电话标签
    @FXML
    private Label address; //地址标签
    @FXML
    private TableView<Map> teamTable;
    @FXML
    private TableColumn<Map,String> numColumn;
    @FXML
    private TableColumn<Map,String> nameColumn;
    @FXML
    private TableColumn<Map,String> classNameColumn;
    @FXML
    private TableColumn<Map,String> teachNoColumn;
    @FXML
    private TableColumn<Map,Double> weightColumn;
    @FXML
    private TableColumn<Map,Double> scoreColumn;
    @FXML
    private TableColumn<Map,String> levelColumn;
    @FXML
    private ComboBox<OptionItem> laboratoryComboBox;  //学生信息  性别输入域
    @FXML
    private TextField weightTextField; //学生信息  学号输入域
    @FXML
    private TableView<Map> workTable;
    @FXML
    private TableColumn<Map,String> weekColumn;
    @FXML
    private TableColumn<Map,String> contentColumn;


    private Integer studentId = null;  //学生主键
    private Integer personId = null;  //学生关联人员主键
    private List<OptionItem> laboratoryList;   //性别选择列表数据
    private Map info = null;
    private List<Map> workList= null;
    /**
     * 页面加载对象创建完成初始话方法，页面中控件属性的设置，初始数据显示等初始操作都在这里完成，其他代码都事件处理方法里
     */
    @FXML
    public void initialize() {
        numColumn.setCellValueFactory(new MapValueFactory("num"));
        nameColumn.setCellValueFactory(new MapValueFactory<>("name"));
        classNameColumn.setCellValueFactory(new MapValueFactory<>("className"));
        teachNoColumn.setCellValueFactory(new MapValueFactory<>("teachNo"));
        weightColumn.setCellValueFactory(new MapValueFactory<>("weight"));
        scoreColumn.setCellValueFactory(new MapValueFactory<>("score"));
        levelColumn.setCellValueFactory(new MapValueFactory<>("level"));
        laboratoryList = HttpRequestUtil.getDictionaryOptionItemList("SYSM");
        laboratoryComboBox.getItems().addAll(laboratoryList);

        workTable.setEditable(true);
        weekColumn.setCellValueFactory(new MapValueFactory("week"));
        contentColumn.setCellValueFactory(new MapValueFactory<>("content"));
        contentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contentColumn.setOnEditCommit(this::onOnEditCommit);
        contentColumn.setEditable(true);
        getStudentInfoData();

    }

    /**
     * getIntroduceData 从后天获取当前学生的所有信息，不传送的面板各个组件中
     */
    public void getStudentInfoData(){
        DataRequest req = new DataRequest();
        DataResponse res;
        res = HttpRequestUtil.request("/api/team/getStudentTeamInfo",req);
        if(res.getCode() != 0)
            return;
        Map data =(Map)res.getData();
        info = (Map)data.get("info");
        studentId = CommonMethod.getInteger(info,"studentId");
        personId = CommonMethod.getInteger(info,"personId");
        num.setText(CommonMethod.getString(info,"num"));
        name.setText(CommonMethod.getString(info,"name"));
        className.setText(CommonMethod.getString(info,"className"));
        teachNo.setText(CommonMethod.getString(info,"teachNo"));
        teamNo.setText(CommonMethod.getString(info,"teamNo"));
        teamScore.setText(CommonMethod.getString(info,"teamScore"));
        uploader.setText(CommonMethod.getString(info,"uploader"));
        uploadTime.setText(CommonMethod.getString(info,"uploadTime"));
        birthday.setText(CommonMethod.getString(info,"birthday"));
        email.setText(CommonMethod.getString(info,"email"));
        phone.setText(CommonMethod.getString(info,"phone"));
        address.setText(CommonMethod.getString(info,"address"));
        Double weight =  CommonMethod.getDouble(info,"address");
        if(weight== null)
            weight = 1.0;
        weightTextField.setText(weight.toString());
        List<Map> teamList= (List)data.get("teamList");
        for (Map m: teamList) {
            teamObservableList.addAll(FXCollections.observableArrayList(m));
        }
        teamTable.setItems(teamObservableList);  // 成绩表数据显示
        workList = (List)data.get("workList");
        for (Map m: workList) {
            workObservableList.addAll(FXCollections.observableArrayList(m));
        }
        workTable.setItems(workObservableList);  // 成绩表数据显示
        laboratoryComboBox.getSelectionModel().select(CommonMethod.getOptionItemIndexByValue(laboratoryList, CommonMethod.getString(info, "laboratory")));
    }
    public void updateInfoPane(){

    }
    /**
     * 点击保存按钮 执行onSubmitButtonClick 调用doSave 实现个人简历保存
     */
    @FXML
    public void onSubmitButtonClick(){
        doSave();
    }


    /**
     * 保存个人简介数据到数据库里
     */

    public void doSave(){
        DataRequest req = new DataRequest();
        req.put("studentId",studentId);
        req.put("weight",weightTextField.getText());
        if(laboratoryComboBox.getSelectionModel() != null && laboratoryComboBox.getSelectionModel().getSelectedItem() != null)
            req.put("laboratory",laboratoryComboBox.getSelectionModel().getSelectedItem().getValue());
        DataResponse res = HttpRequestUtil.request("/api/team/saveStudentTeamInfo", req);
        if(res.getCode() == 0) {
            MessageDialog.showDialog("修改成功！");
            getStudentInfoData();
        }else {
            MessageDialog.showDialog(res.getMsg());
        }
    }
    @FXML
    public void onUploadButtonClick(){
        String teamNo = CommonMethod.getString(info,"teamNo");
        if(teamNo== null || teamNo.length() == 0){
            MessageDialog.showDialog("没有团队信息，不能上传作业！");
            return;
        }
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("作业上传");
        fileDialog.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ZIP 文件", "*.zip"));
        File file = fileDialog.showOpenDialog(null);
        DataResponse res =HttpRequestUtil.uploadFile("/api/team/uploadTeamFile",file.getPath(),"project/" + teamNo + ".zip");
        if(res.getCode() == 0) {
            MessageDialog.showDialog("上传成功！");
            getStudentInfoData();
        }
        else {
            MessageDialog.showDialog(res.getMsg());
        }
    }
    @FXML
    public void onDownloadButtonClick(){
        String teamNo = CommonMethod.getString(info,"teamNo");
        if(teamNo== null || teamNo.length() == 0){
            MessageDialog.showDialog("没有团队信息，不能下载作业！");
            return;
        }
        String uploader = CommonMethod.getString(info,"uploader");
        if(uploader== null || uploader.length() == 0){
            MessageDialog.showDialog("没有上传，不能下载作业！");
            return;
        }
        DataRequest req = new DataRequest();
        req.put("fileName","project/" +teamNo+".zip");
        byte[] bytes = HttpRequestUtil.requestByteData("/api/base/getFileByteData", req);
        if (bytes == null) {
            MessageDialog.showDialog("没有上传，无法下载！");
            return;
        }
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("请选择保存的文件名");
        fileDialog.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("zip 文件", "*.zip"));
        fileDialog.setInitialFileName(teamNo+".zip");
        File file = fileDialog.showSaveDialog(null);
        if(file != null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(bytes);
                out.close();
                MessageDialog.showDialog("下载成功！");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    public void onOnEditCommit(TableColumn.CellEditEvent e){
        int row = e.getTablePosition().getRow();
        int col = e.getTablePosition().getColumn();
        Object value = e.getNewValue();
        Map m = (Map)workList.get(row);
        m.put("modify", true);
        m.put("content", value);
    }
    @FXML
    public void onSaveWorkButtonClick() {
        List mList = new ArrayList();
        Boolean modify;
        for(Map m : workList) {
            modify = (Boolean)m.get("modify");
            if(modify != null && modify.booleanValue()) {
                mList.add(m);
                m.put("modify",false);
            }
        }
        if(mList.size() == 0) {
            MessageDialog.showDialog("没有修改，不需要保存！");
            return;
        }
        DataRequest req = new DataRequest();
        req.put("dataList",mList);
        DataResponse res = HttpRequestUtil.request("/api/team/saveWorkContent", req);
        if(res.getCode() == 0) {
            MessageDialog.showDialog("保存成功！");
        }else {
            MessageDialog.showDialog(res.getMsg());
        }
    }

}
