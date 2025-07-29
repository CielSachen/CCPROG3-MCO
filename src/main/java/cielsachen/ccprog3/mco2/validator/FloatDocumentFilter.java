package cielsachen.ccprog3.mco2.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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

    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr)
            throws BadLocationException {
        String currStr = fb.getDocument().getText(0, fb.getDocument().getLength());

        if (isFloat(currStr.substring(0, offset) + str + currStr.substring(offset))) {
            super.insertString(fb, offset, str, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int len, String text, AttributeSet attrs)
            throws BadLocationException {
        String currText = fb.getDocument().getText(0, fb.getDocument().getLength());

        if (isFloat(currText.substring(0, offset) + text + currText.substring(offset))) {
            super.replace(fb, offset, len, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        String currText = fb.getDocument().getText(0, fb.getDocument().getLength());

        if (isFloat(currText.substring(0, offset) + currText.substring(offset))) {
            super.remove(fb, offset, length);
        }
    }
}
