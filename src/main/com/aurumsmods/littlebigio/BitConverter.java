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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A utility class that provides static methods to convert various primitive
 * data types into byte representations and vice-versa. Aside from
 * {@link ByteBuffer}, Java's libraries usually specify and deal with big-endian
 * data only.
 */
public final class BitConverter {
	/** Private constructor to prevent instantiation. */
	private BitConverter() {
		throw new IllegalStateException();
	}

	/**
	 * Returns the {@code boolean} value converted from the {@code byte} at a
	 * specified position in a {@code byte} array. This method returns {@code true}
	 * if the {@code byte} is nonzero and {@code false} if it is zero.
	 *
	 * @param arr the byte array.
	 * @param off the position in the data array.
	 * @return the {@code boolean} value.
	 * @throws NullPointerException           if {@code arr} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || off >= arr.length}.
	 */
	public static boolean getBoolean(byte[] arr, int off) {
		return arr[off] != 0;
	}

	/**
	 * Returns the {@code byte} value (-128 to 127) at a specified position in a
	 * {@code byte} array.
	 *
	 * @param arr the byte array.
	 * @param off the position in the byte array.
	 * @return the {@code byte} value.
	 * @throws NullPointerException           if {@code arr} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || off >= arr.length}.
	 */
	public static byte getByte(byte[] arr, int off) {
		return arr[off];
	}

	/**
	 * Returns the unsigned {@code byte} value (0 to 255) at a specified position in
	 * a {@code byte} array.
	 *
	 * @param arr the byte array.
	 * @param off the position in the byte array.
	 * @return the {@code byte} value.
	 * @throws NullPointerException           if {@code arr} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || off >= arr.length}.
	 */
	public static int getUnsignedByte(byte[] arr, int off) {
		return arr[off] & 0xFF;
	}

	/**
	 * Returns the {@code short} value (-32768 to 32767) at a specified position in
	 * a {@code byte} array. The way the bytes are interpreted depends on the
	 * specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code short} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || off >= arr.length}.
	 */
	public static short getShort(byte[] arr, int off, ByteOrder endian) {
		Objects.requireNonNull(endian);
		int a = arr[off++] & 0xFF;
		int b = arr[off] & 0xFF;

		if (endian == ByteOrder.BIG_ENDIAN) {
			return (short) (a << 8 | b);
		} else {
			return (short) (b << 8 | a);
		}
	}

	/**
	 * Returns the unsigned {@code short} value (0 to 65535) at a specified position
	 * in a {@code byte} array. The way the bytes are interpreted depends on the
	 * specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code unsigned short} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 1) >= arr.length}.
	 */
	public static int getUnsignedShort(byte[] arr, int off, ByteOrder endian) {
		Objects.requireNonNull(endian);
		int a = arr[off++] & 0xFF;
		int b = arr[off] & 0xFF;

		if (endian == ByteOrder.BIG_ENDIAN) {
			return a << 8 | b;
		} else {
			return b << 8 | a;
		}
	}

	/**
	 * Returns the {@code char} value (0 to 65535) at a specified position in a
	 * {@code byte} array. The way the bytes are interpreted depends on the
	 * specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code char} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 1) >= arr.length}.
	 */
	public static char getChar(byte[] arr, int off, ByteOrder endian) {
		return (char) getShort(arr, off, endian);
	}

	/**
	 * Returns the {@code int} value (-2147483648 to 2147483647) at a specified
	 * position in a {@code byte} array. The way the bytes are interpreted depends
	 * on the specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code int} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 3) >= arr.length}.
	 */
	public static int getInt(byte[] arr, int off, ByteOrder endian) {
		Objects.requireNonNull(endian);
		int a = arr[off++] & 0xFF;
		int b = arr[off++] & 0xFF;
		int c = arr[off++] & 0xFF;
		int d = arr[off] & 0xFF;

		if (endian == ByteOrder.BIG_ENDIAN) {
			return a << 24 | b << 16 | c << 8 | d;
		} else {
			return d << 24 | c << 16 | b << 8 | a;
		}
	}

	/**
	 * Returns the unsigned {@code int} value (0 to 4294967295) at a specified
	 * position in a {@code byte} array. The way the bytes are interpreted depends
	 * on the specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the unsigned {@code int} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 3) >= arr.length}.
	 */
	public static long getUnsignedInt(byte[] arr, int off, ByteOrder endian) {
		return getInt(arr, off, endian) & 0xFFFFFFFFL;
	}

	/**
	 * Returns the {@code long} value (2^63 to 2^63-1) at a specified position in a
	 * {@code byte} array. The way the bytes are interpreted depends on the
	 * specified {@link ByteOrder}.
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code long} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 7) >= arr.length}.
	 */
	public static long getLong(byte[] arr, int off, ByteOrder endian) {
		Objects.requireNonNull(endian);
		long a = arr[off++] & 0xFF;
		long b = arr[off++] & 0xFF;
		long c = arr[off++] & 0xFF;
		long d = arr[off++] & 0xFF;
		long e = arr[off++] & 0xFF;
		long f = arr[off++] & 0xFF;
		long g = arr[off++] & 0xFF;
		long h = arr[off] & 0xFF;

		if (endian == ByteOrder.BIG_ENDIAN) {
			return a << 56 | b << 48 | c << 40 | d << 32 | e << 24 | f << 16 | g << 8 | h;
		} else {
			return h << 56 | g << 48 | f << 40 | e << 32 | d << 24 | c << 16 | b << 8 | a;
		}
	}

	/**
	 * Returns the {@code float} value at a specified position in a {@code byte}
	 * array. The way the bytes are interpreted depends on the specified
	 * {@link ByteOrder}. Essentially, this performs the same action as calling the
	 * following:
	 *
	 * <pre>
	 * Float.intBitsToFloat(getInt(arr, off, endian))
	 * </pre>
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code float} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 3) >= arr.length}.
	 * @see BitConverter#getInt(byte[], int, ByteOrder)
	 * @see Float#intBitsToFloat(int)
	 */
	public static float getFloat(byte[] arr, int off, ByteOrder endian) {
		return Float.intBitsToFloat(getInt(arr, off, endian));
	}

	/**
	 * Returns the {@code double} value at a specified position in a {@code byte}
	 * array. The way the bytes are interpreted depends on the specified
	 * {@link ByteOrder}. Essentially, this performs the same action as calling the
	 * following:
	 *
	 * <pre>
	 * Double.longBitsToDouble(getLong(arr, off, endian))
	 * </pre>
	 *
	 * @param arr    the byte array.
	 * @param off    the position in the byte array.
	 * @param endian the byte order that specifies how the data will be interpreted.
	 * @return the {@code double} value.
	 * @throws NullPointerException           if {@code arr} or {@code endian} is
	 *                                        {@code null}.
	 * @throws ArrayIndexOutOfBoundsException {@code off < 0 || (off + 7) >= arr.length}.
	 * @see BitConverter#getLong(byte[], int, ByteOrder)
	 * @see Double#longBitsToDouble(long)
	 */
	public static double getDouble(byte[] arr, int off, ByteOrder endian) {
		return Double.longBitsToDouble(getLong(arr, off, endian));
	}

	/**
	 * Writes the {@code boolean} value at the specified offset in the raw data
	 * array. If the value is {@code true}, 1 will be written, otherwise 0.
	 *
	 * @param arr the output array.
	 * @param off the offset in the data array.
	 * @param val the {@code boolean} value.
	 */
	public static void putBoolean(byte[] arr, int off, boolean val) {
		arr[off] = val ? (byte) 1 : (byte) 0;
	}

	/**
	 * Writes the {@code byte} value at the specified offset in the raw data array.
	 *
	 * @param arr the output array.
	 * @param off the offset in the data array.
	 * @param val the {@code byte} value.
	 */
	public static void putByte(byte[] arr, int off, byte val) {
		arr[off] = val;
	}

	/**
	 * Writes the {@code short} value at the specified offset in the raw data array.
	 * The way the bytes are written depends on the specified {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code short} value.
	 */
	public static void putShort(byte[] arr, int off, ByteOrder endian, short val) {
		if (Objects.requireNonNull(endian) == ByteOrder.BIG_ENDIAN) {
			arr[off++] = (byte) (val >>> 8);
			arr[off] = (byte) (val & 0xFF);
		} else {
			arr[off++] = (byte) (val & 0xFF);
			arr[off] = (byte) (val >>> 8);
		}
	}

	/**
	 * Writes the {@code char} value at the specified offset in the raw data array.
	 * The way the bytes are written depends on the specified {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code char} value.
	 * @see #putDouble(byte[], int, java.nio.ByteOrder, short)
	 */
	public static void putChar(byte[] arr, int off, ByteOrder endian, char val) {
		putShort(arr, off, endian, (short) val);
	}

	/**
	 * Writes the {@code int} value at the specified offset in the raw data array.
	 * The way the bytes are written depends on the specified {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code int} value.
	 */
	public static void putInt(byte[] arr, int off, ByteOrder endian, int val) {
		if (Objects.requireNonNull(endian) == ByteOrder.BIG_ENDIAN) {
			arr[off++] = (byte) (val >>> 24);
			arr[off++] = (byte) (val >>> 16 & 0xFF);
			arr[off++] = (byte) (val >>> 8 & 0xFF);
			arr[off] = (byte) (val & 0xFF);
		} else {
			arr[off++] = (byte) (val & 0xFF);
			arr[off++] = (byte) (val >>> 8 & 0xFF);
			arr[off++] = (byte) (val >>> 16 & 0xFF);
			arr[off] = (byte) (val >>> 24);
		}
	}

	/**
	 * Writes the {@code long} value at the specified offset in the raw data array.
	 * The way the bytes are written depends on the specified {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code long} value.
	 */
	public static void putLong(byte[] arr, int off, ByteOrder endian, long val) {
		if (Objects.requireNonNull(endian) == ByteOrder.BIG_ENDIAN) {
			arr[off++] = (byte) (val >>> 56);
			arr[off++] = (byte) (val >>> 48 & 0xFF);
			arr[off++] = (byte) (val >>> 40 & 0xFF);
			arr[off++] = (byte) (val >>> 32 & 0xFF);
			arr[off++] = (byte) (val >>> 24 & 0xFF);
			arr[off++] = (byte) (val >>> 16 & 0xFF);
			arr[off++] = (byte) (val >>> 8 & 0xFF);
			arr[off] = (byte) (val & 0xFF);
		} else {
			arr[off++] = (byte) (val & 0xFF);
			arr[off++] = (byte) (val >>> 8 & 0xFF);
			arr[off++] = (byte) (val >>> 16 & 0xFF);
			arr[off++] = (byte) (val >>> 24 & 0xFF);
			arr[off++] = (byte) (val >>> 32 & 0xFF);
			arr[off++] = (byte) (val >>> 40 & 0xFF);
			arr[off++] = (byte) (val >>> 48 & 0xFF);
			arr[off] = (byte) (val >>> 56);
		}
	}

	/**
	 * Writes the {@code float} value at the specified offset in the raw data array.
	 * The way the bytes are written depends on the specified {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code float} value.
	 * @see #putDouble(byte[], int, java.nio.ByteOrder, int)
	 * @see Float#floatToIntBits(float)
	 */
	public static void putFloat(byte[] arr, int off, ByteOrder endian, float val) {
		putInt(arr, off, endian, Float.floatToIntBits(val));
	}

	/**
	 * Writes the {@code double} value at the specified offset in the raw data
	 * array. The way the bytes are written depends on the specified
	 * {@code ByteOrder}.
	 *
	 * @param arr    the output array.
	 * @param off    the offset in the data array.
	 * @param endian the byte order that specifies how the bytes will be written.
	 * @param val    the {@code double} value.
	 * @see #putDouble(byte[], int, java.nio.ByteOrder, long)
	 * @see Double#doubleToLongBits(double)
	 */
	public static void putDouble(byte[] arr, int off, ByteOrder endian, double val) {
		putLong(arr, off, endian, Double.doubleToLongBits(val));
	}

	/**
	 * Creates a byte array that fits 1 byte and writes the {@code boolean} value
	 * into it. If the value is {@code true}, 1 is written, otherwise 0 is written.
	 *
	 * @param val the {@code boolean} value.
	 * @return the array containing the {@code boolean} value.
	 */
	public static byte[] toBytes(boolean val) {
		return new byte[] { val ? (byte) 1 : (byte) 0 };
	}

	/**
	 * Creates a byte array that fits 1 byte and writes the {@code byte} value into
	 * it.
	 *
	 * @param val the {@code byte} value.
	 * @return the array containing the {@code byte} value.
	 */
	public static byte[] toBytes(byte val) {
		return new byte[] { val };
	}

	/**
	 * Creates a byte array that fits 2 bytes and writes the {@code short} value
	 * into it.
	 *
	 * @param val    the {@code short} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code short} value.
	 */
	public static byte[] toBytes(short val, ByteOrder endian) {
		byte[] ret = new byte[2];
		putShort(ret, 0, endian, val);
		return ret;
	}

	/**
	 * Creates a byte array that fits 2 bytes and writes the {@code char} value into
	 * it.
	 *
	 * @param val    the {@code char} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code char} value.
	 */
	public static byte[] toBytes(char val, ByteOrder endian) {
		byte[] ret = new byte[2];
		putChar(ret, 0, endian, val);
		return ret;
	}

	/**
	 * Creates a byte array that fits 4 bytes and writes the {@code int} value into
	 * it.
	 *
	 * @param val    the {@code int} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code int} value.
	 */
	public static byte[] toBytes(int val, ByteOrder endian) {
		byte[] ret = new byte[4];
		putInt(ret, 0, endian, val);
		return ret;
	}

	/**
	 * Creates a byte array that fits 8 bytes and writes the {@code long} value into
	 * it.
	 *
	 * @param val    the {@code long} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code long} value.
	 */
	public static byte[] toBytes(long val, ByteOrder endian) {
		byte[] ret = new byte[8];
		putLong(ret, 0, endian, val);
		return ret;
	}

	/**
	 * Creates a byte array that fits 4 bytes and writes the {@code float} value
	 * into it.
	 *
	 * @param val    the {@code float} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code float} value.
	 */
	public static byte[] toBytes(float val, ByteOrder endian) {
		return toBytes(Float.floatToIntBits(val), endian);
	}

	/**
	 * Creates a byte array that fits 8 bytes and writes the {@code double} value
	 * into it.
	 *
	 * @param val    the {@code double} value.
	 * @param endian the byte order in which the bytes are written.
	 * @return the array containing the {@code double} value.
	 */
	public static byte[] toBytes(double val, ByteOrder endian) {
		return toBytes(Double.doubleToLongBits(val), endian);
	}
}
