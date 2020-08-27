CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
                        `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
                        `email` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
                        `first_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
                        `last_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
                        `profile_image_url` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
                        `is_active` tinyint DEFAULT NULL,
                        `is_lock` tinyint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `role` (
                        `id` int NOT NULL,
                        `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `permission` (
  `id` int NOT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `desc` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `user_role` (
                             `user_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `fk_user_has_role_role1_idx` (`role_id`),
                             KEY `fk_user_has_role_user_idx` (`user_id`),
                             CONSTRAINT `fk_user_has_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
                             CONSTRAINT `fk_user_has_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `role_permission` (
                                   `role_id` int NOT NULL,
                                   `permission_id` int NOT NULL,
                                   PRIMARY KEY (`role_id`,`permission_id`),
                                   KEY `fk_role_has_permission_permission1_idx` (`permission_id`),
                                   KEY `fk_role_has_permission_role1_idx` (`role_id`),
                                   CONSTRAINT `fk_role_has_permission_permission1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
                                   CONSTRAINT `fk_role_has_permission_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
