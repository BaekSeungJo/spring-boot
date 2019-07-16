package com.example.board.controller;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.service.JpaBoardService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
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

    @PostMapping(value = "/jsp/board/write")
    public String writeBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        jpaBoardService.saveBoard(board, multipartHttpServletRequest);
        return "redirect:/jpa/board";
    }

    @GetMapping(value = "/jpa/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
        BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @PutMapping(value = "/jpa/board/{boardIdx")
    public String updateBoard(BoardEntity board) throws Exception {
        jpaBoardService.saveBoard(board, null);
        return "redirect:/jpa/board";
    }

    @GetMapping(value = "/jpa/board/file")
    public void downloadBoardFile(int boardIdx, int idx, HttpServletResponse response) throws Exception {
        BoardFileEntity file = jpaBoardService.selectBoardFileInformation(boardIdx, idx);
        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" +
                URLEncoder.encode(file.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
