package com.pknu.finalproject.officer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "OFFICER_ROLE")
@Getter
@Setter
@NoArgsConstructor
public class OfficerRole {

    @EmbeddedId
    private OfficerRoleId id = new OfficerRoleId();

    public void setAccountId(Long accountId) {
        this.id.setAccountId(accountId);
    }

    public void setRoleId(Long roleId) {
        this.id.setRoleId(roleId);
    }

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class OfficerRoleId implements Serializable {

        @Column(name = "ACCOUNT_ID")
        private Long accountId;

        @Column(name = "ROLE_ID")
        private Long roleId;
    }
}
