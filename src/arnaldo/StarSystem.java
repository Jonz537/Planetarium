package arnaldo;

import java.util.ArrayList;

public class StarSystem {

  private final String name;

  private Star star;
  private ArrayList<Planet> planets = new ArrayList<>();

  /**
   * @param name nome del sistema stellare
   * @param star stella del sistema solare
   *             Costruttore del sistema stellare senza pianeti
   */

  public StarSystem(String name, Star star) {
    this.name = name;
    this.star = star;
  }

  /**
   * Costruttore con lista di pianeta
   * 
   * @param name    nome del sistema stellare
   * @param star    stella del sistema solare
   * @param planets lista dei pianeti
   */
  public StarSystem(String name, Star star, ArrayList<Planet> planets) {
    this.name = name;
    this.star = star;
    this.planets = planets;
  }

  public ArrayList<Planet> getPlanets() {
    return planets;
  }

  public String getStarName() {
    return star.getName();
  }

  public void setStar(Star newStar) {
    star = newStar;
  }

  /**
   * Controlla è presente una stella
   * 
   * @return true se non è presente una stella
   */
  public boolean isStarless() {
    return (star == null);
  }

  /**
   * Aggiungere un pianeta
   * 
   * @param planetToAdd pianeta da aggiungere alla lista
   * @return Restituisce falso se il pianeta è già presente nella lista
   */
  public boolean addPlanet(Planet planetToAdd) {

    if (searchName(planetToAdd.getName())) {
      return false;
    }
    // controlla se i dati inseriti sono gia presenti in altri pianeti
    for (Planet planet : planets) {
      if (planetToAdd.comparePlanet(planet)) {
        return false;
      }
      for (Moon moon : planet.getMoons()) {
        if (planetToAdd.getPosition().isSamePosition(moon.getPosition())) {
          return false;
        }
      }
    }

    planets.add(planetToAdd);
    return true;
  }

  /**
   * Rimozione di un corpo celeste
   * 
   * @param name nome del corpo celeste da rimuovere
   */
  public void destroyCelestialBody(String name) {

    if (star.getName().equals(name)) {
      star = null;
      planets.clear();
    }

    for (Planet planet : planets) {
      if (planet.getName().equals(name)) {
        planets.remove(planet);
        break;
      }
      for (Moon moon : planet.getMoons()) {
        if (moon.getName().equals(name)) {
          if (planet.removeMoon(moon)) {
            System.out.println("Moon destroyed");
          }
          break;
        }
      }
    }
  }

  /**
   * Stampa di tutti i corpi celesti
   */
  public void printCelestialBodies() {

    System.out.println("Star System: " + name);

    if (star == null) {
      System.out.println("Your star system is empty! Add a star");
      return;
    }

    System.out.println(star);

    for (Planet planet : planets) {

      System.out.println(planet.toString());

      for (Moon moon : planet.getMoons()) {
        System.out.println("\t " + moon.toString());
      }
    }
  }

  /**
   * Restituisce vero se trova un corpo celeste con un determinato nome
   * 
   * @param string nome del corpo celeste
   * @return true se il corpo celeste è presente nel sistema
   */
  public boolean searchName(String string) {

    if (star.getName().equals(string)) {
      System.out.println(string + " is the Star");
      return true;
    }

    for (Planet planet : planets) {

      if (planet.getName().equals(string)) {
        System.out.println(string + " is a planet");
        return true;
      }

      for (Moon moon : planet.getMoons()) {

        if (moon.getName().equals(string)) {
          System.out.println(string + " is a moon and orbits around " + planet.getName());
          return true;
        }

      }
    }
    return false;
  }

  /**
   * Restituisce il corpo celeste dato il nome
   * 
   * @param name nome del corpo celeste
   * @return il corpo celeste che presenta quel nome
   */
  public CelestialBody getCelestialBodyByName(String name) {

    if (star.getName().equals(name)) {
      return star;
    }

    for (Planet planet : planets) {
      if (planet.getName().equals(name)) {
        return planet;
      }
      for (Moon moon : planet.getMoons()) {
        if (moon.getName().equals(name)) {
          return moon;
        }
      }
    }

    return null;
  }

  /**
   * @param id id univoco del corpo celeste
   * @return Restituisce vero se trova un corpo celeste con un determinato id
   */
  public boolean searchId(long id) {

    if (id % Moon.MAX_MOON != 0) { // ricerca luna con relativo pianeta
      for (Planet planet : planets) {
        if (planet.searchMoon(id)) {
          return false;
        }
      }
    } else // ricerca stella
    if (id % (Planet.MAX_PLANETS * Planet.PLANET_CODE) != 0) { // ricerca pianeta
      for (Planet planet : planets) {
        if (planet.getId() == id) {
          return false;
        }
      }
    } else
      return star.getId() != id;

    // System.out.println("Id " + id + " doesn't exist");
    return true;
  }

  /**
   * Cerca un pianeta dato il nome
   * 
   * @param name nome del pianeta
   * @return vero se il pianeta è presente nella lista di pianeti
   */
  public boolean searchPlanetByName(String name) {
    for (Planet planet : planets) {
      if (planet.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Restituisce un pianeta dato il nome del pianeta
   * 
   * @param name nome del pianeta
   * @return pianeta cercato
   */
  public Planet getPlanetByName(String name) {
    for (Planet planet : planets) {
      if (planet.getName().equals(name)) {
        return planet;
      }
    }
    return null;
  }

  /**
   * Stampa tutte le lune che ruotano attorno a un dato pianeta
   */
  public void printMoonsAroundAPlanet(String planetName) {

    if (searchPlanetByName(planetName)) {

      System.out.print(planetName + "->");

      for (Moon moon : getPlanetByName(planetName).getMoons()) {
        System.out.println(moon.toString());
      }
    }
  }

  /**
   * Stampa percorso stella->pianeta->luna dato il nome della luna
   * 
   * @param moonName nome della luna cercata
   * @return ritorna false se ha trovato il percorso
   */
  public boolean printFullPath(String moonName) {

    for (Planet planet : planets) {
      if (planet.searchMoonByName(moonName)) {
        System.out.println(star.getName() + "->" + planet.getName() + "->" + moonName);
        return false;
      }
    }
    return true;
  }

  /**
   *
   * @return Restituisce la posizione pesata (centro di massa) di questo sistema
   *         stellare
   */
  public Position getCenterOfMass() {
    double totMass = this.star.getMass(), xPos = 0, yPos = 0;

    for (Planet planet : planets) {
      // Somma di tutte le masse e posizioni pesate dei pianeti
      totMass += planet.getMass();
      xPos += (planet.getMass() * planet.getPosition().getX());
      yPos += (planet.getMass() * planet.getPosition().getY());

      for (Moon moon : planet.getMoons()) {
        // Somma di tutte le masse e posizioni
        totMass += moon.getMass();
        xPos += (moon.getMass() * moon.getPosition().getX());
        yPos += (moon.getMass() * moon.getPosition().getY());
      }
    }

    return new Position(xPos / totMass, yPos / totMass);
  }

  /**
   * Calcola la distanza tra due corpi celesti
   * 
   * @param start       corpo celeste d'inizio
   * @param destination corpo celeste di arrivo
   * @return la distanza tra due corpi celesti
   */
  public double calculateDistance(CelestialBody start, CelestialBody destination) {
    return Math.sqrt(Math.pow(start.getPosition().getX() - destination.getPosition().getX(), 2)
        + Math.pow(start.getPosition().getY() - destination.getPosition().getY(), 2));
  }

  /**
   * Controllo della rotta (lezione su Dijkstra o A* quando?)
   * 
   * @param start       corpo celeste d'inizio
   * @param destination corpo celeste di arrivo
   * @return distanza tra le due destinazioni (tenendo conto delle leggi di scalo)
   */
  public double calculateRoute(CelestialBody start, CelestialBody destination, StarSystem starSystem) {

    CelestialBody nextStep = getCelestialBodyByName(start.getNameFather());
    CelestialBody prevStep;
    StringBuilder path;
    double cost = 0;

    // start instanceof Star
    if (start.getClass().equals(Star.class)) {
      path = new StringBuilder(start.getName() + " -> ");
    } else if (nextStep.equals(destination)) {
      path = new StringBuilder(start.getName() + " -> " + nextStep.getName());
    } else {
      path = new StringBuilder(start.getName() + " -> " + nextStep.getName() + " -> ");
    }
    cost += calculateDistance(start, nextStep);

    if (nextStep.equals(destination)) {
      System.out.println(path);
      return cost;
    }

    // pianeta verso propria luna
    if (start.getClass().equals(Planet.class)) {
      for (Moon moon : ((Planet) start).getMoons()) {
        if (moon.equals(destination)) {
          System.out.println(start.getName() + " -> " + destination.getName());
          return calculateDistance(start, destination);
        }
      }
    }

    // luna verso altro
    if (nextStep.getClass().equals(Planet.class)) {
      // Luna verso luna stesso pianeta
      for (Moon moon : ((Planet) nextStep).getMoons()) {
        if (moon.equals(destination)) {
          path.append(moon.getName());
          cost += calculateDistance(nextStep, moon);
          System.out.println(path);
          return cost;
        }
      }
    }

    // luna verso stella
    if (destination.getClass().equals(Star.class)) {
      prevStep = nextStep;
      nextStep = getCelestialBodyByName(nextStep.getNameFather());
      cost += calculateDistance(prevStep, nextStep);
      path.append(nextStep.getName());
      System.out.println(path);
      return cost;
    }

    // luna verso pianeta/luna altro pianeta
    if (!nextStep.getClass().equals(Star.class)) {
      prevStep = nextStep;
      nextStep = getCelestialBodyByName(nextStep.getNameFather());
      cost += calculateDistance(prevStep, nextStep);
      path.append(nextStep.getName()).append(" -> ");
    }

    for (Planet planet : starSystem.getPlanets()) {
      if (planet.equals(destination)) {
        path.append(planet.getName());
        cost += calculateDistance(nextStep, planet);
        System.out.println(path);
        return cost;
      }

      for (Moon moon : planet.getMoons()) {
        if (moon.equals(destination)) {
          path.append(planet.getName()).append(" -> ").append(moon.getName());
          cost += calculateDistance(nextStep, planet) + calculateDistance(planet, moon);
          System.out.println(path);
          return cost;
        }
      }
    }
    // FINE luna verso pianeta/luna altro pianeta

    // return -1 in tutti gli altri casi
    return -1;
  }

  /**
   * @return true se esiste una configurazione possibile che causerebbe una
   *         collisione tra due qualsiasi corpi celesti
   */
  public boolean calculateImpact() {
    // FATTI: PIANETA-PIANETA, LUNA-PIANETA, LUNA-STELLA, LUNA-LUNA
    // NO SENSE: STELLA-STELLA, PIANETA-STELLA

    for (Planet planetToCheck : planets) {
      for (Planet planet : planets) {
        // PIANETA-PIANETA controlla se raggi dei pianeti sono uguali
        if (!planetToCheck.equals(planet) && planetToCheck.compareDistanceFromStar(planet)) {
          return true;
        }
        // LUNE controllo delle lune
        for (Moon moon : planet.getMoons()) {
          if (moon.collideWithStar()) {
            return true;
          }
          // LUNA-PIANETA controllo luna con pianeta
          if (!planetToCheck.equals(planet) && moon.isBetweenRing(planetToCheck)) {
            return true;
          }

          // LUNA-LUNA controllo luna con altre lune
          for (Moon moonToCheck : planetToCheck.getMoons()) {
            if (!moonToCheck.compareMoon(moon) && moonToCheck.collideWithMoon(moon)
                && !moonToCheck.getNameFather().equals(moon.getNameFather())) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
}