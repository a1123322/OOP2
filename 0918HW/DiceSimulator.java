import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceSimulator extends JFrame {
    // 統計數據變數
    private int count = 0;       // 擲骰次數 N
    private int totalSum = 0;    // 點數總和 M
    private final Random random = new Random();

    // 介面元件
    private JLabel statusLabel;  // 上方統計文字
    private JLabel diceLabel;    // 中央點數文字
    private JButton rollButton;  // 下方按鈕

    public DiceSimulator() {
        // 1. 設定視窗基本屬性
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時置中
        setLayout(new BorderLayout());

        // 2. 上方：顯示統計資訊 (已擲 N 次，總和 M，平均 X.XX)
        statusLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);

        // 3. 中央：顯示目前點數 (字體 60pt)
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        diceLabel.setForeground(Color.BLACK);
        add(diceLabel, BorderLayout.CENTER);

        // 4. 下方：擲骰子按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        // 建立包裝面板使按鈕外觀與留白更協調
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(rollButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // 5. 按鈕事件監聽器
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    // 擲骰子核心邏輯
    private void rollDice() {
        // 隨機產生 1 ~ 6 的點數
        int point = random.nextInt(6) + 1;

        // 更新統計數據
        count++;
        totalSum += point;
        double average = (double) totalSum / count;

        // 更新中央點數文字
        diceLabel.setText(String.valueOf(point));

        // 判斷顏色：點數 6 為綠色、1 為紅色、其餘黑色
        if (point == 6) {
            diceLabel.setForeground(Color.GREEN);
        } else if (point == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        // 更新上方統計文字 (平均格式化至小數點後兩位)
        statusLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", count, totalSum, average));
    }

    public static void main(String[] args) {
        // 在 Event Dispatch Thread (EDT) 中啟動 Swing GUI
        SwingUtilities.invokeLater(() -> {
            new DiceSimulator().setVisible(true);
        });
    }
}