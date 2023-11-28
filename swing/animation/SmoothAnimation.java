import javax.swing.*;

public class SmoothAnimation {
    /**
     *
     * @param timeMillis 지속 시간
     * @param moveDist 이동 거리
     */
    private long timeMillis;
    private int moveDist;
    private double t = 0;
    private double p0 = 0;

    // 아래 p1, p2를 조정하여 곡선의 모양을 조절
    private double p1 = 0.02; // Control point 1
    private double p2 = 0.98; // Control point 2
    private double p3 = 1; // Ending value (velocity = 0, time = 1)

    private static final long DEFAULT_SLEEP_TIME = 6;

    private final double REPAINT_INTERVAL;
    
    
    private static int moveCnt = 0; // debug

    /**
     *
     * @param timeMillis 애니메이션 지속 시간 (설정 지속시간보다 실제로는 더 짧게 걸림)
     * @param moveDist
     */
    SmoothAnimation(long timeMillis, int moveDist) {
        this.timeMillis = timeMillis;
        this.moveDist = moveDist;
        // 1초에 120번 이상 갱신되는 것이 목표
        this.REPAINT_INTERVAL = (double)1000/12000/((double) this.timeMillis /1000)/(DEFAULT_SLEEP_TIME+3);
        System.out.println("REPAINT_INTERVAL = " + REPAINT_INTERVAL);
    }


    private double calculateBezier(double t, double p0, double p1, double p2, double p3) {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttt = tt * t;
        double vg = (uuu * p0) + (3 * uu * t * p1) + (3 * u * tt * p2) + (ttt * p3);
        return vg;
    }

    public double getNextBezierValue() throws InterruptedException{
        double vg = calculateBezier(t, p0, p1, p2, p3);
        t += this.REPAINT_INTERVAL;
        if (t >=1) {
            System.out.println("전체 시간 동안 화면 갱신 횟수 = " + moveCnt);
            throw new InterruptedException("애니메이션 종료");
        }
        Thread.sleep(DEFAULT_SLEEP_TIME);
        // System.out.println("move = " + vg*this.moveDist);
        moveCnt+=1;
        return this.moveDist*vg;
    }
}
