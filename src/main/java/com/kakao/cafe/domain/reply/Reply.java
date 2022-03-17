package com.kakao.cafe.domain.reply;

public class Reply {
    private Long id;
    private String writer;
    private String contents;
    private String createdDate;
    private Long articleId;

    public Reply(Long id, String writer, String contents, String createdDate, Long articleId) {
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.articleId = articleId;
    }

    public Reply(String writer, String contents, Long articleId) {
        this.writer = writer;
        this.contents = contents;
        this.articleId = articleId;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Long getArticleId() {
        return articleId;
    }
}
