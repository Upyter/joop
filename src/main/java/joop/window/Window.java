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

import java.awt.Dimension;
import javax.swing.JFrame;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.size.AdjustableSize;
import unit.tuple.adjustment.NoAdjustment;
import unit.tuple.adjustment.TupleAdjustment;

/**
 * A class to create a window with the most used features. Use
 * {@link BaseWindow} to choose your own features.
 * <p>This class is mutable and not thread-safe, because it mutates its state
 * when {@link #show} is called.</p>
 * @since 0.21
 */
public class Window extends BaseWindow {
    /**
     * Ctor. The window will be centered and the size of the window will be
     * equal to the first size of the given shape. This means, if the size
     * changes, it doesn't affect the window. Only the first state of the shape
     * will be used.
     * @param shape The shape to put on the window.
     */
    public Window(final Shape shape) {
        super(
            new AreaOf(),
            (JFrame frame) -> {
                Area.applyOn(
                    shape.adjustment(
                        new Adjustment() {
                            @Override
                            public TupleAdjustment<Integer, Integer> posAdjustment() {
                                return new NoAdjustment<>();
                            }

                            @Override
                            public TupleAdjustment<Integer, Integer> sizeAdjustment() {
                                return new NoAdjustment<>();
                            }
                        }
                    ),
                    (x, y, width, height) -> {
                        frame.getContentPane().setPreferredSize(
                            new Dimension(width, height)
                        );
                        frame.pack();
                    }
                );
                frame.setLocationRelativeTo(null);
            },
            shape
        );
    }

    /**
     * Ctor. The window will be centered.
     * @param size The size of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final AdjustableSize size, final Shape shape) {
        super(
            new AreaOf(size),
            (JFrame frame) -> frame.setLocationRelativeTo(null),
            shape
        );
    }

    /**
     * Ctor.
     * @param area The area of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final Area area, final Shape shape) {
        super(area, shape);
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param area The area of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final String title, final Area area, final Shape shape) {
        super(area, (JFrame frame) -> frame.setTitle(title), shape);
    }
}
