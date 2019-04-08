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

package joop.event.mouse;

import java.util.concurrent.atomic.AtomicBoolean;
import joop.event.Event;
import org.apache.commons.lang3.mutable.MutableBoolean;
import unit.Overlap;
import unit.functional.Action;

/**
 * A combination of a press and a release. Note that a release may only happen
 * after the press on the same shape.
 * <p>This class is mutable because it keeps track whether the press happened
 * and gives this information to the release through a mutable state. Since
 * this state is saved in an {@link AtomicBoolean} it's still thread-safe.</p>
 * @see Press
 * @see Release
 * @since 0.37
 */
public class PressRelease implements Event {
    /**
     * The mouse press.
     */
    private final Event press;

    /**
     * The mouse release.
     */
    private final Event release;

    /**
     * Ctor.
     * @param press The action that shall be applied on press.
     * @param release The action that shall be applied on release.
     */
    public PressRelease(final Action press, final Action release) {
        this(
            press,
            new MutableBoolean(false),
            release
        );
    }

    /**
     * Ctor.
     * @param press The action that shall be applied on press.
     * @param pressed The state needed to keep track whether the press happened
     *  or not. This is necessary because the release can only happen after the
     *  press has happened.
     * @param release The action that shall be applied on release.
     */
    private PressRelease(
        final Action press, final MutableBoolean pressed, final Action release
    ) {
        this(
            new Press(
                () -> {
                    press.run();
                    pressed.setValue(true);
                }
            ),
            new Release(
                () -> {
                    if (pressed.getValue()) {
                        release.run();
                        pressed.setValue(false);
                    }
                }
            )
        );
    }

    /**
     * Ctor.
     * @param press The mouse press.
     * @param release The mouse release.
     */
    private PressRelease(final Event press, final Event release) {
        this.press = press;
        this.release = release;
    }

    @Override
    public final void registerFor(
        final InputHardware source, final Overlap overlap
    ) {
        this.press.registerFor(source, overlap);
        this.release.registerFor(source, overlap);
    }
}
