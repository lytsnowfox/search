package personal.liyitong.search.entity;

import org.neo4j.ogm.annotation.Id;

public abstract class AllBaseEntity {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (this.id == null) {
            // For newly created entity, id will be null
            return false;
        }

        AllBaseEntity entity = (AllBaseEntity) obj;
        return this.id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }
}
