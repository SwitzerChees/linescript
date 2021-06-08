package ch.ffhs.pm.fac.instr;

public class InstructionIfStatement extends Instruction
{
   
    final Instruction conditionalStatement;
    
    final Instruction ifStatement;
    
    public InstructionIfStatement(Instruction conditionalStatement, Instruction ifStatement)
    {
        this.conditionalStatement = conditionalStatement;
        this.ifStatement = ifStatement;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitIfStatement(this);
    }
 

}
