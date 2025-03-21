package ru.yawlick.brownylib.common.content.menu;

import lombok.Getter;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.menu.IMenuModule;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.AbstractModule;

import java.util.ArrayList;

@Getter
public final class MenuModule extends AbstractModule implements IMenuModule {
    private final ArrayList<Menu> menuList = new ArrayList<>();

    public MenuModule() {
        load();
    }

    public Menu get(String id) {
        for (Menu menu : menuList) {
            if (menu != null && menu.getId().equals(id)) {
                return menu;
            }
        }
        return null;
    }

    public void registerMenu(Menu menu) {
        menuList.add(menu);
        BrownyLib.getInstance().getLogger().info(String.format("The '%s' menu has been successfully registered", menu.getId()));
    }

    @Override
    public void load() {}

    @Override
    public void unload() {}
}
