package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/** Represents the filter used for preventing non-floats from being inputted in a text field. */
public class FloatDocumentFilter extends DocumentFilter {
    private boolean isFloat(String str) {
        if (str.isEmpty()) {
            return true;
        }

        try {
            Float.parseFloat(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** @inheritDoc */
    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attrs)
            throws BadLocationException {
        String currStr = fb.getDocument().getText(0, fb.getDocument().getLength());

        if (isFloat(currStr.substring(0, offset) + str + currStr.substring(offset))) {
            super.insertString(fb, offset, str, attrs);
        }
    }

    /** @inheritDoc */
    @Override
    public void replace(FilterBypass fb, int offset, int len, String text, AttributeSet attrs)
            throws BadLocationException {
        String currText = fb.getDocument().getText(0, fb.getDocument().getLength());

        if (isFloat(currText.substring(0, offset) + text + currText.substring(offset + len))) {
            super.replace(fb, offset, len, text, attrs);
        }
    }
}
