package ch.ffhs.pm.fac.instr;

public class InstructionBinaryOperation extends Instruction
{

    final BinaryOperator operator;
    
    final Instruction leftOperand;
    
    final Instruction rightOperand;
    
    public InstructionBinaryOperation(BinaryOperator operator, Instruction leftOperand, Instruction rightOperand)
    {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitBinaryOperation(this);
    }
 

}
