CREATE TABLE user_role
    (
        user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
        role_id VARCHAR(32) NOT NULL COMMENT '角色ID',
--         外键约束 约束是c_user_id  ，外键名是user_id （外键名，就是在该表名字） reference user[参照的表] (id)[参照的字段]
        CONSTRAINT c_user_id
            FOREIGN KEY (user_id) REFERENCES user (id),
        CONSTRAINT c_role_id
            FOREIGN KEY (role_id) REFERENCES role (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '用户角色表'