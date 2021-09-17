// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.Map;
import net.minecraft.entity.Entity;
import me.alpha432.oyvey.OyVey;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.alpha432.oyvey.features.Feature;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import me.alpha432.oyvey.util.InventoryUtil;
import java.util.Queue;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class AutoArmor extends Module
{
    private final Setting<Integer> delay;
    private final Setting<Boolean> curse;
    private final Setting<Boolean> mendingTakeOff;
    private final Setting<Integer> closestEnemy;
    private final Setting<Integer> repair;
    private final Setting<Integer> actions;
    private final Timer timer;
    private final Queue<InventoryUtil.Task> taskList;
    private final List<Integer> doneSlots;
    boolean flag;
    
    public AutoArmor() {
        super("AutoArmor", "Puts Armor on for you.", Category.COMBAT, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)500));
        this.curse = (Setting<Boolean>)this.register(new Setting("Vanishing", (T)false));
        this.mendingTakeOff = (Setting<Boolean>)this.register(new Setting("AutoMend", (T)false));
        this.closestEnemy = (Setting<Integer>)this.register(new Setting("Enemy", (T)8, (T)1, (T)20, v -> this.mendingTakeOff.getValue()));
        this.repair = (Setting<Integer>)this.register(new Setting("Repair%", (T)80, (T)1, (T)100, v -> this.mendingTakeOff.getValue()));
        this.actions = (Setting<Integer>)this.register(new Setting("Packets", (T)3, (T)1, (T)12));
        this.timer = new Timer();
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.doneSlots = new ArrayList<Integer>();
    }
    
    @Override
    public void onLogin() {
        this.timer.reset();
    }
    
    @Override
    public void onDisable() {
        this.taskList.clear();
        this.doneSlots.clear();
        this.flag = false;
    }
    
    @Override
    public void onLogout() {
        this.taskList.clear();
        this.doneSlots.clear();
    }
    
    @Override
    public void onTick() {
        if (Feature.fullNullCheck() || (AutoArmor.mc.field_71462_r instanceof GuiContainer && !(AutoArmor.mc.field_71462_r instanceof GuiInventory))) {
            return;
        }
        if (this.taskList.isEmpty()) {
            if (this.mendingTakeOff.getValue() && InventoryUtil.holdingItem(ItemExpBottle.class) && AutoArmor.mc.field_71474_y.field_74313_G.func_151470_d() && AutoArmor.mc.field_71441_e.field_73010_i.stream().noneMatch(e -> e != AutoArmor.mc.field_71439_g && !OyVey.friendManager.isFriend(((EntityPlayer)e).func_70005_c_()) && AutoArmor.mc.field_71439_g.func_70032_d((Entity)e) <= this.closestEnemy.getValue()) && !this.flag) {
                int takeOff = 0;
                for (final Map.Entry<Integer, ItemStack> armorSlot : this.getArmor().entrySet()) {
                    final ItemStack stack = armorSlot.getValue();
                    final float percent = this.repair.getValue() / 100.0f;
                    final int dam = Math.round(stack.func_77958_k() * percent);
                    final int goods = stack.func_77958_k() - stack.func_77952_i();
                    if (dam < goods) {
                        ++takeOff;
                    }
                }
                if (takeOff == 4) {
                    this.flag = true;
                }
                if (!this.flag) {
                    final ItemStack itemStack1 = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(5).func_75211_c();
                    if (!itemStack1.field_190928_g) {
                        final float percent2 = this.repair.getValue() / 100.0f;
                        final int dam2 = Math.round(itemStack1.func_77958_k() * percent2);
                        final int goods2 = itemStack1.func_77958_k() - itemStack1.func_77952_i();
                        if (dam2 < goods2) {
                            this.takeOffSlot(5);
                        }
                    }
                    final ItemStack itemStack2 = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(6).func_75211_c();
                    if (!itemStack2.field_190928_g) {
                        final float percent3 = this.repair.getValue() / 100.0f;
                        final int dam3 = Math.round(itemStack2.func_77958_k() * percent3);
                        final int goods3 = itemStack2.func_77958_k() - itemStack2.func_77952_i();
                        if (dam3 < goods3) {
                            this.takeOffSlot(6);
                        }
                    }
                    final ItemStack itemStack3 = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(7).func_75211_c();
                    if (!itemStack3.field_190928_g) {
                        final float percent = this.repair.getValue() / 100.0f;
                        final int dam = Math.round(itemStack3.func_77958_k() * percent);
                        final int goods = itemStack3.func_77958_k() - itemStack3.func_77952_i();
                        if (dam < goods) {
                            this.takeOffSlot(7);
                        }
                    }
                    final ItemStack itemStack4 = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(8).func_75211_c();
                    if (!itemStack4.field_190928_g) {
                        final float percent4 = this.repair.getValue() / 100.0f;
                        final int dam4 = Math.round(itemStack4.func_77958_k() * percent4);
                        final int goods4 = itemStack4.func_77958_k() - itemStack4.func_77952_i();
                        if (dam4 < goods4) {
                            this.takeOffSlot(8);
                        }
                    }
                }
                return;
            }
            this.flag = false;
            final ItemStack helm = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(5).func_75211_c();
            if (helm.func_77973_b() == Items.field_190931_a) {
                final int slot = InventoryUtil.findArmorSlot(EntityEquipmentSlot.HEAD, this.curse.getValue(), true);
                if (slot != -1) {
                    this.getSlotOn(5, slot);
                }
            }
            final ItemStack chest = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(6).func_75211_c();
            if (chest.func_77973_b() == Items.field_190931_a) {
                final int slot2 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.CHEST, this.curse.getValue(), true);
                if (slot2 != -1) {
                    this.getSlotOn(6, slot2);
                }
            }
            final ItemStack legging = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(7).func_75211_c();
            if (legging.func_77973_b() == Items.field_190931_a) {
                final int slot3 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.LEGS, this.curse.getValue(), true);
                if (slot3 != -1) {
                    this.getSlotOn(7, slot3);
                }
            }
            final ItemStack feet = AutoArmor.mc.field_71439_g.field_71069_bz.func_75139_a(8).func_75211_c();
            if (feet.func_77973_b() == Items.field_190931_a) {
                final int slot4 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.FEET, this.curse.getValue(), true);
                if (slot4 != -1) {
                    this.getSlotOn(8, slot4);
                }
            }
        }
        if (this.timer.passedMs((int)(this.delay.getValue() * OyVey.serverManager.getTpsFactor()))) {
            if (!this.taskList.isEmpty()) {
                for (int i = 0; i < this.actions.getValue(); ++i) {
                    final InventoryUtil.Task task = this.taskList.poll();
                    if (task != null) {
                        task.run();
                    }
                }
            }
            this.timer.reset();
        }
    }
    
    private void takeOffSlot(final int slot) {
        if (this.taskList.isEmpty()) {
            int target = -1;
            for (final int i : InventoryUtil.findEmptySlots(true)) {
                if (!this.doneSlots.contains(target)) {
                    target = i;
                    this.doneSlots.add(i);
                }
            }
            if (target != -1) {
                this.taskList.add(new InventoryUtil.Task(slot));
                this.taskList.add(new InventoryUtil.Task(target));
                this.taskList.add(new InventoryUtil.Task());
            }
        }
    }
    
    private void getSlotOn(final int slot, final int target) {
        if (this.taskList.isEmpty()) {
            this.doneSlots.remove((Object)target);
            this.taskList.add(new InventoryUtil.Task(target));
            this.taskList.add(new InventoryUtil.Task(slot));
            this.taskList.add(new InventoryUtil.Task());
        }
    }
    
    private Map<Integer, ItemStack> getArmor() {
        return this.getInventorySlots(5, 8);
    }
    
    private Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoArmor.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
}
