package crudApp.db;
import crudApp.Entities.Trainer;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
public class DBTrainerService {


        private static final String READ_ALL = "SELECT * FROM pokemons.trainer";
        private static final String CREATE = "INSERT INTO pokemons.trainer (name_trainer) VALUE (?)";
        private static final String DELETE = "DELETE FROM pokemons.trainer WHERE name_trainer = ?";
        private static final String EDIT = "UPDATE pokemons.trainer SET name_trainer = ? WHERE name_trainer = ?";
        private static final String CATCH = "UPDATE pokemons.pokemon SET trainer_id = ? WHERE name_pokemon = ?";
        private static final String READ_TRAINERS_POKEMONS = "SELECT name_pokemon FROM pokemons.pokemon WHERE trainer_id = ?";
        private static final String COUNT_POKEMONS_BY_TRAINER =
                "SELECT t.name_trainer, p.trainer_id, COUNT(p.id_pokemon) AS pokemon_count " +
                        "FROM pokemons.pokemon p " +
                        "JOIN pokemons.trainer t ON p.trainer_id = t.id_trainer " +
                        "GROUP BY p.trainer_id, t.name_trainer " +
                        "ORDER BY pokemon_count DESC";

        private static final Logger logger = getLogger(DBTrainerService.class);

        public List<Trainer> readAll() {
            try (Connection connection = HikariCPDataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(READ_ALL)) {

                ResultSet resultSet = statement.executeQuery();
                List<Trainer> trainers = new ArrayList<>();
                while (resultSet.next()) {
                    trainers.add(new Trainer(
                            resultSet.getInt("id_trainer"),
                            resultSet.getString("name_trainer")
                    ));
                }
                return trainers;
            } catch (SQLException e) {
                logger.error("Error while reading all trainers!", e);
                return null;
            }
        }

    public int delete(String name) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setString(1, name);
            // returns number of affected rows
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting trainer!", e);
            return 0;
        }
    }

    public int edit(String name, String newName) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EDIT)) {

            statement.setString(1, newName);
            statement.setString(2, name);
            // returns number of affected rows
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trainer with this name already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while editing trainer!", e);
            return 0;
        }
    }

    public int create(String name) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);

            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trainer with this name already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while creating trainer!", e);
            return 0;
        }
    }

    public int pokemonCatching(int trainerId, String pokemonName) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CATCH)) {

            statement.setInt(1, trainerId);
            statement.setString(2, pokemonName);
            // returns number of affected rows
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("There is problem with input values");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while catching pokemon!", e);
            return 0;
        }
    }
    public List<String> pokemonsOfTrainer(int trainerId){
            try (Connection connection = HikariCPDataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(READ_TRAINERS_POKEMONS)) {

                statement.setInt(1, trainerId);
                ResultSet resultSet = statement.executeQuery();
                List<String> pokemons = new ArrayList<>();
                while (resultSet.next()) {
                    pokemons.add(resultSet.getString("name_pokemon"));
                }
                return pokemons;
            } catch (SQLException e) {
                logger.error("Error while reading pokemons of this trainer!", e);
                return null;
            }
        }
    public List<Map<String, Object>> getTrainersByPokemonCount() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_POKEMONS_BY_TRAINER)) {

            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Object>> trainers = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> trainerData = new HashMap<>();
                trainerData.put("name_trainer", resultSet.getString("name_trainer"));
                trainerData.put("trainer_id", resultSet.getInt("trainer_id"));
                trainerData.put("pokemon_count", resultSet.getInt("pokemon_count"));
                trainers.add(trainerData);
            }
            return trainers;
        } catch (SQLException e) {
            logger.error("Error while counting pokemons by trainer!", e);
            return null;
        }
    }

}
