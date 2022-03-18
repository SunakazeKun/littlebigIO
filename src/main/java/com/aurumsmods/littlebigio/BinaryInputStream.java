/*
 * The MIT License
 *
 * Copyright 2022 Aurum.
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

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A binary data input stream that provides readability of primitive data types based on the specified {@link ByteOrder} (big or
 * little). This implements {@link DataInput} but does not follow the exact specifications prescribed by the contract. This is
 * because {@code DataInput} explicitly states the implementation of big-endian byte ordering.
 * 
 * @author Aurum
 * @since 1.0
 */
public final class BinaryInputStream extends FilterInputStream implements DataInput, UnsignedIntInput {
    /**
     * The byte order in which the data is stored.
     * @see ByteOrder
     */
    private ByteOrder endianness;
    
    /**
     * A working buffer that stores temporarily read data. Fits up to 8 bytes.
     */
    private final byte[] buffer = new byte[8];
    
    /**
     * Creates a {@code BinaryInputStream} that wraps the given input stream and uses the native byte order when reading data.
     * 
     * @param in the wrapped input stream.
     */
    public BinaryInputStream(InputStream in) {
        super(Objects.requireNonNull(in));
        endianness = ByteOrder.nativeOrder();
    }
    
    /**
     * Creates a {@code BinaryInputStream} that wraps the given input stream and uses the specified {@code ByteOrder} when
     * reading data.
     * 
     * @param in the wrapped input stream.
     * @param endian the byte order of the data.
     */
    public BinaryInputStream(InputStream in, ByteOrder endian) {
        super(Objects.requireNonNull(in));
        endianness = Objects.requireNonNull(endian);
    }
    
    /**
     * Returns the byte order of this stream.
     * 
     * @return the byte order.
     */
    public ByteOrder order() {
        return endianness;
    }
    
    /**
     * Sets the byte order of this stream.
     * 
     * @param endian the byte order.
     */
    public void order(ByteOrder endian) {
    	endianness = endian;
    }
    
    /**
     * Reads the specified number of bytes into the working buffer.
     * 
     * @param n the number of bytes to be read.
     * @throws IOException if an error occurs during reading.
     */
    private void readIntoBuffer(int n) throws IOException {
        int read = in.readNBytes(buffer, 0, n);
        
        if (read != n) {
            throw new EOFException();
        }
    }

    /**
     * Reads the specified number of bytes from the input stream into the given byte array. This method blocks until {@code len}
     * bytes of input data have been read, end of stream is detected, or an exception is thrown. This does not close the stream.
     * 
     * @see InputStream#readNBytes(byte[], int, int) 
     * @param b the byte array into which the data is read.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public void readFully(byte[] b) throws IOException {
        in.readNBytes(b, 0, b.length);
    }

    /**
     * Reads the specified number of bytes from the input stream into the given byte array. This method blocks until {@code len}
     * bytes of input data have been read, end of stream is detected, or an exception is thrown. This does not close the stream.
     * 
     * @see InputStream#readNBytes(byte[], int, int) 
     * @param b the byte array into which the data is read.
     * @param off the offset in {@code b} at which the data is written.
     * @param len the maximum number of bytes to read.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        in.readNBytes(b, off, len);
    }

    /**
     * Skips over and discards exactly {@code n} bytes of data from this input stream. If {@code n} is zero or negative, then no
     * bytes are skipped. This method blocks until {@code n} bytes have been skipped, end of stream is detected, or an exception
     * is thrown.
     * 
     * @param n the number of bytes to skip.
     * @return the number of bytes that were skipped.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public int skipBytes(int n) throws IOException {
        if (n < 1) {
            return 0;
        }
        
        int total = 0;
        int cur;

        while ((total < n) && ((cur = (int)in.skip(n - total)) > 0)) {
            total += cur;
        }

        return total;
    }
    
    /**
     * Reads one byte from the input stream and returns {@code true} if that byte is nonzero and {@code false} if it is zero.
     * 
     * @return the {@code boolean} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public boolean readBoolean() throws IOException {
        int ch = in.read();
        
        if (ch < 0) {
            throw new EOFException();
        }
        
        return ch != 0;
    }
    
    /**
     * Reads and returns one signed {@code byte} from the input stream.
     * 
     * @return the signed {@code byte} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public byte readByte() throws IOException {
        int ch = in.read();
        
        if (ch < 0) {
            throw new EOFException();
        }
        
        return (byte)ch;
    }

    /**
     * Reads and returns one unsigned {@code byte} from the input stream.
     * 
     * @return the unsigned {@code byte} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public int readUnsignedByte() throws IOException {
        int ch = in.read();
        
        if (ch < 0) {
            throw new EOFException();
        }
        
        return ch;
    }
    
    /**
     * Reads and returns one signed {@code short} which occupies 2 bytes from the input stream.
     * 
     * @see BitConverter#getShort(byte[], int, java.nio.ByteOrder) 
     * @return the signed {@code short} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public short readShort() throws IOException {
        readIntoBuffer(2);
        return BitConverter.getShort(buffer, 0, endianness);
    }

    /**
     * Reads and returns one unsigned {@code short} which occupies 2 bytes from the input stream.
     * 
     * @see #readShort() 
     * @return the unsigned {@code short} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public int readUnsignedShort() throws IOException {
        return readShort() & 0xFFFF;
    }
    
    /**
     * Reads and returns one {@code char} which occupies 2 bytes from the input stream.
     * 
     * @see #readShort() 
     * @return the {@code char} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public char readChar() throws IOException {
        return (char)readShort();
    }
    
    /**
     * Reads and returns one signed {@code int} which occupies 4 bytes from the input stream.
     * 
     * @see BitConverter#getInt(byte[], int, java.nio.ByteOrder) 
     * @return the signed {@code int} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public int readInt() throws IOException {
        readIntoBuffer(4);
        return BitConverter.getInt(buffer, 0, endianness);
    }
    
    /**
     * Reads and returns one unsigned {@code int} which occupies 4 bytes from the input stream.
     * 
     * @see #readInt() 
     * @return the signed {@code int} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public long readUnsignedInt() throws IOException {
        return readInt() & 0xFFFFFFFFL;
    }
    
    /**
     * Reads and returns one signed {@code long} which occupies 8 bytes from the input stream.
     * 
     * @see BitConverter#getLong(byte[], int, java.nio.ByteOrder) 
     * @return the signed {@code long} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public long readLong() throws IOException {
        readIntoBuffer(8);
        return BitConverter.getLong(buffer, 0, endianness);
    }
    
    /**
     * Reads and returns one {@code float} which occupies 4 bytes from the input stream.
     * 
     * @see #readInt()
     * @see Float#intBitsToFloat(int) 
     * @return the {@code float} value read.
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }
    
    /**
     * Reads and returns one {@code double} which occupies 8 bytes from the input stream.
     * 
     * @see #readLong()
     * @see Double#longBitsToDouble(long) 
     * @throws EOFException if this stream reached the end before reading all the bytes.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
    
    /**
     * Not implemented; use a {@link java.io.BufferedReader} instead.
     * 
     * @return n/a
     * @throws IOException n/a
     * @deprecated The preferred way to read lines of text is via {@link java.io.BufferedReader#readLine()}.
     */
    @Override
    @Deprecated
    public String readLine() throws IOException {
        throw new UnsupportedOperationException("Not implemented. Use BufferedReader instead.");
    }
    
    /**
     * Reads and returns a Unicode character string encoded in modified UTF-8 format from the input stream.
     * 
     * @see DataInputStream#readUTF(java.io.DataInput) 
     * @return the Unicode string read.
     * @throws IOException if an error occurs during reading.
     */
    @Override
    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }
}
