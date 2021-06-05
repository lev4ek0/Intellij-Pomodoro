import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.util.concurrency.EdtExecutorService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PomodoroWidget extends JLabel implements CustomStatusBarWidget, Data {
    private static int coeff = 60;
    private static int workCoeff = 25;
    private int restCoeffLong = 20;
    private int restCoeffShort = 5;
    private int countRests = 0;
    private int counter = 0;
    private Types typeWork = Types.Work;
    private Types typePrevious;
    private int timer = workCoeff * coeff;
    private ScheduledFuture task1;
    private JBPopupMenu popup;
    private JFrame frame;
    private JLabel jLabelTimer;
    private final ArrayList<Button> buttonArrayList = new ArrayList<>();
    private final ArrayList<Component> componentArrayList = new ArrayList<>();
    private String msg = "No comment";

    public static int getCoeff() {
        return coeff;
    }

    public static int getWorkCoeff() {
        return workCoeff;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public @NotNull String ID() {
        return "Pomodoro";
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                if (ev.getButton() != 1) {
                    return;
                }
                frame = new JFrame("Pomodoro Timer");
                frame.setLayout(null);
                Button buttonStart = new Button("Start");
                Button buttonStop = new Button("Stop");
                Button buttonPause = new Button("Pause");
                Button buttonContinue = new Button("Continue");
                Button buttonSkip = new Button("Skip");
                Button buttonSettings = new Button("Settings");
                Button buttonReturn = new Button("Return");
                Button buttonStatistic = new Button("Statistic");
                jLabelTimer = new JLabel(toHoursMinutes(timer, Types.OutputMenu));
                buttonStart.setBounds(100,80,200,30);
                buttonStop.setBounds(100,80,200,30);
                buttonPause.setBounds(100,130,200,30);
                buttonContinue.setBounds(100,130,200,30);
                buttonSkip.setBounds(100,180,200,30);
                buttonSettings.setBounds(100,130,200,30);
                buttonReturn.setBounds(100,180,200,30);
                buttonStatistic.setBounds(100,180,200,30);
                jLabelTimer.setBounds(140, 50, 120, 10);
                if (buttonArrayList.isEmpty()) {
                    buttonArrayList.add(buttonStart);
                    buttonArrayList.add(buttonSettings);
                    buttonArrayList.add(buttonStatistic);
                    componentArrayList.add(jLabelTimer);
                    frame.add(buttonStart);
                    frame.add(buttonSettings);
                    frame.add(buttonStatistic);
                    frame.add(jLabelTimer);
                } else {
                    for (Button button : buttonArrayList) {
                        frame.add(button);
                    }
                    for (Component component : componentArrayList) {
                        if (component instanceof JLabel && !((JLabel) component).getText().contains("time"))
                            frame.add(jLabelTimer);
                        else
                            frame.add(component);
                    }
                }
                JTextField input = new JTextField(String.valueOf(workCoeff));
                JLabel jLabel = new JLabel("Work time");
                JTextField input1 = new JTextField(String.valueOf(restCoeffShort));
                JLabel jLabel1 = new JLabel("SRest time");
                JTextField input2 = new JTextField(String.valueOf(restCoeffLong));
                JLabel jLabel2 = new JLabel("LRest time");
                input.setBounds(115,60,100,30);
                input1.setBounds(115,100,100,30);
                input2.setBounds(115,140,100,30);
                jLabel.setBounds(225,60,170,30);
                jLabel1.setBounds(225,100,170,30);
                jLabel2.setBounds(225,140,170,30);
                frame.setSize(400,330);
                frame.setLocation(450,150);
                frame.setVisible(true);
                EdtExecutorService.getScheduledExecutorInstance().scheduleWithFixedDelay(this::updateTimer, 0, 1, TimeUnit.SECONDS);

                buttonSettings.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        frame.getContentPane().removeAll();
                        frame.getContentPane().repaint();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        buttonArrayList.add(buttonReturn);
                        frame.add(buttonReturn);
                        componentArrayList.add(input);
                        componentArrayList.add(input1);
                        componentArrayList.add(input2);
                        componentArrayList.add(jLabel);
                        componentArrayList.add(jLabel1);
                        componentArrayList.add(jLabel2);
                        frame.add(input);
                        frame.add(jLabel);
                        frame.add(input1);
                        frame.add(jLabel1);
                        frame.add(input2);
                        frame.add(jLabel2);
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

                buttonStatistic.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        PomodoroStatistic.setHashMap(PomodoroFileOutput.parseFile());
                        Messages.showMessageDialog(PomodoroStatistic.getHashMap(), "", Messages.getInformationIcon());
                        /*buttonArrayList.remove(buttonStart);
                        buttonArrayList.remove(buttonSettings);
                        buttonArrayList.remove(buttonContinue);
                        buttonArrayList.remove(buttonStop);
                        buttonArrayList.remove(buttonSkip);
                        buttonArrayList.remove(buttonPause);
                        buttonArrayList.remove(buttonStatistic);
                        frame.remove(buttonStart);
                        frame.remove(buttonSettings);
                        frame.remove(buttonContinue);
                        frame.remove(buttonStop);
                        frame.remove(buttonSkip);
                        frame.remove(buttonPause);
                        frame.remove(buttonStatistic);
                        jLabelTimer.setVisible(false);
                        buttonArrayList.add(buttonReturn);
                        frame.add(buttonReturn);*/
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

                buttonReturn.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        frame.getContentPane().removeAll();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        componentArrayList.add(jLabelTimer);
                        buttonArrayList.add(buttonStart);
                        buttonArrayList.add(buttonSettings);
                        buttonArrayList.add(buttonStatistic);
                        frame.add(jLabelTimer);
                        frame.add(buttonStart);
                        frame.add(buttonSettings);
                        frame.add(buttonStatistic);
                        workCoeff = Integer.parseInt(input.getText());
                        if (workCoeff > 60) {
                            workCoeff = 60;
                        } else if (workCoeff < 20) {
                            workCoeff = 20;
                        }
                        restCoeffShort = Integer.parseInt(input1.getText());
                        if (restCoeffShort > 20) {
                            restCoeffShort = 20;
                        } else if (restCoeffShort < 4) {
                            restCoeffShort = 4;
                        }
                        restCoeffLong = Integer.parseInt(input2.getText());
                        if (restCoeffLong > 40) {
                            restCoeffLong = 40;
                        } else if (restCoeffLong < 16) {
                            restCoeffLong = 16;
                        }
                        input.setText(String.valueOf(workCoeff));
                        input1.setText(String.valueOf(restCoeffShort));
                        input2.setText(String.valueOf(restCoeffLong));
                        if (typeWork == Types.Rest) {
                            counter = 0;
                            typeWork = Types.Work;
                        }
                        timer = workCoeff * coeff;
                        jLabelTimer.setVisible(true);
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

                buttonStart.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        frame.getContentPane().removeAll();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        componentArrayList.add(jLabelTimer);
                        buttonArrayList.add(buttonStop);
                        buttonArrayList.add(buttonPause);
                        buttonArrayList.add(buttonSkip);
                        frame.add(jLabelTimer);
                        frame.add(buttonStop);
                        frame.add(buttonPause);
                        frame.add(buttonSkip);
                        countRests = 0;
                        typeWork = Types.Work;
                        timer = workCoeff * coeff;
                        counter = 1;
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

                buttonStop.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        frame.getContentPane().removeAll();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        componentArrayList.add(jLabelTimer);
                        buttonArrayList.add(buttonStart);
                        buttonArrayList.add(buttonSettings);
                        buttonArrayList.add(buttonStatistic);
                        frame.add(jLabelTimer);
                        frame.add(buttonStart);
                        frame.add(buttonSettings);
                        frame.add(buttonStatistic);
                        typeWork = Types.Work;
                        timer = workCoeff * coeff;
                        counter = 0;
                        PomodoroFileInput.fileInputFinish();
                        PomodoroFileInput.fileInputComment("Aborted");
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

                buttonContinue.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        typeWork = typePrevious;
                        frame.getContentPane().removeAll();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        componentArrayList.add(jLabelTimer);
                        buttonArrayList.add(buttonStop);
                        buttonArrayList.add(buttonPause);
                        buttonArrayList.add(buttonSkip);
                        frame.add(jLabelTimer);
                        frame.add(buttonStop);
                        frame.add(buttonPause);
                        frame.add(buttonSkip);
                        counter = 1;
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

                buttonPause.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        typePrevious = typeWork;
                        typeWork = Types.Pause;
                        frame.getContentPane().removeAll();
                        buttonArrayList.clear();
                        componentArrayList.clear();
                        componentArrayList.add(jLabelTimer);
                        buttonArrayList.add(buttonStop);
                        buttonArrayList.add(buttonContinue);
                        buttonArrayList.add(buttonSkip);
                        frame.add(jLabelTimer);
                        frame.add(buttonStop);
                        frame.add(buttonContinue);
                        frame.add(buttonSkip);
                        counter = 0;
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

                buttonSkip.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        counter = 1;
                        for (var button: buttonArrayList) {
                            if (button.getLabel().equals(buttonContinue.getLabel())) {
                                buttonArrayList.remove(button);
                                buttonArrayList.add(buttonPause);
                            }
                        }
                        frame.getContentPane().removeAll();
                        frame.dispose();
                        timer = 0;
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

                frame.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {

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

            private void updateTimer() {
                jLabelTimer.setText(toHoursMinutes(timer, Types.OutputMenu));
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        this.task1 = EdtExecutorService.getScheduledExecutorInstance().scheduleWithFixedDelay(this::update1, 0, 1, TimeUnit.SECONDS);
    }

    String toHoursMinutes(int timer, Types type) {
        if (type == Types.OutputMenu)
            return "Mode " + typeWork + ": " + timer / 60 + "m: " + timer % 60 + "s";
        return type + ": " +timer / 60 + "m: " + timer % 60 + "s";
    }

    void showMsg(Types type, int time) {
        String msg = String.format("Your %s time is %d minutes left", type, time);
        Messages.showMessageDialog(msg, "Pomodoro Timer", Messages.getInformationIcon());
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    void update1() {
        this.setText(String.format("%s", toHoursMinutes(timer, typeWork)));
        if (counter != 0 && timer == workCoeff * coeff && typeWork == Types.Work) {
            PomodoroFileInput.fileInputStart();
        }
        timer -= counter;
        if (timer < 0) {
            if (typeWork == Types.Pause) {
                typeWork = typePrevious;
            }
            if (typeWork == Types.Work) {
                typeWork = Types.Rest;
                PomodoroFileInput.fileInputFinish();
            }
            else if (typeWork == Types.Rest) {
                typeWork = Types.Work;
                PomodoroFileInput.fileInputComment(msg);
                msg = "No Comment";
            }
            switch (typeWork) {
                case Work:
                    timer = workCoeff * coeff;
                    showMsg(typeWork, workCoeff);
                    break;
                case Rest:
                    countRests++;
                    if (countRests == 4) {
                        timer = restCoeffLong * coeff;
                        showMsg(typeWork, restCoeffLong);
                        countRests = 0;
                    } else {
                        timer = restCoeffShort * coeff;
                        showMsg(typeWork, restCoeffShort);
                    }
            }
        }
    }

    @Override
    public void dispose() {
        if (this.task1 != null) {
            this.task1.cancel(false);
            this.task1 = null;
        }
    }
}