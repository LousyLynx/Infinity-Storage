package infinitystorage.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import infinitystorage.tile.TileCraftingMonitor;

public class MessageCraftingMonitorCancel extends MessageHandlerPlayerToServer<MessageCraftingMonitorCancel> implements IMessage {
    private int x;
    private int y;
    private int z;
    private int id;
    private int depth;

    public MessageCraftingMonitorCancel() {
    }

    public MessageCraftingMonitorCancel(TileCraftingMonitor craftingMonitor, int id, int depth) {
        this.x = craftingMonitor.getPos().getX();
        this.y = craftingMonitor.getPos().getY();
        this.z = craftingMonitor.getPos().getZ();
        this.id = id;
        this.depth = depth;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
        depth = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
        buf.writeInt(depth);
    }

    @Override
    public void handle(MessageCraftingMonitorCancel message, EntityPlayerMP player) {
        TileEntity tile = player.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));

        if (tile instanceof TileCraftingMonitor) {
            TileCraftingMonitor monitor = (TileCraftingMonitor) tile;

            if (monitor.isConnected()) {
                monitor.getNetwork().getItemGridHandler().onCraftingCancelRequested(message.id, message.depth);
            }
        }
    }
}
