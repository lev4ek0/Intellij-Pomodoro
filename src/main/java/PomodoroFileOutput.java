import com.intellij.openapi.ui.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class PomodoroFileOutput {

    public static HashMap<Integer, String[]> parseFile() {
        HashMap<Integer, String[]> data = new HashMap<>();
        int counterOK = 0;
        File file = new File(System.getProperty("user.home") + "/Pomodoro.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Pomodoro.txt"))) {
                String s;
                String timeStart = null;
                String timeFinish = null;
                String comment;
                while ((s = br.readLine()) != null) {
                    String[] tmp = new String[3];
                    tmp[0] = timeStart;
                    tmp[1] = timeFinish;
                    if (s.length() == 54 && s.contains("Start:") && s.contains("Finish:")) {
                        timeFinish = s;
                        timeFinish = timeFinish.substring(35, 54);
                        timeStart = s;
                        timeStart = timeStart.substring(7, 26);
                    }
                    if (!s.equals("") && !s.contains("Finish: 20") && !s.contains("Start: 20")) {
                        comment = s;
                        tmp[2] = comment;
                        assert tmp[1] != null;
                        LocalDateTime localDateFinish = LocalDateTime.parse(tmp[1]);
                        LocalDateTime localDateStart = LocalDateTime.parse(tmp[0]);
                        ZonedDateTime zonedDateTimeFinish = localDateFinish.atZone(ZoneId.of("Europe/Moscow"));
                        ZonedDateTime zonedDateTimeStart = localDateStart.atZone(ZoneId.of("Europe/Moscow"));
                        if (!comment.equals("Aborted") && (zonedDateTimeFinish.toInstant().toEpochMilli() / 1000 - zonedDateTimeStart.toInstant().toEpochMilli() / 1000) > ((long) PomodoroWidget.getCoeff() * PomodoroWidget.getWorkCoeff()) - 30) {
                            data.put(counterOK++, tmp);
                            PomodoroStatistic.counterInc();
                        }
                    }
                }
            } catch (IOException ex) {
                Messages.showMessageDialog("FileIOException", "Pomodoro Timer", Messages.getWarningIcon());
            }
            return data;
        }
        return null;
    }
}