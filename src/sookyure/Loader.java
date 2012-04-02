package sookyure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {


    public String[] consumer() {
        String[] Key = new String[2];
        BufferedReader b = new BufferedReader(fileOpen("./Token/Consumer.dat"));
        for (int i = 0; i < 2; i++) {
            try {
                Key[i] = b.readLine();
            } catch (IOException e) {
                System.out.println("コンシューマキーが登録されていません");
            }
        }
        return Key;
    }

    public String[] accessToken() {
        String[] Key = new String[2];
        FileReader file = fileOpen("./Token/AccsesToken.dat");
        if (file == null) {
            Key[0] = null;
            Key[1] = null;
        } else {

            BufferedReader b = new BufferedReader(file);
            for (int i = 0; i < 2; i++) {

                try {
                    Key[i] = b.readLine();
                } catch (IOException e) {
                    System.out.println("./Token/AccsesToken.datにTokenが保存されてません。./Token/AccsesToken.datを削除してプログラムを再起動してください");
                    return null;
                }
            }
        }
        return Key;
    }

    private static FileReader fileOpen(String pass) {
        FileReader file = null;
        try {
            file = new FileReader(pass);
        } catch (Exception e) {
            System.out.println(pass + "が存在しません。");
            return null;
        }
        return file;
    }
}
