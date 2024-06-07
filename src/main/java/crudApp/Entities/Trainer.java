package crudApp.Entities;

public class Trainer {
    private int id;
    private String name;

    public Trainer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
     public void setName(String name) {
        this.name = name;
     }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
