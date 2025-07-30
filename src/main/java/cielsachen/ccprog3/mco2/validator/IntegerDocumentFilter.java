package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/** Represents the filter used for preventing non-integers from being inputted in a text field. */
public class IntegerDocumentFilter extends DocumentFilter {
    private boolean isInteger(String str) {
        if (str.isEmpty()) {
            return true;
        }

        try {
            Integer.parseInt(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** @inheritDoc */
    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attrs)
            throws BadLocationException {
        if (isInteger(str)) {
            super.insertString(fb, offset, str, attrs);
        }
    }

    /** @inheritDoc */
    @Override
    public void replace(FilterBypass fb, int offset, int len, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isInteger(text)) {
            super.replace(fb, offset, len, text, attrs);
        }
    }
}
