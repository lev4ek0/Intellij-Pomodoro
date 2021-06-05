import com.intellij.openapi.ui.Messages;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;

public class PomodoroFileInput {
    static public void fileInputStart() {
        try(FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Pomodoro.txt", true))
        {
            int seconds = OffsetTime.now().getSecond();
            LocalDateTime localDate = LocalDate.now().atTime(OffsetTime.now().getHour(), OffsetTime.now().getMinute(), seconds);
            writer.write("Start: " + localDate);
            if (seconds == 0)
                writer.write(":00");
        }
        catch(IOException ex){
            Messages.showMessageDialog("FileIOException", "Pomodoro Timer", Messages.getWarningIcon());
        }
    }

    static public void fileInputFinish() {
        try(FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Pomodoro.txt", true))
        {
            int seconds = OffsetTime.now().getSecond();
            LocalDateTime localDate = LocalDate.now().atTime(OffsetTime.now().getHour(), OffsetTime.now().getMinute(), seconds);
            writer.write(" Finish: " + localDate);
            if (seconds == 0)
                writer.write(":00");
            writer.write("\n");
        }
        catch(IOException ex){
            Messages.showMessageDialog("FileIOException", "Pomodoro Timer", Messages.getWarningIcon());
        }
    }

    static public void fileInputComment(String msg) {
        try(FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Pomodoro.txt", true))
        {
            writer.write(msg + "\n");
        }
        catch(IOException ex){
            Messages.showMessageDialog("FileIOException", "Pomodoro Timer", Messages.getWarningIcon());
        }
    }

    public static void checkAndAddAborted() {
        File file = new File(System.getProperty("user.home") + "/Pomodoro.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Pomodoro.txt"))) {
                String s;
                while ((s = br.readLine()) != null) {
                    if (s.length() == 26 && s.contains("Start:") && !s.contains("Finish:")) {
                        fileInputFinish();
                        fileInputComment("Aborted");
                    }
                }
            } catch (IOException ex) {
                Messages.showMessageDialog("FileIOException", "Pomodoro Timer", Messages.getWarningIcon());
            }
        }
    }
}
