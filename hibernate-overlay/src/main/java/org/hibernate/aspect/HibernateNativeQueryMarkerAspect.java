/*
 *
 * Copyright 2008-present MongoDB, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.hibernate.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */

@Aspect
public class HibernateNativeQueryMarkerAspect {

    private static final String MQL_PARAMETER_MARKER = "{$undefined: true}";

    @Pointcut("execution(* org.hibernate.query.sql.internal.ParameterRecognizerImpl.*Parameter(..))")
    public void withinParameterRecognizerImpl() {}

    @Around("withinParameterRecognizerImpl()")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        final var returnValue = joinPoint.proceed();
        final var recognizer = (ParameterRecognizerImpl) joinPoint.getThis();
        final var field = ReflectHelper.findField(ParameterRecognizerImpl.class, "sqlStringBuffer");
        ReflectHelper.ensureAccessibility(field);
        final var sqlStringBuffer = (StringBuilder) field.get(recognizer);
        sqlStringBuffer.setLength(sqlStringBuffer.length() - 1);
        sqlStringBuffer.append(MQL_PARAMETER_MARKER);
        return returnValue;
    }
}
