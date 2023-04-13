package arnaldo;

public class CelestialBody {

    private long id;
    private final String name;
    private final double mass;
    private final Position position;
    private final String nameFather;

    /**
     * Costruttore del corpo celeste
     * @param name nome del corpo celeste
     * @param mass massa del corpo celeste
     * @param position classe posizione (x, y)
     * @param nameFather nome del corpo celeste attorno al quale orbitano
     */
    public CelestialBody(String name, double mass, Position position, String nameFather) {
        this.name = name;
        this.mass = mass;
        this.position = position;
        this.nameFather = nameFather;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public Position getPosition() {
        return position;
    }

    public String getNameFather() {
        return nameFather;
    }

    @Override
    public String toString() {

        return  "(" + id + ") " +
                "name: " + name +
                ", mass: " + mass +
                ", position: " + position.toString();
    }
}
