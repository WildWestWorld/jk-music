CREATE TABLE album
    (
        id VARCHAR(32) NOT NULL
            PRIMARY KEY COMMENT '专辑ID',
        name VARCHAR(64) NOT NULL COMMENT '专辑名',
        --         TEXT 富文本类型
        description TEXT NULL COMMENT '专辑简介',

        photo_id VARCHAR(32) NULL COMMENT '专辑封面文件ID',

        album_state tinyint(1) DEFAULT 0 NOT NULL COMMENT '专辑上架状态 0-待上架，1-以上架,2-已下架',

        recommended TINYINT(1) NOT NULL DEFAULT 0  COMMENT '是否推荐：推荐：1； 不推荐：0；默认：0'  ,
        recommend_factor INT NOT NULL DEFAULT 0  COMMENT '推荐因数：越高越在上面' ,

        created_time datetime(6) NOT NULL COMMENT '创建时间',
        updated_time datetime(6) NOT NULL COMMENT '更新时间'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '歌曲表';