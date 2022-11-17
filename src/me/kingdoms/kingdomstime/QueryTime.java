package me.kingdoms.kingdomstime;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QueryTime implements CommandExecutor, Listener, TabCompleter {
    private static final int daysPerMonth = 27;
    private Inventory calendar;

    private QueryTime() {
        calendar = Bukkit.createInventory(null, 36);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length == 0) {
            Player player = (Player) commandSender;

            ItemStack[] days = new ItemStack[36];
            ItemStack positive = new ItemStack(Material.GREEN_CONCRETE, 1);
            ItemStack negative = new ItemStack(Material.RED_CONCRETE, 1);

            long dayNumber = 13;

            int pastDays = (int) (dayNumber % 27);

            for (int i = 9; i < pastDays; ++i) {
                days[i] = negative.clone();
            }
            for (int i = pastDays; i < daysPerMonth; ++i) {
                days[i] = positive.clone();
            }

            calendar.setContents(days);

            player.openInventory(calendar);

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
