package org.example.nio;// Java program to demonstrate
// get() method 

import java.nio.*;
import java.util.*;

public class GFG {

    public static void main(String[] args) {

        // Declaring the capacity of the ByteBuffer 
        int capacity = 4;


        // Creating the ByteBuffer 
        try {

            // creating object of ByteBuffer 
            // and allocating size capacity 
            ByteBuffer bb = ByteBuffer.allocate(capacity);

            for (int i = 0; i < 3; i++) {
                // putting the int to byte typecast value in ByteBuffer
                bb.put((byte) 20);
                bb.put((byte) 30);

                // print the ByteBuffer
                System.out.println("\nOriginal ByteBuffer:  "
                        + Arrays.toString(bb.array()));

                bb.rewind();
                //bb.flip();

                // Reads the byte at this buffer's current position
                // using get() method
                byte value = bb.get();

                // print the byte value
                System.out.println("\nByte Value: " + value);

                // Reads the Byte at this buffer's next position
                // using get() method
                System.out.print("\nsince the buffer current position is incremented");
                System.out.print(" to greater than its limit ");

                byte value1 = bb.get();

                // print the Byte value
                System.out.print("\nNext Byte Value: " + value1);

//                bb.clear();
                bb.rewind();

//                byte value2 = bb.get();
//                // print the Byte value
//                System.out.print("\nNext Byte Value: " + value2);

            }
        } catch (IllegalArgumentException e) {

            System.out.println("\nIllegalArgumentException catched");
        } catch (ReadOnlyBufferException e) {

            System.out.println("\nReadOnlyBufferException catched");
        } catch (BufferUnderflowException e) {

            System.out.println("\nException throws : " + e);
        }
    }
} 