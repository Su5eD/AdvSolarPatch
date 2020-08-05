package mods.su5ed.advsolarpatch.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class AClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name.contains("com.chocohead.advsolar.tiles.TileEntityMolecularAssembler") || name.equalsIgnoreCase("com.chocohead.advsolar.slots.InvSlotMultiCharge"))
            return patchTileEntityMolecularAssembler(bytes);
        return bytes;
    }

    private byte[] patchTileEntityMolecularAssembler(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        for (MethodNode m : classNode.methods) {
            if (classNode.methods.indexOf(m) == 0) {
                if (classNode.name.equalsIgnoreCase("com/chocohead/advsolar/tiles/TileEntityMolecularAssembler$1")) {
                    //Patch anonymous class in TileEntityMolecularAssembler
                    ((MethodInsnNode) m.instructions.get(10)).desc = "(Lic2/core/block/IInventorySlotHolder;Ljava/lang/String;ILic2/api/recipe/IMachineRecipeManager;)V";
                }
                else if (classNode.name.equalsIgnoreCase("com/chocohead/advsolar/tiles/TileEntityMolecularAssembler")) {
                    //Patch TileEntityMolecularAssembler
                    ((MethodInsnNode) m.instructions.get(24)).desc = "(Lic2/core/block/IInventorySlotHolder;Ljava/lang/String;I)V";
                }
                else if (classNode.name.contains("InvSlotMultiCharge")) {
                    //Patch InvSlotMultiCharge
                    ((MethodInsnNode) m.instructions.get(8)).desc = "(Lic2/core/block/IInventorySlotHolder;Ljava/lang/String;Lic2/core/block/invslot/InvSlot$Access;ILic2/core/block/invslot/InvSlot$InvSide;)V";
                }
            }
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(cw);
        return cw.toByteArray();
    }
}
