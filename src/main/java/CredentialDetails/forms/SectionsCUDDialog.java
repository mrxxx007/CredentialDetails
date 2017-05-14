package CredentialDetails.forms;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.controller.SectionsCUDDialogController;
import CredentialDetails.controller.SectionsCUDDialogSelectionListener;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.SectionsCUDDialogModel;
import CredentialDetails.data.TableRowVo;
import CredentialDetails.service.UserMessageService;
import CredentialDetails.util.Utils;
import CredentialDetails.view.SectionsCUDDialogRender;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class SectionsCUDDialog extends JDialog {
    private SectionsCUDDialogModel model;
    private boolean editMode;
    private String previousSectionName;

    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JButton addColumnButton;
    private JButton removeColumnButton;
    private JButton moveUpButton;
    private JList columnsList;
    private JButton moveDownButton;
    private JTextField sectionNameTextField;

    public SectionsCUDDialog(JFrame owner, boolean isEditMode) {
        super(owner, true);
        this.model = new SectionsCUDDialogModel(new SectionsCUDDialogRender(this));
        this.editMode = isEditMode;

        if (isEditMode) {
            setTitle("Edit section");
            previousSectionName = Application.getInstance().getMainForm().getModel().getActiveSection();
            sectionNameTextField.setText(previousSectionName);
        } else {
            setTitle("New section");
            this.model.addColumn("ID");
        }
        getRootPane().setDefaultButton(okButton);
        getContentPane().add(contentPane);

        SectionsCUDDialogController controller = new SectionsCUDDialogController(this.model);
        addColumnButton.setActionCommand(ActionCommand.SECTIONS_DLG_ADD_COLUMN.name());
        addColumnButton.addActionListener(controller);
        removeColumnButton.setActionCommand(ActionCommand.SECTIONS_DLG_REMOVE_COLUMN.name());
        removeColumnButton.addActionListener(controller);
        moveDownButton.setActionCommand(ActionCommand.SECTIONS_DLG_MOVE_DOWN.name());
        moveDownButton.addActionListener(controller);
        moveUpButton.setActionCommand(ActionCommand.SECTIONS_DLG_MOVE_UP.name());
        moveUpButton.addActionListener(controller);

        //okButton.setActionCommand(ActionCommand.SECTIONS_DLG_OK.name());
        okButton.addActionListener(e -> onOK());
        //cancelButton.setActionCommand(ActionCommand.SECTIONS_DLG_CANCEL.name());
        cancelButton.addActionListener(e -> onCancel());

        columnsList.addListSelectionListener(new SectionsCUDDialogSelectionListener(this.model));
        columnsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                // do not allow to select the first item
                boolean isSelectedItem = isSelected && index != 0;
                return super.getListCellRendererComponent(list, value, index, isSelectedItem, cellHasFocus);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void showDialog() {
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    public SectionsCUDDialogModel getModel() {
        return model;
    }

    public JList getColumnsList() {
        return columnsList;
    }

    private void onOK() {
        String sectionName = sectionNameTextField.getText();
        int listElementsCount = columnsList.getModel().getSize();
        if (Utils.isNullOrEmpty(sectionName)) {
            UserMessageService.displayInfoMessage("Section Name must not be empty", "Mandatory field missed");
        } else if (listElementsCount <= 1) {
            UserMessageService.displayInfoMessage("Please add more columns to continue", "Mandatory field missed");
        } else {
            ApplicationModel appModel = Application.getInstance().getMainForm().getModel();
            Map<String, Collection<TableRowVo>> tableData = appModel.getApplicationData().getTableData();
            Map<String, List<String>> sectionColumns = appModel.getApplicationData().getSectionColumns();

            List<String> newColumns = new ArrayList<>(listElementsCount);
            for (int idx = 0; idx < listElementsCount; idx++) {
                newColumns.add(columnsList.getModel().getElementAt(idx).toString());
            }

            if (editMode) {
                if (model.isColumnOrderChanged()) {
                    sectionColumns.get(sectionName).clear();
                    sectionColumns.get(sectionName).addAll(newColumns);
                }

                if (!previousSectionName.equals(sectionName)) {
                    // section name changed
                    Collection<TableRowVo> removedValues = tableData.remove(previousSectionName);
                    tableData.put(sectionName, removedValues);

                    List<String> removedColumns = sectionColumns.remove(previousSectionName);
                    sectionColumns.put(sectionName, removedColumns);
                }

                Collection<String> diffRemovedColumns = getDifference(sectionColumns.get(sectionName), newColumns);
                if (!diffRemovedColumns.isEmpty()) {
                    // some of columns were removed
                    sectionColumns.get(sectionName).removeAll(diffRemovedColumns);

                    for (TableRowVo tableRowVo : tableData.get(sectionName)) {
                        tableRowVo.getData().keySet().removeAll(diffRemovedColumns);
                    }
                }

                Collection<String> diffAddedColumns = getDifference(newColumns, sectionColumns.get(sectionName));
                if (!diffAddedColumns.isEmpty()) {
                    // some columns were added
                    sectionColumns.get(sectionName).addAll(diffAddedColumns);

                    //TODO: maybe need to add new columns to the existing data
                }
            } else {
                // create new section
                tableData.put(sectionName, new ArrayList<>());
                sectionColumns.put(sectionName, newColumns);
            }

            // close dialog
            dispose();

            appModel.refreshAll();
        }
    }

    private <T> Collection<T> getDifference(Collection<T> collection1, Collection<T> collection2) {
        Collection<T> result = new ArrayList<>(collection1);
        result.removeAll(collection2);
        return result;
    }

    private void onCancel() {
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setOpaque(true);
        contentPane.setPreferredSize(new Dimension(400, 282));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setOpaque(true);
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel2.setOpaque(true);
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setOpaque(true);
        okButton.setText("OK");
        panel2.add(okButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel2.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.putClientProperty("html.disable", Boolean.FALSE);
        contentPane.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        columnsList = new JList();
        columnsList.setSelectionMode(0);
        panel4.add(columnsList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addColumnButton = new JButton();
        addColumnButton.setText("Add column");
        panel5.add(addColumnButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeColumnButton = new JButton();
        removeColumnButton.setText("Remove column");
        panel5.add(removeColumnButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveUpButton = new JButton();
        moveUpButton.setText("Move up");
        panel5.add(moveUpButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveDownButton = new JButton();
        moveDownButton.setText("Move down");
        panel5.add(moveDownButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Columns:");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Section name:");
        panel6.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sectionNameTextField = new JTextField();
        panel6.add(sectionNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    //    public static void main(String[] args) {
//        SectionsCEDDialog dialog = new SectionsCEDDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

}
