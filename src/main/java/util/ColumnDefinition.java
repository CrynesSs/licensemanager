package util;

public record ColumnDefinition(SQLDataType dataType, SQLConstraint... constraints) {
    public enum SQLDataType {
        SERIALKEY,
        ENUM,
        UUID,
        DATE,
        VARCHAR,
        INT
    }
    public String resolveColumnDefinition(){
        if(constraints.length == 0)return dataType.name();
        StringBuilder builder = new StringBuilder();
        builder.append(dataType != SQLDataType.SERIALKEY ? dataType.name() : "").append(" ");
        for(SQLConstraint constraint : constraints){
            if(constraint == SQLConstraint.DEFAULT && dataType == SQLDataType.UUID){
                builder.append(constraint.name()).append(" ");
                builder.append("uuid_generate_v4() ");
                continue;
            }
            //Check if it is a Varchar with length
            if(constraint.length() > 0){
                builder.deleteCharAt(builder.length() - 1);
                builder.append("(").append(constraint.length()).append(")").append(" ");
                continue;
            }
            builder.append(constraint.name()).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
