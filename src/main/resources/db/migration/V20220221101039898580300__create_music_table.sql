CREATE TABLE music
    (
        id VARCHAR(32) NOT NULL
            PRIMARY KEY COMMENT '歌曲ID',
        name VARCHAR(64) NOT NULL COMMENT '歌曲名',
        --         TEXT 富文本类型
        description TEXT NULL COMMENT '歌曲简介',
        music_state tinyint(1) DEFAULT 0 NOT NULL COMMENT '歌曲上架状态 0-待上架，1-以上架,2-已下架',
        created_time datetime(6) NOT NULL COMMENT '创建时间',
        updated_time datetime(6) NOT NULL COMMENT '更新时间'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '歌曲表';