CREATE TABLE album_music
    (
        `album_id` VARCHAR(32) NOT NULL COMMENT '专辑ID',
        `music_id` VARCHAR(32) NOT NULL COMMENT '歌曲ID'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '专辑歌曲关联表';