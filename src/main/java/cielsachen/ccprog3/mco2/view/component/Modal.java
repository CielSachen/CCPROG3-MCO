package cielsachen.ccprog3.mco2.view.component;

import java.awt.Component;
import java.util.List;

import javax.swing.JOptionPane;

/** Represents a smaller pop-up window. */
public final class Modal extends JOptionPane {
    private Modal() {
    }

    /**
     * Shows a confirmation dialog modal with yes or no buttons.
     *
     * @param parentComponent The parent component of the modal.
     * @param msg             The message displayed in the modal.
     * @param title           The title of the modal.
     * @return Whether the user clicked yes or no.
     */
    public static int showConfirmDialog(Component parentComponent, String msg, String title) {
        return JOptionPane.showConfirmDialog(parentComponent, msg, title, JOptionPane.YES_NO_OPTION);
    }

    /**
     * Shows a dialog modal containing an error message.
     *
     * @param parentComponent The parent component of the modal.
     * @param msg             The error message displayed in the modal.
     * @param title           The title of the modal.
     */
    public static void showErrorDialog(Component parentComponent, String msg, String title) {
        JOptionPane.showMessageDialog(parentComponent, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a dialog modal that allows the user to input text in a field.
     *
     * @param parentComponent The parent component of the modal.
     * @param msg             The message displayed in the modal.
     * @param title           The title of the modal.
     * @return The user’s submitted text input.
     */
    public static String showInputDialog(Component parentComponent, String msg, String title) {
        return JOptionPane.showInputDialog(parentComponent, msg, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Shows a dialog modal that allows the user to select from a selection.
     *
     * @param <T>             The object type of the choices.
     * @param parentComponent The parent component of the modal.
     * @param msg             The message displayed in the modal.
     * @param title           The title of the modal.
     * @param choices         The choices to select from.
     * @param defaultChoice   The default selected choice.
     * @return The user’s submitted selected choice.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T showSelectionDialog(Component parentComponent, String msg, String title,
            List<T> choices, T defaultChoice) {
        return (T) JOptionPane.showInputDialog(parentComponent, msg, title, JOptionPane.PLAIN_MESSAGE, null,
                choices.toArray(), defaultChoice);
    }
}
