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
import joop.shape.Line;
import joop.window.BaseWindow;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.color.RGBA;
import unit.pos.PosOf;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Line}.
 * @since 0.12
 */
public class WindowAndLineTest {
    /**
     * {@link BaseWindow#show()} must show a black line for {@link BaseWindow}
     * with a black {@link Line} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * blackLineAndWhiteWindow.png is used as the expected image of the inner
     * area.
     */
    @Test
    public void black() {
        // @checkstyle LocalFinalVariableName (6 lines)
        final var windowWidth = 370;
        final var windowHeight = 140;
        final var fx = 100;
        final var fy = 120;
        final var sx = 200;
        final var sy = 130;
        MatcherAssert.assertThat(
            new Line(
                new PosOf(fx, fy),
                new PosOf(sx, sy)
            ),
            new CorrectContent(
                "window_and_line/blackLineOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a colored line for {@link BaseWindow}
     * with a colored {@link Line} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * coloredLineAndWhiteWindow.png is used as the expected image of the inner
     * area.
     */
    @Test
    public void colored() {
        // @checkstyle LocalFinalVariableName (6 lines)
        final var windowWidth = 450;
        final var windowHeight = 460;
        final var fx = 35;
        final var fy = 100;
        final var sx = 250;
        final var sy = 400;
        final var green = 255;
        MatcherAssert.assertThat(
            new Line(
                new PosOf(fx, fy),
                new PosOf(sx, sy),
                new RGBA(0, green, 0)
            ),
            new CorrectContent(
                "window_and_line/coloredLineOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }
}
