package com.bert.common.batis.plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.xml.sax.SAXException;

import com.bert.common.batis.util.MapperResolver;
import com.bert.common.batis.util.ReflectionUtil;
import com.bert.common.batis.util.SimpleCache;
import com.bert.common.batis.util.TableNameParser;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class})})
public class TransferMybatisSqlInterceptor implements Interceptor {

    private volatile boolean init = false;

    private final String KEY = "key";

    private final String ENTITY = "entity";

    private Set<String> commondSqlIdSet = new TreeSet<String>();

    private void init() {
        if (init)
            return;
        try {
            commondSqlIdSet = MapperResolver.getElementsSet("id",
                    "com/bert/common/batis/dao/mapper/CommonDaoMapper.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void processDynamicResultMapIntercept(Invocation invocation, boolean handleKey) {
    	try {
    		final Object[] queryArgs = invocation.getArgs();
            final MappedStatement ms = (MappedStatement) queryArgs[0];
            final Object parameter = queryArgs[1];
            String targetSimpleName = getTableName(parameter, true);
            String tableSimpleName = getTableName(parameter, false);
            reconstructSqlSource(ms, parameter, targetSimpleName, handleKey, APPENDTYPE.NONE);
            Class<?> targetClass = null;
            try {
                targetClass = Class.forName(tableSimpleName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ResultMap.Builder builder = new ResultMap.Builder(
                    ms.getConfiguration(), ms.getId(), targetClass,
                    new ArrayList<ResultMapping>());
            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
            resultMaps.add(0, builder.build());
            ReflectionUtil.setTarget(ms, resultMaps, "resultMaps");
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    private void processIntercept(Invocation invocation, boolean handleKey, APPENDTYPE appendType) {
    	try {
    		final Object[] queryArgs = invocation.getArgs();
            final MappedStatement ms = (MappedStatement) queryArgs[0];
            final Object parameter = queryArgs[1];
            String tableSimpleName = getTableName(parameter, true);
            reconstructSqlSource(ms, parameter, tableSimpleName, handleKey, appendType);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void reconstructSqlSource(MappedStatement ms,
                                      Object parameter, String tableSimpleName, boolean handleKey, APPENDTYPE appendType) {
    	try {
    		 SqlSource sqlSource = ms.getSqlSource();
    	        if (sqlSource instanceof RawSqlSource) {
    	            BoundSql boundSql = sqlSource.getBoundSql(parameter);
    	            String sql = boundSql.getSql();
    	            String cachePreSql;
    	            SimpleCache.MAPTYPE mapperType = MapperResolver.getSqlIdToCacheMap().get(ms.getId());
    	            if (!sql.contains("@")) {
    	                cachePreSql = SimpleCache.get(tableSimpleName, mapperType);
    	                sql = (cachePreSql == null ? MapperResolver.getMapTypeToRawSqlMap().get(mapperType) : cachePreSql);
    	            }
    	            if (sql.contains("@")) {
    	                TableNameParser tableNameParser;
    	                tableNameParser = new TableNameParser("@", "@", tableSimpleName);
    	                sql = tableNameParser.parse(sql);
    	                SimpleCache.put(tableSimpleName, sql, mapperType);
    	            }
    	            if (appendType == APPENDTYPE.INSERT) {
    	                sql = appendInsertStatement(sql, ((HashMap) parameter).get(ENTITY));
    	            } else {
    	                if (appendType == APPENDTYPE.UPDATE) {
    	                    sql = appendUpdateStatement(sql, ((HashMap) parameter).get(ENTITY));
    	                }
    	                if (handleKey) {
    	                    sql = fixKeyStatement(sql, ((HashMap) parameter).get(KEY));
    	                }
    	            }
    	            RawSqlSource rawSqlSource = new RawSqlSource(ms.getConfiguration(),
    	                    sql, null);
    	            ReflectionUtil.setTarget(ms, rawSqlSource, "sqlSource");
    	        } else if (sqlSource instanceof DynamicSqlSource) {
    	            SqlNode sqlNode = null;
    	            try {
    	                sqlNode = (SqlNode) ReflectionUtil.getFieldValue(sqlSource, "rootSqlNode");
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	            if (sqlNode instanceof MixedSqlNode) {
    	                List<SqlNode> contents = (List<SqlNode>) ReflectionUtil.getFieldValue(sqlNode, "contents");
    	                SqlNode node = contents.get(0);
    	                if (node instanceof StaticTextSqlNode) {
    	                    String sql = (String) ReflectionUtil.getFieldValue(node, "text");
    	                    String cachePreSql;
    	                    SimpleCache.MAPTYPE mapperType = MapperResolver.getSqlIdToCacheMap().get(ms.getId());
    	                    if (!sql.contains("@")) {
    	                        cachePreSql = SimpleCache.get(tableSimpleName, mapperType);
    	                        sql = (cachePreSql == null ? MapperResolver.getMapTypeToRawSqlMap().get(mapperType) : cachePreSql);
    	                    }
    	                    if (sql.contains("@")) {
    	                        TableNameParser tableNameParser;
    	                        tableNameParser = new TableNameParser("@", "@", tableSimpleName);
    	                        sql = tableNameParser.parse(sql);
    	                        SimpleCache.put(tableSimpleName, sql, mapperType);
    	                    }
    	                    if (appendType == APPENDTYPE.INSERT) {
    	                        sql = appendInsertStatement(sql, ((HashMap) parameter).get(ENTITY));
    	                    } else if (appendType == APPENDTYPE.UPDATE) {
    	                        sql = appendUpdateStatement(sql, ((HashMap) parameter).get(ENTITY));
    	                        if (handleKey) {
    	                            sql = fixKeyStatement(sql, ((HashMap) parameter).get(KEY));
    	                        }
    	                    }
    	                    StaticTextSqlNode staticTextSqlNode = new StaticTextSqlNode(sql);
    	                    contents.set(0, staticTextSqlNode);
    	                    ReflectionUtil.setTarget(sqlNode, contents, "contents");
    	                    DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
    	                    ReflectionUtil.setTarget(ms, dynamicSqlSource, "sqlSource");
    	                } else {
    	                    throw new RuntimeException("reconstruct sqlSource exception, sqlNode is not instanceof StaticTextSqlNode!");
    	                }
    	            } else {
    	                throw new RuntimeException("reconstruct sqlSource exception, sqlNode is not instanceof MixedSqlNode!");
    	            }
    	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
       
    }

    private String appendUpdateStatement(String sql, Object obj) {
    	 StringBuilder builder = new StringBuilder();
    	try {
    		builder.append(sql).append(" ");
            List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
            if (fieldList != null) {
                boolean firstVal = true;
                for (int i = 0; i < fieldList.size(); i++) {
                    boolean add = false;
                    Field field = fieldList.get(i);
                    try {
                        add = field.get(obj) != null;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (add) {
                        if (!firstVal) {
                            builder.append(",");
                        } else {
                            builder.append(" set ");
                        }
                        String filedName = field.getName();
                        builder.append(filedName).append("=");
                        builder.append("#{").append(ENTITY).append(".").append(filedName).append("}");
                        firstVal = false;
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
       
        return builder.toString();
    }

    private String appendInsertStatement(String sql, Object obj) {
        StringBuilder builderPre = new StringBuilder();
        StringBuilder builderSuf = new StringBuilder();
        try {
        	 builderPre.append(" (");
             builderSuf.append(" values (");
             List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
             if (fieldList != null) {
                 boolean firstVal = true;
                 for (int i = 0; i < fieldList.size(); i++) {
                     boolean add = false;
                     Field field = fieldList.get(i);
                     try {
                         add = field.get(obj) != null;
                     } catch (IllegalAccessException e) {
                         e.printStackTrace();
                     }
                     if (add) {
                         if (!firstVal) {
                             builderPre.append(",");
                             builderSuf.append(",");
                         }
                         String filedName = field.getName();
                         builderPre.append(filedName);
                         builderSuf.append("#{").append(ENTITY).append(".").append(filedName).append("}");
                         firstVal = false;
                     }
                 }
             } else {
                 throw new RuntimeException("entity key has no field exception!");
             }
             builderPre.append(")");
             builderSuf.append(")");
		} catch (Exception e) {
		}
       
        return sql + builderPre + builderSuf;
    }

    private String fixKeyStatement(String sql, Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(" where ");
        List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
        if (fieldList != null) {
            boolean added = false;
            for (int i = 0; i < fieldList.size(); i++) {
                boolean add = false;
                Field field = fieldList.get(i);
                try {
                    add = field.get(obj) != null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (add) {
                    if (added) {
                        builder.append(" and ");
                    }
                    String filedName = field.getName();
                    builder.append(filedName).append("=#{").append(KEY).append(".").append(filedName).append("}");
                    added = true;
                }
            }
        } else {
            throw new RuntimeException("entity key has no field exception!");
        }
        
        
        return builder.toString();
    }

    @SuppressWarnings("rawtypes")
    private String getTableName(Object obj, boolean simple) {
    	String name;
    	try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if (obj instanceof HashMap) {
            Object param1 = ((HashMap) obj).get("param1");
            if (param1 instanceof Class) {
                name = ((Class) param1).getName();
            } else {
                name = param1.getClass().getName();
            }
            if (!simple) {
                return name;
            }
            int start = name.lastIndexOf(".");
            if (start > 0) name = name.substring(start + 1, name.length());
            return name;
        } else if (obj instanceof Class) {
            if (!simple) {
                return ((Class) obj).getName();
            }
            return ((Class) obj).getName();
        } else {
            if (!simple) {
                return obj.getClass().getSimpleName();
            }
            return obj.getClass().getSimpleName();
        }
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (!init) {
            init();
        }
        boolean intercept = false;
        Object[] obj = invocation.getArgs();
        Object msObj = obj[0];
        if (msObj instanceof MappedStatement) {
            intercept = commondSqlIdSet.contains(((MappedStatement) msObj)
                    .getId());
        }
        if (intercept) {//dynamic
            String sqlId = ((MappedStatement) msObj).getId();
            if (MapperResolver.SELECT_BY_CRITERIA.equals(sqlId)) {
                processDynamicResultMapIntercept(invocation, false);
            } else if (MapperResolver.SELECT_BY_PRIMARYKEY.equals(sqlId)) {
                processDynamicResultMapIntercept(invocation, true);
            } else {//static
                if (MapperResolver.DELETE_BY_PRIMARYKEY.equals(sqlId)) {
                    processIntercept(invocation, true, APPENDTYPE.NONE);
                } else if (MapperResolver.UPDATE_BY_PRIMARYKEY.equals(sqlId)) {
                    processIntercept(invocation, true, APPENDTYPE.UPDATE);
                } else if (MapperResolver.UPDATE_BY_CRITERIA.equals(sqlId)) {
                    processIntercept(invocation, false, APPENDTYPE.UPDATE);
                } else if (MapperResolver.INSERT.equals(sqlId)) {
                    processIntercept(invocation, false, APPENDTYPE.INSERT);
                } else {
                    processIntercept(invocation, false, APPENDTYPE.NONE);
                }
            }
        }
        return invocation.proceed();
    }

    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    public void setProperties(Properties properties) {
    }

    private enum APPENDTYPE {
        UPDATE, INSERT, NONE;
    }
}