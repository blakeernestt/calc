package be.calc.arithmetic;

public class NumericOperand implements Operand {

    private Integer value;

    public NumericOperand(Integer value){
        this.value = value;
    }

    @Override
    public Integer getValue(){
        return this.value;
    }
}
