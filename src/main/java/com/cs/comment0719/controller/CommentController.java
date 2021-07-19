package com.cs.comment0719.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.comment0719.entity.Comment;
import com.cs.comment0719.entity.Record;
import com.cs.comment0719.entity.User;
import com.cs.comment0719.service.CommentService;
import com.cs.comment0719.service.RecordService;
import com.cs.comment0719.service.CommentService;
import com.cs.comment0719.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private RecordService recordService;

    /**
     * 首页
     */
    @GetMapping("/")
    public String home(){
        return "redirect:/com";
    }

    /**
     * 登录页面
     */
    @GetMapping("/userlogin")
    public String userlogin(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

    @GetMapping("/com")
    public String index(Model model, HttpSession session){
        LambdaQueryWrapper<Comment> lqwc = new LambdaQueryWrapper<>();
        lqwc.orderByDesc(Comment::getQuantity);
        BaseMapper cbm =  commentService.getBaseMapper();
        List<Comment> coms = cbm.selectList(lqwc);

        User u = (User) session.getAttribute("user");
        System.out.println(u);
        if(u!=null){
            LambdaQueryWrapper<Record> lqwr = new LambdaQueryWrapper<>();
            lqwr.eq(Record::getUserid,u.getId());
            BaseMapper rbm =  recordService.getBaseMapper();
            List<Record> l = rbm.selectList(lqwr);

            for (Comment c:coms) {
                for (Record r:l) {
                    if(c.getId()==r.getAuthorid()){
                        c.setFlag(1);
                    }
                }
            }
            model.addAttribute("u",u);
            model.addAttribute("num",1);
        }else{
            model.addAttribute("num",0);
        }
        model.addAttribute("coms",coms);
        return "comment";
    }

    @GetMapping("/dz")
    public String dz(@RequestParam("id") Integer id, @RequestParam("userid") Integer userid){
        LambdaQueryWrapper<Comment> lqwc = new LambdaQueryWrapper<>();
        lqwc.eq(Comment::getId,id);
        BaseMapper cbm =  commentService.getBaseMapper();
        Comment c = (Comment) cbm.selectOne(lqwc);
        c.setQuantity(c.getQuantity()+1);
        cbm.updateById(c);

        BaseMapper rbm =  recordService.getBaseMapper();
        Record r = new Record();
        r.setAuthorid(id);
        r.setUserid(userid);
        rbm.insert(r);
        return "redirect:/com";
    }

    @GetMapping("/cancel")
    public String cancel(@RequestParam("id") Integer id,@RequestParam("userid") Integer userid){
        LambdaQueryWrapper<Comment> lqwc = new LambdaQueryWrapper<>();
        lqwc.eq(Comment::getId,id);
        BaseMapper cbm =  commentService.getBaseMapper();
        Comment c = (Comment) cbm.selectOne(lqwc);
        c.setQuantity(c.getQuantity()-1);
        cbm.updateById(c);

        LambdaQueryWrapper<Record> lqwr = new LambdaQueryWrapper<>();
        lqwr.eq(Record::getUserid,userid).eq(Record::getAuthorid,id);
        BaseMapper rbm =  recordService.getBaseMapper();
        Record r = (Record) rbm.selectOne(lqwr);
        rbm.deleteById(r.getId());
        return "redirect:/com";
    }

    @PostMapping("/add")
    public String add(@PathParam("discuss") String discuss,Comment comment, HttpSession session) {
        User u = (User) session.getAttribute("user");
        comment.setAuthor(u.getName());
        comment.setContent(discuss);
        comment.setQuantity(0);
        comment.setCreateTime(new Date());
        BaseMapper<Comment> bm = commentService.getBaseMapper();
        bm.insert(comment);
        return "redirect:/com";
    }
}
