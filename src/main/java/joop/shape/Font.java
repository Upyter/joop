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

package joop.shape;

import java.util.function.Supplier;

/**
 * A font.
 * @since 0.64
 */
public class Font {
    public enum Style {
        PLAIN,
        BOLD,
        ITALIC,
        BOLD_ITALIC
    }

    /**
     * The name of the font.
     */
    private final Supplier<String> name;

    /**
     * The style of the string.
     */
    private final Supplier<Style> style;

    /**
     * The size of the string (0+).
     */
    private final Supplier<Integer> size;

    /**
     * Ctor. {@link Style#PLAIN}, Arial and a size of 18 will be used.
     */
    public Font() {
        this("Arial", Style.PLAIN, 18);
    }

    /**
     * Ctor. {@link Style#PLAIN} and Arial will be used.
     * @param size The size of the string (0+)
     */
    public Font(final int size) {
        this("Arial", Style.PLAIN, size);
    }

    /**
     * Ctor. {@link Style#PLAIN} will be used.
     * @param name The name of the font.
     * @param size The size of the string (0+)
     */
    public Font(final String name, final int size) {
        this(name, Style.PLAIN, size);
    }

    /**
     * Ctor.
     * @param name The name of the font.
     * @param style The style of the font.
     * @param size The size of the string (0+)
     */
    public Font(final String name, final Style style, final int size) {
        this(() -> name, () -> style, () -> size);
    }

    /**
     * Ctor.
     * @param name The name of the font.
     * @param style The style of the font.
     * @param size The size of the string (0+)
     */
    public Font(
        final Supplier<String> name,
        final Supplier<Style> style,
        final Supplier<Integer> size
    ) {
        this.name = name;
        this.style = style;
        this.size = size;
    }

    public final String name() {
        return this.name.get();
    }

    public final Style style() {
        return this.style.get();
    }

    public final int size() {
        return this.size.get();
    }
}
