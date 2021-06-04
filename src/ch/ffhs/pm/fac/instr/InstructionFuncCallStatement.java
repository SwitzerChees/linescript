package ch.ffhs.pm.fac.instr;

import java.util.ArrayList;

public class InstructionFuncCallStatement extends Instruction {

    final String name;

    final ArrayList<Instruction> statements;

    public InstructionFuncCallStatement(String name, ArrayList<Instruction> statements) {
        this.name = name;
        this.statements = statements;
    }

    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor) {
        return visitor.visitFuncCallStatement(this);
    }

}
