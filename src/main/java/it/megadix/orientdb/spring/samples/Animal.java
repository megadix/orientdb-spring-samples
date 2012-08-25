package it.megadix.orientdb.spring.samples;

public class Animal {
    private Object id;
    private City city;
    private String name;

    public Animal() {
    }

    public Animal(City city, String name) {
        super();
        this.city = city;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + id.toString() + "] Name: " + name + ", City:" + (city != null ? city.getName() : "");
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
