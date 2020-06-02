package br.com.paulork.redisexample.commons.error;

import br.com.paulork.redisexample.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Autowired
    private LoggerUtils logger;

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        logger.error("Error on GET info from cache with key ["+key+"].", exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        logger.error("Error on PUT info in cache with key ["+key+"].", exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        logger.error("Error while EVICT cache with key ["+key+"].", exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        logger.error("Error on CLEAR cache.", exception);
    }

}
