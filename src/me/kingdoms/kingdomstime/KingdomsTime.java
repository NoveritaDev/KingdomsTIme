package me.kingdoms.kingdomstime;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class KingdomsTime extends JavaPlugin implements CommandExecutor {
    private static final String[] days = new String[]{
            "Monden", "Tunden", "Vinden", "Thulden", "Falden", "Salden"
    };
    private static final String[] months = new String[]{
            "Month 1", "Month 2", "Month 3", "Month 4", "Month 5", "Month 6", "Month 7", "Month 8", "Month 9", "Month 10", "Month 11", "Month 12", "Month 13", "Month 14", "Month 15"
    };
    private long day = 0;

    private int taskID;

    @Override
    public void onEnable() {
        for (World w: Bukkit.getServer().getWorlds()) {
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        }

        getCommand("settimespeed").setExecutor(this);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World w : Bukkit.getServer().getWorlds()) {
                int timeDelta = (w.getTime() % 24000 > 13000) ? 2 : 1;
                long newTime = w.getTime() + timeDelta;
                w.setTime(newTime);
                /*if (newTime % 24000 == 0) {
                    day = Math.floorDiv(newTime, 24000);
                    String month = months[(int) (Math.floorDiv(day, 24) % 15)];
                    String dayOfMonth = days[(int) (day % 6)];
                    /*for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getWorld().equals(w)) {
                            p.sendTitle("A new day dawns", String.format("%s the %d of %s", dayOfMonth, day % 24, month), 20, 20, 20);
                        }
                    }
                }*/
            }
        }, 6, 6);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Bukkit.getScheduler().cancelTask(taskID);

            int delay = Integer.parseInt(args[0]);

            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (World w : Bukkit.getServer().getWorlds()) {
                    long newTime = w.getTime() + 1;
                    w.setTime(newTime);
                    if (newTime % 24000 == 0) {
                        day = Math.floorDiv(newTime, 24000);
                        String month = months[(int) (Math.floorDiv(day, 24) % 15)];
                        String dayOfMonth = days[(int) (day % 6)];
                        /*for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().equals(w)) {
                                p.sendTitle("A new day dawns", String.format("%s the %d of %s", dayOfMonth, day % 24, month), 20, 20, 20);
                            }
                        }*/
                    }
                }
            }, delay, delay);
            sender.sendMessage("Started time with " + args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid arguments.");
        }

        return true;
    }

    @Override
    public void onDisable() {
    }
}
