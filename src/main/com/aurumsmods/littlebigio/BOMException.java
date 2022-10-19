/*
 * The MIT License
 *
 * Copyright 2022 AurumsMods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.aurumsmods.littlebigio;

import java.io.IOException;

/**
 * Thrown to indicate that an incorrect byte order mark, or BOM for short, has
 * been detected.
 */
public final class BOMException extends IOException {
	@java.io.Serial
	private static final long serialVersionUID = 4430862928916743471L;

	/**
	 * Constructs a {@code BOMException} with {@code null} as its error detail
	 * message.
	 */
	public BOMException() {
		super();
	}

	/**
	 * Constructs a {@code BOMException} with the specified detail message. The
	 * string {@code s} can be retrieved later by the
	 * {@link java.lang.Throwable#getMessage} method of class
	 * {@code java.lang.Throwable}.
	 *
	 * @param s the detail message.
	 */
	public BOMException(String s) {
		super(s);
	}
}
