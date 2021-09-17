// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.misc;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.client.renderer.RenderHelper;
import me.alpha432.oyvey.util.ColorUtil;
import java.awt.Color;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import me.alpha432.oyvey.event.events.Render2DEvent;
import java.util.Iterator;
import net.minecraft.item.ItemShulkerBox;
import java.util.concurrent.ConcurrentHashMap;
import me.alpha432.oyvey.util.Timer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import me.alpha432.oyvey.features.modules.Module;

public class ToolTips extends Module
{
    private static final ResourceLocation SHULKER_GUI_TEXTURE;
    private static ToolTips INSTANCE;
    public Map<EntityPlayer, ItemStack> spiedPlayers;
    public Map<EntityPlayer, Timer> playerTimers;
    private int textRadarY;
    
    public ToolTips() {
        super("ShulkerViewer", "Several tweaks for tooltips.", Category.MISC, true, false, false);
        this.spiedPlayers = new ConcurrentHashMap<EntityPlayer, ItemStack>();
        this.playerTimers = new ConcurrentHashMap<EntityPlayer, Timer>();
        this.textRadarY = 0;
        this.setInstance();
    }
    
    private void setInstance() {
        ToolTips.INSTANCE = this;
    }
    
    public static ToolTips getInstance() {
        if (ToolTips.INSTANCE == null) {
            ToolTips.INSTANCE = new ToolTips();
        }
        return ToolTips.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        for (final EntityPlayer player : ToolTips.mc.field_71441_e.field_73010_i) {
            if (player != null && player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox && ToolTips.mc.field_71439_g != player) {
                final ItemStack stack = player.func_184614_ca();
                this.spiedPlayers.put(player, stack);
            }
        }
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        final int x = -3;
        int y = 124;
        this.textRadarY = 0;
        for (final EntityPlayer player : ToolTips.mc.field_71441_e.field_73010_i) {
            if (this.spiedPlayers.get(player) != null) {
                if (player.func_184614_ca() == null || !(player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox)) {
                    final Timer playerTimer = this.playerTimers.get(player);
                    if (playerTimer == null) {
                        final Timer timer = new Timer();
                        timer.reset();
                        this.playerTimers.put(player, timer);
                    }
                    else if (playerTimer.passedS(3.0)) {
                        continue;
                    }
                }
                else if (player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox) {
                    final Timer playerTimer = this.playerTimers.get(player);
                    if (playerTimer != null) {
                        playerTimer.reset();
                        this.playerTimers.put(player, playerTimer);
                    }
                }
                final ItemStack stack = this.spiedPlayers.get(player);
                this.renderShulkerToolTip(stack, x, y, player.func_70005_c_());
                y += 78;
                this.textRadarY = y - 10 - 114 + 2;
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
    }
    
    public void renderShulkerToolTip(final ItemStack stack, final int x, final int y, final String name) {
        final NBTTagCompound tagCompound = stack.func_77978_p();
        if (tagCompound != null && tagCompound.func_150297_b("BlockEntityTag", 10)) {
            final NBTTagCompound blockEntityTag = tagCompound.func_74775_l("BlockEntityTag");
            if (blockEntityTag.func_150297_b("Items", 9)) {
                GlStateManager.func_179098_w();
                GlStateManager.func_179140_f();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.func_179147_l();
                GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                ToolTips.mc.func_110434_K().func_110577_a(ToolTips.SHULKER_GUI_TEXTURE);
                RenderUtil.drawTexturedRect(x, y, 0, 0, 176, 16, 500);
                RenderUtil.drawTexturedRect(x, y + 16, 0, 16, 176, 57, 500);
                RenderUtil.drawTexturedRect(x, y + 16 + 54, 0, 160, 176, 8, 500);
                GlStateManager.func_179097_i();
                final Color color = new Color(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue(), 200);
                this.renderer.drawStringWithShadow((name == null) ? stack.func_82833_r() : name, (float)(x + 8), (float)(y + 6), ColorUtil.toRGBA(color));
                GlStateManager.func_179126_j();
                RenderHelper.func_74520_c();
                GlStateManager.func_179091_B();
                GlStateManager.func_179142_g();
                GlStateManager.func_179145_e();
                final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.func_191197_a(27, (Object)ItemStack.field_190927_a);
                ItemStackHelper.func_191283_b(blockEntityTag, (NonNullList)nonnulllist);
                for (int i = 0; i < nonnulllist.size(); ++i) {
                    final int iX = x + i % 9 * 18 + 8;
                    final int iY = y + i / 9 * 18 + 18;
                    final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                    ToolTips.mc.func_175597_ag().field_178112_h.field_77023_b = 501.0f;
                    RenderUtil.itemRender.func_180450_b(itemStack, iX, iY);
                    RenderUtil.itemRender.func_180453_a(ToolTips.mc.field_71466_p, itemStack, iX, iY, (String)null);
                    ToolTips.mc.func_175597_ag().field_178112_h.field_77023_b = 0.0f;
                }
                GlStateManager.func_179140_f();
                GlStateManager.func_179084_k();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    public static void displayInv(final ItemStack stack, final String name) {
        try {
            final Item item = stack.func_77973_b();
            final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            final ItemShulkerBox shulker = (ItemShulkerBox)item;
            entityBox.field_145854_h = shulker.func_179223_d();
            entityBox.func_145834_a((World)ToolTips.mc.field_71441_e);
            ItemStackHelper.func_191283_b(stack.func_77978_p().func_74775_l("BlockEntityTag"), entityBox.field_190596_f);
            entityBox.func_145839_a(stack.func_77978_p().func_74775_l("BlockEntityTag"));
            entityBox.func_190575_a((name == null) ? stack.func_82833_r() : name);
            final IInventory inventory;
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException ex) {}
                ToolTips.mc.field_71439_g.func_71007_a(inventory);
            }).start();
        }
        catch (Exception ex2) {}
    }
    
    static {
        SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
        ToolTips.INSTANCE = new ToolTips();
    }
}
