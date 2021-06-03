package ch.ffhs.pm.fac.instr;

public interface InstructionVisitor<R>
{
    public R visitScript(InstructionScript instructionScript);

    public R visitBinaryOperation(InstructionBinaryOperation instructionBinOperation);

    public R visitNegation(InstructionNegate instructionUnaryOperation);

    public R visitConstant(InstructionConstant instructionConstant);

    public R visitGetVariable(InstructionGetVariable instructionGetVariable);

    public R visitSetVariable(InstructionSetVariable instructionSetVariable);


}
