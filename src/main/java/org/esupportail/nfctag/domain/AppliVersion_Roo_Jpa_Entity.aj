// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.nfctag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.esupportail.nfctag.domain.AppliVersion;

privileged aspect AppliVersion_Roo_Jpa_Entity {
    
    declare @type: AppliVersion: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AppliVersion.id;
    
    @Version
    @Column(name = "version")
    private Integer AppliVersion.version;
    
    public Long AppliVersion.getId() {
        return this.id;
    }
    
    public void AppliVersion.setId(Long id) {
        this.id = id;
    }
    
    public Integer AppliVersion.getVersion() {
        return this.version;
    }
    
    public void AppliVersion.setVersion(Integer version) {
        this.version = version;
    }
    
}
