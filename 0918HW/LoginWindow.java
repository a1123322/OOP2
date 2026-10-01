import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    // 介面元件
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;

    public LoginWindow() {
        // 1. 設定視窗基本屬性
        setTitle("系統登入");
        setSize(380, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 視窗置中
        setResizable(false);         // 固定視窗大小

        // 2. 建立主要內容面板，使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // 標題標籤 (上方)
        JLabel titleLabel = new JLabel("使用者登入", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 3. 輸入欄位面板 (中央)，使用 GridBagLayout 讓對齊更工整
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // 元件間距
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 帳號標籤與輸入框
        JLabel userLabel = new JLabel("帳號：");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        formPanel.add(usernameField, gbc);

        // 密碼標籤與輸入框 (JPasswordField)
        JLabel passLabel = new JLabel("密碼：");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 4. 按鈕面板 (下方)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        loginButton = new JButton("登入");
        resetButton = new JButton("清除");

        loginButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 將主面板加入視窗
        add(mainPanel);

        // 預設按下 Enter 鍵觸發登入按鈕
        getRootPane().setDefaultButton(loginButton);

        // 5. 事件監聽處理
        // 登入按鈕事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // 清除按鈕事件
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                usernameField.requestFocus(); // 游標重回帳號欄位
            }
        });
    }

    // 處理登入驗證邏輯
    private void handleLogin() {
        String username = usernameField.getText().trim();
        // JPasswordField 推薦使用 getPassword() 取得 char[] 以提升安全性
        String password = new String(passwordField.getPassword());

        // 檢查是否有欄位留空
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "帳號或密碼不能為空！",
                "輸入錯誤",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 模擬驗證帳密（假設帳號為 admin，密碼為 123456）
        if ("admin".equals(username) && "123456".equals(password)) {
            JOptionPane.showMessageDialog(
                this,
                "登入成功！歡迎回來，" + username + "！",
                "成功",
                JOptionPane.INFORMATION_MESSAGE
            );
            // 登入成功後的後續動作（例如關閉登入視窗並開啟主畫面）
            // this.dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "帳號或密碼錯誤，請重新輸入！",
                "登入失敗",
                JOptionPane.ERROR_MESSAGE
            );
            passwordField.setText(""); // 清空密碼欄位
            passwordField.requestFocus();
        }
    }

    public static void main(String[] args) {
        // 在 Event Dispatch Thread (EDT) 中啟動視窗
        SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}