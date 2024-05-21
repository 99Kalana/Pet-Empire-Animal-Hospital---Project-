package lk.ijse.model;

public class Pet {

    private String id;
    private String name;
    private String breed;
    private double weight;
    private String colour;

    public Pet(){

    }
    public Pet(String id, String name, String breed, double weight, String colour) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.colour = colour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", weight=" + weight +
                ", colour='" + colour + '\'' +
                '}';
    }
}
