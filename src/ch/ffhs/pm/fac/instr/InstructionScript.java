package ch.ffhs.pm.fac.instr;

import java.util.List;

public class InstructionScript extends Instruction
{

    final List<Instruction> assignments;
    
    final Instruction lastInstruction;
        
    public InstructionScript(List<Instruction> assignments, Instruction lastInstruction)
    {
        this.assignments = assignments;
        this.lastInstruction = lastInstruction;
    }
    
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitScript(this);
    }

}
