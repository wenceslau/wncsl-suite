package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Random;
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
    @Column(columnDefinition = "uuid", updatable= false)
    private UUID uuid;
    @Column(updatable= false)
    private LocalDateTime created;
    private LocalDateTime updated;
    private String hash;

    public void generateHash(){
        this.hash = String.valueOf(new Random().nextInt(9999));
    }

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

    @Override
    public String toString() {
        return Util.toJson(this,true);
    }
}
