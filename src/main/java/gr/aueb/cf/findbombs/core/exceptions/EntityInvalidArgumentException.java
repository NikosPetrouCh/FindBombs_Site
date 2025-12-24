package gr.aueb.cf.findbombs.core.exceptions;

public class EntityInvalidArgumentException extends RuntimeException {
    private final String entityName;

    public EntityInvalidArgumentException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}


