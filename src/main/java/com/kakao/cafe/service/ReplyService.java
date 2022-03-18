package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ReplySaveRequestDto;
import com.kakao.cafe.domain.reply.Reply;
import com.kakao.cafe.domain.reply.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void save(ReplySaveRequestDto dto) {
        replyRepository.save(dto.toEntity());
    }

    public List<Reply> findReplyByArticleId(Long id) {
        return replyRepository.findReplyByArticleId(id);
    }
}
