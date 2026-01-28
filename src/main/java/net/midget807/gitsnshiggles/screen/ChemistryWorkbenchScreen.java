package net.midget807.gitsnshiggles.screen;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.block.entity.ChemistryWorkbenchBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.midget807.gitsnshiggles.block.ChemistryWorkbenchBlock.Modes;

public class ChemistryWorkbenchScreen extends HandledScreen<ChemistryWorkbenchScreenHandler> {
    private static final Identifier MENU_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/chemistry_workbench_menu.png");
    private static final Identifier DISTILLATION_TAB_UNAVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/distillation_tab_unavailable.png");
    private static final Identifier DISTILLATION_TAB_AVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/distillation_tab_available.png");
    private static final Identifier DISTILLATION_TAB_SELECTED_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/distillation_tab_selected.png");
    private static final Identifier FILTER_TAB_UNAVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/filter_tab_unavailable.png");
    private static final Identifier FILTER_TAB_AVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/filter_tab_available.png");
    private static final Identifier FILTER_TAB_SELECTED_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/filter_tab_selected.png");
    private static final Identifier EVAPORATE_TAB_UNAVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/evaporate_tab_unavailable.png");
    private static final Identifier EVAPORATE_TAB_AVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/evaporate_tab_available.png");
    private static final Identifier EVAPORATE_TAB_SELECTED_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/evaporate_tab_selected.png");
    private static final Identifier DISSOLVE_TAB_UNAVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/dissolve_tab_unavailable.png");
    private static final Identifier DISSOLVE_TAB_AVAILABLE_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/dissolve_tab_available.png");
    private static final Identifier DISSOLVE_TAB_SELECTED_TEXTURE = GitsAndShigglesMain.id("textures/gui/container/dissolve_tab_selected.png");

    public static final int BACKGROUND_WIDTH = 212;
    public static final int BACKGROUND_HEIGHT = 222;
    public static final int BACKGROUND_X_OFFSET = 29;
    public static final int TAB_WIDTH = 32;
    public static final int TAB_HEIGHT = 26;
    public static final int TAB_OFFSET = 4;

    private Modes selectedTab = Modes.NONE;

    public ChemistryWorkbenchScreen(ChemistryWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = BACKGROUND_WIDTH;
        this.backgroundHeight = BACKGROUND_HEIGHT;
        this.playerInventoryTitleX = 26;
        this.playerInventoryTitleY = BACKGROUND_HEIGHT - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - BACKGROUND_WIDTH) / 2;
        int y = (height - BACKGROUND_HEIGHT) / 2;

        ChemistryWorkbenchScreenHandler screenHandler = this.getScreenHandler();
        ChemistryWorkbenchBlockEntity blockEntity = screenHandler.blockEntity;

        context.drawTexture(MENU_TEXTURE, x, y, BACKGROUND_X_OFFSET, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 256, 256);

        context.drawTexture(this.getTabTexture(blockEntity, Modes.DISTILLATION), x - TAB_WIDTH + TAB_OFFSET, y + TAB_OFFSET, 0, 0, TAB_WIDTH, TAB_HEIGHT, 32, 26);
        context.drawTexture(this.getTabTexture(blockEntity, Modes.FILTER), x - TAB_WIDTH + TAB_OFFSET, y + TAB_OFFSET + TAB_HEIGHT + 1, 0, 0, TAB_WIDTH, TAB_HEIGHT, 32, 26);
        context.drawTexture(this.getTabTexture(blockEntity, Modes.EVAPORATE), x - TAB_WIDTH + TAB_OFFSET, y + TAB_OFFSET + (TAB_HEIGHT + 1) * 2, 0, 0, TAB_WIDTH, TAB_HEIGHT, 32, 26);
        context.drawTexture(this.getTabTexture(blockEntity, Modes.DISSOLVE), x - TAB_WIDTH + TAB_OFFSET, y + TAB_OFFSET + (TAB_HEIGHT + 1) * 3 , 0, 0, TAB_WIDTH, TAB_HEIGHT, 32, 26);
    }

    private Identifier getTabTexture(ChemistryWorkbenchBlockEntity blockEntity, Modes modes) {
        return switch (modes) {
            case DISTILLATION:
                if (blockEntity.distillationAvailable) {
                    yield selectedTab == Modes.DISTILLATION ? DISTILLATION_TAB_SELECTED_TEXTURE : DISTILLATION_TAB_AVAILABLE_TEXTURE;
                } else {
                    yield DISTILLATION_TAB_UNAVAILABLE_TEXTURE;
                }
            case FILTER:
                if (blockEntity.filterAvailable) {
                    yield selectedTab == Modes.FILTER ? FILTER_TAB_SELECTED_TEXTURE : FILTER_TAB_AVAILABLE_TEXTURE;
                } else {
                    yield FILTER_TAB_UNAVAILABLE_TEXTURE;
                }
            case EVAPORATE:
                if (blockEntity.evaporateAvailable) {
                    yield selectedTab == Modes.EVAPORATE ? EVAPORATE_TAB_SELECTED_TEXTURE : EVAPORATE_TAB_AVAILABLE_TEXTURE;
                } else {
                    yield EVAPORATE_TAB_UNAVAILABLE_TEXTURE;
                }
            case DISSOLVE:
                if (blockEntity.dissolveAvailable) {
                    yield selectedTab == Modes.DISSOLVE ? DISSOLVE_TAB_SELECTED_TEXTURE : DISSOLVE_TAB_AVAILABLE_TEXTURE;
                } else {
                    yield DISSOLVE_TAB_UNAVAILABLE_TEXTURE;
                }
            case NONE:
                yield null;
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double d = mouseX - this.x;
            double e = mouseY - this.y;

            for (Modes modes : Modes.getModesToDisplay()) {
                if (isClickInTab(modes, d, e)) {
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double d = mouseX - this.x;
            double e = mouseY - this.y;

            for (Modes modes : Modes.getModesToDisplay()) {
                if (this.isClickInTab(modes, d, e)) {
                    this.setSelectedTab(modes);
                    return true;
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void setSelectedTab(Modes newMode) {
        Modes currentModes = selectedTab;
        selectedTab = newMode;
        this.cursorDragSlots.clear();
        this.endTouchDrag();
    }

    private boolean isClickInTab(Modes modes, double mouseX, double mouseY) {
        int i = this.getTabX(modes);
        int j = this.getTabY(modes);
        return mouseX >= i && mouseX <= i + TAB_WIDTH && mouseY >= j && mouseY <= j + TAB_HEIGHT;
    }

    private int getTabX(Modes modes) {
        return (width - BACKGROUND_WIDTH) / 2 - TAB_WIDTH;
    }

    private int getTabY(Modes modes) {
        int i = modes.getRow(modes);
        int k = (height - BACKGROUND_HEIGHT) / 2 + TAB_OFFSET + (TAB_HEIGHT + 1) * i;
        return k;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
