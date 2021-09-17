// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.init.Blocks;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.NonNullList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.RayTraceResult;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.RenderUtil;
import java.awt.Color;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.event.events.Render3DEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.MobEffects;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.misc.AutoGG;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import java.util.Comparator;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import me.alpha432.oyvey.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import me.alpha432.oyvey.event.events.PacketEvent;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.features.modules.Module;

public class AutoCrystal extends Module
{
    private final Timer placeTimer;
    private final Timer breakTimer;
    private final Timer preditTimer;
    private final Timer manualTimer;
    public Setting<Boolean> place;
    public Setting<Float> placeDelay;
    public Setting<Float> placeRange;
    public Setting<Boolean> explode;
    public Setting<Boolean> packetBreak;
    private Setting<Integer> attackFactor;
    public Setting<Boolean> predicts;
    public Setting<Boolean> rotate;
    public Setting<Float> breakDelay;
    public Setting<Float> breakRange;
    public Setting<Float> breakWallRange;
    public Setting<Boolean> opPlace;
    public Setting<Boolean> suicide;
    public Setting<Boolean> autoswitch;
    public Setting<Boolean> ignoreUseAmount;
    public Setting<Integer> wasteAmount;
    public Setting<Boolean> facePlaceSword;
    public Setting<Float> targetRange;
    public Setting<Float> minDamage;
    public Setting<Float> facePlace;
    public Setting<Float> breakMaxSelfDamage;
    public Setting<Float> breakMinDmg;
    public Setting<Float> minArmor;
    public Setting<SwingMode> swingMode;
    public Setting<Boolean> render;
    public Setting<Boolean> renderDmg;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    private Setting<Integer> boxAlpha;
    private Setting<Float> lineWidth;
    private Setting<Integer> cRed;
    private Setting<Integer> cGreen;
    private Setting<Integer> cBlue;
    private Setting<Integer> cAlpha;
    private EntityLivingBase target;
    private BlockPos pos;
    private int hotBarSlot;
    private boolean armor;
    private boolean armorTarget;
    private int crystalCount;
    private int predictWait;
    private int predictPackets;
    private boolean packetCalc;
    private float yaw;
    private EntityLivingBase realTarget;
    private int predict;
    EntityEnderCrystal crystal;
    private float pitch;
    private boolean rotating;
    
    public AutoCrystal() {
        super("AutoCrystal", "NiggaHack ac best ac", Category.COMBAT, true, false, false);
        this.placeTimer = new Timer();
        this.breakTimer = new Timer();
        this.preditTimer = new Timer();
        this.manualTimer = new Timer();
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true));
        this.placeDelay = (Setting<Float>)this.register(new Setting("PlaceDelay", (T)4.0f, (T)0.0f, (T)300.0f));
        this.placeRange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.explode = (Setting<Boolean>)this.register(new Setting("Break", (T)true));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.attackFactor = (Setting<Integer>)this.register(new Setting("PredictDelay", (T)0, (T)0, (T)200));
        this.predicts = (Setting<Boolean>)this.register(new Setting("Predict", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)4.0f, (T)0.0f, (T)300.0f));
        this.breakRange = (Setting<Float>)this.register(new Setting("BreakRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.breakWallRange = (Setting<Float>)this.register(new Setting("BreakWallRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.opPlace = (Setting<Boolean>)this.register(new Setting("1.13 Place", (T)true));
        this.suicide = (Setting<Boolean>)this.register(new Setting("AntiSuicide", (T)true));
        this.autoswitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)true));
        this.ignoreUseAmount = (Setting<Boolean>)this.register(new Setting("IgnoreUseAmount", (T)true));
        this.wasteAmount = (Setting<Integer>)this.register(new Setting("UseAmount", (T)4, (T)1, (T)5));
        this.facePlaceSword = (Setting<Boolean>)this.register(new Setting("FacePlaceSword", (T)true));
        this.targetRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)4.0f, (T)1.0f, (T)12.0f));
        this.minDamage = (Setting<Float>)this.register(new Setting("MinDamage", (T)4.0f, (T)0.1f, (T)20.0f));
        this.facePlace = (Setting<Float>)this.register(new Setting("FacePlaceHP", (T)4.0f, (T)0.0f, (T)36.0f));
        this.breakMaxSelfDamage = (Setting<Float>)this.register(new Setting("BreakMaxSelf", (T)4.0f, (T)0.1f, (T)12.0f));
        this.breakMinDmg = (Setting<Float>)this.register(new Setting("BreakMinDmg", (T)4.0f, (T)0.1f, (T)7.0f));
        this.minArmor = (Setting<Float>)this.register(new Setting("MinArmor", (T)4.0f, (T)0.1f, (T)80.0f));
        this.swingMode = (Setting<SwingMode>)this.register(new Setting("Swing", (T)SwingMode.MainHand));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.renderDmg = (Setting<Boolean>)this.register(new Setting("RenderDmg", (T)true));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.cRed = (Setting<Integer>)this.register(new Setting("OL-Red", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.cGreen = (Setting<Integer>)this.register(new Setting("OL-Green", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.cBlue = (Setting<Integer>)this.register(new Setting("OL-Blue", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.cAlpha = (Setting<Integer>)this.register(new Setting("OL-Alpha", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.rotating = false;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && this.rotate.getValue() && this.rotating && event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = event.getPacket();
            packet.field_149476_e = this.yaw;
            packet.field_149473_f = this.pitch;
            this.rotating = false;
        }
    }
    
    private void rotateTo(final Entity entity) {
        if (this.rotate.getValue()) {
            final float[] angle = MathUtil.calcAngle(AutoCrystal.mc.field_71439_g.func_174824_e(AutoCrystal.mc.func_184121_ak()), entity.func_174791_d());
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.rotating = true;
        }
    }
    
    private void rotateToPos(final BlockPos pos) {
        if (this.rotate.getValue()) {
            final float[] angle = MathUtil.calcAngle(AutoCrystal.mc.field_71439_g.func_174824_e(AutoCrystal.mc.func_184121_ak()), new Vec3d((double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() - 0.5f), (double)(pos.func_177952_p() + 0.5f)));
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.rotating = true;
        }
    }
    
    @Override
    public void onEnable() {
        this.placeTimer.reset();
        this.breakTimer.reset();
        this.predictWait = 0;
        this.hotBarSlot = -1;
        this.pos = null;
        this.crystal = null;
        this.predict = 0;
        this.predictPackets = 1;
        this.target = null;
        this.packetCalc = false;
        this.realTarget = null;
        this.armor = false;
        this.armorTarget = false;
    }
    
    @Override
    public void onDisable() {
        this.rotating = false;
    }
    
    @Override
    public void onTick() {
        this.onCrystal();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.realTarget != null) {
            return this.realTarget.func_70005_c_();
        }
        return null;
    }
    
    public void onCrystal() {
        if (AutoCrystal.mc.field_71441_e == null || AutoCrystal.mc.field_71439_g == null) {
            return;
        }
        this.realTarget = null;
        this.manualBreaker();
        this.crystalCount = 0;
        if (!this.ignoreUseAmount.getValue()) {
            for (final Entity crystal : AutoCrystal.mc.field_71441_e.field_72996_f) {
                if (crystal instanceof EntityEnderCrystal && this.IsValidCrystal(crystal)) {
                    boolean count = false;
                    final double damage = this.calculateDamage(this.target.func_180425_c().func_177958_n() + 0.5, this.target.func_180425_c().func_177956_o() + 1.0, this.target.func_180425_c().func_177952_p() + 0.5, (Entity)this.target);
                    if (damage >= this.minDamage.getValue()) {
                        count = true;
                    }
                    if (!count) {
                        continue;
                    }
                    ++this.crystalCount;
                }
            }
        }
        this.hotBarSlot = -1;
        if (AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP) {
            int crystalSlot = (AutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? AutoCrystal.mc.field_71439_g.field_71071_by.field_70461_c : -1;
            if (crystalSlot == -1) {
                for (int l = 0; l < 9; ++l) {
                    if (AutoCrystal.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
                        crystalSlot = l;
                        this.hotBarSlot = l;
                        break;
                    }
                }
            }
            if (crystalSlot == -1) {
                this.pos = null;
                this.target = null;
                return;
            }
        }
        if (AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao && AutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_185158_cP) {
            this.pos = null;
            this.target = null;
            return;
        }
        if (this.target == null) {
            this.target = (EntityLivingBase)this.getTarget();
        }
        if (this.target == null) {
            this.crystal = null;
            return;
        }
        if (this.target.func_70032_d((Entity)AutoCrystal.mc.field_71439_g) > 12.0f) {
            this.crystal = null;
            this.target = null;
        }
        this.crystal = (EntityEnderCrystal)AutoCrystal.mc.field_71441_e.field_72996_f.stream().filter(this::IsValidCrystal).map(p_Entity -> p_Entity).min(Comparator.comparing(p_Entity -> this.target.func_70032_d(p_Entity))).orElse(null);
        if (this.crystal != null && this.explode.getValue() && this.breakTimer.passedMs(this.breakDelay.getValue().longValue())) {
            this.breakTimer.reset();
            if (this.packetBreak.getValue()) {
                this.rotateTo((Entity)this.crystal);
                AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity((Entity)this.crystal));
            }
            else {
                this.rotateTo((Entity)this.crystal);
                AutoCrystal.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal.mc.field_71439_g, (Entity)this.crystal);
            }
            if (this.swingMode.getValue() == SwingMode.MainHand) {
                AutoCrystal.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            }
            else if (this.swingMode.getValue() == SwingMode.OffHand) {
                AutoCrystal.mc.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
            }
        }
        if (this.placeTimer.passedMs(this.placeDelay.getValue().longValue()) && this.place.getValue()) {
            this.placeTimer.reset();
            double damage2 = 0.5;
            for (final BlockPos blockPos : this.placePostions(this.placeRange.getValue())) {
                if (blockPos == null) {
                    continue;
                }
                if (this.target == null) {
                    continue;
                }
                if (!AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                    continue;
                }
                final double targetRange = this.target.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
                if (targetRange > this.targetRange.getValue()) {
                    continue;
                }
                if (this.target.field_70128_L) {
                    continue;
                }
                if (this.target.func_110143_aJ() + this.target.func_110139_bj() <= 0.0f) {
                    continue;
                }
                final double targetDmg = this.calculateDamage(blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1.0, blockPos.func_177952_p() + 0.5, (Entity)this.target);
                this.armor = false;
                for (final ItemStack is : this.target.func_184193_aE()) {
                    final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
                    final float red = 1.0f - green;
                    final int dmg = 100 - (int)(red * 100.0f);
                    if (dmg <= this.minArmor.getValue()) {
                        this.armor = true;
                    }
                }
                Label_1212: {
                    if (targetDmg < this.minDamage.getValue()) {
                        if (this.facePlaceSword.getValue()) {
                            if (this.target.func_110139_bj() + this.target.func_110143_aJ() <= this.facePlace.getValue()) {
                                break Label_1212;
                            }
                        }
                        else if (!(AutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && this.target.func_110139_bj() + this.target.func_110143_aJ() <= this.facePlace.getValue()) {
                            break Label_1212;
                        }
                        if (this.facePlaceSword.getValue()) {
                            if (!this.armor) {
                                continue;
                            }
                        }
                        else {
                            if (AutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
                                continue;
                            }
                            if (!this.armor) {
                                continue;
                            }
                        }
                    }
                }
                final double selfDmg = this.calculateDamage(blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1.0, blockPos.func_177952_p() + 0.5, (Entity)AutoCrystal.mc.field_71439_g);
                if (selfDmg + (this.suicide.getValue() ? 2.0 : 0.5) >= AutoCrystal.mc.field_71439_g.func_110143_aJ() + AutoCrystal.mc.field_71439_g.func_110139_bj() && selfDmg >= targetDmg && targetDmg < this.target.func_110143_aJ() + this.target.func_110139_bj()) {
                    continue;
                }
                if (damage2 >= targetDmg) {
                    continue;
                }
                this.pos = blockPos;
                damage2 = targetDmg;
            }
            if (damage2 == 0.5) {
                this.pos = null;
                this.target = null;
                this.realTarget = null;
                return;
            }
            this.realTarget = this.target;
            if (AutoGG.getINSTANCE().isOn()) {
                final AutoGG autoGG = (AutoGG)OyVey.moduleManager.getModuleByName("AutoGG");
                autoGG.addTargetedPlayer(this.target.func_70005_c_());
            }
            if (this.hotBarSlot != -1 && this.autoswitch.getValue() && !AutoCrystal.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
                AutoCrystal.mc.field_71439_g.field_71071_by.field_70461_c = this.hotBarSlot;
            }
            if (!this.ignoreUseAmount.getValue()) {
                int crystalLimit = this.wasteAmount.getValue();
                if (this.crystalCount >= crystalLimit) {
                    return;
                }
                if (damage2 < this.minDamage.getValue()) {
                    crystalLimit = 1;
                }
                if (this.crystalCount < crystalLimit && this.pos != null) {
                    this.rotateToPos(this.pos);
                    AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.pos, EnumFacing.UP, (AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
            else if (this.pos != null) {
                this.rotateToPos(this.pos);
                AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.pos, EnumFacing.UP, (AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packet = event.getPacket();
            if (packet.func_148993_l() == 51 && this.predicts.getValue() && this.preditTimer.passedMs(this.attackFactor.getValue()) && this.predicts.getValue() && this.explode.getValue() && this.packetBreak.getValue() && this.target != null) {
                if (!this.isPredicting(packet)) {
                    return;
                }
                final CPacketUseEntity predict = new CPacketUseEntity();
                predict.field_149567_a = packet.func_149001_c();
                predict.field_149566_b = CPacketUseEntity.Action.ATTACK;
                AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)predict);
            }
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.pos != null && this.render.getValue() && this.target != null) {
            RenderUtil.drawBoxESP(this.pos, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.outline.getValue(), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.cAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
            if (this.renderDmg.getValue()) {
                final double renderDamage = this.calculateDamage(this.pos.func_177958_n() + 0.5, this.pos.func_177956_o() + 1.0, this.pos.func_177952_p() + 0.5, (Entity)this.target);
                RenderUtil.drawText(this.pos, ((Math.floor(renderDamage) == renderDamage) ? Integer.valueOf((int)renderDamage) : String.format("%.1f", renderDamage)) + "");
            }
        }
    }
    
    private boolean isPredicting(final SPacketSpawnObject packet) {
        final BlockPos packPos = new BlockPos(packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e());
        if (AutoCrystal.mc.field_71439_g.func_70011_f(packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e()) > this.breakRange.getValue()) {
            return false;
        }
        if (!this.canSeePos(packPos) && AutoCrystal.mc.field_71439_g.func_70011_f(packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e()) > this.breakWallRange.getValue()) {
            return false;
        }
        final double targetDmg = this.calculateDamage(packet.func_186880_c() + 0.5, packet.func_186882_d() + 1.0, packet.func_186881_e() + 0.5, (Entity)this.target);
        if (EntityUtil.isInHole((Entity)AutoCrystal.mc.field_71439_g) && targetDmg >= 1.0) {
            return true;
        }
        final double selfDmg = this.calculateDamage(packet.func_186880_c() + 0.5, packet.func_186882_d() + 1.0, packet.func_186881_e() + 0.5, (Entity)AutoCrystal.mc.field_71439_g);
        if (selfDmg + (this.suicide.getValue() ? 2.0 : 0.5) < AutoCrystal.mc.field_71439_g.func_110143_aJ() + AutoCrystal.mc.field_71439_g.func_110139_bj() && targetDmg >= this.target.func_110139_bj() + this.target.func_110143_aJ()) {
            return true;
        }
        this.armorTarget = false;
        for (final ItemStack is : this.target.func_184193_aE()) {
            final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (dmg <= this.minArmor.getValue()) {
                this.armorTarget = true;
            }
        }
        return (targetDmg >= this.breakMinDmg.getValue() && selfDmg <= this.breakMaxSelfDamage.getValue()) || (EntityUtil.isInHole((Entity)this.target) && this.target.func_110143_aJ() + this.target.func_110139_bj() <= this.facePlace.getValue());
    }
    
    private boolean IsValidCrystal(final Entity p_Entity) {
        if (p_Entity == null) {
            return false;
        }
        if (!(p_Entity instanceof EntityEnderCrystal)) {
            return false;
        }
        if (this.target == null) {
            return false;
        }
        if (p_Entity.func_70032_d((Entity)AutoCrystal.mc.field_71439_g) > this.breakRange.getValue()) {
            return false;
        }
        if (!AutoCrystal.mc.field_71439_g.func_70685_l(p_Entity) && p_Entity.func_70032_d((Entity)AutoCrystal.mc.field_71439_g) > this.breakWallRange.getValue()) {
            return false;
        }
        if (this.target.field_70128_L || this.target.func_110143_aJ() + this.target.func_110139_bj() <= 0.0f) {
            return false;
        }
        final double targetDmg = this.calculateDamage(p_Entity.func_180425_c().func_177958_n() + 0.5, p_Entity.func_180425_c().func_177956_o() + 1.0, p_Entity.func_180425_c().func_177952_p() + 0.5, (Entity)this.target);
        if (EntityUtil.isInHole((Entity)AutoCrystal.mc.field_71439_g) && targetDmg >= 1.0) {
            return true;
        }
        final double selfDmg = this.calculateDamage(p_Entity.func_180425_c().func_177958_n() + 0.5, p_Entity.func_180425_c().func_177956_o() + 1.0, p_Entity.func_180425_c().func_177952_p() + 0.5, (Entity)AutoCrystal.mc.field_71439_g);
        if (selfDmg + (this.suicide.getValue() ? 2.0 : 0.5) < AutoCrystal.mc.field_71439_g.func_110143_aJ() + AutoCrystal.mc.field_71439_g.func_110139_bj() && targetDmg >= this.target.func_110139_bj() + this.target.func_110143_aJ()) {
            return true;
        }
        this.armorTarget = false;
        for (final ItemStack is : this.target.func_184193_aE()) {
            final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (dmg <= this.minArmor.getValue()) {
                this.armorTarget = true;
            }
        }
        return (targetDmg >= this.breakMinDmg.getValue() && selfDmg <= this.breakMaxSelfDamage.getValue()) || (EntityUtil.isInHole((Entity)this.target) && this.target.func_110143_aJ() + this.target.func_110139_bj() <= this.facePlace.getValue());
    }
    
    EntityPlayer getTarget() {
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer entity : AutoCrystal.mc.field_71441_e.field_73010_i) {
            if (AutoCrystal.mc.field_71439_g == null) {
                continue;
            }
            if (AutoCrystal.mc.field_71439_g.field_70128_L) {
                continue;
            }
            if (entity.field_70128_L) {
                continue;
            }
            if (entity == AutoCrystal.mc.field_71439_g) {
                continue;
            }
            if (OyVey.friendManager.isFriend(entity.func_70005_c_())) {
                continue;
            }
            if (entity.func_70032_d((Entity)AutoCrystal.mc.field_71439_g) > 12.0f) {
                continue;
            }
            this.armorTarget = false;
            for (final ItemStack is : entity.func_184193_aE()) {
                final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
                final float red = 1.0f - green;
                final int dmg = 100 - (int)(red * 100.0f);
                if (dmg <= this.minArmor.getValue()) {
                    this.armorTarget = true;
                }
            }
            if (EntityUtil.isInHole((Entity)entity) && entity.func_110139_bj() + entity.func_110143_aJ() > this.facePlace.getValue() && !this.armorTarget && this.minDamage.getValue() > 2.2f) {
                continue;
            }
            if (closestPlayer == null) {
                closestPlayer = entity;
            }
            else {
                if (closestPlayer.func_70032_d((Entity)AutoCrystal.mc.field_71439_g) <= entity.func_70032_d((Entity)AutoCrystal.mc.field_71439_g)) {
                    continue;
                }
                closestPlayer = entity;
            }
        }
        return closestPlayer;
    }
    
    private void manualBreaker() {
        if (this.manualTimer.passedMs(200L) && AutoCrystal.mc.field_71474_y.field_74313_G.func_151470_d() && AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_151153_ao && AutoCrystal.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() != Items.field_151153_ao && AutoCrystal.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() != Items.field_151031_f && AutoCrystal.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() != Items.field_151062_by) {
            final RayTraceResult result = AutoCrystal.mc.field_71476_x;
            if (result != null) {
                if (result.field_72313_a.equals((Object)RayTraceResult.Type.ENTITY)) {
                    final Entity entity = result.field_72308_g;
                    if (entity instanceof EntityEnderCrystal) {
                        if (this.packetBreak.getValue()) {
                            AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
                        }
                        else {
                            AutoCrystal.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal.mc.field_71439_g, entity);
                        }
                        this.manualTimer.reset();
                    }
                }
                else if (result.field_72313_a.equals((Object)RayTraceResult.Type.BLOCK)) {
                    final BlockPos mousePos = new BlockPos((double)AutoCrystal.mc.field_71476_x.func_178782_a().func_177958_n(), AutoCrystal.mc.field_71476_x.func_178782_a().func_177956_o() + 1.0, (double)AutoCrystal.mc.field_71476_x.func_178782_a().func_177952_p());
                    for (final Entity target : AutoCrystal.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(mousePos))) {
                        if (target instanceof EntityEnderCrystal) {
                            if (this.packetBreak.getValue()) {
                                AutoCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(target));
                            }
                            else {
                                AutoCrystal.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal.mc.field_71439_g, target);
                            }
                            this.manualTimer.reset();
                        }
                    }
                }
            }
        }
    }
    
    private boolean canSeePos(final BlockPos pos) {
        return AutoCrystal.mc.field_71441_e.func_147447_a(new Vec3d(AutoCrystal.mc.field_71439_g.field_70165_t, AutoCrystal.mc.field_71439_g.field_70163_u + AutoCrystal.mc.field_71439_g.func_70047_e(), AutoCrystal.mc.field_71439_g.field_70161_v), new Vec3d((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()), false, true, false) == null;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.func_177958_n();
        final int cy = loc.func_177956_o();
        final int cz = loc.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = sphere ? (cy + r) : ((float)(cy + h));
                    if (y >= f) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    private NonNullList<BlockPos> placePostions(final float placeRange) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)getSphere(new BlockPos(Math.floor(AutoCrystal.mc.field_71439_g.field_70165_t), Math.floor(AutoCrystal.mc.field_71439_g.field_70163_u), Math.floor(AutoCrystal.mc.field_71439_g.field_70161_v)), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> this.canPlaceCrystal(pos, true)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return positions;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        try {
            if (!this.opPlace.getValue()) {
                if (AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
                    return false;
                }
                if (AutoCrystal.mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || AutoCrystal.mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
                }
                for (final Entity entity : AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost))) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        return false;
                    }
                }
                for (final Entity entity : AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost2))) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        return false;
                    }
                }
            }
            else {
                if (AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
                    return false;
                }
                if (AutoCrystal.mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty();
                }
                for (final Entity entity : AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost))) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        return false;
                    }
                }
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    private float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion((World)AutoCrystal.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.func_94539_a(explosion);
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            int k = 0;
            try {
                k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.func_76131_a((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.func_70644_a(MobEffects.field_76429_m)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
        return damage;
    }
    
    private float getDamageMultiplied(final float damage) {
        final int diff = AutoCrystal.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public enum SwingMode
    {
        MainHand, 
        OffHand, 
        None;
    }
}
