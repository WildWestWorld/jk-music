ALTER TABLE `music`
    ADD COLUMN `lyc_id` VARCHAR(32) NULL COMMENT '音乐歌词文件ID' AFTER `file_id` ;
