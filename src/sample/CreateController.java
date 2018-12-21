package sample;

import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import versione1.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CreateController {

    public static final String SOCCER_NAME = "Partite di calcio";


    // ~~~~~ newEvent Stage ~~~~~~~~~~~

    @FXML
    private Label durUnitLbl, catLbl, numPLbl, deadLLbl, placeLbl,dateLbl,timeLbl,indTeeLbl, endDateLbl, endTimeLbl, ageLbl,totTeLbl, genderLbl;
    @FXML
    private TextField titleTxtF, numPTxtF, placeTxtF, indTeeTxtF, totTeeTxtF;
    @FXML
    private Button createBtn;
    @FXML
    private ChoiceBox<String> catCB;
    @FXML
    private DatePicker deadLineDP, dateDP,endDateDP;
    @FXML
    private ChoiceBox<Integer> minAgeCB, maxAgeCB, durBigCB,durLitCB;
    @FXML
    private ChoiceBox<Gender> genderCB;
    @FXML
    private JFXTimePicker endTimeTP, timeTP;
    @FXML
    private TextArea noteTxtA;
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private boolean create = false;
    private ArrayList<Integer> isValid;
    private AgeGroup ageGroup;
    private ArrayList<Integer> ageRangeMin;



    // ~~~~~~ Campi FACOLTATIVI ~~~~~~~~

    private String titleIns;
    private String totTeeIns;
    private LocalDate endDateIns;
    private LocalTime endTimeIns;
    private String noteIns;
    private int durH, durM, durD;
    private String durationIns;

    private boolean endDateIsVal = false;
    private boolean endTimeIsVal = false;
    private boolean durIsVal = false;


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~ Campi OBBLIGATORI ~~~~~~

    private String categoryIns;
    private int numParIns;
    private LocalDate deadLineIns;
    private String placeIns;
    private LocalDate dateIns;
    private LocalTime timeIns;
    private float indTeeIns;
    private int minAge, maxAge;
    private String ageRangeIns;
    private Enum<Gender> genderIns;


    private boolean catIsVal = false;
    private boolean numIsVal = false;
    private boolean deadLineIsVal = false;
    private boolean placeIsVal = false;
    private boolean dateIsVal = false;
    private boolean timeIsVal = false;
    private boolean indTeeIsVal = false;
    private boolean ageIsVal= false;
    private boolean genderIsVal = false;


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    private SocialNetwork socialNetwork;
    private Stage thisStage;


    // ~~~~~~~~ Metodi ~~~~~~~~~~~~~


    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }


    /**
     * Il metodo serve per inizializzare la finestra, in particolare per impostare gli observable list nei
     * choiceBox e per rendere determinati choiceBox abilitati o meno in base alla scelta della categoria.
     * Se si seleziona Partita di Calcio viene abilitata la selezione della fascia di età e del genere,
     * poichè non ci deve essere inconsistenza tra fascia di età minima e massima il choiceBox della massima
     * viene abilitato solo dopo aver selezionato la minima e con valori suoeriori (maggiore stretto) dalla
     * minima selezionata
     *
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        isValid = new ArrayList<>();
        ageGroup = new AgeGroup();
        ageRangeMin = ageGroup.getNumeri();

        catCB.setItems(FXCollections.observableArrayList(SOCCER_NAME));

        catCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(catCB.getSelectionModel().getSelectedItem().equals(SOCCER_NAME)){
                    minAgeCB.setDisable(false);
                    minAgeCB.setItems(FXCollections.observableArrayList(ageRangeMin));
                    genderCB.setDisable(false);
                    durBigCB.setDisable(false);
                    durLitCB.setDisable(true);
                    durUnitLbl.setText("d");
                    durBigCB.setItems(FXCollections.observableArrayList(MyUtil.getArray(1, 10)));
                }
            }
        });

        minAgeCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maxAgeCB.setDisable(false);
                ArrayList<Integer> ageRangeMax = ageGroup.getMinOf(minAgeCB.getSelectionModel().getSelectedItem());
                maxAgeCB.setItems(FXCollections.observableArrayList(ageRangeMax));
            }
        });



        endDateDP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(endDateIsVal == true && dateIsVal == true && (endDateDP.getValue().isEqual(dateDP.getValue())) && (endTimeTP.getValue() == null)) {
                    durBigCB.setDisable(false);
                    durBigCB.setItems(FXCollections.observableArrayList(MyUtil.getArray(0, 23)));
                    durLitCB.setDisable(false);
                    durLitCB.setItems(FXCollections.observableArrayList(MyUtil.getArray(1, 59)));
                    durUnitLbl.setText("hh:mm");
                }
                else {
                    durBigCB.setDisable(true);
                    durLitCB.setDisable(true);
                    durUnitLbl.setText(" ");
                }
            }
        });

        endTimeTP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                durBigCB.setDisable(true);
                durLitCB.setDisable(true);
                durUnitLbl.setText(" ");
            }
        });

        genderCB.setItems(FXCollections.observableArrayList(Gender.Maschile, Gender.Femminile));

    }


    /**
     * Metodo per la creazione effettiva dell'evento, il metotodo applicato su thisStage chiude la finestra corrente una volta
     * inseriti tutti i campi e effettuati i controlli. I campi obbligatori sono:
     * - numero dei partecipanti
     * - termine ultimo iscrizione
     * - luogo
     * - data
     * - ora
     * - quota individuale
     * Gli altri campi sono facolatitivi e quindi possono anche essere omessi.
     * In oltre è obbligatorio prima di creare l'evento selezionare a quale categoria appartenga.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void createEvent(ActionEvent actionEvent) throws IOException {

        // Acquisisco la categoria dal choiceBox, e se la categoria non selezionata non vado avanti
        // OBBLIGATORIO
        // categoria

        if (catCB.getSelectionModel().getSelectedItem() == null) {
            catLbl.setTextFill(Color.RED);
            catIsVal = false;
        } else {
            categoryIns = catCB.getSelectionModel().getSelectedItem();
            catLbl.setTextFill(Color.BLACK);
            catIsVal = true;


            // Acquisisco se c e il titolo
            // FACOLTATIVO
            //titolo
            titleIns = titleTxtF.getText();

            // Acquisisco il numero di partecipanti, controllo sia inserito e che sia un valore numerico
            // OBBLIGATORIO
            // numero partecipanti

            if (numPTxtF.getText().isEmpty() || !MyUtil.checkInteger(numPTxtF.getText())) {
                numPLbl.setTextFill(Color.RED);
                numIsVal = false;
            } else {
                String numPS = numPTxtF.getText();
                numPLbl.setTextFill(Color.BLACK);
                numParIns = Integer.parseInt(numPS);
                numIsVal = true;
            }

            // Acquisco il campo data e controllo che non sia una data precedente ad oggi e vuota
            // OBBLIGATORIO
            // termine ultimo iscrizione

            if ((deadLineDP.getValue() == null) || deadLineDP.getValue().isBefore(LocalDate.now().plusDays(1))) {
                deadLLbl.setTextFill(Color.RED);
                deadLineIsVal = false;
            } else {
                deadLineIns = deadLineDP.getValue();
                deadLLbl.setTextFill(Color.BLACK);
                deadLineIsVal = true;
            }

            // Acquisisco il luogo controllando che sia inserito e che sia una stringa
            // OBBLIGATORIO
            //luogo

            if (placeTxtF.getText().isEmpty() || !MyUtil.checkString(placeTxtF.getText())) {
                placeLbl.setTextFill(Color.RED);
                placeIsVal = false;
            } else {
                placeIns = placeTxtF.getText();
                placeLbl.setTextFill(Color.BLACK);
                placeIsVal = true;
            }

            // Acquisisco la data dell'evento, controllo che il campo non sia vuoto, non sia prima il termine ultimo + 1
            // OBBLIGATORIO
            //data

            if ((dateDP.getValue() == null) || deadLineIsVal == false || dateDP.getValue().isBefore(deadLineIns.plusDays(1))) {
                dateLbl.setTextFill(Color.RED);
                dateIsVal = false;
            } else {
                dateIns = dateDP.getValue();
                dateLbl.setTextFill(Color.BLACK);
                dateIsVal = true;
            }

            // Acquisisco l ora a cui si verifichera
            // OBBLIGATORIO
            //ora

            if (timeTP.getValue() == null || dateIsVal == false) {
                timeLbl.setTextFill(Color.RED);
                timeIsVal = false;
            } else {
                timeIns = timeTP.getValue();
                timeLbl.setTextFill(Color.BLACK);
                timeIsVal = true;
            }

            // Acquisisco la quota individuale e controllo non sia vuota, in caso la converto in decimale
            // OBBLIGATORIO
            //quota individuale

            if (indTeeTxtF.getText().isEmpty() || !MyUtil.checkFloat(indTeeTxtF.getText())) {
                indTeeLbl.setTextFill(Color.RED);
                indTeeIsVal = false;
            } else {
                String indTeeS = indTeeTxtF.getText();
                indTeeLbl.setTextFill(Color.BLACK);
                indTeeIns = Float.parseFloat(indTeeS);
                indTeeIsVal = true;
            }

            // Indicala voci di spesa
            // FACOLTATIVO
            // voci della quota
            totTeeIns = totTeeTxtF.getText();


            // Acquisisco se c e la data di termine e controllo che non sia prima della data dell evento
            // FACOLTATIVO -> ma se c e devo controllare la coerenza
            // data conclusiva

            if (endDateDP.getValue() != null && dateIsVal == true) {
                if (endDateDP.getValue().isBefore(dateIns)) {
                    endDateLbl.setTextFill(Color.RED);
                    endDateIsVal = false;
                } else {
                    endDateIns = endDateDP.getValue();
                    endDateLbl.setTextFill(Color.BLACK);
                    endDateIsVal = true;
                }
            }
            else {
                endDateIsVal = true;
            }


            // Acquisisco se c e l ora di termine e controllo non sia prima dell ora impostata nello stesso giorno
            // FACOLTATIVO -> ma se c e devo controllare la coerenza
            // ora conclusiva

            if (endTimeTP.getValue() != null && timeIsVal == true && endDateIsVal == true && dateIsVal == true) {
                if (endTimeTP.getValue().isBefore(timeIns) && endDateIns.isEqual(dateIns)) {
                    endTimeLbl.setTextFill(Color.RED);
                    endTimeIsVal = false;
                } else {
                    endTimeIns = endTimeTP.getValue();
                    endTimeLbl.setTextFill(Color.BLACK);
                    endTimeIsVal = true;
                }
            }
            else {
                endTimeIsVal = true;
            }


            // Acquisisco l eta controllando che l'utente la inserisca se ha scelto partita di calcio
            // OBBLIGATORIO -> se partita di calcio
            //fascia età
            // Acquisisco il genere se partita di calcio
            // OBBLIGATORIO -> se partita di calcio
            // genere
            if (categoryIns == SOCCER_NAME) {

                if (minAgeCB.getValue() != null && maxAgeCB.getValue() != null) {
                    minAge = minAgeCB.getValue();
                    maxAge = maxAgeCB.getValue();
                    ageGroup.setRange(minAge, maxAge);
                    ageRangeIns = ageGroup.getRange();
                    ageLbl.setTextFill(Color.BLACK);
                    ageIsVal = true;
                } else {
                    ageLbl.setTextFill(Color.RED);
                    ageIsVal = false;
                }


                genderIns = genderCB.getSelectionModel().getSelectedItem();
                if (genderCB.getSelectionModel().getSelectedItem() == null) {
                    genderLbl.setTextFill(Color.RED);
                    genderIsVal = false;
                } else {
                    genderLbl.setTextFill(Color.BLACK);
                    genderIsVal = true;
                }
            }

            // FACOLTATIVO -> ma se c e devo controllare la coerenza
            // durata
//            else if (endDateIns == null) {
//                durD = durBigCB.getValue();
//                durationIns = MyUtil.getDurationFormat(0, durD, 0, 0);
//            } else if (endTimeIns == null) {
//                durH = durBigCB.getValue();
//                durM = durLitCB.getValue();
//                durationIns = MyUtil.getDurationFormat(1, 0, durH, durM);
//            }


            // FACOLTATIVO
            // noteIns
            noteIns = noteTxtA.getText();


            switch (categoryIns) {
                case SOCCER_NAME: {
                    if (catIsVal && numIsVal && deadLineIsVal && placeIsVal && dateIsVal && timeIsVal && endDateIsVal && endTimeIsVal && indTeeIsVal && ageIsVal && genderIsVal) {

                        EventSoccerMatch match = new EventSoccerMatch(titleIns, numParIns, deadLineIns, placeIns, dateIns, timeIns, durationIns, indTeeIns, totTeeIns, endDateIns, endTimeIns, ageRangeIns, genderIns, noteIns);
                        socialNetwork.getCategories().get(0).addEvent(match);
                        thisStage.close();


                        System.out.println(match.getTitle().getValue());
                        System.out.println(match.getNumOfPartecipants().getValue());
                        System.out.println(match.getRegistrationDeadline().getValue());
                        System.out.println(match.getPlace().getValue());
                        System.out.println(match.getDate().getValue());
                        System.out.println(match.getTime().getValue());
                        System.out.println(match.getDuration().getValue());
                        System.out.println(match.getIndTee().getValue());
                        System.out.println(match.getTeeInclude().getValue());
                        System.out.println(match.getEndDate().getValue());
                        System.out.println(match.getEndTime().getValue());
                        System.out.println(match.getAgeRange().getValue());
                        System.out.println(match.getGender().getValue());
                        System.out.println(match.getNote().getValue());


                    }
                }
            }
        }
    }






}
