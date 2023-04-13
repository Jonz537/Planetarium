package arnaldo;

import java.util.Objects;

public class Moon extends CelestialBody {

    private final double internalRing;
    private final double externalRing;
    public static final int MAX_MOON = 5000;

    public Moon(String name, double mass, Position position, String planetName, StarSystem starSystem) {
        super(name, mass, position, planetName);

        for (int i = 1; i <= MAX_MOON; i++) {
            if (starSystem.searchId(starSystem.getPlanetByName(planetName).getId() + i)) {
                this.setId(starSystem.getPlanetByName(planetName).getId() + i);
                break;
            }
        }
        double distanceFromPlanet = Math.sqrt(Math.pow(starSystem.getPlanetByName(planetName).getPosition().getX() - position.getX(), 2) + Math.pow(starSystem.getPlanetByName(planetName).getPosition().getY() - position.getY(), 2));
        internalRing = starSystem.getPlanetByName(planetName).getDistanceFromStar() - distanceFromPlanet;
        externalRing = starSystem.getPlanetByName(planetName).getDistanceFromStar() + distanceFromPlanet;
    }

    public double getInternalRing() {
        return internalRing;
    }

    public double getExternalRing() {
        return externalRing;
    }

    /**
     * @param moon luna da comparare
     * @return true se hanno anche solo un attributo in comune tra: id, nome e posizione
     */
    public boolean compareMoon(Moon moon) {
        return (this.getId() == moon.getId() || Objects.equals(this.getName(), moon.getName()) || this.getPosition().isSamePosition(moon.getPosition()));
    }

    /**
     * @param planet pianeta con il quale si controlla la collisione
     * @return vero se il pianeta può essere contenuto nella corona della luna
     */
    public boolean isBetweenRing(Planet planet) {
        // controlla che il raggio della stella è contenuto nella corona
        return (planet.getDistanceFromStar() >= internalRing && planet.getDistanceFromStar() <= externalRing);
    }

    /**
     * @param moon luna con il quale si controlla la collisione
     * @return vero se l'intersezione delle corone delle lune è diversa da zero (c'è collisione)
     */
    public boolean collideWithMoon(Moon moon) {
        //[a:b] [c:d] oppure [c:d] [a:b]
        // b>c && c>a || d>a && a>c
        return ((this.externalRing >= moon.getInternalRing() && moon.getInternalRing() >= this.internalRing )
                || (moon.getExternalRing() >= this.internalRing && this.internalRing >= moon.getInternalRing()));
    }

    public boolean collideWithStar() {
        return (this.externalRing >= 0 && this.internalRing <= 0 );
    }
}
