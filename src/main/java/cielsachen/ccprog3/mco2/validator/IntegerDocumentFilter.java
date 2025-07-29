package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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

    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (isInteger(str)) {
            super.insertString(fb, offset, str, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int len, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isInteger(text)) {
            super.replace(fb, offset, len, text, attrs);
        }
    }
}
