package ch.ffhs.pm.fac.instr;

public class InstructionIfElseStatement extends Instruction
{
   
    final InstructionConditionalStatement conditionalStatement;
    
    final Instruction ifStatement;

    final Instruction elseStatement;
    
    public InstructionIfElseStatement(InstructionConditionalStatement conditionalStatement, Instruction ifStatement, Instruction elseStatement)
    {
        this.conditionalStatement = conditionalStatement;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitIfElseStatement(this);
    }
 

}
