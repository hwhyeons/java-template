/**
일부 Java Swing에서, 마우스 더블 클릭시에 싱글 클릭 액션이 같이 발동 되는 경우 처리 방법
*/
class CustomMouseActionListener extends MouseAdapter {
    private Timer clickTimer; // java.util의 타이머가 아님

    public CustomMouseActionListener() {
        clickTimer = new Timer(250, e -> {
            // Perform single-click action here
            System.out.println("Single-click detected");
        });
        clickTimer.setRepeats(false);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON3_MASK && e.getClickCount() == 2) {
            // Cancel the single-click action timer
            clickTimer.stop();

            // Perform double-click action here
            System.out.println("Double-click detected");
        } else if (e.getModifiers() == InputEvent.BUTTON3_MASK && e.getClickCount() == 1) {
            // Start or restart the timer for single-click action
            if (!clickTimer.isRunning()) {
                clickTimer.restart();
            }
        }
    }
}
