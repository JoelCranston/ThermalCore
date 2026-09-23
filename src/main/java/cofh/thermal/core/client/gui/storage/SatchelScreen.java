package cofh.thermal.core.client.gui.storage;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.ElementTexture;
import cofh.core.client.gui.element.panel.SecurityPanel;
import cofh.core.common.network.packet.server.FilterableGuiTogglePacket;
import cofh.core.util.filter.IFilterableItem;
import cofh.core.util.helpers.FilterHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.common.inventory.storage.SatchelMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Collections;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.Constants.PATH_ELEMENTS;
import static cofh.lib.util.Constants.PATH_GUI;

public class SatchelScreen extends ContainerScreenCoFH<SatchelMenu> {

    public static final Identifier TEXTURE = Identifier.parse(PATH_GUI + "generic.png");
    public static final Identifier TEXTURE_EXT = Identifier.parse(PATH_GUI + "generic_extension.png");
    public static final Identifier SLOT_OVERLAY = Identifier.parse(PATH_ELEMENTS + "locked_overlay_slot.png");

    protected int renderExtension;

    public SatchelScreen(SatchelMenu container, Inventory inv, Component titleIn) {

        super(container, inv, titleIn, 176, 166 + container.getExtraRows() * 18);

        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.satchel");

        renderExtension = container.getExtraRows() * 18;
    }

    @Override
    public void init() {

        super.init();

        for (int i = 0; i < menu.getContainerInventorySize(); ++i) {
            Slot slot = menu.slots.get(i);
            addElement(createSlot(this, slot.x, slot.y));
        }
        addPanel(new SecurityPanel(this, menu, SecurityHelper.getID(player)));

        // Filter Tab
        addElement(new ElementTexture(this, 4, -21)
                .setUV(24, 0)
                .setSize(24, 21)
                .setTexture(TAB_TOP, 48, 32)
                .setVisible(() -> FilterHelper.hasFilter(menu.getSatchel())));

        addElement(new ElementTexture(this, 8, -17) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                FilterableGuiTogglePacket.openFilterGui(menu.getSatchel());
                return true;
            }
        }
                .setSize(16, 16)
                .setTexture(NAV_FILTER, 16, 16)
                .setTooltipFactory((element, mouseX, mouseY) -> ((IFilterableItem) menu.getSatchel().getItem()).getFilter(menu.getSatchel()) instanceof MenuProvider menuProvider ? Collections.singletonList(menuProvider.getDisplayName()) : Collections.emptyList())
                .setVisible(() -> FilterHelper.hasFilter(menu.getSatchel())));
    }

    @Override
    protected void drawBackgroundTexture(GuiGraphicsExtractor guiGraphics) {

        super.drawBackgroundTexture(guiGraphics);

        if (renderExtension > 0) {
            drawTexturedModalRect(guiGraphics, TEXTURE_EXT, leftPos, topPos + renderExtension, 0, 0, imageWidth, imageHeight);
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {

        super.extractLabels(guiGraphics, mouseX, mouseY);

        drawTexturedModalRect(guiGraphics, SLOT_OVERLAY, menu.lockedSlot.x, menu.lockedSlot.y, 0, 0, 16, 16, 16, 16);
    }

}
