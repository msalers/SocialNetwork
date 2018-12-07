package versione1;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.Date;

public abstract class Event{

    //Costanti della classe Event
    public static final String TITLE_NAME = "Titolo";
    public static final String TITLE_DESCRIPTION = "Campo facoltativo che consiste in un nome di fantasia attribuito all’evento";
    public static final String NUMPLAY_NAME = "Numero di partecipanti";
    public static final String NUMPLAY_DESCRIPTION = "Campo obbligatorio che stabilisce il numero di persone da coinvolgere nell'evento";
    public static final String REGDEADLINE_NAME = "Termine ultimo iscrizione";
    public static final String REGDEADLINE_DESCRIPTION = "Campo obbligatorio che inidica l'ultima data possibile per iscriversi";
    public static final String PLACE_NAME = "Luogo";
    public static final String PLACE_DESCRIPTION = "Campo obbligatorio che indica l’indirizzo del luogo che ospiterà l’evento oppure, se l’evento è itinerante, il luogo di ritrovo dei partecipanti";
    public static final String DATE_NAME = "Data";
    public static final String DATE_DESCRIPTION = "Campo obbligatorio che indica la data in cui l’evento proposto deve svolgersi o, nel caso l’evento non termini nello stesso giorno in cui ha inizio, la data di inizio dell’evento";
    public static final String TIME_NAME = "Ora";
    public static final String TIME_DESCRIPTION = "Campo obbligatorio che indica l’ora in cui i partecipanti dovranno trovarsi nel luogo “Luogo” in data “Data” per dare inizio all’evento";
    public static final String DURATION_NAME = "Durata";
    public static final String DURATION_DESCRIPTION =  "Campo facoltativo che indica la durata in termini di numero (approssimativo) di ore e minuti, per gli eventi che si esauriscono in un sol giorno, o in termini di numero esatto di giorni, per gli eventi che occupano più giorni consecutivi";
    public static final String INDTEE_NAME = "Quota individuale";
    public static final String INDTEE_DESCRIPTION = "Campo obbligatorio che indica la spesa (o una stima della stessa) che ogni partecipante all’iniziativa dovrà sostenere (si noti che la spesa può anche essere nulla)";
    public static final String TEEINC_NAME = "Compreso nella quota";
    public static final String TEEINC_DESCRIPTION = "Campo facoltativo che indica tutte le voci di spesa comprese nell’ammontare indicato nella “Quota individuale”";
    public static final String ENDDATE_NAME = "Data conclusiva";
    public static final String ENDDATE_DESCRIPTION = "Campo facoltativo che fissa la data di conclusione dell’evento";
    public static final String ENDTIME_NAME = "Ora conclusiva";
    public static final String ENDTIME_DESCRIPTION = "Campo facoltativo che stima l’ora di conclusione dell’evento";
    public static final String NOTE_NAME = "Note";
    public static final String NOTE_DESCRIPTION = "Campo facoltativo contenente informazioni aggiuntive circa l’evento";


	//Attributi della classe Event
	private ArrayList<Field> fields;
	private Field title = new Field(TITLE_NAME,TITLE_DESCRIPTION);
	private Field numOfPartecipants = new Field(NUMPLAY_NAME, NUMPLAY_DESCRIPTION);
	private Field registrationDeadline = new Field(REGDEADLINE_NAME, REGDEADLINE_DESCRIPTION);
	private Field place = new Field(PLACE_NAME, PLACE_DESCRIPTION);
	private Field date = new Field(DATE_NAME,DATE_DESCRIPTION);
	private Field time = new Field(TIME_NAME, TIME_DESCRIPTION);
	private Field duration = new Field(DURATION_NAME, DURATION_DESCRIPTION);
	private Field indTee = new Field(INDTEE_NAME,INDTEE_DESCRIPTION);
	private Field teeInclude = new Field(TEEINC_NAME, TEEINC_DESCRIPTION);
	private Field endDate = new Field(ENDDATE_NAME,ENDDATE_DESCRIPTION);
	private Field endTime = new Field(ENDTIME_NAME, ENDTIME_DESCRIPTION);
	private Field note = new Field(NOTE_NAME,NOTE_DESCRIPTION);


	/*
	Pietro: dite che potremmo fare a meno dell'array di eventi?
        Per me sì: dal momento che quando dovremo ricavare informazioni su un campo, anche per piazzarlo
        nell'interfaccia grafica, useremo il get su quel campo specifico..
        L'alternativa è avere un metodo getCampo(String nomeCampo) che itera l'array cercando
        un campo con nome = nomeCampo ed eventualmente lo restituisce.
        Qual è la soluzione migliore secondo voi?
    Maria: per me come soluzione è fattibile, l'array era stato pensato effettivamente come contenitore ma in realtà sarebbe
        solo una struttura in più che ci potrebbe dare fastidio, in caso potremmo ri introdurlo in seguito.

	 */



    /**
     * Costruttore vuoto: viene inizializzato l'array di campi, ciascuno dei quali con
     * nome e descrizione ma senza valore.
     */
	public Event() {
		this.fields = new ArrayList<>();
		init();
	}

    /**
     * Costruttore dei campi obbligatori: viene popolato l'attributo valore di ciascun campo obbligatorio.
     * @param numOfPartecipants numero dei partecipanti
     * @param registrationDeadline termine ultimo iscrizione
     * @param place luogo dell'evento
     * @param date data dell'evento
     * @param time ora dell'evento
     * @param indTee quota individuale
     */
	public Event(int numOfPartecipants, Date registrationDeadline, String place, Date date, Date time, float indTee) {
	    this.numOfPartecipants.setValue(numOfPartecipants);
	    this.registrationDeadline.setValue(registrationDeadline);
	    this.place.setValue(place);
	    this.date.setValue(date);
	    this.time.setValue(time);
	    this.indTee.setValue(indTee);
    }



    //Metodo da togliere in mancanza dell'ArrayList
	private void init() {
		fields.add(title);
		fields.add(numOfPartecipants);
		fields.add(registrationDeadline);
		fields.add(place);
		fields.add(date);
		fields.add(time);
		fields.add(duration);
		fields.add(indTee);
		fields.add(teeInclude);
		fields.add(endDate);
		fields.add(endTime);
		fields.add(note);
	}

    public ArrayList<Field> getFields() {
        return fields;
    } //Metodo da togliere in mancanza dell'ArrayList

    public Field getTitle() {
        return title;
    }

    public Field getNumOfPartecipants() {
        return numOfPartecipants;
    }

    public Field getRegistrationDeadline() {
        return registrationDeadline;
    }

    public Field getPlace() {
        return place;
    }

    public Field getDate() {
        return date;
    }

    public Field getTime() {
        return time;
    }

    public Field getDuration() {
        return duration;
    }

    public Field getIndTee() {
        return indTee;
    }

    public Field getTeeInclude() {
        return teeInclude;
    }

    public Field getEndDate() {
        return endDate;
    }

    public Field getEndTime() {
        return endTime;
    }

    public Field getNote() {
        return note;
    }


}
