package ch.ffhs.pm.fac.instr;

import java.util.List;

public class InstructionScript extends Instruction
{

    final List<Instruction> assignments;
        
    public InstructionScript(List<Instruction> assignments)
    {
        this.assignments = assignments;
    }
    
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitScript(this);
    }

}
