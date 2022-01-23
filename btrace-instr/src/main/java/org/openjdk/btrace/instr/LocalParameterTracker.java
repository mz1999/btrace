package org.openjdk.btrace.instr;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

final class LocalParameterTracker {
  private final Map<String, Integer> paramMap = new HashMap<>();

  public int getParameter(
      String clzName,
      String methoName,
      String paramName,
      MethodInstrumentorHelper mHelper,
      MethodVisitor mv) {
    return paramMap.computeIfAbsent(
        clzName + "#" + methoName + "#" + paramName,
        k -> {
          mv.visitTypeInsn(Opcodes.NEW, Constants.VALUE_HOLDER_INTERNAL);
          mv.visitInsn(Opcodes.DUP);
          mv.visitMethodInsn(
              Opcodes.INVOKESPECIAL,
              "org/openjdk/btrace/runtime/ValueHolder",
              "<init>",
              "()V",
              false);
          return mHelper.storeAsNew();
        });
  }
}
