import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceSimulator extends JFrame {
    private JLabel infoLabel;    // 視窗上方統計資訊
    private JLabel diceLabel;    // 中央點數顯示
    private JButton rollButton;  // 下方擲骰子按鈕

    private int rollCount = 0;   // 擲骰次數 N
    private int totalSum = 0;    // 點數總和 M
    private final Random random = new Random();

    public DiceSimulator() {
        // 1. 視窗設定
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 關閉時結束程式
        setLocationRelativeTo(null);                    // 開啟時置中
        setLayout(new BorderLayout(10, 10));

        // 2. 上方統計資訊標籤
        infoLabel = new JLabel("已擲0次,總和0,平均0.00", SwingConstants.CENTER);
        infoLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(infoLabel, BorderLayout.NORTH);

        // 3. 中央點數標籤 (初始顯示 -，字體 60pt)
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        diceLabel.setForeground(Color.BLACK);
        add(diceLabel, BorderLayout.CENTER);

        // 4. 下方擲骰子按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("微軟正黑體", Font.BOLD, 18));
        rollButton.setFocusPainted(false);
        
        // 為了版面美觀，將按鈕置於一個帶邊距的 Panel 中
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        bottomPanel.add(rollButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // 5. 按鈕點擊事件監聽
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // 隨機產生 1 ~ 6 點數
        int point = random.nextInt(6) + 1;

        // 更新累計數據
        rollCount++;
        totalSum += point;
        double average = (double) totalSum / rollCount;

        // 更新中央點數顯示
        diceLabel.setText(String.valueOf(point));

        // 根據點數變更顏色：6 為綠色、1 為紅色、其餘黑色
        if (point == 6) {
            diceLabel.setForeground(new Color(34, 139, 34)); // 森林綠 (或 Color.GREEN)
        } else if (point == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        // 更新上方統計資訊
        infoLabel.setText(String.format("已擲%d次,總和%d,平均%.2f", rollCount, totalSum, average));
    }

    public static void main(String[] args) {
        // 在 Event Dispatch Thread 中建立與顯示 GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DiceSimulator().setVisible(true);
            }
        });
    }
}