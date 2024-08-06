/**
 * This package contains various JDBC related adapter interfaces (as in the sense of Java's Swing internal event adapter classes)
 * so Mongo specific JDBC classes don't end up with implementing an intimidatingly long interface method list.
 * They could implement the corresponding adapter interface instead and focus on the
 * methods they need to override alone.
 * <p>
 * Only interfaces were provided in this package so Mongo specific JDBC classes could extend from other parent classes.
 * <p>
 * The downside of such adapter usage is the overlooking of overriding method. Once that happened,
 * some exception would be thrown from its parent adapter class/interface and then we need to override it.
 * <p>
 * Another caveat is once JDBC interface added new methods, we might fail to sync up in these adapter interfaces, but
 * then all the child Mongo specific classes compilation would fail and then we need to either override or add it
 * to the adapter interface here.
 */
package org.hibernate.omm.jdbc.adapter;
