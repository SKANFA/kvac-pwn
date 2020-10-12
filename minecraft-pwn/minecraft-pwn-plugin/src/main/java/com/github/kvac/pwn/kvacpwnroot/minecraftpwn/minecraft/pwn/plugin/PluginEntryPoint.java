package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact.ArtefactType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class PluginEntryPoint extends JavaPlugin implements Listener {

    // WEAPON
    static char wws = ' ';

    private static final String WEAPON_lore0 = Character.toString(wws);

    ArtefactInventTask artefactInventTask;
    PluginEntryPoint plugin = this;

    public static ArrayList<String> domains = new ArrayList<String>();

    private ProtocolManager protocolManager;

    boolean fakeOnline = false;

    public static File pingFile = new File("PING_LOG");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }

    boolean random = true;
    int fake = 140;
    int min = 40;
    int max = 20000;

    public static void main(String[] args) {

    }

    @SuppressWarnings("deprecation") // OUT_SERVER_INFO
    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener) new PacketAdapter(PacketAdapter
                .params((Plugin) this, new PacketType[]{PacketType.Status.Server.OUT_SERVER_INFO}).optionAsync()) {
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getServerPings().read(0) instanceof WrappedServerPing) {
                    try {
                        if (!pingFile.exists()) {
                            pingFile.createNewFile();
                        }
                        Files.write(pingFile.toPath(), (event.getPlayer().getAddress().toString() + '\n').getBytes(),
                                StandardOpenOption.APPEND);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (fakeOnline) {
                        WrappedServerPing ping = (WrappedServerPing) event.getPacket().getServerPings().read(0);
                        ping.setPlayersMaximum(max);
                        if (random) {
                            Random r = new Random();
                            int online = Math.abs(r.nextInt() % (max - min) + 1 + min);
                            ping.setPlayersOnline(online);
                        } else {
                            ping.setPlayersOnline(fake);
                        }
                    }
                }
            }
        });

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                domains.add(i + ":" + j);
            }
        }
        domains.add(".com");

        System.out.println("JDCSPlugin.onEnable()");
        checkAndInitBackInventory();
        this.getCommand("artefact").setExecutor(new CommandAddART());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onLoad() {
        stopBackInventory();
        System.out.println("JDCSPlugin.onLoad()");
    }

    @Override
    public void onDisable() {
        stopBackInventory();
        System.out.println("JDCSPlugin.onDisable()");
    }

    @EventHandler
    public void antiSpam(AsyncPlayerChatEvent event) {
        if (SpamUtils.spamContains(event.getMessage())) {
            event.getPlayer().sendMessage("NAX");
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void damageFall(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.FALL)) {
            Entity entity = event.getEntity();
            double damage = event.getDamage();

            if (entity instanceof Villager) {
                event.setCancelled(true);
                return;
            }
            if (entity instanceof Player) {
                Player player = (Player) entity;

                PlayerInventory inventory = player.getInventory();

                ItemStack[] storage = inventory.getStorageContents();
                for (int i = 0; i < storage.length; i++) {
                    ItemStack itemStack = storage[i];
                    if (itemStack != null) {
                        if (itemStack.getType().equals(Material.PHANTOM_MEMBRANE)) {
                            ArtefactType typeART = Artefact.recognizeArtedact(itemStack);
                            if (typeART != null) {
                                int count = itemStack.getAmount();
                                if (typeART.equals(ArtefactType.PILLOW)) {
                                    double defaultHEAL = 0.1119;
                                    if (damage > 19.3) {
                                        event.setDamage(0);
                                        double addHeal = ((double) count * 0.5)
                                                + ((double) count * 0.5) / 100 * defaultHEAL;
                                        player.sendMessage("" + addHeal);
                                        if (addHeal >= 20) {
                                            player.setHealth(20);
                                        } else if (addHeal <= 20) {
                                            player.setHealth(addHeal);
                                        }
                                    } else {
                                        event.setDamage(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkAndInitBackInventory() {
        stopBackInventory();
        startBackInventory();
    }

    @EventHandler
    public void weaponDamageHandler(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        double damage = event.getDamage();

        if (event.getEntity() instanceof Villager) {
            if (damager instanceof LivingEntity) {
                LivingEntity damagerL = (LivingEntity) damager;
                double health = damagerL.getHealth();
                if (health - damage <= 0) {
                    damagerL.setHealth(0);
                } else if (health - damage > 0) {
                    damagerL.setHealth(health - damage);
                }
            } else if (damager instanceof Arrow) {
                Arrow arrow = (Arrow) damager;
                ProjectileSource src = arrow.getShooter();// кто выстрелил
                if (src instanceof LivingEntity) {
                    LivingEntity damagerL = (LivingEntity) src;
                    double health = damagerL.getHealth();
                    if (health - damage <= 0) {
                        damagerL.setHealth(0);
                    } else if (health - damage > 0) {
                        damagerL.setHealth(health - damage);
                    }
                }

            }
            event.setCancelled(true);
            return;
        }

        if (damager instanceof Arrow) {
            Arrow arrow = (Arrow) damager;

            // getServer().broadcastMessage("damager:Arrow:" + arrow.getName());
            // getServer().broadcastMessage("Damage:" + event.getDamage());
            ProjectileSource src = arrow.getShooter();// кто выстрелил
            if (src instanceof Player) {
                // getServer().broadcastMessage("src:Player:" + ((Player) src).getName());
            }

            if (arrow.getName().equals(Material.WOODEN_SWORD.toString())) {
                event.setDamage(1);
            } else if (arrow.getName().equals(Material.STONE_SWORD.toString())) {
                event.setDamage(2);
            } else if (arrow.getName().equals(Material.IRON_SWORD.toString())) {
                event.setDamage(3);
            } else if (arrow.getName().equals(Material.GOLDEN_SWORD.toString())) {
                if (event.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) event.getEntity();
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20 * 10, 3);
                    entity.addPotionEffect(effect);
                    event.setDamage(8);
                }
            } else if (arrow.getName().equals(Material.DIAMOND_SWORD.toString())) {
                event.setDamage(20000);
            }
        }
    }

    @EventHandler
    public void ArrowLaunch(PlayerToggleSneakEvent event) {
        /*
                 * if (event.getPlayer().isGliding()) { Projectile projectile =
                 * event.getPlayer().launchProjectile(Arrow.class);
                 * projectile.setVelocity(event.getPlayer().getLocation().getDirection().
                 * multiply(100)); }
         */

        // ITEMSTACK
        ItemStack itemstack = event.getPlayer().getInventory().getItemInMainHand();
        // MATERIAL
        Material itemInMainHand = itemstack.getType();

        // ДАЛЬШЕ БЛОК ОРУЖИЯ
        //
        if (itemstack.hasItemMeta()) {
            ItemMeta meta = itemstack.getItemMeta();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                if (lore.size() > 0) {
                    String lore0 = lore.get(0);
                    // есть лор оружия
                    if (lore0.equals(WEAPON_lore0)) {
                        if (itemInMainHand.equals(Material.WOODEN_SWORD)) {
                            Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
                            Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);

                            vel.setX(vel.getX() + randDouble(-10, 10));
                            vel.setY(vel.getY() + randDouble(-10, 10));
                            vel.setZ(vel.getZ() + randDouble(-10, 10));
                            projectile2.setVelocity(vel);
                            projectile2.setTicksLived(20 * 3);
                            projectile2.setCustomName(itemInMainHand.toString());
                            // projectile2.setCustomNameVisible(true);
                        } else if (itemInMainHand.equals(Material.STONE_SWORD)) {
                            for (int i = 1; i <= 3; i++) {
                                Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
                                Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
                                vel.setX(vel.getX() + randDouble(-10, 10));
                                vel.setY(vel.getY() + randDouble(-10, 10));
                                vel.setZ(vel.getZ() + randDouble(-10, 10));

                                projectile2.setCustomName(itemInMainHand.toString());
                                // projectile2.setCustomNameVisible(true);
                                projectile2.setVelocity(vel);
                                projectile2.setTicksLived(20 * 3);

                            }

                        } else if (itemInMainHand.equals(Material.IRON_SWORD)) {
                            for (int i = 1; i <= 4; i++) {
                                Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
                                Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
                                vel.setX(vel.getX() + randDouble(-10, 10));
                                vel.setY(vel.getY() + randDouble(-10, 10));
                                vel.setZ(vel.getZ() + randDouble(-10, 10));

                                projectile2.setCustomName(itemInMainHand.toString());
                                // projectile2.setCustomNameVisible(true);
                                projectile2.setVelocity(vel);
                                projectile2.setTicksLived(20 * 3);

                            }
                        } else if (itemInMainHand.equals(Material.GOLDEN_SWORD)) {

                            for (int i = 1; i <= 2; i++) {
                                Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
                                Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
                                vel.setX(vel.getX() + randDouble(-10, 10));
                                vel.setY(vel.getY() + randDouble(-10, 10));
                                vel.setZ(vel.getZ() + randDouble(-10, 10));

                                projectile2.setCustomName(itemInMainHand.toString());
                                // projectile2.setCustomNameVisible(true);
                                projectile2.setVelocity(vel);
                                projectile2.setTicksLived(20 * 3);

                            }
                        } else if (itemInMainHand.equals(Material.DIAMOND_SWORD)) {
                            for (int i = 0; i <= 50; i++) {
                                Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
                                Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
                                vel.setX(vel.getX() + randDouble(-10, 10));
                                vel.setY(vel.getY() + randDouble(-10, 10));
                                vel.setZ(vel.getZ() + randDouble(-10, 10));

                                projectile2.setCustomName(itemInMainHand.toString());
                                // projectile2.setCustomNameVisible(true);
                                projectile2.setVelocity(vel);
                                projectile2.setTicksLived(20 * 3);
                            }
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public static void pkm(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action action = event.getAction();
        // Block clickedBlock = event.getClickedBlock();

        ItemStack itemInmainHand = player.getInventory().getItemInMainHand();
        Material material = itemInmainHand.getType();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (material.equals(Material.NETHER_STAR)) {
                if (itemInmainHand.hasItemMeta()) {
                    ItemMeta meta = itemInmainHand.getItemMeta();
                    List<String> lore = meta.getLore();

                    if (lore != null && lore.size() > 0) {
                        boolean fireActivator = false;
                        for (String string : lore) {
                            if (string.equals(WEAPON_lore0)) {
                                fireActivator = true;
                            }
                        }
                        if (fireActivator) {
                            ArrayList<LivingEntity> LivingEntityList = new ArrayList<LivingEntity>();
                            List<Entity> entitySList = player.getNearbyEntities(30, 30, 30);
                            for (Entity entity : entitySList) {
                                try {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntityList.add((LivingEntity) entity);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            int fireCount = 0;
                            int expCount = 0;
                            for (LivingEntity liv : LivingEntityList) {
                                EntityType type = liv.getType();
                                if (type.equals(EntityType.VILLAGER)) {
                                    continue;
                                }
                                Block flocBlock = liv.getLocation().getBlock();
                                Block flocEyesBlock = liv.getEyeLocation().getBlock();

                                if (!(flocBlock.getType().equals(Material.WATER)
                                        || flocEyesBlock.getType().equals(Material.WATER))) {

                                    if (liv.getFireTicks() == -1) {
                                        liv.setFireTicks(20 * 10);
                                        fireCount = fireCount + 1;
                                        expCount = expCount + 1;
                                    }
                                }
                            }
                            player.giveExp(Integer.parseInt("-" + expCount));

                            if (expCount > 0) {
                                player.sendMessage("Потрачено опыта:" + expCount);
                            }
                            if (fireCount > 0) {
                                player.sendMessage("Подожено:" + fireCount);
                            }
                        }
                    }
                }
            }
        }
    }

    private static double randDouble(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }

    private void stopBackInventory() {
        if (artefactInventTask != null) {
            artefactInventTask.cancel();
        }
    }

    private void startBackInventory() {
        artefactInventTask = new ArtefactInventTask(plugin);
        artefactInventTask.runTaskAsynchronously(this);
    }

}
