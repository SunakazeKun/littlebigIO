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

import java.nio.ByteOrder;

/**
 * An interface for byte order-dependent components, such as IO streams. The
 * {@link ByteOrder} value can be retrieved or set to another value. Every class
 * should consist of a single {@link ByteOrder} attribute which can be accessed
 * by {@link #getOrder()} and {@link #setOrder(ByteOrder)}.
 * <p>
 * It is not allowed that the attribute be {@code null}. The developer has to
 * ensure that {@link #getOrder()} never returns {@code null} as well as
 * {@link #setOrder(ByteOrder)} never sets the attribute to {@code null}.
 * <p>
 * The contract does not prescribe how to work with multithreaded applications.
 */
public interface ByteOrdered {
	/**
	 * Returns the {@link ByteOrder}. This should always return a proper instance,
	 * {@code null} is not allowed.
	 *
	 * @return the {@link ByteOrder}.
	 */
	public ByteOrder getOrder();

	/**
	 * Sets the {@link ByteOrder}. The parameter cannot be {@code null}. In that
	 * case, a {@code NullPointerException} is thrown.
	 *
	 * @param byteOrder the {@link ByteOrder}.
	 * @throws NullPointerException if {@code byteOrder} is {@code null}.
	 */
	public void setOrder(ByteOrder byteOrder);

	/**
	 * Returns {@code true} if the byte order is {@link ByteOrder#BIG_ENDIAN}.
	 * Otherwise, returns {@code false}.
	 *
	 * @return {@code true} if byte order is {@link ByteOrder#BIG_ENDIAN}, otherwise
	 *         {@code false}.
	 */
	public default boolean isBigEndian() {
		return getOrder() == ByteOrder.BIG_ENDIAN;
	}

	/**
	 * Returns {@code true} if the byte order is {@link ByteOrder#LITTLE_ENDIAN}.
	 * Otherwise, returns {@code false}.
	 *
	 * @return {@code true} if byte order is {@link ByteOrder#LITTLE_ENDIAN},
	 *         otherwise {@code false}.
	 */
	public default boolean isLittleEndian() {
		return getOrder() == ByteOrder.LITTLE_ENDIAN;
	}

	/**
	 * Returns {@code true} if the byte order equals the machine's native order.
	 * Otherwise, returns {@code false}.
	 *
	 * @return {@code true} if byte order equals the machine's native order.
	 * @see ByteOrder#nativeOrder()
	 */
	public default boolean isNativeOrder() {
		return getOrder() == ByteOrder.nativeOrder();
	}

	/**
	 * Swaps the {@link ByteOrder}. If it's currently set to
	 * {@link ByteOrder#BIG_ENDIAN}, it will be changed to
	 * {@link ByteOrder#LITTLE_ENDIAN}. However, if it's currently set to
	 * {@link ByteOrder#LITTLE_ENDIAN}, it will be changed to
	 * {@link ByteOrder#BIG_ENDIAN}.
	 */
	public default void swapOrder() {
		if (getOrder() == ByteOrder.LITTLE_ENDIAN) {
			setOrder(ByteOrder.BIG_ENDIAN);
		} else {
			setOrder(ByteOrder.LITTLE_ENDIAN);
		}
	}
}
