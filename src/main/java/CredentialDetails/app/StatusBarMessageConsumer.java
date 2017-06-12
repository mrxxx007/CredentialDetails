package CredentialDetails.app;

import CredentialDetails.forms.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer for all messages addressed to the status bar
 */
public class StatusBarMessageConsumer implements Runnable {
    private BlockingQueue<String> statusBarMessageQueue;
    private Timer timer;
    protected final Object fadeLock = new Object();

    public StatusBarMessageConsumer(BlockingQueue<String> statusBarMessageQueue) {
        this.statusBarMessageQueue = statusBarMessageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = statusBarMessageQueue.take();

                MainForm mainForm = Application.getInstance().getMainForm();
                JLabel label = mainForm.getStatusBarLabel();
                label.setText(message);

                // show message for 1 sec
                Thread.sleep(1000);

                fadeOut(mainForm.getStatusBarLabel());
                synchronized (fadeLock) {
                    fadeLock.wait(5000);
                    timer.stop();
                }

                // restore color and remove text from label
                Color color = label.getForeground();
                label.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                label.setText("");
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void fadeOut(JComponent component) {
        final Color color = component.getForeground();
        timer = new Timer(80, new ActionListener() {
            final int increment = -20;
            int alpha = 255;


            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += increment;

                if (alpha <= 0) {
                    alpha = 0;
                    timer.stop();
                    synchronized (fadeLock) {
                        fadeLock.notify();
                    }
                }

                component.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            }
        });
        timer.start();
    }
}
