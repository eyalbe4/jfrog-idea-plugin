package org.jfrog.idea.xray.persistency.types;

import com.intellij.openapi.diagnostic.Logger;
import com.jfrog.xray.client.services.summary.General;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by romang on 4/12/17.
 */
public class GeneralInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final transient Logger log = Logger.getInstance(GeneralInfo.class);

    public String componentId = "";
    public String name = "";
    public String path = "";
    public String pkgType = "";
    public String groupId = "";
    public String artifactId = "";
    public String version = "";
    public String sha256 = "";

    public GeneralInfo() {
    }

    public GeneralInfo(General general) {
        componentId = general.getComponentId();
        name = general.getName();
        path = general.getPath();
        pkgType = general.getPkgType();
        sha256 = general.getSha256();
    }

    public String getGroupId() {
        if (StringUtils.isNotBlank(groupId)) {
            return groupId;
        }
        return isValid() ? componentId.substring(0, componentId.indexOf(":")) : "";
    }

    public String getArtifactId() {
        if (StringUtils.isNotBlank(artifactId)) {
            return artifactId;
        }
        if (!isValid()) {
            return "";
        }
        int indexOfColon = componentId.indexOf(":");
        if (StringUtils.countMatches(componentId, ":") == 1) {
            return componentId.substring(0, indexOfColon);
        }
        return componentId.substring(indexOfColon + 1, componentId.lastIndexOf(":"));
    }

    public String getVersion() {
        if (StringUtils.isNotBlank(version)) {
            return version;
        }
        return isValid() ? componentId.substring(componentId.lastIndexOf(":") + 1) : "";
    }

    public GeneralInfo componentId(String componentId) {
        this.componentId = componentId;
        return this;
    }

    public GeneralInfo name(String name) {
        this.name = name;
        return this;
    }

    public GeneralInfo path(String path) {
        this.path = path;
        return this;
    }

    public GeneralInfo groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public GeneralInfo artifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public GeneralInfo version(String version) {
        this.version = version;
        return this;
    }

    public GeneralInfo pkgType(String pkgType) {
        this.pkgType = pkgType;
        return this;
    }

    public GeneralInfo sha256(String sha256) {
        this.sha256 = sha256;
        return this;
    }

    private boolean isValid() {
        int colonCount = StringUtils.countMatches(componentId, ":");
        if (colonCount != 1 && colonCount != 2) {
            log.warn("Bad component ID structure. Should be <GroupID>:<ArtifactID>:<Version> or <ArtifactID>:<Version>, got '" + componentId + "'");
            return false;
        }
        return true;
    }
}