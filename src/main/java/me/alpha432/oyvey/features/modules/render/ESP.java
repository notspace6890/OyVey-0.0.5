// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.render;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityXPOrb;
import me.alpha432.oyvey.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import me.alpha432.oyvey.util.EntityUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class ESP extends Module
{
    private final Setting<Boolean> items;
    private final Setting<Boolean> xporbs;
    private final Setting<Boolean> xpbottles;
    private final Setting<Boolean> pearl;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> boxAlpha;
    private final Setting<Integer> alpha;
    private static ESP INSTANCE;
    
    public ESP() {
        super("ESP", "Renders a nice ESP.", Category.RENDER, false, false, false);
        this.items = (Setting<Boolean>)this.register(new Setting("Items", (T)false));
        this.xporbs = (Setting<Boolean>)this.register(new Setting("XpOrbs", (T)false));
        this.xpbottles = (Setting<Boolean>)this.register(new Setting("XpBottles", (T)false));
        this.pearl = (Setting<Boolean>)this.register(new Setting("Pearls", (T)false));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)120, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.setInstance();
    }
    
    private void setInstance() {
        ESP.INSTANCE = this;
    }
    
    public static ESP getInstance() {
        if (ESP.INSTANCE == null) {
            ESP.INSTANCE = new ESP();
        }
        return ESP.INSTANCE;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.items.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityItem && ESP.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xporbs.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityXPOrb && ESP.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.pearl.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityEnderPearl && ESP.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xpbottles.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityExpBottle && ESP.mc.field_71439_g.func_70068_e(entity) < 2500.0) {
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.func_184121_ak());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05 - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05 - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1 - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05 - entity.field_70161_v + interp.field_72449_c);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179120_a(770, 771, 0, 1);
                    GlStateManager.func_179090_x();
                    GlStateManager.func_179132_a(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.func_189696_b(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179121_F();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    static {
        ESP.INSTANCE = new ESP();
    }
}
