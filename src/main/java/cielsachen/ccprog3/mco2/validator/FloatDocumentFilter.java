package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FloatDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (isFloat(str)) {
            super.insertString(fb, offset, str, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isFloat(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isFloat(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }

        try {
            Float.parseFloat(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
