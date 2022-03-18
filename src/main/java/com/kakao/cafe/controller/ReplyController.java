package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ReplySaveRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/articles/{articleId}/reply")
    public String saveReply(@PathVariable Long articleId, ReplySaveRequestDto dto, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        dto.setWriter(sessionUser.getUserId());
        dto.setArticleId(articleId);

        replyService.save(dto);

        return "redirect:/articles/" + articleId;
    }
}
