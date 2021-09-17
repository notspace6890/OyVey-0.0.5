// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.util.RenderUtil;
import java.awt.Color;
import me.alpha432.oyvey.util.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class HoleESP extends Module
{
    public Setting<Boolean> future;
    public Setting<Boolean> fov;
    public Setting<Boolean> renderOwn;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    private Setting<Integer> range;
    private Setting<Integer> rangeY;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    private Setting<Integer> boxAlpha;
    private Setting<Float> lineWidth;
    private Setting<Integer> safeRed;
    private Setting<Integer> safeGreen;
    private Setting<Integer> safeBlue;
    private Setting<Integer> safeAlpha;
    private Setting<Integer> cRed;
    private Setting<Integer> cGreen;
    private Setting<Integer> cBlue;
    private Setting<Integer> cAlpha;
    private Setting<Integer> safecRed;
    private Setting<Integer> safecGreen;
    private Setting<Integer> safecBlue;
    private Setting<Integer> safecAlpha;
    private static HoleESP INSTANCE;
    
    public HoleESP() {
        super("HoleESP", "Shows safe spots.", Category.RENDER, false, false, false);
        this.future = (Setting<Boolean>)this.register(new Setting("FutureRender", (T)true));
        this.fov = (Setting<Boolean>)this.register(new Setting("InFov", (T)true));
        this.renderOwn = (Setting<Boolean>)this.register(new Setting("RenderOwn", (T)true));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.range = (Setting<Integer>)this.register(new Setting("RangeX", (T)0, (T)0, (T)10));
        this.rangeY = (Setting<Integer>)this.register(new Setting("RangeY", (T)0, (T)0, (T)10));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.safeRed = (Setting<Integer>)this.register(new Setting("BedrockRed", (T)0, (T)0, (T)255));
        this.safeGreen = (Setting<Integer>)this.register(new Setting("BedrockGreen", (T)255, (T)0, (T)255));
        this.safeBlue = (Setting<Integer>)this.register(new Setting("BedrockBlue", (T)0, (T)0, (T)255));
        this.safeAlpha = (Setting<Integer>)this.register(new Setting("BedrockAlpha", (T)255, (T)0, (T)255));
        this.cRed = (Setting<Integer>)this.register(new Setting("OL-Red", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.cGreen = (Setting<Integer>)this.register(new Setting("OL-Green", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.cBlue = (Setting<Integer>)this.register(new Setting("OL-Blue", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.cAlpha = (Setting<Integer>)this.register(new Setting("OL-Alpha", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecRed = (Setting<Integer>)this.register(new Setting("OL-BedrockRed", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecGreen = (Setting<Integer>)this.register(new Setting("OL-BedrockGreen", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecBlue = (Setting<Integer>)this.register(new Setting("OL-BedrockBlue", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecAlpha = (Setting<Integer>)this.register(new Setting("OL-BedrockAlpha", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.setInstance();
    }
    
    private void setInstance() {
        HoleESP.INSTANCE = this;
    }
    
    public static HoleESP getInstance() {
        if (HoleESP.INSTANCE == null) {
            HoleESP.INSTANCE = new HoleESP();
        }
        return HoleESP.INSTANCE;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        assert HoleESP.mc.field_175622_Z != null;
        final Vec3i playerPos = new Vec3i(HoleESP.mc.field_175622_Z.field_70165_t, HoleESP.mc.field_175622_Z.field_70163_u, HoleESP.mc.field_175622_Z.field_70161_v);
        for (int x = playerPos.func_177958_n() - this.range.getValue(); x < playerPos.func_177958_n() + this.range.getValue(); ++x) {
            for (int z = playerPos.func_177952_p() - this.range.getValue(); z < playerPos.func_177952_p() + this.range.getValue(); ++z) {
                for (int y = playerPos.func_177956_o() + this.rangeY.getValue(); y > playerPos.func_177956_o() - this.rangeY.getValue(); --y) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (HoleESP.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                        if (HoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                            if (HoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                                if (!pos.equals((Object)new BlockPos(HoleESP.mc.field_71439_g.field_70165_t, HoleESP.mc.field_71439_g.field_70163_u, HoleESP.mc.field_71439_g.field_70161_v)) || this.renderOwn.getValue()) {
                                    if (BlockUtil.isPosInFov(pos) || !this.fov.getValue()) {
                                        if (HoleESP.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h) {
                                            RenderUtil.drawBoxESP(((boolean)this.future.getValue()) ? pos.func_177977_b() : pos, new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.outline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                                        }
                                        else if (BlockUtil.isBlockUnSafe(HoleESP.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c()) && BlockUtil.isBlockUnSafe(HoleESP.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c()) && BlockUtil.isBlockUnSafe(HoleESP.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c()) && BlockUtil.isBlockUnSafe(HoleESP.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c()) && BlockUtil.isBlockUnSafe(HoleESP.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c())) {
                                            RenderUtil.drawBoxESP(((boolean)this.future.getValue()) ? pos.func_177977_b() : pos, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.outline.getValue(), new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.cAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    static {
        HoleESP.INSTANCE = new HoleESP();
    }
}
