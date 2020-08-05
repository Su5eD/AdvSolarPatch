# Advanced Solars Patcher
Patches the Advanced Solar Panels mod to work with newer IC2 builds (2.8.191+)

Fixed errors:
- `java.lang.NoSuchMethodError: ic2.core.block.invslot.InvSlotProcessable.<init>(Lic2/core/block/TileEntityInventory;Ljava/lang/String;ILic2/api/recipe/IMachineRecipeManager;)V`
- `java.lang.NoSuchMethodError: ic2.core.block.invslot.InvSlotOutput.<init>(Lic2/core/block/TileEntityInventory;Ljava/lang/String;I)V`
- `java.lang.NoSuchMethodError: ic2.core.block.invslot.InvSlot.<init>(Lic2/core/block/TileEntityInventory;Ljava/lang/String;Lic2/core/block/invslot/InvSlot$Access;ILic2/core/block/invslot/InvSlot$InvSide;)V`

Side note: These errors can be fixed by just re-compiling the mod with IC2 2.8.191 or newer. Because the source is not available, and the developer hasn't updated it since March 9th 2020 when the errors were first reported, I decided to make this small patcher.