package arnaldo;

import unibs.InputInterface;
import unibs.MenuManager;

public class UserInterface {

    private static final String CONTINUE = "Press anything to continue...";
    private static final String ERROR = "Error: Some data collide with other celestial body";
    private static final String STARLESS_ERROR = "You can't add this celestial body because there isn't the star"; //No stars?
    private static final String PLANETLESS_ERROR = "There is not a planet with that name"; //No planets?

    /**
     * Apre un menu per far decidere quale corpo celeste aggiungere
     * @param starSystem sistema stellare
     */
    public static void addCelestialBody(StarSystem starSystem) {

        MenuManager menuManager = new MenuManager("Choose an action ", new String[]{"Add star", "Add planet", "Add moon"});
        switch (menuManager.choose()) {
            case -1 -> {}
            case 0 -> addSun(starSystem);
            case 1 -> addPlanet(starSystem);
            case 2 -> addMoon(starSystem);
            default -> throw new IllegalStateException("Unexpected value: " + menuManager.choose());
        }
    }

    /**
     * Chiede all'utente le informazioni necessarie per la creazione di un stella, con controllo se esiste già
     * @param starSystem sistema stellare
     */
    public static void addSun(StarSystem starSystem) {
        if(starSystem.isStarless()) {
            starSystem.setStar(new Star(InputInterface.readNotEmptyString("Enter the star's name:"),
                    (InputInterface.readDouble("Enter the mass of the planet:", 0,
                            InputInterface.MAX_DOUBLE))));

            System.out.println("Star set");
        } else {
            System.out.println("The star already exits");
        }

        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Chiede all'utente le informazioni necessarie per la creazione di un pianeta
     * @param starSystem sistema stellare
     */
    public static void addPlanet(StarSystem starSystem) {

        if(starSystem.isStarless()) {
            System.out.println(STARLESS_ERROR);
            System.out.println(CONTINUE);
            new java.util.Scanner(System.in).nextLine();
            return;
        }

        Planet planet = new Planet(
                InputInterface.readNotEmptyString("Enter the planet's name:"),
                (InputInterface.readDouble("Enter the mass of the planet:", 0, InputInterface.MAX_DOUBLE)),
                new Position(InputInterface.readDouble("Enter x position:"), InputInterface.readDouble("Enter y position:")),
                starSystem);

        if(starSystem.addPlanet(planet)) {
            System.out.println("Planet added");
        } else {
            System.out.println(ERROR);
        }
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Chiede all'utente le informazioni necessarie per la creazione di una luna
     * @param starSystem sistema stellare
     */
    public static void addMoon(StarSystem starSystem) {

        if(starSystem.isStarless()) {
            System.out.println(STARLESS_ERROR);
            System.out.println(CONTINUE);
            new java.util.Scanner(System.in).nextLine();
            return;
        }

        String planetName = InputInterface.readNotEmptyString("Enter the planet name (the one the moon orbits around):");
        Planet planet = starSystem.getPlanetByName(planetName);

        if(planet == null) {
            System.out.println(PLANETLESS_ERROR);
            System.out.println(CONTINUE);
            new java.util.Scanner(System.in).nextLine();
            return;
        }

        Moon moon = new Moon(InputInterface.readNotEmptyString("Enter the name:"),
                (InputInterface.readDouble("Enter the mass of the moon:", 0, InputInterface.MAX_DOUBLE)),
                new Position(InputInterface.readDouble("Enter x position:"), InputInterface.readDouble("Enter y position:")),
                planetName, starSystem);

        if(planet.addMoon(moon, starSystem)) {
            System.out.println("Moon added");
        } else {
            System.out.println(ERROR);
        }
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Pianeta da distruggere
     * @param starSystem sistema stellare
     */
    public static void destroyCelestialBody(StarSystem starSystem) {
        starSystem.destroyCelestialBody(InputInterface.readNotEmptyString("Insert celestial body's name to be destroyed: "));
    }

    /**
     * Stampa di tutti i corpi celesti
     * @param starSystem sistema stellare
     */
    public static void printCelestialBodies(StarSystem starSystem) {
        starSystem.printCelestialBodies();
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Cerca se un corpo celeste esiste e se è una luna, identifica il pianeta
     * @param starSystem sistema stellare
     */
    public static void searchCelestialBody(StarSystem starSystem) {
        if(!starSystem.searchName(InputInterface.readNotEmptyString("Enter the name of the celestial body: "))) {
            System.out.println("No celestial body with that name exists ");
        }
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Stampa le lune attorno a un dato pianeta
     * @param starSystem sistema stellare
     */
    public static void printMoonsAroundAPlanet(StarSystem starSystem) {
        starSystem.printMoonsAroundAPlanet(InputInterface.readNotEmptyString("Insert planet's name to see all the moons that orbits around it: "));
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Stampa percorso stella->pianeta->luna
     * @param starSystem sistema stellare
     */
    public static void printFullPath(StarSystem starSystem) {
        if(starSystem.printFullPath(InputInterface.readNotEmptyString("Insert moon's name to see the full path: "))) {
            System.out.println("That moon's name doesn't exist");
        }
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Stampa il centro di massa
     * @param starSystem sistema stellare
     */
    public static void printCenterOfMass(StarSystem starSystem) {
        System.out.println("The center of mass is: " + starSystem.getCenterOfMass());
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    public static void calculateRoute(StarSystem starSystem) {
        CelestialBody start = starSystem.getCelestialBodyByName(InputInterface.readNotEmptyString("Enter the name of the start: "));
        CelestialBody destination = starSystem.getCelestialBodyByName(InputInterface.readNotEmptyString("Enter the name of the destination: "));

        if(start == null || destination == null) {
            System.out.println("One of the two celestial bodies doesn't exist");
        } else {
            System.out.println("the cost is: " + starSystem.calculateRoute(start, destination, starSystem));
        }

        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Stampa all'utente se ci sono possibili collisioni
     * @param starSystem sistema stellare
     */
    public static void calculateImpact(StarSystem starSystem) {
        if(starSystem.calculateImpact()) {
            System.out.println("There might be collisions");
        } else {
            System.out.println("There are no possible collisions");
        }
        System.out.println(CONTINUE);
        new java.util.Scanner(System.in).nextLine();
    }
}
