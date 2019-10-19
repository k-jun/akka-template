DROP TABLE IF EXISTS `todo`;
CREATE TABLE IF NOT EXISTS `todo` (
`id` int(10) NOT NULL AUTO_INCREMENT,
`name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
`create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1;