package club.deltapvp.core.core;

import lombok.Data;

@Data
public class Replacer<I, O> {

    private I input;
    private O output;

    public Replacer(I input, O output) {
        this.input = input;
        this.output = output;
    }
}
