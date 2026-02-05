package pl.craftverse.shop;

import org.bukkit.plugin.java.JavaPlugin;

public class CraftVerseShop extends JavaPlugin {

    private static CraftVerseShop instance;
    private Database database;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        database = new Database(this);
        database.connect();

        new OrderTask(database).runTaskTimerAsynchronously(
                this,
                20L,
                getConfig().getInt("check-interval") * 20L
        );

        getLogger().info("CraftVerseShop uruchomiony!");
    }

    public static CraftVerseShop get() {
        return instance;
    }
}
