package com.example.board.service;

import com.example.board.common.FileUtils;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
@Slf4j
//@Transactional
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto board, MultipartHttpServletRequest request) throws Exception {
        boardMapper.insertBoard(board);
        List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), request);
        if (CollectionUtils.isEmpty(list) == false) {
            boardMapper.insertBoardFileList(list);
        }


//        if(ObjectUtils.isEmpty(request) == false) {
//            Iterator<String> iterator = request.getFileNames();
//            String name;
//            while (iterator.hasNext()) {
//                name = iterator.next();
//                log.debug("file tag name : " + name);
//                List<MultipartFile> list = request.getFiles(name);
//                for(MultipartFile multipartFile : list) {
//                    log.debug("start file information");
//                    log.debug("file name : " + multipartFile.getOriginalFilename());
//                    log.debug("file size : " + multipartFile.getSize());
//                    log.debug(("file content type : " + multipartFile.getContentType()));
//                    log.debug("end file information.\n");
//                }
//            }
//        }
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setFileList(fileList);

        boardMapper.updateHitCount(boardIdx);

        return board;
    }

    @Override
    public void updateBoard(BoardDto board) throws Exception {
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }
}
