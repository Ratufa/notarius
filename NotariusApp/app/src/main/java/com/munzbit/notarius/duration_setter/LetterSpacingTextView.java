package com.munzbit.notarius.duration_setter;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;


public class LetterSpacingTextView extends RobotoTextView {

    private float letterSpacing = 0.0F;

    private CharSequence originalText = "";


    public LetterSpacingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(float mLetterSpacing) {
        this.letterSpacing = mLetterSpacing;
        requestLayout();
        invalidate();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        // Below if block taken from android source code, this ensures we dont have NPE in future.
        // However note that this was not causing FLT-1507
        if (text == null) {
            text = "";
        }
        originalText = text;
        applyLetterSpacing();
    }

    @Override
    public CharSequence getText() {
        return originalText;
    }

    private void applyLetterSpacing() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            builder.append(originalText.charAt(i));
            if (i + 1 < originalText.length()) {
                builder.append('\u00A0');
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                finalText.setSpan(new ScaleXSpan((letterSpacing + 1.0F) / 10.0F), i, i + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, BufferType.SPANNABLE);
    }
}
