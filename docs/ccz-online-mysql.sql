CREATE TABLE IF NOT EXISTS `ORIENTACOES` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(255) NOT NULL UNIQUE,
	`titulo` varchar(25) NOT NULL,
	`especie_id` int NOT NULL,
	`tipo_solicitacao_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ALERTA_CIDADAO` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`endereco` varchar(255),
	`municipio_id` int NOT NULL,
	`descricao` varchar(255) NOT NULL UNIQUE,
	`data` datetime NOT NULL,
	`coord_latitude` decimal(10,0),
	`coord_longitude` decimal(10,0),
	`tipo_notificacao_id` int NOT NULL,
	`especie_id` int,
	`usuario_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ESPECIE` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(255) NOT NULL UNIQUE,
	`imagem` varchar(255),
	`zoonose_id` int,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `TIPO_NOTIFICACAO` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(255) NOT NULL UNIQUE,
	`urgencia` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `USUARIO` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`nome` int NOT NULL,
	`cartao_sus` int NOT NULL,
	`perfil` varchar(255) NOT NULL DEFAULT '25',
	`cpf` varchar(11) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ALERTA_CCZ` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`titulo` varchar(25) NOT NULL,
	`descricao` varchar(255) NOT NULL UNIQUE,
	`endereco` varchar(255),
	`municipio_id` int NOT NULL,
	`data` datetime NOT NULL,
	`coord_latitude` decimal(10,0) NOT NULL,
	`coord_longitude` decimal(10,0) NOT NULL,
	`tipo_notificacao_id` int NOT NULL,
	`especie_id` int,
	`usuario_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `visita` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`morador` varchar(255) NOT NULL,
	`data` datetime NOT NULL,
	`agente_id` int NOT NULL,
	`especie_id` int,
	`endereco` varchar(255),
	`municipio_id` int NOT NULL,
	`coord_latitude` decimal(10,0),
	`coord_longitude` decimal(10,0),
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `MUNICIPIO` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(255) NOT NULL,
	`uf` varchar(255) NOT NULL,
	`codigo_ibge` int,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ZOONOSE` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `ORIENTACOES` ADD CONSTRAINT `ORIENTACOES_fk3` FOREIGN KEY (`especie_id`) REFERENCES `ESPECIE`(`id`);

ALTER TABLE `ORIENTACOES` ADD CONSTRAINT `ORIENTACOES_fk4` FOREIGN KEY (`tipo_solicitacao_id`) REFERENCES `TIPO_NOTIFICACAO`(`id`);
ALTER TABLE `ALERTA_CIDADAO` ADD CONSTRAINT `ALERTA_CIDADAO_fk2` FOREIGN KEY (`municipio_id`) REFERENCES `MUNICIPIO`(`id`);

ALTER TABLE `ALERTA_CIDADAO` ADD CONSTRAINT `ALERTA_CIDADAO_fk7` FOREIGN KEY (`tipo_notificacao_id`) REFERENCES `TIPO_NOTIFICACAO`(`id`);

ALTER TABLE `ALERTA_CIDADAO` ADD CONSTRAINT `ALERTA_CIDADAO_fk8` FOREIGN KEY (`especie_id`) REFERENCES `ESPECIE`(`id`);

ALTER TABLE `ALERTA_CIDADAO` ADD CONSTRAINT `ALERTA_CIDADAO_fk9` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO`(`id`);
ALTER TABLE `ESPECIE` ADD CONSTRAINT `ESPECIE_fk3` FOREIGN KEY (`zoonose_id`) REFERENCES `ZOONOSE`(`id`);


ALTER TABLE `ALERTA_CCZ` ADD CONSTRAINT `ALERTA_CCZ_fk4` FOREIGN KEY (`municipio_id`) REFERENCES `MUNICIPIO`(`id`);

ALTER TABLE `ALERTA_CCZ` ADD CONSTRAINT `ALERTA_CCZ_fk8` FOREIGN KEY (`tipo_notificacao_id`) REFERENCES `TIPO_NOTIFICACAO`(`id`);

ALTER TABLE `ALERTA_CCZ` ADD CONSTRAINT `ALERTA_CCZ_fk9` FOREIGN KEY (`especie_id`) REFERENCES `ESPECIE`(`id`);

ALTER TABLE `ALERTA_CCZ` ADD CONSTRAINT `ALERTA_CCZ_fk10` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO`(`id`);
ALTER TABLE `visita` ADD CONSTRAINT `visita_fk3` FOREIGN KEY (`agente_id`) REFERENCES `USUARIO`(`id`);

ALTER TABLE `visita` ADD CONSTRAINT `visita_fk4` FOREIGN KEY (`especie_id`) REFERENCES `ESPECIE`(`id`);

ALTER TABLE `visita` ADD CONSTRAINT `visita_fk6` FOREIGN KEY (`municipio_id`) REFERENCES `MUNICIPIO`(`id`);

