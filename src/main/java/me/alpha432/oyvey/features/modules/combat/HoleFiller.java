// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.TestUtil;
import net.minecraft.block.BlockEnderChest;
import me.alpha432.oyvey.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import java.util.function.Consumer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import java.util.HashMap;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class HoleFiller extends Module
{
    private final Setting<Integer> range;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerTick;
    private static HoleFiller INSTANCE;
    private final Timer offTimer;
    private final Timer timer;
    private int blocksThisTick;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    private ArrayList<BlockPos> holes;
    private int trie;
    private static final BlockPos[] surroundOffset;
    
    public HoleFiller() {
        super("HoleFiller", "Fills holes around you.", Category.COMBAT, true, false, true);
        this.range = (Setting<Integer>)this.register(new Setting("PlaceRange", (T)8, (T)0, (T)10));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)250));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)20, (T)8, (T)30));
        this.offTimer = new Timer();
        this.timer = new Timer();
        this.blocksThisTick = 0;
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.holes = new ArrayList<BlockPos>();
        this.setInstance();
    }
    
    private void setInstance() {
        HoleFiller.INSTANCE = this;
    }
    
    public static HoleFiller getInstance() {
        if (HoleFiller.INSTANCE == null) {
            HoleFiller.INSTANCE = new HoleFiller();
        }
        return HoleFiller.INSTANCE;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
        this.offTimer.reset();
        this.trie = 0;
    }
    
    @Override
    public void onTick() {
        if (this.isOn()) {
            this.doHoleFill();
        }
    }
    
    @Override
    public void onDisable() {
        this.retries.clear();
    }
    
    private void doHoleFill() {
        if (this.check()) {
            return;
        }
        this.holes = new ArrayList<BlockPos>();
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.func_177980_a(HoleFiller.mc.field_71439_g.func_180425_c().func_177982_a(-this.range.getValue(), -this.range.getValue(), -this.range.getValue()), HoleFiller.mc.field_71439_g.func_180425_c().func_177982_a((int)this.range.getValue(), (int)this.range.getValue(), (int)this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleFiller.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76230_c() && !HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a().func_76230_c()) {
                final boolean solidNeighbours = (HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h | HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z) && (HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h | HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z) && (HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h | HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z) && (HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h | HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z) && HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 0)).func_185904_a() == Material.field_151579_a && HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a() == Material.field_151579_a && HoleFiller.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a() == Material.field_151579_a;
                if (!solidNeighbours) {
                    continue;
                }
                this.holes.add(pos);
            }
        }
        this.holes.forEach(this::placeBlock);
        this.toggle();
    }
    
    private void placeBlock(final BlockPos pos) {
        for (final Entity entity : HoleFiller.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }
        if (this.blocksThisTick < this.blocksPerTick.getValue()) {
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            if (obbySlot == -1 && eChestSot == -1) {
                this.toggle();
            }
            final int originalSlot = HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c;
            HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c = ((obbySlot == -1) ? eChestSot : obbySlot);
            HoleFiller.mc.field_71442_b.func_78765_e();
            TestUtil.placeBlock(pos);
            if (HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c != originalSlot) {
                HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c = originalSlot;
                HoleFiller.mc.field_71442_b.func_78765_e();
            }
            this.timer.reset();
            ++this.blocksThisTick;
        }
    }
    
    private boolean check() {
        if (fullNullCheck()) {
            this.disable();
            return true;
        }
        this.blocksThisTick = 0;
        if (this.retryTimer.passedMs(2000L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        return !this.timer.passedMs(this.delay.getValue());
    }
    
    static {
        HoleFiller.INSTANCE = new HoleFiller();
        surroundOffset = BlockUtil.toBlockPos(EntityUtil.getOffsets(0, true));
    }
}
