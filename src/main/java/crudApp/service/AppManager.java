package crudApp.service;

import crudApp.db.DBPokemonService;
import crudApp.db.DBTrainerService;
import crudApp.inputUtils.InputUtils;

public class AppManager {
    private final DBPokemonService pokemonService;
    private final DBTrainerService trainerService;

    public AppManager(){
        this.pokemonService = new DBPokemonService();
        this.trainerService = new DBTrainerService();
    }
    public void start(){
        System.out.println("------------Welcome to PokemonApp--------");
        while(true){
            System.out.println("1. Pokemon options");
            System.out.println("2. Trainer options");
            System.out.println("3. Exit");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 1 -> pokemonOptions();
                case 2 -> trainerOptions();
                case 3 -> {
                    System.out.println("Good bye");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    public void pokemonOptions() {
        System.out.println("----Options for pokemon, CRUD and actions----");
        while (true) {
            System.out.println("1. Create new pokemon");
            System.out.println("2. Update existing pokemon");
            System.out.println("3. Delete pokemon from DB");
            System.out.println("4. Print all pokemons");
            System.out.println("5. Print free pokemons");
            System.out.println("6. Back to main page");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 1 -> createPokemon();
                case 2 -> updatePokemon();
                case 3 -> deletePokemon();
                case 4 -> printAllPokemons();
                case 5 -> printFreePokemons();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    public void createPokemon(){
        System.out.println("Enter name of new pokemon");
        final String name = InputUtils.readString();
        pokemonService.create(name);
    }
    public void updatePokemon(){
        System.out.println("Enter name of pokemon which you want to change: ");
        final String name = InputUtils.readString();
        System.out.println("Enter different name for this pokemon: ");
        final String newName = InputUtils.readString();
        pokemonService.edit(name, newName);
        System.out.println("You changed the name of pokemon ***" + name + "*** to name ***" + newName + "***");
    }
    public void deletePokemon(){
        System.out.println("Enter name of pokemon you want to delete");
        final String name = InputUtils.readString();
        pokemonService.delete(name);
    }
    public void printAllPokemons(){
        System.out.println("List of all pokemons");
        System.out.println(pokemonService.readAll());
    }
    public void printFreePokemons(){
        System.out.println("List of free pokemons");
        System.out.println(pokemonService.readNotCatchedPokemons());
    }


    public void trainerOptions () {
        System.out.println("----Options for trainer, CRUD and actions----");
        while (true) {
            System.out.println("1. Create new trainer");
            System.out.println("2. Update existing trainer");
            System.out.println("3. Delete trainer from DB");
            System.out.println("4. Print all trainers");
            System.out.println("5. Print trainers by amount of pokemons");
            System.out.println("6. Print pokemons of trainer");
            System.out.println("7. Catch pokemon");
            System.out.println("8. Back to main page");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 1 -> createTrainer();
                case 2 -> updateTrainer();
                case 3 -> deleteTrainer();
                case 4 -> printAllTrainers();
                case 5 -> printTrainersBySuccess();
                case 6 -> printPokemonsOfTrainer();
                case 7 -> trainerCatching();
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    public void createTrainer(){
        System.out.println("Enter name of new trainer");
        final String name = InputUtils.readString();
        trainerService.create(name);
    }
    public void updateTrainer(){
        System.out.println("Enter name of trainer which you want to change: ");
        final String name = InputUtils.readString();
        System.out.println("Enter different name for this trainer: ");
        final String newName = InputUtils.readString();
        trainerService.edit(name, newName);
        System.out.println("You changed the name of trainer ***" + name + "*** to name ***" + newName + "***");
    }
    public void deleteTrainer(){
        System.out.println("Enter name of trainer you want to delete");
        final String name = InputUtils.readString();
        trainerService.delete(name);
    }
    public void printAllTrainers(){
        System.out.println("List of all trainers");
        System.out.println(trainerService.readAll());
    }
    public void printTrainersBySuccess(){
        System.out.println(trainerService.getTrainersByPokemonCount());
    }
    public void trainerCatching(){
        System.out.println("List of available trainers:");
        System.out.println(trainerService.readAll());
        System.out.println("Enter id of trainer which is going to catch pokemon: ");
        final int trainerId = InputUtils.readInt();
        System.out.println("List of free pokemons: ");
        System.out.println(pokemonService.readNotCatchedPokemons());
        System.out.println("Enter name of pokemon which you want to catch: ");
        final String pokemonName = InputUtils.readString();
        trainerService.pokemonCatching(trainerId, pokemonName);
        System.out.println("Trainer with id ***" + trainerId + "*** has caught pokemon with name ***" + pokemonName + "***");
    }
    public void printPokemonsOfTrainer(){
        System.out.println("Enter ID of trainer which is going to show his pokemons: ");
        System.out.println(trainerService.readAll());
        final int trainerId = InputUtils.readInt();
        System.out.println("Printing pokemons of trainer with id " + trainerId +": ");
        System.out.println(trainerService.pokemonsOfTrainer(trainerId));
    }
}
