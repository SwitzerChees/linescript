package ch.ffhs.pm.fac.instr;

public class InstructionSetVariable extends Instruction {
    final String name;

    final AssignOperator assignOperator;

    final InstructionConstant value;

    public InstructionSetVariable(String name, AssignOperator assignOperator, InstructionConstant value) {
        this.name = name;
        this.assignOperator = assignOperator;
        this.value = value;
    }

    public <R> R acceptVisitor(InstructionVisitor<R> visitor) {
        return visitor.visitSetVariable(this);
    }
}
