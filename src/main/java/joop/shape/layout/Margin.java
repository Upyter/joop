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

import java.util.function.Supplier;

/**
 * The space between the elements.
 * <p>Whether this class is mutable depends on the constructor arguments.</p>
 * @since 0.66
 */
public class Margin {
    private final Supplier<Integer> left;
    private final Supplier<Integer> right;
    private final Supplier<Integer> top;
    private final Supplier<Integer> bottom;

    public Margin(
        final int left,
        final int right,
        final int top,
        final int bottom
    ) {
        this(
            () -> left,
            () -> right,
            () -> top,
            () -> bottom
        );
    }

    public Margin(
        final Supplier<Integer> left,
        final Supplier<Integer> right,
        final Supplier<Integer> top,
        final Supplier<Integer> bottom
    ) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public final int top() {
        return this.top.get();
    }

    public final int right() {
        return this.right.get();
    }

    public final int left() {
        return this.left.get();
    }

    public final int bottom() {
        return this.bottom.get();
    }
}
