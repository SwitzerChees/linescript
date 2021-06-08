package ch.ffhs.pm.fac.instr;

public class InstructionElseStatement extends Instruction
{
   
    final Instruction ifStatement;
    
    final Instruction elseStatement;
    
    public InstructionElseStatement(Instruction ifStatement, Instruction elseStatement)
    {
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitElseStatement(this);
    }
 

}
