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

package joop.window_and_shape;

import joop.matcher.CorrectContent;
import joop.shape.Rect;
import joop.window.BaseWindow;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.Area;
import unit.area.AreaOf;
import unit.color.RGBA;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Rect}.
 * @since 0.8
 */
public final class WindowAndRectTest {
    /**
     * The path to an image with a black rect on a window.
     */
    private static final String BLACK_RECT_PATH =
        "window_and_rect/blackRectOnWhiteWindow.png";

    /**
     * {@link BaseWindow#show()} must show a black rect for {@link BaseWindow}
     * with a rect from {@link Rect#Rect(Area)} on it.
     */
    @Test
    public void plainConstructed() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 500;
        final var windowHeight = 600;
        final var x = 50;
        final var y = 200;
        final var width = 100;
        final var height = 140;
        MatcherAssert.assertThat(
            new Rect(x, y, width, height),
            new CorrectContent(
                WindowAndRectTest.BLACK_RECT_PATH,
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a black rect for {@link BaseWindow}
     * with a rect from {@link Rect#Rect(int, int, int, int)} )} on it.
     */
    @Test
    public void areaConstructed() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 500;
        final var windowHeight = 600;
        final var x = 50;
        final var y = 200;
        final var width = 100;
        final var height = 140;
        MatcherAssert.assertThat(
            new Rect(
                new AreaOf(x, y, width, height)
            ),
            new CorrectContent(
                WindowAndRectTest.BLACK_RECT_PATH,
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a rect with the given color
     * for a {@link BaseWindow} with a {@link Rect} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * coloredRectAndWhiteWindow.png is used as the expected image of the inner
     * area.
     */
    @Test
    public void colored() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 340;
        final var windowHeight = 270;
        final var x = 50;
        final var y = 10;
        final var width = 100;
        final var height = 140;
        final var red = 255;
        MatcherAssert.assertThat(
            new Rect(
                new AreaOf(x, y, width, height),
                new RGBA(red, 0, 0)
            ),
            new CorrectContent(
                "window_and_rect/coloredRectOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }
}
