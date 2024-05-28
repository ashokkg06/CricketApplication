package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.CommentsDAO;
import com.example.cricketapp.Model.MatchComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchCommentService {

    @Autowired
    CommentsDAO commentsDAO;

    @Cacheable(value = "comments", key = "#season + '-' + #matchNo")
    public List<List<MatchComments>> getComments(int matchNo, int season) {
        return commentsDAO.getComments(matchNo);
    }
}
