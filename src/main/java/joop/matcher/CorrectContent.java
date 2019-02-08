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

package joop.matcher;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import joop.shape.Shape;
import joop.window.BaseWindow;
import net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers;
import net.avh4.util.reflection.StackUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import unit.area.AreaOf;
import unit.functional.Cached;
import unit.functional.Lazy;

/**
 * A matcher to check whether a window has the expected content.
 * <p>This class is immutable and thread-safe (assuming that
 * the parent class {@link TypeSafeDiagnosingMatcher} is also immutable). </p>
 * @since 0.14
 */
public class CorrectContent extends TypeSafeDiagnosingMatcher<Shape> {
    /**
     * The image comparison matcher to check for the content of the window.
     */
    private final Lazy<Matcher<?>> matcher;

    /**
     * The recorded image of the window content.
     */
    private final Lazy<BufferedImage> image;

    /**
     * The width of the window to create.
     */
    private final int width;

    /**
     * The height of the window to create.
     */
    private final int height;

    /**
     * Ctor.
     * @param path The path to the image that contains the expected look of
     *  window content.
     * @param width The width of the window to create.
     * @param height The height of the window to create.
     */
    public CorrectContent(
        final String path, final int width, final int height
    ) {
        super();
        this.matcher = new Cached<>(
            () -> {
                final Class<?> clazz;
                try {
                    clazz = Class.forName(
                        StackUtils
                            .getCallingTestMethodStackTraceElement()
                            .getClassName()
                    );
                } catch (final ClassNotFoundException exception) {
                    throw new IllegalStateException(
                        "Couldn't get the class object of the caller.",
                        exception
                    );
                }
                try {
                    return ImageComparisonMatchers.looksLike(path, clazz);
                } catch (final IOException exception) {
                    throw new IllegalStateException(
                        "The path to the image seems to be wrong.", exception
                    );
                }
            }
        );
        this.width = width;
        this.height = height;
        this.image = new Cached<>(
            () -> {
                try {
                    return new Robot().createScreenCapture(
                        new Rectangle(0, 0, this.width, this.height)
                    );
                } catch (final AWTException exception) {
                    throw new IllegalStateException(
                        "Robot screen capture failed", exception
                    );
                }
            }
        );
    }

    @Override
    public final void describeTo(final Description description) {
        this.matcher.value().describeTo(description);
    }

    @Override
    protected final boolean matchesSafely(
        final Shape shape, final Description description
    ) {
        new BaseWindow(
            new AreaOf(this.width, this.height),
            shape
        ).show();
        try {
            final var sleep = 250L;
            Thread.sleep(sleep);
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(exception);
        }
        final boolean matches = this.matcher.value().matches(
            this.image.value()
        );
        if (!matches) {
            this.matcher.value().describeMismatch(
                this.image.value(), description
            );
        }
        return matches;
    }
}
