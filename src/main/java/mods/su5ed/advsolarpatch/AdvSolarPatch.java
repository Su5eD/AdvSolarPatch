package mods.su5ed.advsolarpatch;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = AdvSolarPatch.MODID, name = AdvSolarPatch.NAME, version = AdvSolarPatch.VERSION)
public class AdvSolarPatch
{
    public static final String MODID = "advsolarpatch";
    public static final String NAME = "Advanced Solar Panels Patcher";
    public static final String VERSION = "1.1";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
