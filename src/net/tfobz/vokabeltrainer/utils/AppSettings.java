package net.tfobz.vokabeltrainer.utils;

import net.tfobz.vokabeltrainer.model.Lernkartei;

public class AppSettings {
    private static boolean darkMode = false;
    public static String[] languages = {"Deutsch", "Englisch",  "Franzoesisch", "Italienisch"};
    private static Lernkartei lernkartei = null;

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void setDarkMode(boolean darkMode) {
        AppSettings.darkMode = darkMode;
    }

    public static int getLanguageIndex(String language) {
        for (int i = 0; i < languages.length; i++) 
            if (languages[i].equals(language))
                return i;
        return -1;
    }

    public static Lernkartei getLernkartei() {
        return AppSettings.lernkartei;
    }

    public static void setLernkartei(Lernkartei lernkartei) {
        AppSettings.lernkartei = lernkartei;
    }
}
