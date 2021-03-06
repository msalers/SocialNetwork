package main.model;

import main.model.event.*;
import main.model.soccerMatchCategory.*;
import main.model.cinemaCategory.*;
import main.fileUtilities.*;



import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale del social network, contiene i riferimenti alle varie categorie, agli utenti iscritti,
 * agli oggetti di utilità per la lettura/scrittura su file
 */
public class SocialNetwork {

	//Attributi
    private List<Category<? extends Event>> categories;
	private SoccerMatchCategory soccerMatchCategory;
	private CinemaCategory cinemaCategory;
	private List<User> users;
	private User loggedUser; //utente attualmente loggato
	private FileUtility fileUtility;

	private String themePath;
//	private NotificationsBuilder notificationsHandler;

	/**
	 * Costruttore SocialNetwork
	 */
	public SocialNetwork() {
	    this.categories = new ArrayList<>();
	    this.soccerMatchCategory = new SoccerMatchCategory();
	    this.cinemaCategory = new CinemaCategory();
	    this.categories.add(soccerMatchCategory); //aggiungo la categoria all'array
		this.categories.add(cinemaCategory);
		this.fileUtility = FileUtility.getInstance();
//		this.notificationsHandler = new NotificationsBuilder();
	}

	public void setThemePath(String path){ this.themePath = path; }

	public String getThemePath() { return themePath; }

	public void addCategory(Category category) {
	    this.categories.add(category);
    }

    public List<Category<? extends Event>> getCategories() {
	    return this.categories;
    }

	public SoccerMatchCategory getSoccerMatchCategory(){
		return this.soccerMatchCategory;
	}

	public CinemaCategory getCinemaCategory(){
		return this.cinemaCategory;
	}


	public List<User> getUsers() { return this.users;	}

//	public NotificationsBuilder getNotificationsHandler() { return this.notificationsHandler; }

	public void addUser(User user) {
		this.users.add(user);
	}

	public void setUsers(List<User> users){
		this.users = users;
	}

	/**
	 * Metodo per la ricerca di una categoria all'interno del Social Network, la ricerca
	 * viene effettuata attraverso il nome, considerato ceh sono delle costanti non ci possono
	 * essere errori di battitura
	 * @param name
	 * @return
	 */
	public Category findCategoryByName(String name){
		Category found = null;

		for(Category cat : categories){
			if ( cat.getName().equalsIgnoreCase(name)){
				found = cat;
			}
		}

		return found;
	}

	/**
	 * Metodo per la ricerca di una categoria all'interno del Social Network attraverso
	 * l'indice della categoria, visto che scegliamo noi l'ordine e l'utente non puo modificarlo
	 * non possono verificarsi errori
	 * @param index
	 * @return categoria trovata
	 */
	public Category findCategoryByIndex(int index){
		return categories.get(index);
	}

	/**
	 * Metodo che serve per il controllo della presenza di un utente dal nome, torna un valore boleano "true"
	 * se l'utente esiste e "false" se invece non esiste
	 * @param userNameToCheck
	 * @return una costante boleana che indica se l'utente esiste o meno
	 */
	public boolean doesUserExist(String userNameToCheck){
		for (User user : users) {
			if(userNameToCheck.equalsIgnoreCase(user.getUsername()))
				return true;
		}
		return false;
	}

	/**
	 * Controlla se l'utente è già presente nella lista utenti e incaso ritorna un suo riferimento
	 * @param usernameToFind il nome dell'utente da cercare
	 * @return l'utente trovato
	 */
	public User findUserByName(String usernameToFind){
		for (User user : users) {
			if(usernameToFind.equalsIgnoreCase(user.getUsername()))
				return user;
		}
		return null;
	}

	/**
	 * Registra un nuovo utente, aggiungendolo alla lisa di utenti e aggiornando il file contenente la list
	 * @param user l'utente da registrare
	 */
	public void registerNewUser(User user) {
		addUser(user); //aggiungo utente all'array
		updateUsersListFile(); //aggiorno il file degli utenti
	}

	/**
	 * Effettua il login dell'utente aggiornando loggedUser come l'utente corrente
	 * @param loggedUser l'utente da loggare
	 */
	public void loginUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	/**
	 * legge il file contenente la lista di utenti registrati lo carica all'interno di users
	 */
	public void loadUsersListFromFile() {
		this.users = fileUtility.readUsersList();
	}

	/**
	 * Aggiorna il file contenente la lista degli utenti scrivendo al suo interno
	 * il contenuto attuale della lista di utenti dell'applicazione.
	 */
	public void updateUsersListFile () {
		fileUtility.writeUsersList(this.users);
	}

	/**
	 *  Salva su file la lista aggiornata di utenti e quella di eventi di tipo Soccer Match
	 */
	public void updateUserAndEventsListFile(){
		fileUtility.writeUsersList(this.users);
		writeSoccerMatchEventListOnFile();
		writeCinemaEventListOnFile();
	}

	/**
	 * legge da file tutti gli eventi appartenenti alle categorie esistenti
	 */
	public void loadAllEventsFromFile() {
		loadSoccerMatchEventListFromFile();
		loadCinemaEventListFromFile();
	}


	/**
	 * Salva in maniera persistente su file la lista di eventi di tipo SoccerMatchEvent
	 */
	public void writeSoccerMatchEventListOnFile() {
        fileUtility.writeSoccerMatchEvents(soccerMatchCategory.getEvents());
    }

	/**
	 * Salva in maniera persistente su file la lista di eventi di tipo CinemaEvent
	 */
	public void writeCinemaEventListOnFile() {
		fileUtility.writeCinemaEvents(cinemaCategory.getEvents());
	}

	/**
	 * legge il file contenente la lista di SoccerMatchEvent e lo carica all'interno della lista di eventi della categoria corrispondente
	 */
	private void loadSoccerMatchEventListFromFile() {
		soccerMatchCategory.setEvents(fileUtility.readSoccerMatchEvents());
	}

	/**
	 * legge il file contenente la lista di CinemaEvent e lo carica all'interno della lista di eventi della categoria corrispondente
	 */
	private void loadCinemaEventListFromFile() {
		cinemaCategory.setEvents(fileUtility.readCinemaEvents());
	}


	// Metodo che serve per trovare tutti gli eventi a cui e' iscritto un dato utente e ne ritorna i nomi
	public List<String> findEventByUserNameS(String userSession){

		ArrayList<String> eventsUser = new ArrayList<>();
	/*
		for(int i=0; i<categories.size(); i++){
			Category catSel = categories.get(i);
			List<Event> eventsByCat = catSel.getEvents();
			for(int j=0; j < eventsByCat.size(); j++){
				if(eventsByCat.get(j).isUserAlreadyRegistered(userSession)== true){
					eventsUser.add((String) eventsByCat.get(j).getTitle().getValue());
				}
			}

		}
	*/
		for(Category<?extends Event> cat: categories){
			for(Event event: cat.getEvents()){
				if(event.isUserAlreadyRegistered(userSession))
					eventsUser.add((String) event.getTitle().getValue());
			}
		}

		return eventsUser;
	}

	// Simile al precedente ma in questo caso trona un arraylist di eventi, serve per la classe notification
	public List<Event> findEventByUserNameE(String userSession){

		ArrayList<Event> eventsUser = new ArrayList<>();
/*
		for(int i=0; i<categories.size(); i++){
			Category catSel = categories.get(i);
			List<Event> eventsByCat = catSel.getEvents();
			for(int j=0; j < eventsByCat.size(); j++){
				if(eventsByCat.get(j).isUserAlreadyRegistered(userSession)== true){
					eventsUser.add(eventsByCat.get(j));
				}
			}

		}
*/
		for(Category<?extends Event> cat: categories){
			for(Event event: cat.getEvents()){
				if(event.isUserAlreadyRegistered(userSession))
					eventsUser.add(event);
			}
		}
		return eventsUser;
	}

	/**
	 * Cerca un evento a partire dal suo nome tra tutte le categorie
	 * @param eventName il nome dell'evento da cercare
	 * @return il riferimento all'evento nel caso in cui sia stato trovato
	 */
	public Event findEventByEventName(String eventName){
		for (Category<? extends Event> category : categories) {
			for (Event event : category.getEvents()) {
				if(eventName.equalsIgnoreCase(event.getTitle().getValue()))
					return event;
			}
		}
		return null;
	}

	/**
	 * Restituisce la lista di eventi creati da un certo utente
	 * @param user l'username dell'utente
	 * @return la lista di eventi creati dall'utente specificato
	 */
	private List<Event> getEventsCreatedByUser(String user){


		List<Event> eventsCreatedByUser = new ArrayList<>();

		for(Category<? extends Event> category : categories) {

			eventsCreatedByUser.addAll(category.getEventCreateByUserinCat(user));
		}

		return eventsCreatedByUser;

	}

	/**
	 * Restituisce la lista di utenti che hanno partecipato ad eventi passati creati da un utente specificato
	 * @param user l'username dell'utente, creatore degli eventi passati
	 * @return la lista di utenti che hanno partecipato ad eventi creati dall'utente specificato
	 */
	public List<String> getUserThatPlayOtherCreatorEvents(String user){

		List<String> participantsAlreadyPlay = new ArrayList<>();
		List<Event> eventsCreatedByUser = getEventsCreatedByUser(user);

		for(Event event: eventsCreatedByUser){
			for(Member participant: event.getParticipants()){
				if(!participantsAlreadyPlay.contains(participant.getUsername()) && !(participant.getUsername()).equalsIgnoreCase(user)){
					participantsAlreadyPlay.add(participant.getUsername());
				}
			}
		}

		return participantsAlreadyPlay;

	}


}
