CREATE TABLE tag_playlist
    (
        `tag_id` VARCHAR(32) NOT NULL COMMENT '标签ID',
        `playlist_id` VARCHAR(32) NOT NULL COMMENT '歌单ID'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '标签歌单关联表';