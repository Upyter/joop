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

import java.util.function.Consumer;
import javax.swing.JFrame;
import joop.shape.Shape;
import joop.window.feature.Centered;
import joop.window.feature.Features;
import joop.window.feature.FoundArea;
import joop.window.feature.NoFeature;
import joop.window.feature.Resizability;
import joop.window.feature.ShapeSized;
import unit.area.Area;
import unit.area.AreaOf;
import unit.pos.AdjustablePos;
import unit.size.AdjustableSize;
import unit.size.SoftSize;

/**
 * A class to create a window with the most used features. Use
 * {@link BaseWindow} to choose your own features.
 * <p>This class is mutable and not thread-safe, because it mutates its state
 * when {@link #show} is called.</p>
 * @since 0.21
 */
public class Window extends BaseWindow {
    /**
     * Ctor. The window will get the size of the shape.
     * @param pos The position of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final AdjustablePos pos,
        final Shape shape
    ) {
        this(
            "",
            pos,
            shape
        );
    }

    /**
     * Ctor.
     * @param pos The position of the window.
     * @param size The size of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final AdjustablePos pos,
        final AdjustableSize size,
        final Shape shape
    ) {
        this(
            "",
            new AreaOf(pos, size),
            shape
        );
    }

    /**
     * Ctor. The window will be centered.
     * @param size The size of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final AdjustableSize size, final Shape shape) {
        this(
            "",
            new AreaOf(size),
            shape,
            new Centered()
        );
    }

    /**
     * Ctor. The window will be centered.
     * @param title The title of the window.
     * @param size The size of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final String title, final AdjustableSize size, final Shape shape
    ) {
        this(
            title,
            new AreaOf(size),
            shape,
            new Centered()
        );
    }

    /**
     * Ctor. The window will get the size of the shape.
     * @param title The title of the window.
     * @param pos The position of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final String title,
        final AdjustablePos pos,
        final Shape shape
    ) {
        this(
            title,
            new AreaOf(pos, new SoftSize()),
            shape,
            new ShapeSized(shape)
        );
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param pos The position of the window.
     * @param size The size of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final String title,
        final AdjustablePos pos,
        final AdjustableSize size,
        final Shape shape
    ) {
        this(
            title,
            new AreaOf(pos, size),
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
     * Ctor. The window will be centered and the size of the window will be
     * equal to the first size of the given shape. This means, if the size
     * changes, it doesn't affect the window. Only the first state of the shape
     * will be used.
     * @param shape The shape to put on the window.
     */
    public Window(final Shape shape) {
        this(
            "",
            shape
        );
    }

    /**
     * Ctor. The window will be centered and the size of the window will be
     * equal to the first size of the given shape. This means, if the size
     * changes, it doesn't affect the window. Only the first state of the shape
     * will be used.
     * @param title The title of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final String title, final Shape shape) {
        this(
            title,
            new AreaOf(new SoftSize()),
            shape,
            new FoundArea(shape)
        );
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param area The area of the window.
     * @param shape The shape to put on the window.
     */
    public Window(final String title, final Area area, final Shape shape) {
        this(
            title,
            area,
            shape,
            NoFeature.cached()
        );
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param area The area of the window.
     * @param shape The shape to put on the window.
     */
    public Window(
        final String title,
        final Area area,
        final Shape shape,
        final Consumer<JFrame> feature
    ) {
        super(
            area,
            new Features(
                (JFrame frame) -> frame.setTitle(title),
                new Resizability(area),
                feature
            ),
            shape
        );
    }
}
