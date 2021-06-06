package ch.ffhs.pm.fac.instr;

public class InstructionSetVariable extends Instruction {
    final String name;

    final AssignOperator assignOperator;

    final Instruction value;

    public InstructionSetVariable(String name, AssignOperator assignOperator, Instruction value) {
        this.name = name;
        this.assignOperator = assignOperator;
        this.value = value;
    }

    public <R> R acceptVisitor(InstructionVisitor<R> visitor) {
        return visitor.visitSetVariable(this);
    }
}
