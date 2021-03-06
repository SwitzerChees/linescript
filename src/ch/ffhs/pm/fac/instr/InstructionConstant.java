package ch.ffhs.pm.fac.instr;
import java.math.BigDecimal;

public class InstructionConstant extends Instruction
{
    final Object value;
    final VariableType variableType;
    
    public static InstructionConstant number(String str)
    {
        return new InstructionConstant(new BigDecimal(str.trim()), VariableType.NUMBER);
    }

    public static InstructionConstant instruction(Instruction instr)
    {
        return new InstructionConstant(instr, VariableType.NUMBER);
    }

    public static InstructionConstant string(String str)
    {
        return new InstructionConstant(str.replace("\"", ""), VariableType.STRING);
    }

    public static InstructionConstant bool(boolean val)
    {
        return new InstructionConstant(val, VariableType.BOOLEAN);
    }
    
    public InstructionConstant(Object value, VariableType variableType)
    {
        this.value = value;
        this.variableType = variableType;
    }
    
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitConstant(this);
    }
}
