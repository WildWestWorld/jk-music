CREATE TABLE album_artist
    (
        `album_id` VARCHAR(32) NOT NULL COMMENT '专辑ID',
        `artist_id` VARCHAR(32) NOT NULL COMMENT '歌曲ID'

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '专辑与歌手关联表';