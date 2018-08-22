package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum RelationshipEnum {

    PARENTS(1),
    SPOUSE(2),
    FRIEND(3),
    CHILDREN(4),
    OTHER(5),
    ;

    private Integer relationship;

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    RelationshipEnum(Integer relationship) {
        this.relationship = relationship;
    }

    public static boolean isEmpty(Integer relationship){
        if (relationship == null){
            return true;
        }
        for(RelationshipEnum relationshipEnum : RelationshipEnum.values()){
            if(relationshipEnum.getRelationship().equals(relationship)){
                return false;
            }
        }
        return true;
    }
}
