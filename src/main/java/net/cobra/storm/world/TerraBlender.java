package net.cobra.storm.world;

import net.cobra.storm.ThunderStorm;
import net.minecraft.util.Identifier;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class TerraBlender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(Identifier.of(ThunderStorm.MOD_ID, "overworld"), RegionType.OVERWORLD, 4));
    }
}
