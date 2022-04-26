CREATE TABLE user_playlist
    (
        `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
        `playlist_id` VARCHAR(32) NOT NULL COMMENT '歌单ID'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '用户歌单关联表';