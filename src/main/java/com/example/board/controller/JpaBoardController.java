package com.example.board.controller;

import com.example.board.entity.BoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class JpaBoardController {

    @Autowired
    private JpaBoardService jpaBoardService;

    @GetMapping(value = "/jpa/board")
    public ModelAndView openBoardList(ModelMap model) throws Exception {
        ModelAndView mv = new ModelAndView("/board/jpaBoardList");

        List<BoardEntity> list = jpaBoardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }

    @GetMapping(value = "/jpa/board/write")
    public String openBoardWrite() throws Exception {
        return "/board/japBoardWrite";
    }

//    @PostMapping(value = "")
}
