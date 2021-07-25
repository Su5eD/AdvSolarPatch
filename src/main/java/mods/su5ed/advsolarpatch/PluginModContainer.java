package mods.su5ed.advsolarpatch;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class PluginModContainer extends DummyModContainer {

    public PluginModContainer() {
        super(getModMetadata());
    }
    
    @Override
    public List<ArtifactVersion> getDependencies() {
        return getMetadata().dependencies;
    }
    
    @Override
    public Set<ArtifactVersion> getRequirements() {
        return getMetadata().requiredMods;
    }
    
    private static ModMetadata getModMetadata() {
        InputStream is = PluginModContainer.class.getClassLoader().getResourceAsStream("advsolarpatch.info");
        if (is == null) throw new RuntimeException("Couldn't find mod metadata file");
        
        MetadataCollection metadata = MetadataCollection.from(is, "AdvSolarPatch");
        return metadata.getMetadataForId("advsolarpatch", null);
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
