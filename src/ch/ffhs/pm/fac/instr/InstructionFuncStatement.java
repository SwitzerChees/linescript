package ch.ffhs.pm.fac.instr;

import java.util.ArrayList;

public class InstructionFuncStatement extends Instruction {

    final String name;

    final ArrayList<String> parameters;

    final ArrayList<Instruction> statementList;

    public InstructionFuncStatement(String name, ArrayList<String> parameters, ArrayList<Instruction> statementList) {
        this.name = name;
        this.parameters = parameters;
        this.statementList = statementList;
    }

    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor) {
        return visitor.visitFuncStatement(this);
    }

    public String getParameters(){
        return String.join(",", parameters);
    }

}
