package cielsachen.ccprog3.mco2.view.component;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Modal extends JOptionPane {
    public static int showConfirmDialog(JFrame parentFrame, String msg, String title) {
        return JOptionPane.showConfirmDialog(parentFrame, msg, title, JOptionPane.YES_NO_OPTION);
    }

    public static void showErrorDialog(JFrame parentFrame, String msg, String title) {
        JOptionPane.showMessageDialog(parentFrame, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Object> T showSelectionDialog(JFrame parentFrame, String msg, String title,
            List<T> choices, T defaultChoice) {
        return (T) JOptionPane.showInputDialog(
                parentFrame,
                msg,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices.toArray(),
                defaultChoice);
    }
}
