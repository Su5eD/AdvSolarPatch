package mods.su5ed.advsolarpatch;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static org.objectweb.asm.Opcodes.*;

public class ClassTransformer implements IClassTransformer {
    private static final Map<String, BiFunction<Integer, ClassVisitor, ClassVisitor>> TRANSFORMERS = new HashMap<>();
    
    static {
        TRANSFORMERS.put("com.chocohead.advsolar.tiles.TileEntityMolecularAssembler$1", InvSlotProcessableVisitor::new);
        TRANSFORMERS.put("com.chocohead.advsolar.tiles.TileEntityMolecularAssembler", MolecularAssemblerVisitor::new);
        TRANSFORMERS.put("com.chocohead.advsolar.slots.InvSlotMultiCharge", InvSlotMultiChargeVisitor::new);
        TRANSFORMERS.put("com.chocohead.advsolar.items.ItemDoubleSlab", ItemDoubleSlabVisitor::new);
    }
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (TRANSFORMERS.containsKey(name)) {
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                        
            reader.accept(TRANSFORMERS.get(name).apply(ASM4, writer), 0);
                        
            return writer.toByteArray();
        }
        
        return bytes;
    }

    private static class InvSlotProcessableVisitor extends ClassVisitor {

        public InvSlotProcessableVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new InvSlotProcessableMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
        }

        private static class InvSlotProcessableMethodVisitor extends MethodVisitor {

            public InvSlotProcessableMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                //Patch anonymous class in TileEntityMolecularAssembler
                if (opcode == INVOKESPECIAL
                        && owner.equals("ic2/core/block/invslot/InvSlotProcessable")
                        && name.equals("<init>")
                        && desc.equals("(Lic2/core/block/TileEntityInventory;Ljava/lang/String;ILic2/api/recipe/IMachineRecipeManager;)V")
                        && !itf) {
                    super.visitMethodInsn(opcode, owner, name, desc.replace("ic2/core/block/TileEntityInventory", "ic2/core/block/IInventorySlotHolder"), false);
                }
                else super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }
    
    private static class MolecularAssemblerVisitor extends ClassVisitor {
        
        public MolecularAssemblerVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new MolecularAssemblerMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
        }

        private static class MolecularAssemblerMethodVisitor extends MethodVisitor {

            public MolecularAssemblerMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                //Patch TileEntityMolecularAssembler
                if (opcode == INVOKESPECIAL 
                        && owner.equals("ic2/core/block/invslot/InvSlotOutput") 
                        && name.equals("<init>") 
                        && desc.equals("(Lic2/core/block/TileEntityInventory;Ljava/lang/String;I)V") 
                        && !itf) {
                    super.visitMethodInsn(opcode, owner, name, desc.replace("ic2/core/block/TileEntityInventory", "ic2/core/block/IInventorySlotHolder"), false);
                }
                else super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }

    private static class InvSlotMultiChargeVisitor extends ClassVisitor {

        public InvSlotMultiChargeVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new InvSlotMultiChargeMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
        }

        private static class InvSlotMultiChargeMethodVisitor extends MethodVisitor {

            public InvSlotMultiChargeMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (opcode == INVOKESPECIAL
                        && owner.equals("ic2/core/block/invslot/InvSlot")
                        && name.equals("<init>")
                        && desc.equals("(Lic2/core/block/TileEntityInventory;Ljava/lang/String;Lic2/core/block/invslot/InvSlot$Access;ILic2/core/block/invslot/InvSlot$InvSide;)V")
                        && !itf) {
                    super.visitMethodInsn(opcode, owner, name, desc.replace("ic2/core/block/TileEntityInventory", "ic2/core/block/IInventorySlotHolder"), false);
                } else super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }
    
    private static class ItemDoubleSlabVisitor extends ClassVisitor {

        public ItemDoubleSlabVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new ItemDoubleSlabMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
        }
        
        private static class ItemDoubleSlabMethodVisitor extends MethodVisitor {
            private boolean replaceInsn;
            
            public ItemDoubleSlabMethodVisitor(int api, MethodVisitor mv) {
                super(api, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (opcode == INVOKEVIRTUAL 
                        && owner.equals("net/minecraft/entity/player/EntityPlayer") 
                        && name.equals("canPlayerEdit") 
                        && desc.equals("(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z") 
                        && !itf) {
                    replaceInsn = true;
                }
                else if (opcode == INVOKEVIRTUAL 
                        && owner.equals("net/minecraft/item/ItemStack") 
                        && name.equals("getMetadata") 
                        && desc.equals("()I") 
                        && !itf) {
                    replaceInsn = false;
                }
                
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }

            @Override
            public void visitVarInsn(int opcode, int var) {
                if (replaceInsn && opcode == ALOAD && var == 10) {
                    visitVarInsn(ALOAD, 0);
                    visitFieldInsn(GETFIELD, "net/minecraft/item/ItemBlock", "block", "Lnet/minecraft/block/Block;");
                }
                else super.visitVarInsn(opcode, var);
            }
        }
    }
}
