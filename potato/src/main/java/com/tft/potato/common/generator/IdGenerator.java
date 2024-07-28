package com.tft.potato.common.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class IdGenerator implements IdentifierGenerator, Configurable {

    // 엔티티의 시퀀스들을 상수로 넣어놓고 class simple name으로 커스텀 매핑한다.
    private static final String USER_ID_PREFIX = "US";
    private static final String USER_ROLE_PREFIX = "UR";

    private String prefix;


    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = ConfigurationHelper.getString("userIdPrefix", params);

        //IdentifierGenerator.super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        String seq = "";
        String classSimpleName = o.getClass().getSimpleName();


        String query = String.format("select %s from %s",
                        // Entity Class full path로부터 @Id 필드를 가져온다.
                        session.getEntityPersister(o.getClass().getName(), o).getIdentifierPropertyName()
                        // Entity Class의 클래스명만 가져온다.
                        , classSimpleName);



        // User 엔티티의 경우의 seq generate
        if(classSimpleName.equals("User")){
            Stream<String> ids = session.createQuery(query).stream();
            int max = ids.mapToInt(str -> Integer.valueOf(str.replace(prefix, "")) )
                    .max()
                    .orElse(0);

            seq = String.format("%011d", max + 1);
        }

        return prefix + seq;
    }

    @Override
    public void configure(Map map) {

    }
}
