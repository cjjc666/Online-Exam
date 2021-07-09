package com.cjjc.exam.controller;

import com.cjjc.exam.entity.Course;
import com.cjjc.exam.entity.Question;
import com.cjjc.exam.entity.Teacher;
import com.cjjc.exam.service.Couservice;
import com.cjjc.exam.service.Queservice;
import com.cjjc.exam.service.Teaservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/14 18:09
 * @description:
 */
@Controller
public class Teacon {
    @Autowired
    Teaservice teaservice;
    @Autowired
    Couservice couservice;
    @Autowired
    Queservice queservice;



    @RequestMapping("/add")
    public String addcou(){
        return "addcou";
    }

    @RequestMapping("query")
    public String query(){
        return "modifycourse";
    }

    @RequestMapping("/teacher/back")
    public String back(){
        return "teacon";
    }

    @RequestMapping("/addques")
    public String addques(){
        return "addques";
    }

    @RequestMapping("/quescon")
    public String quescon(){
        return "quescon";
    }



    @RequestMapping("/teacher/login")
    public String login(HttpServletRequest request, Model m, @RequestParam("tid") String tid, @RequestParam("name") String name){
        Teacher teacher=new Teacher();
        teacher=teaservice.login(tid,name);
        if (null==teacher){
            m.addAttribute("msg","Login failed!");
            return "tealogin";
        }else {
            System.out.println("good");
            m.addAttribute("name","Welcome back! "+name);
            m.addAttribute("exit","Exit account");
            HttpSession session=request.getSession();
            session.setAttribute("teaid",teacher.getId());
            session.setAttribute("tname",name);
            //将teacher的id与name存入session!!!!!!
            return "teacon";
        }
        //teacher的登录
    }


    @RequestMapping("/teacher/addcourse")
    public String addcou(HttpServletRequest request,Model m,@RequestParam("cname") String cname,@RequestParam("cid") String cid){
        //int cid=Integer.parseInt(id);
        //System.out.println(cid);
        //System.out.println(cname);
        Course course=new Course();
        course.setCid(cid);
        course.setCname(cname);
        int teaid= (int) request.getSession().getAttribute("teaid");
        course.setTeaid(teaid);
        if (null==couservice.addoraltercou(course)){
            //HttpSession session=request.getSession();
            //session.setAttribute("couid",course.getId());
            m.addAttribute("msg","Add Course failed!");
            return "addcou";
        }else {
            System.out.println("good");
            m.addAttribute("msg", "Added Course successfully!");
            return "teacon";
        }
        //添加课程
    }

    @RequestMapping("/teacher/update")
    public String upcou(HttpServletRequest request,Model m,@RequestParam("id") int id,@RequestParam("cname") String cname,@RequestParam("cid") String cid){
        Course course=new Course();
        course.setId(id);
        course.setCid(cid);
        course.setCname(cname);
        int teaid= (int) request.getSession().getAttribute("teaid");
        course.setTeaid(teaid);
        if (null==couservice.addoraltercou(course)){
            m.addAttribute("msg","Update Course failed!");
            return "updatecou";
        }else {
            System.out.println("good");
            m.addAttribute("msg", "Update Course successfully!");
            return "modifycourse";
        }
        //更新课程
    }

    @RequestMapping("/teacher/query")
    public String query(Model m,@RequestParam("cid") String cid,@RequestParam("cname") String cname){
        Course course=new Course();
        course=couservice.query(cid,cname);
        if (null==course){
            m.addAttribute("msg","Query course not found!");
            return "modifycourse";
        }else {
            m.addAttribute("couinfo",course);
            return "updatecou";
        }
        //查找到课程，准备更新
    }

    @RequestMapping("/teacher/showall")
    public String showall(HttpServletRequest request,Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        //int teaid= (int) request.getSession().getAttribute("teaid");
        Page<Course> courses=couservice.getpage(pageNum,pageSize);
        Iterator<Course> c =courses.iterator(); //Iterator:迭代器
        while (c.hasNext()){
            System.out.println(c.next().toString());
        }
        m.addAttribute("courses",courses);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "showall";
        //分页查询
    }

    @RequestMapping("/teacher/delete")
    public String del(HttpServletRequest request,Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        //int teaid= (int) request.getSession().getAttribute("teaid");
        Page<Course> courses=couservice.getpage(pageNum,pageSize);
        Iterator<Course> c =courses.iterator(); //Iterator:迭代器
        while (c.hasNext()){
            System.out.println(c.next().toString());
        }
        m.addAttribute("courses",courses);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "delcourse";
        //返回course信息，准备删除
    }

    @RequestMapping("/teacher/del")
    public String delete(Model m,@RequestParam("id") int id){
        couservice.delcou(id);
        return "redirect:/teacher/delete";
        //通过course id 删除course
    }

    @RequestMapping("/teacher/addques")
    public String addques(HttpServletRequest request,Model m,@RequestParam("content") String content,@RequestParam("answer") String answer,@RequestParam("cid") String cid,@RequestParam("cname") String cname){
        Question question=new Question();
        question.setContent(content);
        question.setAnswer(answer);
        //int couid= (int) request.getSession().getAttribute("couid");
        //question.setCouid(couid);
        Course course=new Course();
        course=couservice.query(cid,cname);
        if (null==course){
            m.addAttribute("msg","Add Question failed,please try again!");
            return "addques";
        }else {
            question.setCouid(course.getId());
            queservice.addoralterques(question);
            m.addAttribute("msg","Add Question successfully!");
            m.addAttribute("exit","Exit account");
            return "quescon";
        }
        //通过couid添加questions
    }

    @RequestMapping("/teacher/delques")
    public String del(Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        Page<Question> questions=queservice.getpage(pageNum,pageSize);
        Iterator<Question> q =questions.iterator(); //Iterator:迭代器
        while (q.hasNext()){
            System.out.println(q.next().toString());
        }
        m.addAttribute("questions",questions);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "delques";
    }

    @RequestMapping("/teacher/deleteques")
    @Transactional
    public String delques(Model m,@RequestParam("id") int id){
        queservice.delques(id);
        return "redirect:/teacher/delques";
    }

    @RequestMapping("/teacher/showques")
    public String showall(Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        Page<Question> questions=queservice.getpage(pageNum,pageSize);
        Iterator<Question> q =questions.iterator(); //Iterator:迭代器
        while (q.hasNext()){
            System.out.println(q.next().toString());
        }
        m.addAttribute("questions",questions);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "showques";
    }

    @RequestMapping("/teacher/upques")
    public String up(Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        Page<Question> questions=queservice.getpage(pageNum,pageSize);
        Iterator<Question> q =questions.iterator(); //Iterator:迭代器
        while (q.hasNext()){
            System.out.println(q.next().toString());
        }
        m.addAttribute("questions",questions);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "updateques";
        //查到信息后，更新
    }

    @RequestMapping("/teacher/updateques")
    public String update(Model m,@RequestParam("qid") int qid,@RequestParam("couid")int couid,@RequestParam("content") String content,@RequestParam("answer") String answer){
        System.out.println(qid);
        System.out.println(couid);
        System.out.println(content);
        System.out.println(answer);
        Question question=new Question();
        question.setCouid(couid);
        question.setContent(content);
        question.setAnswer(answer);
        question.setQid(qid);
        if (null==queservice.addoralterques(question)){
            m.addAttribute("msg","Update question failed!");
            return "updateques";
        }else {
            System.out.println("good");
            m.addAttribute("msg", "Update question successfully!");
            return "redirect:/teacher/upques";
        }
    }

    @RequestMapping("/teacher/queryques")
    public String queryques(Model m,@RequestParam("id") int qid){
        Question question=new Question();
        question=queservice.query(qid);
        m.addAttribute("quesinfo",question);
        return "upquestion";
        //通过qid查询到question的信息
    }

    @RequestMapping("/teacher/getreport")
    public String getreport(Model m,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        Page<Course> courses=couservice.getpage(pageNum,pageSize);
        Iterator<Course> q =courses.iterator(); //Iterator:迭代器
        while (q.hasNext()){
            System.out.println(q.next().toString());
        }
        m.addAttribute("courses",courses);
        //m.addAttribute("name","Welcome back! "+);
        m.addAttribute("exit","Exit account");
        return "report";
    }








}
