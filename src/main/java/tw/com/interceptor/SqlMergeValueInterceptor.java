package tw.com.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Intercepts(
{
		@Signature(type = Executor.class, method = "query", args =
		{ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args =
		{ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "update", args =
		{ MappedStatement.class, Object.class })
})
public class SqlMergeValueInterceptor implements Interceptor
{
	private static Logger log = LoggerFactory.getLogger(SqlMergeValueInterceptor.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{
		
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1)
		{
			parameter = invocation.getArgs()[1];
		}
		String javaMethod = mappedStatement.getId();
		
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		
		Configuration configuration = mappedStatement.getConfiguration();
		
		Object returnValue;
		long start = System.currentTimeMillis();
		returnValue = invocation.proceed();
		long end = System.currentTimeMillis();
		long time = (end - start);
		
		String sql = getSql(configuration, boundSql);
		
		log.info("call java method : {} │ use sql : {} │ sql run time : {}mS", javaMethod, sql, time);
		
		return returnValue;
	}
	
	private String getSql(Configuration configuration, BoundSql boundSql)
	{
		String sql = showSql(configuration, boundSql);
		StringBuilder str = new StringBuilder(3000);
		str.append(sql);
		return str.toString();
	}
	
	private String showSql(Configuration configuration, BoundSql boundSql)
	{
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && null != parameterObject)
		{
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
			{
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			} else
			{
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings)
				{
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName))
					{
						Object object = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(object));
					} else if (boundSql.hasAdditionalParameter(propertyName))
					{
						Object object = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(object));
					}
				}
			}
		}
		return sql;
	}
	
	private String getParameterValue(Object object)
	{
		String value;
		if (object instanceof String)
		{
			value = "'" + object.toString() + "'";
		} else if (object instanceof Date)
		{
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.TAIWAN);
			value = "'" + formatter.format(object) + "'";
		} else
		{
			if (null != object)
			{
				value = object.toString();
			} else
			{
				value = "";
			}
		}
		return value;
	}
	
	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}
	
	@Override
	public void setProperties(Properties properties)
	{
		// TODO Auto-generated method stub
		
	}
	
}
