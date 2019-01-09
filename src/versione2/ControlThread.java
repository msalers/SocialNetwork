package versione2;
import versione1.Category;
import versione1.Event;
import versione1.SocialNetwork;
import versione1.User;

import java.util.ArrayList;
import java.util.List;

public class ControlThread extends Thread {

    int i;
    SocialNetwork socialNetwork;


    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    @Override
    public void run() {

        while (true) {

        	if(socialNetwork != null) {
        		for (Category cat : socialNetwork.getCategories()) {
        		     for(Event event : (ArrayList<Event>) cat.getEvents()){
        		         if(event.controlState()){
        		             String notificationToSend = event.getNotificationToSend();
        		             List<String> destinationUser = event.getParticipants();
        		             User sendTo;

        		             for(int i=0; i<destinationUser.size(); i++){
        		                 sendTo = socialNetwork.findUserByName(destinationUser.get(i));
        		                 sendTo.addNotification(notificationToSend);
                             }

                         }
                     }

        		}
        	}

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }

    }
