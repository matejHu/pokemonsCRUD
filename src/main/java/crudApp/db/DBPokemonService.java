package crudApp.db;

import crudApp.Entities.Pokemon;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBPokemonService {
    private static final String READ_ALL = "SELECT * FROM pokemons.pokemon";
    private static final String CREATE = "INSERT INTO pokemons.pokemon (name_pokemon) VALUE (?)";
    private static final String DELETE = "DELETE FROM pokemons.pokemon WHERE name_pokemon = ?";
    private static final String EDIT = "UPDATE pokemons.pokemon SET name_pokemon = ? WHERE name_pokemon = ?";
    private static final String NOTCATCHED = "SELECT * FROM pokemons.pokemon WHERE trainer_id IS NULL";


    private static final Logger logger = getLogger(DBPokemonService.class);

    public List<Pokemon> readAll() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            List<Pokemon> pokemons = new ArrayList<>();
            while (resultSet.next()) {
                pokemons.add(new Pokemon(
                        resultSet.getInt("id_pokemon"),
                        resultSet.getString("name_pokemon")
                ));
            }
            return pokemons;
        } catch (SQLException e) {
            logger.error("Error while reading all pokemons!", e);
            return null;
        }
    }

    public List<Pokemon> readNotCatchedPokemons() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(NOTCATCHED)) {

            ResultSet resultSet = statement.executeQuery();
            List<Pokemon> pokemons = new ArrayList<>();
            while (resultSet.next()) {
                pokemons.add(new Pokemon(
                        resultSet.getInt("id_pokemon"),
                        resultSet.getString("name_pokemon")
                ));
            }
            return pokemons;
        } catch (SQLException e) {
            logger.error("Error while reading all pokemons!", e);
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
            logger.error("Error while deleting pokemon!", e);
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
            System.out.println("Pokemon with this name already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while editing pokemon!", e);
            return 0;
        }
    }

    public int create(String name) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);

            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Pokemon with this name already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while creating pokemon!", e);
            return 0;
        }
    }
}
