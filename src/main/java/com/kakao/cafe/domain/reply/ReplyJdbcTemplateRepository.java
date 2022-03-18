package com.kakao.cafe.domain.reply;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ReplyJdbcTemplateRepository implements ReplyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReplyJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Reply reply) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("writer", reply.getWriter());
        params.addValue("contents", reply.getContents());
        params.addValue("article_id", reply.getArticleId());

        String query = "INSERT INTO reply (writer, contents, article_id) VALUES (:writer, :contents, :article_id)";

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public List<Reply> findReplyByArticleId(Long articleId) {
        String query = "SELECT * FROM reply WHERE article_id = " + articleId;
        return jdbcTemplate.query(query, replyRowMapper());
    }


    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String writer = rs.getString("writer");
            String contents = rs.getString("contents");
            String createdDate = rs.getTimestamp("created_date").toString();
            Long articleId = rs.getLong("article_id");
            return new Reply(id, writer, contents, createdDate, articleId);
        };
    }
}
