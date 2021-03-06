package infinitystorage.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import infinitystorage.InfinityStorageItems;
import infinitystorage.gui.grid.GridFilteredItem;
import infinitystorage.gui.grid.GuiGrid;
import infinitystorage.item.ItemGridFilter;

import java.util.List;

public class ItemHandlerGridFilterInGrid extends ItemHandlerBasic {
    private List<GridFilteredItem> filteredItems;

    public ItemHandlerGridFilterInGrid(List<GridFilteredItem> filteredItems) {
        super(4, new ItemValidatorBasic(InfinityStorageItems.GRID_FILTER));

        this.filteredItems = filteredItems;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);

        filteredItems.clear();

        for (int i = 0; i < getSlots(); ++i) {
            ItemStack filter = getStackInSlot(i);

            if (filter != null) {
                int compare = ItemGridFilter.getCompare(filter);

                ItemHandlerGridFilter items = new ItemHandlerGridFilter(filter);

                for (ItemStack item : items.getFilteredItems()) {
                    if (item != null) {
                        filteredItems.add(new GridFilteredItem(item, compare));
                    }
                }
            }
        }

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            GuiGrid.markForSorting();
        }
    }
}
