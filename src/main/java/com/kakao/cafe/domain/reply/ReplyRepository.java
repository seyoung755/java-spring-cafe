package com.kakao.cafe.domain.reply;

import java.util.List;

public interface ReplyRepository {
    void save(Reply reply);
    List<Reply> findReplyByArticleId(Long id);
}
