package mekanism.common.base;

import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public interface IGregtechEnergy extends IEnergyConnected {

    void writeToNBT(NBTTagCompound nbt);
    
    void readFromNBT(NBTTagCompound nbt);

    default TileEntity selfAsTileEntity() {
        return (TileEntity) this;
    }

    // BEGIN BULLSHIT

    @Override
    default byte getColorization() {
        return 0;
    }

    @Override
    default byte setColorization(byte arg0) {
        return arg0;
    }

    @Override
    default boolean getAir(int x, int y, int z) {
         return GT_Utility.isBlockAir(this.getWorld(), x, y, z);
    }

    @Override
    default boolean getAirAtSide(byte side) {
        return this.getAirAtSideAndDistance(side, 1);
    }

    @Override
    default boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
        return this.getAir(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default boolean getAirOffset(int x, int y, int z) {
        return this.getAir(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default BiomeGenBase getBiome() {
        return this.getBiome(this.getXCoord(), this.getZCoord());
    }

    @Override
    default BiomeGenBase getBiome(int aX, int aZ) {
        return this.getWorld().getBiomeGenForCoords(aX, aZ);
    }

    @Override
    default Block getBlock(int x, int y, int z) {
        return this.getWorld().getBlock(x, y, z);
    }

    @Override
    default Block getBlockAtSide(byte aSide) {
        return this.getBlockAtSideAndDistance(aSide, 1);
    }

    @Override
    default Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
        return this.getBlock(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default Block getBlockOffset(int aX, int aY, int aZ) {
        return this.getBlock(this.getXCoord() + aX, this.getYCoord() + aY, this.getZCoord() + aZ);
    }

    @Override
    default IGregTechTileEntity getIGregTechTileEntity(int arg0, int arg1, int arg2) {
        TileEntity te = this.getWorld().getTileEntity(arg0, arg1, arg2);
        if (!(te instanceof IGregTechTileEntity))
            return null;

        return (IGregTechTileEntity) te;
    }

    @Override
    default IGregTechTileEntity getIGregTechTileEntityAtSide(byte arg0) {
        return this.getIGregTechTileEntityAtSideAndDistance(arg0, 1);
    }

    @Override
    default IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        return this.getIGregTechTileEntity(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default IGregTechTileEntity getIGregTechTileEntityOffset(int x, int y, int z) {
        return this.getIGregTechTileEntity(
            this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z
        );
    }

    @Override
    default IInventory getIInventory(int arg0, int arg1, int arg2) {
        TileEntity te = this.getWorld().getTileEntity(arg0, arg1, arg2);
        if (!(te instanceof IInventory))
            return null;

        return (IInventory) te;
    }

    @Override
    default IInventory getIInventoryAtSide(byte arg0) {
        return this.getIInventoryAtSideAndDistance(arg0, 1);
    }

    @Override
    default IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {
        return this.getIInventory(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default IInventory getIInventoryOffset(int x, int y, int z) {
        return this.getIInventory(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default IFluidHandler getITankContainer(int arg0, int arg1, int arg2) {
        TileEntity te = this.getWorld().getTileEntity(arg0, arg1, arg2);
        if (!(te instanceof IFluidHandler))
            return null;

        return (IFluidHandler) te;
    }

    @Override
    default IFluidHandler getITankContainerAtSide(byte arg0) {
        return this.getITankContainerAtSideAndDistance(arg0, 1);
    }

    @Override
    default IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
        return this.getITankContainer(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default IFluidHandler getITankContainerOffset(int x, int y, int z) {
        return this.getITankContainer(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default byte getLightLevel(int aX, int aY, int aZ) {
        return (byte) (this.getWorld().getLightBrightness(aX, aY, aZ) * 15.0F);
    }

    @Override
    default byte getLightLevelAtSide(byte arg0) {
        return this.getLightLevelAtSideAndDistance(arg0, 1);
    }

    @Override
    default byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {
        return this.getLightLevel(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default byte getLightLevelOffset(int x, int y, int z) {
        return this.getLightLevel(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default byte getMetaID(int x, int y, int z) {
        return (byte) this.getWorld().getBlockMetadata(x, y, z);
    }

    @Override
    default byte getMetaIDAtSide(byte arg0) {
        return this.getMetaIDAtSideAndDistance(arg0, 1);
    }

    @Override
    default byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
        return this.getMetaID(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default byte getMetaIDOffset(int x, int y, int z) {
        return this.getMetaID(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default int getOffsetX(byte aSide, int aMultiplier) {
        return this.getXCoord() + ForgeDirection.getOrientation(aSide).offsetX * aMultiplier;
    }

    @Override
    default short getOffsetY(byte aSide, int aMultiplier) {
        return (short
        ) (this.getYCoord() + ForgeDirection.getOrientation(aSide).offsetY * aMultiplier);
    }

    @Override
    default int getOffsetZ(byte aSide, int aMultiplier) {
        return this.getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ * aMultiplier;
    }

    @Override
    default boolean getOpacity(int x, int y, int z) {
        return GT_Utility.isOpaqueBlock(this.getWorld(), x, y, z);
    }

    @Override
    default boolean getOpacityAtSide(byte arg0) {
        return this.getOpacityAtSideAndDistance(arg0, 1);
    }

    @Override
    default boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
        return this.getOpacity(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default boolean getOpacityOffset(int x, int y, int z) {
        return this.getOpacity(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default int getRandomNumber(int max) {
        return this.getWorld().rand.nextInt(max);
    }

    @Override
    default boolean getSky(int x, int y, int z) {
        return this.getWorld().canBlockSeeTheSky(x, y, z);
    }

    @Override
    default boolean getSkyAtSide(byte arg0) {
        return this.getSkyAtSideAndDistance(arg0, 1);
    }

    @Override
    default boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
        return this.getSky(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default boolean getSkyOffset(int x, int y, int z) {
        return this.getSky(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    @Override
    default TileEntity getTileEntity(int x, int y, int z) {
        return this.getWorld().getTileEntity(x, y, z);
    }

    @Override
    default TileEntity getTileEntityAtSide(byte arg0) {
        return this.getTileEntityAtSideAndDistance(arg0, 1);
    }

    @Override
    default TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        return this.getTileEntity(
            this.getOffsetX(aSide, aDistance),
            this.getOffsetY(aSide, aDistance),
            this.getOffsetZ(aSide, aDistance)
        );
    }

    @Override
    default TileEntity getTileEntityOffset(int x, int y, int z) {
        return this.getTileEntity(x, y, z);
    }

    @Override
    default long getTimer() {
        // TODO: WTF
        return 0;
    }

    @Override
    default World getWorld() {
        return this.selfAsTileEntity().getWorldObj();
    }

    @Override
    default int getXCoord() {
        return this.selfAsTileEntity().xCoord;
    }

    @Override
    default short getYCoord() {
        return (short) this.selfAsTileEntity().yCoord;
    }

    @Override
    default int getZCoord() {
        return this.selfAsTileEntity().zCoord;
    }

    @Override
    default boolean isClientSide() {
        return this.getWorld().isRemote;
    }

    @Override
    default boolean isDead() {
        return this.isInvalidTileEntity();
    }

    @Override
    default boolean isInvalidTileEntity() {
        return this.selfAsTileEntity().isInvalid();
    }

    @Override
    default boolean isServerSide() {
        return !this.isClientSide();
    }

    @Override
    default boolean openGUI(EntityPlayer arg0) {
        throw new UnsupportedOperationException("alec");
    }

    @Override
    default boolean openGUI(EntityPlayer arg0, int arg1) {
        throw new UnsupportedOperationException("alec");
    }

    @Override
    default void sendBlockEvent(byte arg0, byte arg1) {
        throw new UnsupportedOperationException("alec");
    }

    @Override
    default void setLightValue(byte arg0) {
        throw new UnsupportedOperationException("alec");
    }

    // END BULLSHIT
    
}
