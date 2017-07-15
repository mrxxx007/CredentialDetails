package CredentialDetails.service;

import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationData;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableContentVo;
import CredentialDetails.data.TableRowVo;
import CredentialDetails.exception.CryptographyException;
import CredentialDetails.util.CryptoUtil;
import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Admin on 25.04.2017.
 */
public class FileService {
    public static ApplicationData loadFromFile(File file, String password) throws IOException, ClassNotFoundException, InvalidClassException, CryptographyException {
        final String transform = CryptoUtil.AES_TRANSFORMATION;
        final SecretKeySpec key = CryptoUtil.getAESSecretKey(password);
        final IvParameterSpec iv = CryptoUtil.getAESParameters(password);

        ApplicationData data = null;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (CryptoInputStream cis = new CryptoInputStream(transform, new Properties(), fileInputStream, key, iv)) {
                try (ObjectInputStream input = new ObjectInputStream(cis)) {
                    data = (ApplicationData) input.readObject();
                } catch (ClassNotFoundException e) {
                    UserMessageService.displayErrorMessage(e.getMessage());
                } catch (InvalidClassException e) {
                    UserMessageService.displayErrorMessage("<html>Unable to load from file <b>" + file.getName() +
                            "</b>. <hr/>Perhaps it was created by another application or by previous version of this <br/>" +
                            "application which is not compatible with the current one.</html>", "Error: incompatible file");
                }
            } catch (Exception e) {
                UserMessageService.displayWarningMessage("Could not load data because of corrupted file and/or incorrect password.");
            }
        } catch (IOException e) {
            UserMessageService.displayErrorMessage(e.getMessage());
        }

        return data;
    }

    public static void saveApplicationDataToFile(String password) {
        final String transform = CryptoUtil.AES_TRANSFORMATION;
        final SecretKeySpec key = CryptoUtil.getAESSecretKey(password);
        final IvParameterSpec iv = CryptoUtil.getAESParameters(password);

        ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();
        ApplicationData applicationData = applicationModel.getApplicationData();
        final File file = applicationModel.getCurrentFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (CryptoOutputStream cos = new CryptoOutputStream(transform, new Properties(), fileOutputStream, key, iv)){
                try (ObjectOutputStream output = new ObjectOutputStream(cos)) {
                    output.writeObject(applicationData);
                    StatusBarService.displayMessage("Saved successfully");
                }
            }
        } catch (IOException e) {
            UserMessageService.displayErrorMessage(e.getMessage());
            StatusBarService.displayMessage("Could not save to file");
        }
    }

    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    private static ApplicationData getNewTestData() {
        ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();

        Map<String, List<String>> sectionColumns = new HashMap<>();

        List<String> internetSectionColumns = new ArrayList<>();
        internetSectionColumns.add("ID");
        internetSectionColumns.add("Title");
        internetSectionColumns.add("URL");
        internetSectionColumns.add("Login");
        internetSectionColumns.add("Password");
        internetSectionColumns.add("Comments");

        List<String> gamesSectionColumns = new ArrayList<>();
        gamesSectionColumns.add("ID");
        gamesSectionColumns.add("Title");
        gamesSectionColumns.add("URL");
        gamesSectionColumns.add("Login");
        gamesSectionColumns.add("Password");
        gamesSectionColumns.add("Comments");

        sectionColumns.put("Internet", internetSectionColumns);
        sectionColumns.put("Games", gamesSectionColumns);

        // table data
        long id = applicationModel.getNextTableId();
        Map<String, String> internetData1 = new HashMap<>();
        internetData1.put("ID", Long.toString(id));
        internetData1.put("Title", "Mail Ru");
        internetData1.put("URL", "www.mail.ru");
        internetData1.put("Login", "mail1");
        internetData1.put("Password", "qwerty123");
        internetData1.put("Comments", "Mailbox service");

        TableRowVo internetRow1 = new TableRowVo(id);
        internetRow1.setSectionName("Internet");
        internetRow1.setData(internetData1);

        //
        id = applicationModel.getNextTableId();
        Map<String, String> internetData2 = new HashMap<>();
        internetData2.put("ID", Long.toString(id));
        internetData2.put("Title", "Sberbank");
        internetData2.put("URL", "sberbank.online.ru");
        internetData2.put("Login", "login21");
        internetData2.put("Password", "123456");
        internetData2.put("Comments", "");

        TableRowVo internetRow2 = new TableRowVo(id);
        internetRow2.setSectionName("Internet");
        internetRow2.setData(internetData2);

        List<TableRowVo> internetTableData = new ArrayList<>();
        internetTableData.add(internetRow1);
        internetTableData.add(internetRow2);


        ApplicationData applicationData = new ApplicationData();
        applicationData.setSectionColumns(sectionColumns);
        //applicationData.setMaxTableId(applicationModel.getMaxTableId());
        applicationData.getTableData().put("Internet", internetTableData);

        return applicationData;
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

        //////
        List<TableContentVo> gamesList = new ArrayList<>();
        TableContentVo vo3 = new TableContentVo();
        vo3.setTitle("World of Warcraft");
        vo3.setUrl("www.wow-circle.com");
        vo3.setLogin("wow-login");
        vo3.setPassword("wow-passw");
        vo3.setComment("Private wow server");

        gamesList.add(vo3);

        data.put("Games", gamesList);

        return data;
    }
}
