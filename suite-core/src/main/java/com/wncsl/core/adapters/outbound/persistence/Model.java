package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(ListenerModel.class)
public class Model {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionModel that = (PermissionModel) o;

        return uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
