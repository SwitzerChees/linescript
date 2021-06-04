package ch.ffhs.pm.fac.instr;

import java.util.ArrayList;

public class InstructionFuncStatement extends Instruction {

    final String name;

    final ArrayList<String> parameters;

    final Instruction statement;

    public InstructionFuncStatement(String name, ArrayList<String> parameters, Instruction statement) {
        this.name = name;
        this.parameters = parameters;
        this.statement = statement;
    }

    @Override
    public <R> R acceptVisitor(InstructionVisitor<R> visitor) {
        return visitor.visitFuncStatement(this);
    }

    public String getParameters(){
        return String.join(",", parameters);
    }

}
