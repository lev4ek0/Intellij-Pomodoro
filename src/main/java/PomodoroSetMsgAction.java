import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PomodoroSetMsgAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JFrame frame = new JFrame("Pomodoro Timer");
        frame.setLayout(null);
        JTextField input = new JTextField();
        frame.add(input);
        input.setBounds(100,0,685,30);
        Button button = new Button("OK");
        button.setBounds(785,0,35,30);
        frame.add(button);
        JLabel jLabel = new JLabel("Comment tomato:");
        jLabel.setBounds(0,0,100,30);
        frame.add(jLabel);
        frame.setSize(835,65);
        frame.setLocation(200,300);
        frame.setVisible(true);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int minValue = Math.min(100, input.getText().length());
                PomodoroWidgetFactory.getActiveWidgets().get(0).setMsg(input.getText().substring(0, minValue));
                frame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }
}
