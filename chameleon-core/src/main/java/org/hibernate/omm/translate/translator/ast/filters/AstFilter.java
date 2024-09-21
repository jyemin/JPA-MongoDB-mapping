package org.hibernate.omm.translate.translator.ast.filters;

import org.hibernate.omm.translate.translator.ast.AstNode;

public interface AstFilter extends AstNode {
    /**
     * @return true if this is filtering by equality of the _id field.  Useful for determining whether it's safe to do an
     * updateOne/deleteOne instead of updateMany/deleteMany.  The former is preferred if possible because the server
     * can automatically retry updateOne/deleteOne but not updateMany/deleteMany.
     */
    default boolean isIdEqualityFilter() {
        return false;
    }
}
