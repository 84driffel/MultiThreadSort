//package edu.uiowa.mc.hw1;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DatasetGenerator {

    public static void main(String[] args) throws IOException {
        int N = 200;

        long batch[] = new long[N];
        Random r = new Random(1234);
        for (int i = 0; i < batch.length; i++) {
            batch[i] = r.nextLong() % 1000;
        }

        System.out.println(batch);

        FileOutputStream fos = new FileOutputStream("array.bin");
        DataOutputStream dos = new DataOutputStream(fos);

        for (int i = 0; i < batch.length; i++) {
            dos.writeLong(batch[i]);
        }
        dos.close();
        fos.close();
    }
}
