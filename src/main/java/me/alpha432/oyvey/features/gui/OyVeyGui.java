// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.gui;

import java.io.IOException;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import me.alpha432.oyvey.features.gui.components.items.Item;
import java.util.function.Function;
import java.util.Comparator;
import me.alpha432.oyvey.features.Feature;
import me.alpha432.oyvey.features.gui.components.items.buttons.Button;
import me.alpha432.oyvey.features.gui.components.items.buttons.ModuleButton;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.gui.components.Component;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class OyVeyGui extends GuiScreen
{
    private static OyVeyGui oyveyGui;
    private final ArrayList<Component> components;
    private static OyVeyGui INSTANCE;
    
    public OyVeyGui() {
        this.components = new ArrayList<Component>();
        this.setInstance();
        this.load();
    }
    
    public static OyVeyGui getInstance() {
        if (OyVeyGui.INSTANCE == null) {
            OyVeyGui.INSTANCE = new OyVeyGui();
        }
        return OyVeyGui.INSTANCE;
    }
    
    private void setInstance() {
        OyVeyGui.INSTANCE = this;
    }
    
    public static OyVeyGui getClickGui() {
        return getInstance();
    }
    
    private void load() {
        int x = -84;
        for (final Module.Category category : OyVey.moduleManager.getCategories()) {
            x += 90;
            this.components.add(new Component(category.getName(), x, 4, true) {
                @Override
                public void setupItems() {
                    OyVeyGui$1.counter1 = new int[] { 1 };
                    OyVey.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing((Function<? super Item, ? extends Comparable>)Feature::getName)));
    }
    
    public void updateModule(final Module module) {
        for (final Component component : this.components) {
            for (final Item item : component.getItems()) {
                if (item instanceof ModuleButton) {
                    final ModuleButton button = (ModuleButton)item;
                    final Module mod = button.getModule();
                    if (module == null || !module.equals(mod)) {
                        continue;
                    }
                    button.initSettings();
                }
            }
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel();
        this.func_146276_q_();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void func_73864_a(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void func_146286_b(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        }
        else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (component.getName().equalsIgnoreCase(name)) {
                return component;
            }
        }
        return null;
    }
    
    public void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
    
    static {
        OyVeyGui.INSTANCE = new OyVeyGui();
    }
}
