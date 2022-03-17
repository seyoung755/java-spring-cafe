package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.reply.Reply;

public class ReplySaveRequestDto {
    private String writer;
    private String contents;
    private Long articleId;

    public ReplySaveRequestDto(String writer, String contents, Long articleId) {
        this.writer = writer;
        this.contents = contents;
        this.articleId = articleId;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Reply toEntity() {
        return new Reply(writer, contents, articleId);
    }
}
