public class Example {
      public static void main(String[] args) {
        JFrame frame = new JFrame("Bezier Curve Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var firstSize = 400;
        frame.setSize(firstSize, 200);
        frame.setLocation(300,250);
        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            var b = new SmoothAnimation(1000,500);
            var t = new Thread(() -> {
                long startTime=0;
                try {
                    Thread.sleep(2000);
                    startTime = System.currentTimeMillis();
                    while (true) {
                        var val = (int)b.getNextBezierValue();
                        frame.setSize(firstSize+val, frame.getHeight());
                    }
                } catch (InterruptedException e) {
                    System.out.println("애니메이션 종료");
                    long endTime = System.currentTimeMillis();
                    System.out.println("걸린 시간 = " + (endTime-startTime));
                }
            });
            t.start();
        });
    }
}
