package pl.craftverse.shop;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class OrderTask extends BukkitRunnable {

    private final Database db;

    public OrderTask(Database db) {
        this.db = db;
    }

    @Override
    public void run() {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT o.id, o.nickname, s.name, s.command " +
                "FROM orders o " +
                "JOIN shop_items s ON s.id=o.item_id " +
                "WHERE o.status='paid'"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("id");
                String nick = rs.getString("nickname");
                String command = rs.getString("command");

                Bukkit.getScheduler().runTask(
                    CraftVerseShop.get(),
                    () -> Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        command.replace("{player}", nick)
                    )
                );

                PreparedStatement upd = db.getConnection().prepareStatement(
                    "UPDATE orders SET status='done' WHERE id=?"
                );
                upd.setInt(1, orderId);
                upd.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
