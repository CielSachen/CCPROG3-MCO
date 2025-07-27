package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FloatDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (isFloat(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isFloat(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isFloat(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }

        try {
            Float.parseFloat(string);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
