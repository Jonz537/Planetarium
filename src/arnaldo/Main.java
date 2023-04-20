package arnaldo;

import unibs.MenuManager;

import java.util.ArrayList;

/**
 * @author Saleri Giorgio, Christian Colosio, Maiolo Daniele
 */
public class Main {

  public static MenuManager menuManager = new MenuManager("What do you want to do?",
      new String[] { "Add a celestial body", "Destroy a celestial body", "See celestial bodies",
          "Search a celestial body by name", "Moons around a planet", "Print full path given a moon",
          "Print center of mass", "Calculate the path between two celestial bodies", "Calculate any collisions" });

  public static void main(String[] args) {

    StarSystem starSystem = new StarSystem("Sun", new Star("Sun", 4));
    init(starSystem);

    boolean running = true;
    while (running) {
      switch (menuManager.choose()) {
        case -1 -> running = false; // Uscita dal programma
        case 0 -> UserInterface.addCelestialBody(starSystem); // Aggiunta di un corpo celeste
        case 1 -> UserInterface.destroyCelestialBody(starSystem); // Cancellazione di un corpo celeste
        case 2 -> UserInterface.printCelestialBodies(starSystem); // Stampa di tutti i corpi
        case 3 -> UserInterface.searchCelestialBody(starSystem); // Ricerca di un corpo celeste
        case 4 -> UserInterface.printMoonsAroundAPlanet(starSystem); // Stampa delle lune attorno a un pianeta
        case 5 -> UserInterface.printFullPath(starSystem); // Stampa del percorso Stella->Pianeta->Luna
        case 6 -> UserInterface.printCenterOfMass(starSystem); // Stampa del centro di massa
        case 7 -> UserInterface.calculateRoute(starSystem); // Calcolo del percorso da un corpo celeste a un altro
        case 8 -> UserInterface.calculateImpact(starSystem); // Calcolo delle collisioni
      }
    }

    System.out.println("\nToo bad!");
  }

  /**
   * Funzione d'inizializzazione per testing
   * 
   * @param starSystem sistema stellare
   */

  public static void init(StarSystem starSystem) {
    try {
      // too bad!
      starSystem.addPlanet(new Planet("Giove", 1, new Position(5, 0), starSystem));
      starSystem.getPlanetByName("Giove").addMoon(new Moon("Orecchie", 1, new Position(5, 1), "Giove", starSystem),
          starSystem);
      starSystem.getPlanetByName("Giove").addMoon(new Moon("Gatto", 1, new Position(5, 4), "Giove", starSystem),
          starSystem);
      starSystem.addPlanet(new Planet("Dante", 1, new Position(10, 0),
          starSystem));
      starSystem.getPlanetByName("Dante").addMoon(new Moon("Nero", 1, new Position(10, 0.9), "Dante", starSystem),
          starSystem);
      starSystem.getPlanetByName("Dante").addMoon(new Moon("V", 1, new Position(10,
          2), "Dante", starSystem), starSystem);
    } catch (NullPointerException ignored) {

    }
  }

  /*
   * ● Gestione dei corpi celesti del sistema stellare:
   * ○ Aggiunta di nuovi pianeti o lune, in caso di nuove scoperte. FATTO
   * ○ Rimozione di vecchi pianeti o lune, in caso di “catastrofi naturali”. FATTO
   * ○ Identificazione di ciascun corpo celeste con un codice univoco. FATTO
   * ● Ricerca di un corpo celeste all’interno del sistema:
   * ○ Possibilità di capire se è presente nel sistema stellare. FATTO
   * ○ Nel caso di lune, identificazione del pianeta attorno a cui gira. FATTO
   * ● Visualizzazione delle informazioni:
   * ○ Dato un pianeta, visualizzazione delle lune che gli orbitano intorno. FATTO
   * ○ Data una luna, visualizzazione del percorso [stella > pianeta > luna]
   * necessario per raggiungerla. FATTO
   * ● Calcolo del centro di massa su richiesta, sulla base delle informazioni
   * disponibili volta per volta. FATTO
   * 
   * FATTO Calcolo della rotta fra due corpi celesti:
   * ○ Su richiesta dell’utente, il programma deve mostrare la rotta fra due corpi
   * del sistema stellare
   * selezionati dall’utente stesso.
   * ○ Le leggi imperiali impongono che, nel caso in cui si viaggi fra due corpi
   * di uguale “grado” (due lune, o
   * due pianeti), si faccia scalo sul corpo celeste di grado più alto
   * (rispettivamente, il pianeta in comune o la
   * stella del sistema). In questo modo, la rotta fra due corpi celesti qualunque
   * sia unica, e l’Impero riesce a
   * mantenere l’ordine.
   * ○ La rotta deve essere rappresentata come sequenza di corpi celesti (ad
   * esempio: Luna 1 > Pianeta 1 >
   * Stella > Pianeta 2).
   * ○ Deve essere indicata la distanza totale che si percorrerebbe seguendo la
   * sequenza sopra indicata
   * (nell’esempio precedente: 8,65).
   * FATTO Calcolo della collisione fra i corpi celesti:
   * ○ Su richiesta dell’utente, il programma deve stabilire se sia possibile che
   * due corpi qualunque del
   * sistema collidano uno contro l’altro. Non è necessario stabilire quando e
   * dove, né quali corpi
   * siano, ma solamente se questo possa succedere.
   * ○ Due corpi possono collidere se e solo se esiste una configurazione del
   * sistema per cui due corpi
   * possono trovarsi nella stessa posizione puntuale.
   * ○ Ogni corpo ha distanza fissa dal corpo attorno a cui ruota (detta raggio di
   * rivoluzione). Pertanto,
   * nessuna luna colliderà mai col proprio pianeta e nessun pianeta colliderà mai
   * con la stella del
   * sistema.
   * TODO testing
   */

}