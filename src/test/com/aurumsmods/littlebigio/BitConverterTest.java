package com.aurumsmods.littlebigio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

class BitConverterTest {
	private final ByteOrder[] byteOrders = { ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN };
	private final byte[] byteValues = { 1, -127, 78, 23 };
	private final int[] unsignedByteValues = { 1, 255, 129, 23 };
	private final short[] shortValues = { 1, 256, 4474, -32000 };
	private final int[] unsignedShortValues = { 1, 65535, 32000, 0 };
	private final int[] intValues = { 1, 48945, 398190874, -498373992 };
	private final long[] unsignedIntValues = { 1, -((long) Integer.MIN_VALUE), 0, 4294967295L };
	private final long[] longValues = { 1, Long.MAX_VALUE, Long.MIN_VALUE, -4386573894564L };
	private final float[] floatValues = { 0f, 1f, -1f, Float.MAX_VALUE, Float.MIN_VALUE, Float.POSITIVE_INFINITY,
			Float.NEGATIVE_INFINITY };
	private final double[] doubleValues = { 0f, 1f, -1f, Double.MAX_VALUE, Double.MIN_VALUE, Double.POSITIVE_INFINITY,
			Double.NEGATIVE_INFINITY };

	@Test
	void testByte() {
		byte[] array = new byte[Byte.BYTES];

		for (byte i : byteValues) {
			BitConverter.putByte(array, 0, i);
			assertEquals(BitConverter.getByte(array, 0), i);
		}
	}

	@Test
	void testUnsignedByte() {
		byte[] array = new byte[Byte.BYTES];

		for (int i : unsignedByteValues) {
			BitConverter.putByte(array, 0, (byte) i);
			assertEquals(BitConverter.getUnsignedByte(array, 0), i);
		}
	}

	@Test
	void testShort() {
		byte[] array = new byte[Short.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (short i : shortValues) {
				BitConverter.putShort(array, 0, byteOrder, i);
				assertEquals(BitConverter.getShort(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testUnsignedShort() {
		byte[] array = new byte[Short.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (int i : unsignedShortValues) {
				BitConverter.putShort(array, 0, byteOrder, (short) i);
				assertEquals(BitConverter.getUnsignedShort(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testInt() {
		byte[] array = new byte[Integer.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (int i : intValues) {
				BitConverter.putInt(array, 0, byteOrder, i);
				assertEquals(BitConverter.getInt(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testUnsignedInt() {
		byte[] array = new byte[Integer.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (long i : unsignedIntValues) {
				BitConverter.putInt(array, 0, byteOrder, (int) i);
				assertEquals(BitConverter.getUnsignedInt(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testLong() {
		byte[] array = new byte[Long.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (long i : longValues) {
				BitConverter.putLong(array, 0, byteOrder, i);
				assertEquals(BitConverter.getLong(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testFloat() {
		byte[] array = new byte[Float.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (float i : floatValues) {
				BitConverter.putFloat(array, 0, byteOrder, i);
				assertEquals(BitConverter.getFloat(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testDouble() {
		byte[] array = new byte[Double.BYTES];

		for (ByteOrder byteOrder : byteOrders) {
			for (double i : doubleValues) {
				BitConverter.putDouble(array, 0, byteOrder, i);
				assertEquals(BitConverter.getDouble(array, 0, byteOrder), i);
			}
		}
	}

	@Test
	void testNullPointerException() {
		assertThrows(NullPointerException.class, () -> BitConverter.getBoolean(null, 0));
		assertThrows(NullPointerException.class, () -> BitConverter.getByte(null, 0));
		assertThrows(NullPointerException.class, () -> BitConverter.getShort(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getShort(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getUnsignedShort(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getUnsignedShort(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getChar(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getChar(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getInt(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getInt(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getUnsignedInt(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getUnsignedInt(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getLong(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getLong(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getFloat(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getFloat(new byte[0], 0, null));
		assertThrows(NullPointerException.class, () -> BitConverter.getDouble(null, 0, ByteOrder.BIG_ENDIAN));
		assertThrows(NullPointerException.class, () -> BitConverter.getDouble(new byte[0], 0, null));
	}

	@Test
	void testArrayIndexOutOfBoundsException() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getBoolean(new byte[0], -1));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getBoolean(new byte[0], 0));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getByte(new byte[0], -1));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getByte(new byte[0], 0));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getUnsignedByte(new byte[0], -1));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> BitConverter.getUnsignedByte(new byte[0], 0));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getShort(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getShort(new byte[2], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getUnsignedShort(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getUnsignedShort(new byte[2], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getChar(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getChar(new byte[2], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getInt(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getInt(new byte[4], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getUnsignedInt(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getUnsignedInt(new byte[4], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getLong(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getLong(new byte[8], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getFloat(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getFloat(new byte[4], 1, ByteOrder.BIG_ENDIAN));

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getDouble(new byte[0], -1, ByteOrder.BIG_ENDIAN));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> BitConverter.getDouble(new byte[8], 1, ByteOrder.BIG_ENDIAN));
	}

}
