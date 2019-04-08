/*
 * Copyright 2019 Upyter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package joop.shape.gui;

import unit.area.Area;
import unit.area.AreaOf;
import unit.functional.Action;
import unit.pos.SoftPos;
import unit.size.SoftSize;

/**
 * A labeled button.
 * <p>This class is mutable and not thread-safe because of the button
 * that is also mutable.</p>
 * @see Button
 * @see Labeled
 * @since 0.46
 */
public class TextButton extends Labeled {
    /**
     * Ctor. An area of x = 0, y = 0, width = 0 and height = 0 will be taken
     * (useful in combination with layouts).
     * @param text The text of the button.
     * @param action The action to be run when the button is released.
     */
    public TextButton(
        final int text, final Action action
    ) {
        super(
            text,
            new AreaOf(
                new SoftPos(),
                new SoftSize()
            ),
            (area1, color, ignored) -> new Button(area1, action)
        );
    }

    /**
     * Ctor. An area of x = 0, y = 0, width = 0 and height = 0 will be taken
     * (useful in combination with layouts).
     * @param text The text of the button.
     * @param action The action to be run when the button is released.
     */
    public TextButton(
        final String text, final Action action
    ) {
        super(
            text,
            new AreaOf(
                new SoftPos(),
                new SoftSize()
            ),
            (area1, color, ignored) -> new Button(area1, action)
        );
    }

    /**
     * Ctor.
     * @param text The text of the button.
     * @param area The area of the button
     * @param action The action to be run when the button is released.
     */
    public TextButton(
        final int text, final Area area, final Action action
    ) {
        super(
            text,
            area,
            (area1, color, ignored) -> new Button(area1, action)
        );
    }

    /**
     * Ctor.
     * @param text The text of the button.
     * @param area The area of the button
     * @param action The action to be run when the button is released.
     */
    public TextButton(
        final String text, final Area area, final Action action
    ) {
        super(
            text,
            area,
            (area1, color, ignored) -> new Button(area1, action)
        );
    }
}
