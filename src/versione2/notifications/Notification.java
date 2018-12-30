package versione2.notifications;

public class Notification {

    private String message;
    private boolean read; // per distinguere notifiche gia' lette e non

    public Notification(String message) {
        this.message = message;
        read = false;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public void setRead () {
        this.read = true;
    }

    public String getMessage() {
        return message;
    }

    public boolean getRead () {
        return read;
    }
}
