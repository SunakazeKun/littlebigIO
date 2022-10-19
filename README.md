# littlebigIO
... is a lightweight Java library that provides I/O streams and bit-conversion methods to deal with primitive data types in big-endian or little-endian byte order. Java's standard classes, such as ``DataInputStream``, are mostly laid out for network transmission, which supports big-endian data only. In some applications, we may have to use little-endian, so this small library was created to fill this gap. The project can be imported and opened in Eclipse.

The libraries most important classes are:
- [BinaryInputStream](https://aurumsmods.com/littlebigIO/com/aurumsmods/littlebigio/BinaryInputStream.html): An input stream to read primitive data types in a specified byte order from an underlying ``InputStream``.
- [BinaryOutputStream](https://aurumsmods.com/littlebigIO/com/aurumsmods/littlebigio/BinaryOutputStream.html): An output stream to write primitive data types in a specified byte order to an underlying ``OutputStream``.
- [BitConverter](https://aurumsmods.com/littlebigIO/com/aurumsmods/littlebigio/BitConverter.html): Consists of helper methods to convert between bytes and primitive data types, similar to *.NET*'s ``BitConverter`` class.

Essentially, ``BinaryInputStream`` and ``BinaryOutputStream`` can be used like Java's ``DataInputStream`` and ``DataOutputStream``, respectively.

## Documentation
All javadocs for this library can be found [on my website](https://aurumsmods.com/littlebigIO/com/aurumsmods/littlebigio/package-summary.html).

## Example
This should help you get a basic idea of how to use the features of this library. Below is some example code showing how to read and write ``int`` array files. A byte order mark, or BOM for short, is written at the file's start, allowing us to detect the correct byte order while reading from the file.

```java
	public int[] readIntArray(File f) throws IOException {
		try (BinaryInputStream in = new BinaryInputStream(new FileInputStream(f))) {
			// Read and verify BOM
			char bom = in.readChar();

			if (bom == 0xFFFE) {
				in.swapOrder();
			} else if (bom != 0xFEFF) {
				throw new BOMException("File does not start with UTF-16 BOM");
			}

			// Read int array
			int[] arr = new int[in.readInt()];

			for (int i = 0; i < arr.length; i++) {
				arr[i] = in.readInt();
			}

			return arr;
		}
	}

	public void writeIntArray(File f, int[] arr) throws IOException {
		try (BinaryOutputStream out = new BinaryOutputStream(new FileOutputStream(f))) {
			out.writeChar(0xFEFF);
			out.writeInt(arr.length);

			for (int i : arr) {
				out.writeInt(i);
			}
		}
	}
```