package io.github.tong12580.rpc.core.spring.factory;

import org.springframework.beans.factory.FactoryBean;

import static org.springframework.util.Assert.notNull;

/**
 * 映射工厂
 * MapperFactoryBean
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-7-11 11:25
 */
public class MapperFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mapperInterface;

    private boolean addToConfig = true;


    public MapperFactoryBean() {
        //intentionally empty
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * {@inheritDoc}
     */
//    @Override
//    protected void checkDaoConfig() {
//        super.checkDaoConfig();
//
//        notNull(this.mapperInterface, "Property 'mapperInterface' is required");
//
//        Configuration configuration = getSqlSession().getConfiguration();
//        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
//            try {
//                configuration.addMapper(this.mapperInterface);
//            } catch (Exception e) {
//                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
//                throw new IllegalArgumentException(e);
//            } finally {
//                ErrorContext.instance().reset();
//            }
//        }
//    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
