package refinedstorage.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import refinedstorage.block.EnumGridType;
import refinedstorage.tile.grid.TileGrid;

public class MessageGridPatternCreate extends MessageHandlerPlayerToServer<MessageGridPatternCreate> implements IMessage {
    private int x;
    private int y;
    private int z;

    public MessageGridPatternCreate() {
    }

    public MessageGridPatternCreate(TileGrid grid) {
        this.x = grid.getPos().getX();
        this.y = grid.getPos().getY();
        this.z = grid.getPos().getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void handle(MessageGridPatternCreate message, EntityPlayerMP player) {
        TileEntity tile = player.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));

        if (tile instanceof TileGrid && ((TileGrid) tile).getType() == EnumGridType.PATTERN) {
            ((TileGrid) tile).onCreatePattern();
        }
    }
}
