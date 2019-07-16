package com.example.board.service;

import com.example.board.common.FileUtils;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public class JpaBardServiceImpl implements JpaBoardService {

    @Autowired
    private JpaBoardRepository jpaBoardRepository;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardEntity> selectBoardList() throws Exception {
        return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
    }

    @Override
    public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        board.setCreatorId("admin");
        List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);

    }

    @Override
    public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
        return null;
    }

    @Override
    public void deleteBoard(int boardIdx) {

    }

    @Override
    public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
        return null;
    }
}
