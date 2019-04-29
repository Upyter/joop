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
import joop.shape.Oval;
import joop.window.BaseWindow;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.SoftArea;
import unit.color.RGBA;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Oval}.
 * @since 0.9
 */
public final class WindowAndOvalTest {
    /**
     * {@link BaseWindow#show()} must show a black oval for {@link BaseWindow}
     * with a black {@link Oval} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * blackOvalAndWhiteWindow.png is used as the expected image of the inner
     * area.
     * @throws Exception Because of Thread.sleep and Robot.
     */
    @Test
    public void black() throws Exception {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 300;
        final var windowHeight = 200;
        final var x = 10;
        final var y = 20;
        final var width = 100;
        final var height = 100;
        MatcherAssert.assertThat(
            new Oval(x, y, width, height),
            new CorrectContent(
                "window_and_oval/blackOvalOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a colored oval for {@link BaseWindow}
     * with a colored {@link Oval} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * coloredOvalAndWhiteWindow.png is used as the expected image of the inner
     * area.
     * @throws Exception Because of Thread.sleep and Robot.
     */
    @Test
    public void colored() throws Exception {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 450;
        final var windowHeight = 300;
        final var x = 40;
        final var y = 30;
        final var width = 140;
        final var height = 150;
        final var blue = 255;
        MatcherAssert.assertThat(
            new Oval(
                new SoftArea(x, y, width, height),
                new RGBA(0, 0, blue)
            ),
            new CorrectContent(
                "window_and_oval/coloredOvalOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }
}
