package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.controller.dto.ArticleUpdateRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.reply.Reply;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/qna")
    public String articlePage() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String save(ArticleSaveRequestDto requestDto, HttpSession session) {
        validateWriter(requestDto, session);
        articleService.save(requestDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        List<Reply> replyList = replyService.findReplyByArticleId(article.getId());
        model.addAttribute("article", article);
        model.addAttribute("reply", replyList);
        model.addAttribute("count", replyList.size());
        return "qna/show";
    }

    @DeleteMapping("/articles/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        validateSessionUser(id, session);
        articleService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        validateSessionUser(id, session);
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "qna/update_form";
    }

    @PutMapping("/articles/{id}/update")
    public String update(@PathVariable Long id, ArticleUpdateRequestDto dto, HttpSession session) {
        validateSessionUser(id, session);
        articleService.update(dto);
        return "redirect:/articles/" + id;
    }

    private void validateSessionUser(Long id, HttpSession session) {
        Article article = articleService.findById(id);
        String writer = article.getWriter();
        Object sessionUser = session.getAttribute("sessionUser");
        if (sessionUser == null || !((User) sessionUser).getUserId().equals(writer)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정 가능합니다");
        }
    }

    private void validateWriter(ArticleSaveRequestDto requestDto, HttpSession session) {
        Object sessionUser = session.getAttribute("sessionUser");
        User user = (User) sessionUser;
        if (!user.getUserId().equals(requestDto.getWriter())) {
            throw new IllegalArgumentException("글쓴이와 현재 유저 아이디가 다릅니다.");
        }
    }
}
