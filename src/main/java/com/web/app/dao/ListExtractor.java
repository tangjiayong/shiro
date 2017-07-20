package com.web.app.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class ListExtractor implements ResultSetExtractor< List<List<Object>> >
{
	@Override
	public List< List<Object> > extractData(ResultSet rs) throws SQLException,DataAccessException 
	{
		List< List<Object> > result = new ArrayList< List<Object> >();
		ResultSetMetaData meta = rs.getMetaData();
		int colnum = meta.getColumnCount();
		List<Object> colnames = new ArrayList<Object>();
		for( int i =1; i<=colnum; i++)
		 colnames.add(meta.getColumnName(i));
		result.add(colnames);
		while( rs.next() )
		{
			List<Object> aRow = new ArrayList<Object>();
			for( int i =1; i<=colnum; i++)
			 aRow.add( rs.getObject(i) );
			result.add(aRow);
		}
		return result;
	}
}
