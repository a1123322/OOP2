import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UnitConverter extends JFrame {
    // 介面元件
    private JComboBox<String> typeComboBox;
    private JTextField inputField;
    private JComboBox<String> fromUnitComboBox;
    private JTextField resultField;
    private JComboBox<String> toUnitComboBox;
    private JButton convertButton;

    // 定義各類型的單位選項
    private final Map<String, String[]> unitOptions = new HashMap<>();

    public UnitConverter() {
        super("單位換算器");
        initData();
        initUI();
    }

    private void initData() {
        // 設定各類型對應的單位選單
        unitOptions.put("長度", new String[]{"公尺", "公分", "英吋", "英尺"});
        unitOptions.put("重量", new String[]{"公斤", "公克", "磅", "盎司"});
        unitOptions.put("溫度", new String[]{"攝氏", "華氏", "克氏"});
    }

    private void initUI() {
        // 1. 基本視窗設定 (480x280, BorderLayout)
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 2. NORTH: 換算類型選單
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(new JLabel("換算類型:"));
        typeComboBox = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
        northPanel.add(typeComboBox);
        add(northPanel, BorderLayout.NORTH);

        // 3. CENTER: 兩列輸入/輸出區域 (每列 JTextField + JComboBox)
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // 第一列：來源數值與來源單位
        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        inputField = new JTextField();
        fromUnitComboBox = new JComboBox<>();
        row1.add(inputField, BorderLayout.CENTER);
        row1.add(fromUnitComboBox, BorderLayout.EAST);

        // 第二列：目標結果與目標單位
        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        resultField = new JTextField();
        resultField.setEditable(false); // 結果欄位設為唯讀
        toUnitComboBox = new JComboBox<>();
        row2.add(resultField, BorderLayout.CENTER);
        row2.add(toUnitComboBox, BorderLayout.EAST);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // 4. SOUTH: 「換算」按鈕
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        convertButton = new JButton("換算");
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // 5. 事件監聽設定
        // 切換類型時動態更新下拉選單內容
        typeComboBox.addActionListener(e -> updateUnitComboBoxes());

        // 點擊「換算」按鈕執行換算
        convertButton.addActionListener(e -> performConversion());

        // 初始化第一個類型的下拉選項
        updateUnitComboBoxes();
    }

    // 更新來源與目標單位的選單項目
    private void updateUnitComboBoxes() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        String[] units = unitOptions.get(selectedType);

        if (units != null) {
            fromUnitComboBox.setModel(new DefaultComboBoxModel<>(units));
            toUnitComboBox.setModel(new DefaultComboBoxModel<>(units));
            // 預設將目標單位選至第二個（若有的話），操作體驗更好
            if (units.length > 1) {
                toUnitComboBox.setSelectedIndex(1);
            }
        }
        resultField.setText(""); // 切換類型時清空結果
    }

    // 核心換算邏輯
    private void performConversion() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入欲換算的數值！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請輸入合法的數值格式！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = (String) typeComboBox.getSelectedItem();
        String from = (String) fromUnitComboBox.getSelectedItem();
        String to = (String) toUnitComboBox.getSelectedItem();

        double result = 0.0;

        switch (type) {
            case "長度":
                result = convertLength(value, from, to);
                break;
            case "重量":
                result = convertWeight(value, from, to);
                break;
            case "溫度":
                result = convertTemperature(value, from, to);
                break;
        }

        // 顯示結果（格式化至小數點後 4 位，若是整數則簡化）
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            resultField.setText(String.format("%.0f", result));
        } else {
            resultField.setText(String.format("%.4f", result));
        }
    }

    // 長度換算 (統一以「公尺」為基準進行轉換)
    private double convertLength(double val, String from, String to) {
        double inMeters = 0.0;
        switch (from) {
            case "公尺": inMeters = val; break;
            case "公分": inMeters = val / 100.0; break;
            case "英吋": inMeters = val * 0.0254; break;
            case "英尺": inMeters = val * 0.3048; break;
        }

        switch (to) {
            case "公尺": return inMeters;
            case "公分": return inMeters * 100.0;
            case "英吋": return inMeters / 0.0254;
            case "英尺": return inMeters / 0.3048;
            default: return inMeters;
        }
    }

    // 重量換算 (統一以「公斤」為基準進行轉換)
    private double convertWeight(double val, String from, String to) {
        double inKg = 0.0;
        switch (from) {
            case "公斤": inKg = val; break;
            case "公克": inKg = val / 1000.0; break;
            case "磅":   inKg = val * 0.45359237; break;
            case "盎司": inKg = val * 0.028349523125; break;
        }

        switch (to) {
            case "公斤": return inKg;
            case "公克": return inKg * 1000.0;
            case "磅":   return inKg / 0.45359237;
            case "盎司": return inKg / 0.028349523125;
            default: return inKg;
        }
    }

    // 溫度換算 (統一以「攝氏」為基準進行轉換)
    private double convertTemperature(double val, String from, String to) {
        double inCelsius = 0.0;
        switch (from) {
            case "攝氏": inCelsius = val; break;
            case "華氏": inCelsius = (val - 32.0) * 5.0 / 9.0; break;
            case "克氏": inCelsius = val - 273.15; break;
        }

        switch (to) {
            case "攝氏": return inCelsius;
            case "華氏": return (inCelsius * 9.0 / 5.0) + 32.0;
            case "克氏": return inCelsius + 273.15;
            default: return inCelsius;
        }
    }

    public static void main(String[] args) {
        // 在 Event Dispatch Thread 中啟動 GUI
        SwingUtilities.invokeLater(() -> {
            new UnitConverter().setVisible(true);
        });
    }
}