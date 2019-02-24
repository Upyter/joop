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

package joop.shape.layout;

import java.awt.Graphics;
import java.util.Collection;
import java.util.List;
import joop.event.mouse.Mouse;
import joop.shape.Shape;
import unit.area.Area;
import unit.area.AreaOf;

/**
 * A layout that adjust its shapes to be in a column
 * (like {@link javafx.scene.layout.VBox}).
 * @since 0.29
 */
public class Column implements Shape {
    private static final int DEFAULT_MARGIN = 5;

    /**
     * The shapes to adjust.
     */
    private final Collection<Shape> shapes;

    /**
     * The sum of the heights of the shapes. It is used during the drawing to
     * add the heights of the previous shapes to the current. This field will
     * be reset to 0 after each drawing.
     */
    private int heights;

    private int current;

    private int max;

    private final int margin;

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Column(final Shape... shapes) {
        this(Column.DEFAULT_MARGIN, List.of(shapes));
    }

    /**
     * Ctor.
     * @param margin The space between the shapes. It will be applied around the
     *  shapes, meaning that they will get that margin at top, left, right and
     *  bottom.
     * @param shapes The shapes to adjust.
     */
    public Column(final int margin, final Shape... shapes) {
        this(margin, List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Column(final Collection<Shape> shapes) {
        this(Column.DEFAULT_MARGIN, shapes);
    }

    /**
     * Ctor.
     * @param margin The space between the shapes. It will be applied around the
     *  shapes, meaning that they will get that margin at top, left, right and
     *  bottom.
     * @param shapes The shapes to adjust.
     */
    public Column(final int margin, final Collection<Shape> shapes) {
        this.shapes = shapes;
        this.heights = 0;
        this.current = 0;
        this.max = 0;
        this.margin = margin;
    }

    @Override
    public final void draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        for (final Shape shape : this.shapes) {
            shape.draw(
                graphics,
                (AreaAdjustment) (area, target) -> {
                    Area.applyOn(
                        area,
                        // @checkstyle ParameterName (1 line)
                        (x, y, width, height) -> {
                            target.accept(
                                x + this.margin, this.heights + y + this.margin, width + this.margin, height + this.margin
                            );
                            this.current = height + this.margin * 2;
                            this.max = Math.max(this.max, width + this.margin * 3);
                        }
                    );
                }
            );
            this.heights += this.current;
            this.current = 0;
        }
        adjustment.adjustedApply(
            new AreaOf(0, 0, this.max, this.heights + this.margin),
            (x, y, w, h) -> {}
        );
        this.heights = 0;
        this.max = 0;
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shapes.forEach(shape -> shape.registerFor(mouse));
    }
}
