package main.model.event.states;

import main.model.event.*;
import main.model.notifications.*;

import java.time.LocalDate;

public class Created extends State{

    public Created(){
        super.setEvolution("Giorno creazione: " + LocalDate.now());
        super.setStateValue(StateValue.Creata);
    }
    @Override
    public Notification changeState(Event event) {
        event.setState(new Opened(super.getEvolution()));
        return buildNotificationNewEvent( event );
    }

    public Notification buildNotificationNewEvent(Event event){
        return NotificationsBuilder.buildNotificationNewEvent ((String) event.getTitle().getValue());
    }
}
