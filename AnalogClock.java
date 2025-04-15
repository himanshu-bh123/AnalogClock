import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class AnalogClock extends JPanel {
    private int hour, minute, second;

    public AnalogClock() {
        Timer timer = new Timer(1000, e -> {
            updateClock();
            repaint(); // Repaint the clock every second
        });
        timer.start(); // Start to update the time every sec
    }

    private void updateClock() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //graphics
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //center of the clock
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 10;

        //face (circle) of the clock
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        //clock marks (12 hours)
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30); // 30 degrees btwn each hr
            int x1 = (int) (centerX + Math.cos(angle) * (radius - 15));
            int y1 = (int) (centerY + Math.sin(angle) * (radius - 15));
            int x2 = (int) (centerX + Math.cos(angle) * radius);
            int y2 = (int) (centerY + Math.sin(angle) * radius);
            g2d.drawLine(x1, y1, x2, y2);
        }

        // hands of the clock(hr,min,sec)
        drawHand(g2d, (hour % 12) * 30 + (minute / 2), radius - 40, 8); 
        drawHand(g2d, minute * 6, radius - 20, 4); 
        drawHand(g2d, second * 6, radius - 10, 2); 

        //center dot
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
    }

    private void drawHand(Graphics2D g2d, int angle, int length, int width) {
        // Convert the angle to radians
        double radians = Math.toRadians(angle - 90); // Rotate so that 0 degree is point to 3 o'cl0ck
        int xEnd = (int) (getWidth() / 2 + Math.cos(radians) * length);
        int yEnd = (int) (getHeight() / 2 + Math.sin(radians) * length);

        //hand of the clock
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(width));
        g2d.drawLine(getWidth() / 2, getHeight() / 2, xEnd, yEnd);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Clock");
        AnalogClock clock = new AnalogClock();
        frame.add(clock);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
