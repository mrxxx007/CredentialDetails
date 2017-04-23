package CredentialDetails.controller;

import CredentialDetails.app.Application;
import CredentialDetails.data.TableContentVo;
import CredentialDetails.view.MainFormRender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Admin on 23.04.2017.
 */
public class LoadButtonController implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        MainFormRender mainFormRender = new MainFormRender(Application.getInstance());
        mainFormRender.renderSectionsList(getTestData().keySet());
        mainFormRender.renderTable(getTestData().get("Internet"));
    }


    private Map<String, Collection<TableContentVo>> getTestData() {
        Map<String, Collection<TableContentVo>> data = new HashMap<String, Collection<TableContentVo>>();
        List<TableContentVo> voList = new ArrayList<TableContentVo>();

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
