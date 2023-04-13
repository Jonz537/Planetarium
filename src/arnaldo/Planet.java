package arnaldo;

import java.util.ArrayList;
import java.util.Objects;

public class Planet extends CelestialBody {

    public static final int PLANET_CODE = 10000;
    public static final int MAX_PLANETS = 26000;
    private final ArrayList<Moon> moons = new ArrayList<>();
    private final double distanceFromStar;


    public Planet(String name, double mass, Position position, StarSystem starSystem) {
        super(name, mass, position, starSystem.getStarName());

        for (int i = PLANET_CODE; i <= MAX_PLANETS * PLANET_CODE; i+=PLANET_CODE) {
            if (starSystem.searchId(Star.STAR_CODE + i)) {
                this.setId(Star.STAR_CODE + i);
                break;
            }
        }
        distanceFromStar = Math.sqrt(Math.pow(position.getX(), 2) + Math.pow(position.getY(), 2));
    }

    public ArrayList<Moon> getMoons() {
        return moons;
    }

    public double getDistanceFromStar() {
        return distanceFromStar;
    }

    /**
     * Cerca una luna dato un id
     * @param id id della luna da cercare
     * @return vero se esiste una luna con quell'id
     */
    public boolean searchMoon(long id) {
        for (Moon moon : moons) {
            if (moon.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Aggiunge una luna
     * @param moonToAdd luna da aggiungere
     * @return true se l'operazione va a buon fine
     */
    public boolean addMoon(Moon moonToAdd, StarSystem starSystem) {

        if (starSystem.searchName(moonToAdd.getName())){
            return false;
        }
        for (Moon moon: moons) {
            if (moonToAdd.compareMoon(moon)){
                return false;
            }
        }
        for (Planet planet: starSystem.getPlanets()) {
            if(moonToAdd.getPosition().isSamePosition(planet.getPosition())) {
                return false;
            }
        }

        moons.add(moonToAdd);

        return true;
    }

    /**
     * (Manz) rimuove una luna
     * @param moonToRemove luna da rimuovere
     * @return true se la luna da rimuovere è presente nella lista
     */
    public boolean removeMoon(Moon moonToRemove) {
        return moons.remove(moonToRemove);
    }

    /**
     * Cerca una luna dato il nome
     * @param moonName il nome della luna da cercare
     * @return vero se esiste una luna con quel nome
     */
    public boolean searchMoonByName(String moonName) {

        for (Moon moon : moons) {
            if (moon.getName().equals(moonName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Compara due differenti pianeti
     * @param planet pianeta da comparare
     * @return true se uno solo tra: id, nome e posizione sono uguali falso in tutti gli altri casi
     */
    public boolean comparePlanet(Planet planet) {
        return (this.getId() == planet.getId() || Objects.equals(this.getName(), planet.getName()) || this.getPosition().isSamePosition(planet.getPosition()));
    }

    /**
     * @param planet pianeta la quale distanza è da comparare
     * @return vero se la distanza tra due pianeti è uguale
     */
    public boolean compareDistanceFromStar(Planet planet) {
        return (this.distanceFromStar == planet.getDistanceFromStar());
    }
}
