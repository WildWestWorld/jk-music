CREATE TABLE tag_music
    (
        `tag_id` VARCHAR(32) NOT NULL COMMENT '标签ID',
        `music_id` VARCHAR(32) NOT NULL COMMENT '歌曲ID'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '标签歌曲关联表';