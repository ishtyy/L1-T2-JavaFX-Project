package client;

import javafx.application.Platform;

public class NotificationThread implements Runnable {
    private ClubHomeWindowController clubHomeWindowController;
    Thread thread;

    public NotificationThread(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
//                System.out.println("inside thread");
                Platform.runLater(() -> clubHomeWindowController.loadNotification());
                Thread.sleep(2 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
//        System.out.println("thread is closing");
    }

    public Thread getThread() {
        return thread;
    }
}
