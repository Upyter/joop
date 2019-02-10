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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;
import joop.event.Event;
import unit.Overlap;
import unit.functional.Action;

/**
 * A mouse button release bound on a component to apply some action on
 * activation.
 * <p>This class is immutable and thread-safe.</p>
 * @since 0.35
 */
public class Release implements Event {
    /**
     * The target action to be applied with the x and y coordinates of the
     * press.
     */
    private final BiConsumer<Integer, Integer> target;

    /**
     * Ctor.
     * @param action The action to be applied when the release occurs.
     */
    public Release(final Action action) {
        this(
            // @checkstyle ParameterName (1 line)
            (x, y) -> action.run()
        );
    }

    /**
     * Ctor.
     * @param target The target with the action, who gets the x and y
     *  coordinates of the release.
     */
    public Release(final BiConsumer<Integer, Integer> target) {
        this.target = target;
    }

    @Override
    public final void registerFor(final Mouse source, final Overlap overlap) {
        source.register(
            (MouseListener) new MouseAdapter() {
                @Override
                public void mouseReleased(final MouseEvent event) {
                    // @checkstyle LocalFinalVariableName (2 lines)
                    final int x = event.getX();
                    final int y = event.getY();
                    if (overlap.contains(x, y)) {
                        Release.this.target.accept(x, y);
                    }
                }
            }
        );
    }
}
