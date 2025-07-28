package cielsachen.ccprog3.mco2.view.component;

import javax.swing.JOptionPane;

public final class Modal {
    public static int showConfirmation(String msg, String title) {
        return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
    }

    public static void showError(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
