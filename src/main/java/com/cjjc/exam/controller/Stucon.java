package com.cjjc.exam.controller;

import com.cjjc.exam.dao.Studao;
import com.cjjc.exam.entity.Course;
import com.cjjc.exam.entity.Question;
import com.cjjc.exam.entity.Student;
import com.cjjc.exam.service.Couservice;
import com.cjjc.exam.service.Queservice;
import com.cjjc.exam.service.Stuservice;
import com.cjjc.exam.utils.Stringutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/10 11:46
 * @description:
 */
@Controller
public class Stucon {
    @Autowired
    Stuservice stuservice;
    @Autowired
    Couservice couservice;
    @Autowired
    Queservice queservice;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/logreg")
    public String logregpage(){
        return "loginreg";
    }

    @RequestMapping("/tea")
    public String teacher(){
        return "tealogin";
    }

    @RequestMapping("/exit")
    public String exit(){
        return "redirect:/";
        //return "index";
        //重定向到  / = /->"index"
    }

    @RequestMapping("/con")
    public String con(){
        return "contact";
        //return "index";
    }

    @RequestMapping("/ab")
    public String ab(){
        return "aboutus";
    }



    @RequestMapping("/student/reg")
    public String reg(Model m,@RequestParam("classes") String classs, @RequestParam("sid") String id, @RequestParam("name") String name, @RequestParam("school") String school, @RequestParam("college") String college, @RequestParam("grade") int grade){
        Student student=new Student();
        student.setSid(id);
        student.setName(name);
        student.setSchool(school);
        student.setCollege(college);
        student.setGrade(grade);
        student.setClasses(classs);
        if (null==stuservice.add(student)){
            m.addAttribute("message","Registration failed!");
        }else {
            m.addAttribute("message","Successful registration!");
        }
        return "loginreg";
    }

    @RequestMapping("/student/login")
    public String login(HttpServletRequest request, Model m, @RequestParam("sid") String id, @RequestParam("name") String name){
        Student student=new Student();
        student=stuservice.login(id,name);
        if (null==student){
            m.addAttribute("message","Login failed!");
            return "loginreg";
        }else {
            //System.out.println("good");
            HttpSession session=request.getSession();
            session.setAttribute("sname",student.getName());
            session.setAttribute("id",student.getId());
            //session.setAttribute("tname",name);
            m.addAttribute("name","Welcome back! Classmate "+name);
            m.addAttribute("exit","Exit account");
            return "index";
        }
    }

    @RequestMapping("/student/findexam")
    public String findexam(HttpServletRequest request,Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        String sname= (String) request.getSession().getAttribute("sname");
        if (sname==null){
            m.addAttribute("message","You are not logged in yet，please login first!");
            return "loginreg";
            //判断是否登录，未登录的话，跳转到登录界面
        }else {
            Page<Course> courses = couservice.getpage(pageNum, pageSize);
            Iterator<Course> c = courses.iterator(); //Iterator:迭代器
            while (c.hasNext()) {
                System.out.println(c.next().toString());
            }
            m.addAttribute("courses", courses);
            //m.addAttribute("name","Welcome back! "+);
            m.addAttribute("exit", "Exit account");
            return "exam";
            //遍历所有exam科目
        }
    }


    @RequestMapping("/student/getques")
    public String getques(Model m,@RequestParam("id") int couid){
        List<Question> questions=queservice.findbycouid(couid);
        m.addAttribute("quesinfo",questions);
        m.addAttribute("exit","Exit account");
        return "quesinfo";
    }

    @RequestMapping("/student/getscore")
    public String getscore(HttpServletRequest request,@RequestParam("couid") int couid,Model m,@RequestParam("qid") int qid,@RequestParam("answer[]") String[] answer){
        Question question=new Question();
        question=queservice.query(qid);
        int score=0;
        for (String a:answer){
            System.out.println(a);
            if (a.equals(question.getAnswer())){
                score+=5;
                //后端获取到[answer]数组的值后遍历与对应的答案进行比较，并计算分数
            }
        }
        //System.out.println(score);
        HttpSession session=request.getSession();
        session.setAttribute("score",score);
        int id= (int) request.getSession().getAttribute("id");
        Course course=couservice.getbyid(couid);
        String cname=course.getCname();
        String cid=course.getCid();
        int teaid=course.getTeaid();
        Course c=new Course();
        c.setCid(cid);
        c.setCname(cname);
        c.setScore(score);
        c.setStuid(id);
        c.setTeaid(teaid);
        couservice.addoraltercou(c);
        m.addAttribute("score","Your final score is "+score);
        return "quesinfo";
    }

    @RequestMapping("/student/getinfo")
    public String getinfor(Model m, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        System.out.println(id);
        if (null==id) {
            //m.addAttribute("msg", "There are no student details!");
            redirectAttributes.addAttribute("msg", "There are no student details!Plese try other.");
            return "redirect:/teacher/getreport";
        } else {
            Student student = stuservice.getinfo(id);
            m.addAttribute("sinfo", student);
            //m.addAttribute("exit","Exit account");
            return "reportdetails";
            //通过学生id查询学生的详情，无id就重定向到report
        }
    }

    /*@RequestMapping("/covers")
    public String covers(MultipartFile file) throws Exception{
        String folder="D:/examimg";
        File imagefolder=new File(folder);
        File f=new File(imagefolder, Stringutils.getRandomstring(6)+file.getOriginalFilename().substring(file.getOriginalFilename().length()-4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8080/covers/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }//上传图片路径
    }*/
}
