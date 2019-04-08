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

import io.vavr.collection.List;
import joop.shape.Shape;
import unit.area.Area;
import unit.size.AdjustableSize;

/**
 * A grid of shapes.
 * <p>This class is mutable and thread-safe due to adjustments.</p>
 * @since 0.62
 */
public class Grid extends Column {
    /**
     * Ctor.
     * @param size The size of the grid.
     * @param shapes The shapes for the grid. The array represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(final AdjustableSize size, final List<Shape>... shapes) {
        this(size, List.of(shapes));
    }

    /**
     * Ctor.
     * @param size The size of the grid.
     * @param shapes The shapes for the grid. The outer list represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(final AdjustableSize size, final List<List<Shape>> shapes) {
        super(size, shapes.map(Row::new));
    }

    /**
     * Ctor.
     * @param shapes The shapes for the grid. The array represents the
     *  columns and the inner the rows of the grid.
     */
    @SafeVarargs
    public Grid(final List<Shape>... shapes) {
        this(List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes for the grid. The outer list represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(final List<List<Shape>> shapes) {
        super(shapes.map(Row::new));
    }

    /**
     * Ctor.
     * @param area The area of the grid.
     * @param shapes The shapes for the grid. The outer list represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(
        final int rows,
        final Area area,
        final Shape... shapes
    ) {
        this(
            rows,
            area,
            List.of(shapes)
        );
    }

    /**
     * Ctor.
     * @param area The area of the grid.
     * @param shapes The shapes for the grid. The outer list represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(
        final int rows,
        final Area area,
        final List<Shape> shapes
    ) {
        this(
            area,
            shapes.grouped(shapes.size() / rows).toList()
        );
    }

    /**
     * Ctor.
     * @param area The area of the grid.
     * @param shapes The shapes for the grid. The array represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(final Area area, final List<Shape>... shapes) {
        this(area, List.of(shapes));
    }

    /**
     * Ctor.
     * @param area The area of the grid.
     * @param shapes The shapes for the grid. The outer list represents the
     *  columns and the inner the rows of the grid.
     */
    public Grid(final Area area, final List<List<Shape>> shapes) {
        super(area, shapes.map(Row::new));
    }
}
