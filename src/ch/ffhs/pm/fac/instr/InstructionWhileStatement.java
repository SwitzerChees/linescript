package ch.ffhs.pm.fac.instr;

import java.util.ArrayList;

public class InstructionWhileStatement extends Instruction
{
   
    final Instruction conditionalStatement;
    
    ArrayList<Instruction> statementList;
    
    public InstructionWhileStatement(Instruction conditionalStatement, ArrayList<Instruction> statementList)
    {
        this.conditionalStatement = conditionalStatement;
        this.statementList = statementList;
    }
    
    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor)
    {
        return visitor.visitWhileStatement(this);
    }
 

}
