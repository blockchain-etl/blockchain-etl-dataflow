package io.blockchainetl.bitcoinetl.fns;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ParseEntitiesFromJsonFn<E> extends ErrorHandlingDoFn<String, E> {
    
    private Class<E> clazz;

    public ParseEntitiesFromJsonFn(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void doProcessElement(ProcessContext c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        E transaction = mapper.readValue(c.element(), this.clazz);
        c.output(transaction);
    }
}
