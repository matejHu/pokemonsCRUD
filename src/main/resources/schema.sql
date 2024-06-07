CREATE DATABASE pokemons;

CREATE TABLE IF NOT EXISTS `pokemon` (
  `id_pokemon` int NOT NULL AUTO_INCREMENT,
  `name_pokemon` varchar(45) NOT NULL,
  `trainer_id` int DEFAULT NULL,
  PRIMARY KEY (`id_pokemon`),
  UNIQUE KEY `id_pokemon_UNIQUE` (`id_pokemon`),
  UNIQUE KEY `name_pokemon_UNIQUE` (`name_pokemon`),
  KEY `trainer_id_idx` (`trainer_id`),
  CONSTRAINT `fk_trainer_id` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id_trainer`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE IF NOT EXISTS `trainer` (
  `id_trainer` int NOT NULL AUTO_INCREMENT,
  `name_trainer` varchar(45) NOT NULL,
  PRIMARY KEY (`id_trainer`),
  UNIQUE KEY `id_trainer_UNIQUE` (`id_trainer`),
  UNIQUE KEY `name_trainer_UNIQUE` (`name_trainer`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

INSERT INTO `pokemon` (`name_pokemon`) VALUES ('Pikachu');
INSERT INTO `pokemon` (`name_pokemon`) VALUES ('Bulbasaur');
INSERT INTO `pokemon` (`name_pokemon`) VALUES ('Charmander');
INSERT INTO `pokemon` (`name_pokemon`) VALUES ('Squirtle');
INSERT INTO `pokemon` (`name_pokemon`) VALUES ('Jigglypuff');

INSERT INTO `trainer` (`name_trainer`) VALUES ('Pep Guardiola');
INSERT INTO `trainer` (`name_trainer`) VALUES ('Jurgen Klopp');
INSERT INTO `trainer` (`name_trainer`) VALUES ('Carlo Ancelotti');