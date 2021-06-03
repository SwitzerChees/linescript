package ch.ffhs.pm.fac.instr;

public class InstructionConditionalStatement extends Instruction
{

    final ConditionalExpression conditionalExpression;
    
    final Instruction leftOperand;
    
    final Instruction rightOperand;
    
    public InstructionConditionalStatement(ConditionalExpression conditionalExpression, Instruction leftOperand, Instruction rightOperand)
    {
        this.conditionalExpression = conditionalExpression;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitConditionalStatement(this);
    }
 
}
