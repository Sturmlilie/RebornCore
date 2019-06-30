/*
 * Copyright (c) 2018 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package reborncore.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import reborncore.api.items.InventoryBase;
import reborncore.common.tile.SlotConfiguration;
import reborncore.common.tile.TileMachineBase;

import javax.annotation.Nonnull;

public class RebornInventory<T extends TileMachineBase> extends InventoryBase {

	private final String name;
	private final int stackLimit;
	private T tile;
	private boolean hasChanged = false;
	private IInventoryAccess<T> inventoryAccess;

	public RebornInventory(int size, String invName, int invStackLimit, T tileEntity, IInventoryAccess<T> access) {
		super(size);
		name = invName;
		stackLimit = (invStackLimit == 64 ? Items.AIR.getMaxCount() : invStackLimit); //Blame asie for this
		this.tile = tileEntity;
		this.inventoryAccess = access;
	}

	//If you are using this with a machine, dont forget to set .withConfiguredAccess()
	public RebornInventory(int size, String invName, int invStackLimit, T tileEntity) {
		this(size, invName, invStackLimit, tileEntity, (slotID, stack, facing, direction, tile) -> true);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void setInvStack(int slot,
	                           @Nonnull
		                           ItemStack stack) {
		super.setInvStack(slot, stack);
		setChanged();
	}

	public ItemStack shrinkSlot(int slot, int count) {
		ItemStack stack = getInvStack(slot);
		stack.decrement(count);
		setChanged();
		return stack;
	}


	public RebornInventory getExternal(Direction facing) {
		throw new UnsupportedOperationException("needs fixing");
		//return externalInventory.withFacing(facing);
	}

	public boolean configuredAccess;

	/**
	 * This enables the default IO access that is setup to use the SlotConfiguration of the tile
	 */
	public RebornInventory<T> withConfiguredAccess() {
		configuredAccess = true;
		this.inventoryAccess = (slotID, stack, facing, direction, tile) -> {
			if(facing == null){
				return true;
			}
			switch (direction) {
				case INSERT:
					return SlotConfiguration.canInsertItem(slotID, stack, facing, tile);
				case EXTRACT:
					return SlotConfiguration.canExtractItem(slotID, stack, facing, tile);
			}
			return false;
		};
		return this;
	}

	public void read(CompoundTag data) {
		read(data, "Items");
	}

	public void read(CompoundTag data, String tag) {
		CompoundTag nbttaglist = data.getCompound(tag);
		deserializeNBT(nbttaglist);
		hasChanged = true;
	}

	public void write(CompoundTag data) {
		write(data, "Items");
	}

	public void write(CompoundTag data, String tag) {
		data.put(tag, serializeNBT());
	}



	public int getContents() {
		int count = 0;
		for (ItemStack stack : getStacks()) {
			if (stack.isEmpty()) {
				continue;
			}
			count += stack.getCount();
		}
		return count;
	}

	public void setTile(T tileEntity) {
		tile = tileEntity;
	}

	public T getTile() {
		return tile;
	}

	public boolean hasChanged() {
		return hasChanged;
	}

	public void setChanged() {
		this.hasChanged = true;
	}

	public void setChanged(boolean changed) {
		this.hasChanged = changed;
	}

	public void resetChanged() {
		this.hasChanged = false;
	}

	public int getStackLimit() {
		return stackLimit;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		tile.markDirty();
	}

}