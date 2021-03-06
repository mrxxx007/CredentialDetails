package CredentialDetails.forms;

import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableRowVo;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CredentialsCUDDialog extends JDialog {
    // Map<columnName, columnValue>
    private Map<String, JTextField> columnValuesMap;
    private boolean editMode;
    private long selectedTableRowId;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane mainScrollPane;
    private JPanel mainPanel;

    public CredentialsCUDDialog(JFrame owner, boolean isEditMode) {
        this(owner, isEditMode, -1);
    }

    public CredentialsCUDDialog(JFrame owner, boolean isEditMode, long selectedTableRowId) {
        super(owner, true);
        this.editMode = isEditMode;
        this.selectedTableRowId = selectedTableRowId;

        $$$setupUI$$$();
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);


        // listeners
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void showDialog() {
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    private void onOK() {
        ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();
        String activeSection = applicationModel.getActiveSection();

        Map<String, String> data = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : columnValuesMap.entrySet()) {
            data.put(entry.getKey(), entry.getValue().getText());
        }

        long id = Long.parseLong(data.get("ID"));

        TableRowVo newTableRow = new TableRowVo(id);
        newTableRow.setSectionName(activeSection);
        newTableRow.setData(data);

        if (editMode) {
            applicationModel.updateTableRowData(newTableRow);
        } else {
            applicationModel.appendTableDataForActiveSection(newTableRow);
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private void createUIComponents() {
        final ApplicationModel appModel = Application.getInstance().getMainForm().getModel();
        final String activeSection = appModel.getActiveSection();
        final List<String> sectionColumns = appModel.getApplicationData().getSectionColumns().get(activeSection);
        final long tableRowId = editMode
                ? this.selectedTableRowId
                : appModel.getNextTableId();

        final TableRowVo selectedTableRow = editMode
                ? getTableRowFromAppModel(appModel, activeSection, tableRowId)
                : null;

        columnValuesMap = new HashMap<>(sectionColumns.size());

        GridLayoutManager layout = new GridLayoutManager(sectionColumns.size(), 2, new Insets(10, 10, 10, 10), -1, -1);
        mainPanel = new JPanel(layout);
        mainPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        int index = 0;
        for (String column : sectionColumns) {
            JLabel label = new JLabel(column);
            mainPanel.add(label,
                    new GridConstraints(index, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            JTextField field = new JTextField();
            mainPanel.add(field,
                    new GridConstraints(index, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_CAN_SHRINK, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

            if (column.equalsIgnoreCase("ID")) {
                field.setEnabled(false);
                field.setEditable(false);
                field.setText(tableRowId + "");
            } else {
                if (editMode && selectedTableRow != null) {
                    field.setText(selectedTableRow.getData().get(column));
                }
            }

            columnValuesMap.put(column, field);
            index++;
        }
    }

    private TableRowVo getTableRowFromAppModel(ApplicationModel appModel, String activeSection, long rowId) {
        Collection<TableRowVo> tableRowVos = appModel.getApplicationData().getTableData().get(activeSection);

        TableRowVo result = null;
        for (TableRowVo tableRowVo : tableRowVos) {
            if (tableRowVo.getId() == rowId) {
                result = tableRowVo;
                break;
            }
        }

        return result;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainScrollPane = new JScrollPane();
        mainScrollPane.setVerticalScrollBarPolicy(20);
        contentPane.add(mainScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(300, -1), null, 0, false));
        mainScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        mainScrollPane.setViewportView(mainPanel);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
