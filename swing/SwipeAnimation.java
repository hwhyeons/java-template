
import javax.swing.*;
import java.awt.*;


/**
스와이프 등 애니메이션 효과에 사용할 수 있는 함수 + 예시
*/
public class SwipeAnimation extends JPanel {

    // 화면 갱신 주기 : 너무 짧으면 너무 빠르게 끝나고 너무 길면 부자연스럽게 이어짐
    // 하지만 5ms라고해서 정확히 5ms 쉬는게 아니므로 적당히 여유롭게
    // 실제 repaint 횟수 자체는 REPAINT_INTERVAL로 조정 -> 이 sleep 시간은 repaint자체의 호출 간격
    // REPAINT_SLEEP과 REPAINT_INTERVAL을 둘다 적당히 조절해서 애니메이션 최종 시간과 프레임 조절
    static final int REPAINT_SLEEP = 5; // ms

    // 0.0에서 1.0까지 도달할 때 간격 -> 1 / 0.008 = 125 프레임
    static final double REPAINT_INTERVAL = 0.008;

    private double t = 0.0; // Time parameter 't' for animation
    private Timer timer;
    private double p0 = 0; // Starting value (velocity = 0, time = 0)
    private double p1 = 0.8; // Control point 1
    private double p2 = 0.9; // Control point 2
    private double p3 = 1; // Ending value (velocity = 0, time = 1)

    private long beforeTime = 0;

    private Thread drawThread;

    public SwipeAnimation()  {
        // new thread
        var thread = new Thread(() -> {
            try {
                beforeTime = System.currentTimeMillis();
                // Timer대신 반복문 + Thread.sleep() 사용
                // 타이머 클래스 이용시 delay를 짧게 설정해도 평균 16ms정도의 딜레이를 무조건 가지게됨 -> 1000ms(1초)에 16ms -> 60프레임정도 밖에 안나옴
                //    -> sleep()으로 간격 직접 조정
                while (true) {
                    updateScreen();
                    Thread.sleep(REPAINT_SLEEP);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.drawThread = thread;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x = (int) (getWidth() * calculateBezier(t, p0, p1, p2, p3)); // Calculate x position based on Bezier curve
        int y = getHeight() / 2; // Set y position at the center of the panel

        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y, 50, 50); // Draw a rectangle at the calculated x position

        Toolkit.getDefaultToolkit().sync(); // Synchronize the display on some systems
    }

    private double calculateBezier(double t, double p0, double p1, double p2, double p3) {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttt = tt * t;

        // Formula for cubic Bezier curve
        double vg = (uuu * p0) + (3 * uu * t * p1) + (3 * u * tt * p2) + (ttt * p3);

        return vg;
    }

    public void updateScreen() {
        // Increment time parameter 't' for animation (value between 0 and 1)
        long curTime = System.currentTimeMillis();
        System.out.println(curTime-beforeTime);
        beforeTime = curTime;
        t += REPAINT_INTERVAL; // Change this value to adjust the speed of the animation

        if (t > 1.0) {
            t = 0.0; // Reset time parameter when it reaches 1.0
            System.exit(0);
            return;
        }
        repaint(); // Request a repaint to update the animation
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bezier Curve Animation");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SwipeAnimation animation = new SwipeAnimation();
            frame.add(animation);
            frame.setVisible(true);
            try {
                // 처음 화면 켜지고 1초 뒤부터 그리기 시작
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            animation.drawThread.start();
        });
    }
}
