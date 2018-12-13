package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import versione1.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SampleController{


    @FXML
    private ListView categoryListView;
    @FXML
    private ListView eventListView;
    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        System.out.println("Hai cliccato su "+categoryListView.getSelectionModel().getSelectedItems());
    }

    private SocialNetwork socialNetwork;
    private EventSoccerMatch eventSoccerMatch;
    private ObservableList<Category> categories;
    ObservableList<Event> events;


    //Metodi

    public void setSocialNetwork(SocialNetwork socialNetwork) { this.socialNetwork = socialNetwork; }

    public SocialNetwork getSocialNetwork() { return socialNetwork;}

    public EventSoccerMatch getEventSoccerMatch() { return eventSoccerMatch; }

    public void setEventSoccerMatch(EventSoccerMatch eventSoccerMatch) { this.eventSoccerMatch = eventSoccerMatch; }


    @FXML
    private void initialize() {

//  Funziona come pezzo di codice ma così è inutile
//        categoryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("Hai cliccato su "+categoryListView.getSelectionModel().getSelectedItems());
//            }
//        });


        System.out.println("Carico la View...");

        categories = FXCollections.observableArrayList(socialNetwork.getCategories());


        for (int i=0; i<categories.size();i++) { // popolo automaticamente la ListView con gli elementi dell'array caetgories di SocialNetwork
            categoryListView.getItems().add(categories.get(i).getName());


            events = FXCollections.observableArrayList( FXCollections.observableArrayList(categories.get(i).getEvents()));
            for (int j=0; j<events.size(); j++) {
                //eventListView.getItems().add(events.get(j).getTitle().getName());
            }
        }




    }



}
