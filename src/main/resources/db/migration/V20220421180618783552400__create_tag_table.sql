CREATE TABLE tag
    (
        id BIGINT(64) NOT NULL
            PRIMARY KEY COMMENT '标签ID',
        name VARCHAR(128) NULL COMMENT '标签名称',

        created_time datetime(6) NOT NULL COMMENT '创建时间',
        updated_time datetime(6) NOT NULL COMMENT '更新时间'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '标签表';