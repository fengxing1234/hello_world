package cn.projects.com.projectsdemo.actionbar;

/**
 * Created by fengxing on 17/3/21.
 */

public class MenuItem {

    public MenuItem(String text, boolean isSelected, int icon, int iconSelected) {
        this.text = text;
        this.isSelected = isSelected;
        this.icon = icon;
        this.iconSelected = iconSelected;
    }

    boolean isSelected;
    String text;
    int icon;
    int iconSelected;
}
