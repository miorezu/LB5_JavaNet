import java.io.Serial;
import java.io.Serializable;

public class Factorial implements Executable, Serializable {

    @Serial
    private final static long serialVersionUID = -1L;
    private int number;

    public Factorial(int number) {
        this.number = number;
    }

    @Override
    public Object execute() {
        long result = 1;
        for (long i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}