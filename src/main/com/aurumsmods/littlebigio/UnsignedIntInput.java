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

import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provides a contract for reading unsigned 32-bit integers from a data source,
 * such as an {@link InputStream}.
 */
public interface UnsignedIntInput {
	/**
	 * Reads four input bytes and returns a {@code long} value in the range
	 * {@code 0} through {@code 2147483647}.
	 *
	 * This method is suitable for reading the bytes written by
	 * {@link DataOutput#writeInt} if the argument to {@code writeInt} was intended
	 * to be a value in the range {@code 0} through {@code 2147483647}.
	 *
	 * @return the unsigned 32-bit value read.
	 * @throws EOFException if this stream reaches the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 */
	public long readUnsignedInt() throws IOException;
}
