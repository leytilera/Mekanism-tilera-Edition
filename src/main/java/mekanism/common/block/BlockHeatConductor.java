package mekanism.common.block;

import mekanism.common.tile.TileEntityHeatConductor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeatConductor extends BlockContainer {
    public BlockHeatConductor() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World arg0, int arg1) {
        return new TileEntityHeatConductor();
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon("mekanism:HeatConductor");
    }
}
