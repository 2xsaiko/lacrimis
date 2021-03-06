package modfest.lacrimis.util;

import modfest.lacrimis.Lacrimis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworksState extends PersistentState {
    public static final String KEY = "lacrimis_network";
    private final Map<Integer, NetworkList> colorMap = new HashMap<>();

    public NetworksState() {
        super(KEY);
    }

    public NetworkList getNetwork(int color) {
        return colorMap.getOrDefault(color, null);
    }

    public NetworkList addLink(int color, BlockPos pos) {
        NetworkList l;
        if(colorMap.containsKey(color))
            l = colorMap.get(color);
        else {
            l = new NetworkList(color);
            colorMap.put(color, l);
        }

        if(!l.contains(pos))
            l.add(pos);
        markDirty();
        return l;
    }

    public void removeLink(int color, BlockPos pos) {
        if(colorMap.containsKey(color)) {
            colorMap.get(color).remove(pos);
            markDirty();
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        for(Tag networkTag : tag.getList("list", 10)) {
            int color = ((CompoundTag)networkTag).getInt("color");
            NetworkList list = new NetworkList(color);
            for(Tag pairTag : ((CompoundTag) networkTag).getList("list", 10)) {
                int x = ((CompoundTag) pairTag).getInt("X");
                int y = ((CompoundTag) pairTag).getInt("Y");
                int z = ((CompoundTag) pairTag).getInt("Z");
                BlockPos pos = new BlockPos(x, y, z);
                if(!list.contains(pos))
                    list.add(pos);
            }
            colorMap.put(color, list);
            Lacrimis.LOGGER.info("Loading network " + color + " " + list);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        ListTag list = new ListTag();
        for (NetworkList network : colorMap.values()) {
            if(network.size() > 0) {
                ListTag linkList = new ListTag();
                for (BlockPos pos : network) {
                    CompoundTag pairTag = new CompoundTag();
                    pairTag.putInt("X", pos.getX());
                    pairTag.putInt("Y", pos.getY());
                    pairTag.putInt("Z", pos.getZ());
                    linkList.add(pairTag);
                }

                CompoundTag networkTag = new CompoundTag();
                networkTag.putInt("color", network.color);
                networkTag.put("list", linkList);
                list.add(networkTag);
            }
        }
        tag.put("list", list);
        return tag;
    }

    public static class NetworkList extends ArrayList<BlockPos> {
        public int color;

        public NetworkList(int color) {
            this.color = color;
        }
    }
}
