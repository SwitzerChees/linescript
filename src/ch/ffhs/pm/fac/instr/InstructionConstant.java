package ch.ffhs.pm.fac.instr;

import java.math.BigInteger;

public class InstructionConstant extends Instruction
{
    final Object value;
    
    public static InstructionConstant integer(String str)
    {
        return new InstructionConstant(new BigInteger(str));
    }
    
    public InstructionConstant(Object value)
    {
        this.value = value;
    }
    
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitConstant(this);
    }
}
