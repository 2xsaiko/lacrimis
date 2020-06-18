package modfest.lacrimis.block.entity;

import modfest.lacrimis.crafting.InfusionInventory;
import modfest.lacrimis.init.ModBlockEntityTypes;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CombinerEntity extends SoulTankEntity {
    public EntityType<?> type;
    public InfusionInventory inventory;
    
    public CombinerEntity() {
        super(ModBlockEntityTypes.combiner, 0);
        inventory = new InfusionInventory(this, 2);
        type = null;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        if(!tag.getString("entity").equals("null"))
            type = Registry.ENTITY_TYPE.get(Identifier.tryParse(tag.getString("entity")));
        inventory.clear();
        inventory.readTags(tag.getList("Inventory", NbtType.COMPOUND));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(type != null)
            tag.putString("entity", Registry.ENTITY_TYPE.getId(type).toString());
        else
            tag.putString("entity", "null");
        tag.put("Inventory", this.inventory.getTags());
        return super.toTag(tag);
    }
}