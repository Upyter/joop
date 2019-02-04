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

package joop.window;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import unit.area.Area;
import unit.functional.Cached;
import unit.functional.Lazy;
import unit.tuple.Tuple;

/**
 * Represents a simple window. To apply some settings on this window,
 * one has to use a constructor that takes a feature. This feature gets
 * the JFrame of this window to apply the needed settings or to get information
 * from it. By using this, one can bind the feature on a certain event like a
 * click on a button. <b>Don't capture the JFrame to use it elsewhere!</b>
 * <p>This class is mutable and not thread-safe, because it mutates its state
 * when {@link #show} is called.</p>
 * @since 0.3
 */
public class BaseWindow implements Showable {
    /**
     * A value containing the actual frame. This is necessary, because the
     * JFrame will probably be lazily constructed.
     */
    private final Lazy<JFrame> frame;

    /**
     * Ctor.
     * @param area The area of this window.
     */
    public BaseWindow(final Area area) {
        this(
            new Cached<>(
                () -> {
                    final var result = new JFrame();
                    final var panel = new JPanel();
                    Tuple.applyOn(
                        area,
                        (pos, size) -> Tuple.applyOn(
                            size,
                            (width, height) -> panel.setPreferredSize(
                                new Dimension(width, height)
                            )
                        )
                    );
                    panel.setBackground(Color.WHITE);
                    result.add(panel);
                    result.pack();
                    result.setVisible(true);
                    Area.applyOn(
                        area,
                        // @checkstyle ParameterName (1 line)
                        (x, y, width, height) -> result.setLocation(
                            x - result.getInsets().left,
                            y - result.getInsets().top
                        )
                    );
                    return result;
                }
            )
        );
    }

    /**
     * Ctor.
     * @param frame The construction of the frame.
     */
    private BaseWindow(final Lazy<JFrame> frame) {
        this.frame = frame;
    }

    @Override
    public final void show() {
        this.frame.value();
    }
}
