package ch.ffhs.pm.fac.instr;

public class InstructionSetVariable extends Instruction
{
    final String name;
    
    final Instruction value;
    
    public InstructionSetVariable(String name, Instruction value)
    {
        this.name = name;
        this.value = value;
    }
    
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitSetVariable(this);
    }
}
