package arnaldo;

public class Star extends CelestialBody {

    public static final int STAR_CODE = 1000000000;

    /**
     * Costruttore di una stella
     * @param name nome della stella
     * @param mass massa della stella
     */
    public Star(String name, double mass) {
        super(name, mass, new Position(0, 0), name);
        setId(STAR_CODE);
    }


}
