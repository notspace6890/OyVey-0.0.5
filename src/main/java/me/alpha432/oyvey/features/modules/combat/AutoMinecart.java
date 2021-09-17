// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import me.alpha432.oyvey.util.EntityUtil;
import net.minecraft.block.material.Material;
import me.alpha432.oyvey.util.BlockUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityMinecartTNT;
import me.alpha432.oyvey.util.MathUtil;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import me.alpha432.oyvey.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import me.alpha432.oyvey.util.InventoryUtil;
import net.minecraft.init.Items;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class AutoMinecart extends Module
{
    public Setting<Float> minHP;
    private Setting<Integer> blocksPerTick;
    private Setting<Integer> delay;
    private final Setting<Boolean> web;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> packet;
    int wait;
    int waitFlint;
    private boolean check;
    int originalSlot;
    
    public AutoMinecart() {
        super("AutoMinecart", "Places and explodes minecarts on other players.", Category.COMBAT, true, false, false);
        this.minHP = (Setting<Float>)this.register(new Setting("MinHP", (T)4.0f, (T)0.0f, (T)36.0f));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)1, (T)1, (T)4));
        this.delay = (Setting<Integer>)this.register(new Setting("Carts", (T)20, (T)0, (T)50));
        this.web = (Setting<Boolean>)this.register(new Setting("Web", (T)Boolean.FALSE));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)Boolean.FALSE));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)Boolean.FALSE));
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.toggle();
        }
        this.wait = 0;
        this.waitFlint = 0;
        this.originalSlot = AutoMinecart.mc.field_71439_g.field_71071_by.field_70461_c;
        this.check = true;
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            this.toggle();
        }
        final int i = InventoryUtil.findStackInventory(Items.field_151142_bV);
        for (int j = 0; j < 9; ++j) {
            if (AutoMinecart.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_190931_a && i != -1) {
                AutoMinecart.mc.field_71442_b.func_187098_a(AutoMinecart.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoMinecart.mc.field_71439_g);
                AutoMinecart.mc.field_71442_b.func_78765_e();
            }
        }
        final int webSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
        final int tntSlot = InventoryUtil.getItemHotbar(Items.field_151142_bV);
        final int flintSlot = InventoryUtil.getItemHotbar(Items.field_151033_d);
        final int railSlot = InventoryUtil.findHotbarBlock(Blocks.field_150408_cc);
        final int picSlot = InventoryUtil.getItemHotbar(Items.field_151046_w);
        if (tntSlot == -1 || railSlot == -1 || flintSlot == -1 || picSlot == -1 || (this.web.getValue() && webSlot == -1)) {
            Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No (tnt minecart/activator rail/flint/pic/webs) in hotbar disabling...");
            this.toggle();
        }
        final EntityPlayer target = this.getTarget();
        if (target == null) {
            return;
        }
        final BlockPos pos = new BlockPos(target.field_70165_t, target.field_70163_u, target.field_70161_v);
        final Vec3d hitVec = new Vec3d((Vec3i)pos).func_72441_c(0.0, -0.5, 0.0);
        if (AutoMinecart.mc.field_71439_g.func_174818_b(pos) <= MathUtil.square(6.0)) {
            this.check = true;
            if (AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150408_cc && !AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos)).isEmpty()) {
                InventoryUtil.switchToHotbarSlot(flintSlot, false);
                BlockUtil.rightClickBlock(pos.func_177977_b(), hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, this.packet.getValue());
            }
            if (AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150408_cc && AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos)).isEmpty() && AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos.func_177984_a())).isEmpty() && AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos.func_177977_b())).isEmpty()) {
                InventoryUtil.switchToHotbarSlot(railSlot, false);
                BlockUtil.rightClickBlock(pos.func_177977_b(), hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, this.packet.getValue());
                this.wait = 0;
            }
            if (this.web.getValue() && this.wait != 0 && AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150408_cc && !target.field_70134_J && (BlockUtil.isPositionPlaceable(pos.func_177984_a(), false) == 1 || BlockUtil.isPositionPlaceable(pos.func_177984_a(), false) == 3) && AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos.func_177984_a())).isEmpty()) {
                InventoryUtil.switchToHotbarSlot(webSlot, false);
                BlockUtil.placeBlock(pos.func_177984_a(), EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), false);
            }
            if (AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150408_cc) {
                InventoryUtil.switchToHotbarSlot(tntSlot, false);
                for (int u = 0; u < this.blocksPerTick.getValue(); ++u) {
                    BlockUtil.rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, this.packet.getValue());
                }
            }
            if (this.wait < this.delay.getValue()) {
                ++this.wait;
                return;
            }
            this.check = false;
            this.wait = 0;
            InventoryUtil.switchToHotbarSlot(picSlot, false);
            if (AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150408_cc && !AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos)).isEmpty()) {
                AutoMinecart.mc.field_71442_b.func_180512_c(pos, EnumFacing.UP);
            }
            InventoryUtil.switchToHotbarSlot(flintSlot, false);
            if (AutoMinecart.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176194_O().func_177621_b().func_185904_a() != Material.field_151581_o && !AutoMinecart.mc.field_71441_e.func_72872_a((Class)EntityMinecartTNT.class, new AxisAlignedBB(pos)).isEmpty()) {
                BlockUtil.rightClickBlock(pos.func_177977_b(), hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, this.packet.getValue());
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.check) {
            return ChatFormatting.GREEN + "Placing";
        }
        return ChatFormatting.RED + "Breaking";
    }
    
    @Override
    public void onDisable() {
        AutoMinecart.mc.field_71439_g.field_71071_by.field_70461_c = this.originalSlot;
    }
    
    private EntityPlayer getTarget() {
        EntityPlayer target = null;
        double distance = Math.pow(6.0, 2.0) + 1.0;
        for (final EntityPlayer player : AutoMinecart.mc.field_71441_e.field_73010_i) {
            if (EntityUtil.isntValid((Entity)player, 6.0)) {
                continue;
            }
            if (player.func_70090_H()) {
                continue;
            }
            if (player.func_180799_ab()) {
                continue;
            }
            if (!EntityUtil.isTrapped(player, false, false, false, false, false)) {
                continue;
            }
            if (player.func_110143_aJ() + player.func_110139_bj() > this.minHP.getValue()) {
                continue;
            }
            if (target == null) {
                target = player;
                distance = AutoMinecart.mc.field_71439_g.func_70068_e((Entity)player);
            }
            else {
                if (AutoMinecart.mc.field_71439_g.func_70068_e((Entity)player) >= distance) {
                    continue;
                }
                target = player;
                distance = AutoMinecart.mc.field_71439_g.func_70068_e((Entity)player);
            }
        }
        return target;
    }
}
