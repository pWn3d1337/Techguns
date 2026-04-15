package techguns.util;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Utility Functions for packets
 * copied from PacketBuffer
 */
public class PacketUtil {

	/**
	 * copied from PacketBuffer
     * Writes the ItemStack's ID (short), then size (byte), then damage. (short)
     */
    public static void writeItemStackToBuffer(ByteBuf buf, ItemStack itemstack) throws IOException
    {
        if (itemstack == null)
        {
            buf.writeShort(-1);
        }
        else
        {
            buf.writeShort(Item.getIdFromItem(itemstack.getItem()));
            buf.writeByte(itemstack.stackSize);
            buf.writeShort(itemstack.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (itemstack.getItem().isDamageable() || itemstack.getItem().getShareTag())
            {
                nbttagcompound = itemstack.stackTagCompound;
            }

            writeNBTTagCompoundToBuffer(buf,nbttagcompound);
        }
    }

	/**
	 * copied from PacketBuffer
     * Reads an ItemStack from this buffer
     */
    public static ItemStack readItemStackFromBuffer(ByteBuf buf) throws IOException
    {
        ItemStack itemstack = null;
        short short1 = buf.readShort();

        if (short1 >= 0)
        {
            byte b0 = buf.readByte();
            short short2 = buf.readShort();
            itemstack = new ItemStack(Item.getItemById(short1), b0, short2);
            itemstack.stackTagCompound = readNBTTagCompoundFromBuffer(buf);
        }

        return itemstack;
    }
    
    /**
     * copied from PacketBuffer
     * Writes a compressed NBTTagCompound to this buffer
     */
    public static void writeNBTTagCompoundToBuffer(ByteBuf buf,NBTTagCompound compound) throws IOException
    {
        if (compound == null)
        {
            buf.writeShort(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.compress(compound);
            buf.writeShort((short)abyte.length);
            buf.writeBytes(abyte);
        }
    }

    /**
     * copied from PacketBuffer
     * Reads a compressed NBTTagCompound from this buffer
     */
    public static NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf buf) throws IOException
    {
        short short1 = buf.readShort();

        if (short1 < 0)
        {
            return null;
        }
        else
        {
            byte[] abyte = new byte[short1];
            buf.readBytes(abyte);
            return CompressedStreamTools.func_152457_a(abyte, new NBTSizeTracker(2097152L));
        }
    }
}
