package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import versione1.SocialNetwork;
import versione1.User;
import versione2.notifications.Notification;
import versione2.notifications.NotificationType;


public class NotificationController {

    private SocialNetwork socialNetwork;
    private User sessionUser;
    private Notification notification;
    private int notificationIndex;
    private Stage thisStage;
    private ListView notificationListView;

    public void setSocialNetwork(SocialNetwork socialNetwork) { this.socialNetwork = socialNetwork; }

    public void setThisStage(Stage thisStage) { this.thisStage = thisStage; }

    public void setNotification(Notification notification) { this.notification = notification; }

    public void setSessionUser(User sessionUser) { this.sessionUser = sessionUser; }

    public void setNotificationIndex(int notificationIndex) { this.notificationIndex = notificationIndex; }



    @FXML
    private Label messageLbl;
    @FXML
    private Button removeBtn;

    @FXML
    private void initialize(){
        messageLbl.setText(notification.getNotificationMessage());

        if (notification.getNotificationType().equals(NotificationType.Alert)) {
        }
        else if(notification.getNotificationType().equals(NotificationType.Reminder)){
        }

    }

    public void removeNotification(){
        sessionUser.removeNotification(notificationIndex);
        // Queste due righe mi servono perchè altrimenti rimangono notifiche eliminate all'interno del listView
        notificationListView.refresh();
        notificationListView.getSelectionModel().clearSelection(); // elimino la selezione precedente che in caso rimarrebbe salvata
        //

        socialNetwork.updateUsersListFile();

        thisStage.close();
    }

    public void setNotificationListView(ListView notificationListView) {
        this.notificationListView = notificationListView;
    }
}