package edu.uiowa.mc.hw1;

import java.io.*;

public class DataLoader {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("array.bin");
        DataInputStream dis = new DataInputStream(fis);

        try {
            long index = 0;
            while (true) {
                long l = dis.readLong();
                System.out.println(index + " " + l);
                index += 1;
            }
        } catch (EOFException e) {
            fis.close();
        }
    }
}
