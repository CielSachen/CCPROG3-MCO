package cielsachen.ccprog3.mco2.view.component;

import javax.swing.JOptionPane;

public final class Modal {
    public static int showConfirmation(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }

    public static void showError(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
