import com.intellij.ide.lightEdit.LightEditCompatible;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class PomodoroWidgetFactory implements StatusBarWidgetFactory, LightEditCompatible {
    static ArrayList<PomodoroWidget> activeWidgets = new ArrayList<>();

    public static ArrayList<PomodoroWidget> getActiveWidgets() {
        return activeWidgets;
    }

    @Override
    public @NotNull String getId() {
        return "Pomodoro";
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return "pomodoro";
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        PomodoroFileInput.checkAndAddAborted();
        var w = new PomodoroWidget();
        activeWidgets.add(w);
        return w;
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget statusBarWidget) {
        if (statusBarWidget instanceof PomodoroWidget)
            activeWidgets.remove(statusBarWidget);
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }

}