package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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

        Model that = (Model) o;

        return uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
