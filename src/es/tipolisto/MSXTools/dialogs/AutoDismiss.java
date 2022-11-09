package es.tipolisto.MSXTools.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoDismiss implements ActionListener
{
    private JDialog dialog;

    private AutoDismiss(JDialog dialog)
    {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        dialog.dispose();
    }

    public static void showMessageDialog(Component parentComponent, String message, String title,int delayInMilliseconds)
    {
        final JOptionPane optionPane = new JOptionPane(message);
        final JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setTitle(title);
        Timer timer = new Timer(delayInMilliseconds, new AutoDismiss(dialog));
        timer.setRepeats(false);
        timer.start();
        if (dialog.isDisplayable())
        {
            dialog.setVisible(true);
        }
    }
}