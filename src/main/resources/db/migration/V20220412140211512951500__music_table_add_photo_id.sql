ALTER TABLE `music`
    ADD COLUMN `photo_id` VARCHAR(32) NULL COMMENT '音乐封面文件ID' AFTER `description`
