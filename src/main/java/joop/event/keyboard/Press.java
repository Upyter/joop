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

package joop.event.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import joop.event.Event;
import joop.event.mouse.InputHardware;
import unit.Overlap;

/**
 * A key press on a keyboard.
 * <p>This class is immutable and thread-safe.</p>
 * @since 0.67
 */
public class Press implements Event {
    /**
     * The action to apply.
     */
    private final Consumer<Character> action;

    /**
     * Ctor.
     * @param action The action to apply.
     */
    public Press(final Consumer<Character> action) {
        this.action = action;
    }

    @Override
    public final void registerFor(
        final InputHardware source, final Overlap overlap
    ) {
        source.register(
            new KeyListener() {
                @Override
                public void keyTyped(final KeyEvent event) {

                }

                @Override
                public void keyPressed(final KeyEvent event) {
                    Press.this.action.accept(event.getKeyChar());
                }

                @Override
                public void keyReleased(final KeyEvent event) {

                }
            }
        );
    }
}
