package com.weavus.board.controller;

import com.weavus.board.dto.Board1Dto;
import com.weavus.board.entity.Board1;
import com.weavus.board.repo.Board1Repository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ModuleDescriptor;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board1")
public class Board1Controller {

    @Autowired
    private Board1Repository board1Repo;

    @GetMapping("/list")
    private  String list(Model model){
        List<Board1> board1List= board1Repo.findAll();
        model.addAttribute("board1List",board1List);
        return "main";
    }

    @GetMapping("/reg")
    private String movereg(){
        return "board1/reg";
    }

    @PostMapping("/reg")
    private String reg(Board1Dto board1Dto, HttpServletRequest request){

        Board1 board1 = new Board1();
        board1.setNo(board1Dto.getNo());
        board1.setTitle(board1Dto.getTitle());
        board1.setContent(board1Dto.getContent());
        HttpSession session = request.getSession();
        board1.setWriter(session.getAttribute("sessionUserName").toString());

        board1Repo.save(board1);
        return "board1/regconfirm";
    }
    @GetMapping("/detail/{no}")
    private String detail(Model model, @PathVariable Integer no){
        Optional<Board1> board1 = board1Repo.findById(no);

        if(board1.isPresent()){
            model.addAttribute("board1", board1.get());
            return "board1/detail";
        } else {
            return "redirect:/board1/list";
        }
    }



    @GetMapping("/modify/{no}")
    private String moveModify(@PathVariable Integer no,Model model){
        Optional<Board1> board1 = board1Repo.findById(no);
        if(board1.isPresent()){
            model.addAttribute("board1",board1.get());
            return "board1/modify";

        }else{
            return "redirect:/board1/list";

        }
    }
    @PostMapping ("/modify/{no}")
    private String modify(@PathVariable Integer no, Board1Dto board1Dto, HttpServletRequest request) {
        Board1 board1 = new Board1();
        board1.setNo(no);
        board1.setTitle(board1Dto.getTitle());
        board1.setContent(board1Dto.getContent());
        HttpSession session = request.getSession();
        board1.setWriter(session.getAttribute("sessionUserName").toString());

        board1Repo.save(board1);
        return "redirect:/board1/detail/"+no;
    }
    @GetMapping("/delete/{no}")
    public String delete(@PathVariable("no") Integer no){
        board1Repo.deleteById(no);
        return "redirect:/board1/list";
    }

}
