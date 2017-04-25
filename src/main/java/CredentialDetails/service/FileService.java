package CredentialDetails.service;

import CredentialDetails.data.TableContentVo;

import java.io.*;
import java.util.*;

/**
 * Created by Admin on 25.04.2017.
 */
public class FileService {
    private static final String TEMP_FILE_NAME = "C:\\temp\\credentialDetails.dat";

    public static Map<String, Collection<TableContentVo>> loadFromFile() {
        File file = new File(TEMP_FILE_NAME);
        Map<String, Collection<TableContentVo>> data = Collections.emptyMap();
        if (file.exists()) {
            try (FileInputStream fileOutputStream = new FileInputStream(file)) {
                try (ObjectInputStream input = new ObjectInputStream(fileOutputStream)) {
                    data = (Map<String, Collection<TableContentVo>>) input.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            data = getTestData();
        }

        return data;
    }

    public static void saveToFile(Map<String, Collection<TableContentVo>> data) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(TEMP_FILE_NAME)) {
            try (ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
                output.writeObject(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Collection<TableContentVo>> getTestData() {
        Map<String, Collection<TableContentVo>> data = new HashMap<>();
        List<TableContentVo> voList = new ArrayList<>();

        TableContentVo vo1 = new TableContentVo();
        vo1.setTitle("Mail.ru");
        vo1.setUrl("www.mail.ru");
        vo1.setLogin("login1");
        vo1.setPassword("123456abc");

        TableContentVo vo2 = new TableContentVo();
        vo2.setTitle("Sberbank");
        vo2.setUrl("www.sberbank-online.ru");
        vo2.setLogin("login2");
        vo2.setPassword("qwerty123");
        vo2.setComment("Личный кабинет");

        voList.add(vo1);
        voList.add(vo2);

        data.put("Internet", voList);
        return data;
    }
}
