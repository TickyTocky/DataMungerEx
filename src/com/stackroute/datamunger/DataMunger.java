package com.stackroute.datamunger;



/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {

		return queryString.toLowerCase().split(" ");
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {

		String str[] = queryString.toLowerCase().split(" ");
		/*for(int i = 0; i < str.length; i++)
		{
			if((str[i].equalsIgnoreCase("WHere")) || (str[i].equalsIgnoreCase("order")) || (str[i].equalsIgnoreCase("group")))
			{
				return str[i - 1];
			}
		}*/
		
		//return str[str.length - 1];
		return str[3];
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		
		String array[] = queryString.split(" ");
		String str = "";
		for ( int i = 0; i < array.length; i++)
		{
			if((array[i].equals("where")) || (array[i].equals("group")) || (array[i].equals("order")))
			{
				break;
			}
				str += array[i] + " ";
		}
			
		return str.trim();
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		String arr[] = queryString.split(" ");
		String str[] = arr[1].split(",");
		return str;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		if(!queryString.contains("where"))
			return null;
		
		String array[] = queryString.split(" ");
		String str = "";
		int flag = 0;
		for(int i = 0; i < array.length; i++)
		{
			if((array[i].equals("order")) || (array[i].equals("group")))
			{
				break;
			}
			if(flag == 1)
			{
				str += array[i] + " ";
			}
			if(array[i].equals("where"))
			{
				flag = 1;
			}
			
		}
		return str.toLowerCase().trim();
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {

		if(!queryString.contains("where"))
			return null;
		String array = queryString.toLowerCase().split("where")[1];
		String str = array.toLowerCase().split("order by|group by")[0];
		String resultArray[] = str.trim().split("\\s+and\\s+|\\s+or\\s+");
		return resultArray;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		if(!queryString.contains("and") && !queryString.contains("or"))
			return null;
		
		String str = queryString.toLowerCase().split("where")[1].split("order by|group by")[0];
		String [] array = str.split(" ");
		String [] resultArray = new String[count(array)];
		int j = 0;
		for(int i = 0; i < array.length; i++)
		{
			if(array[i].equals("and") || array[i].equals("or"))
			{
				resultArray[j] = array[i];
				j++;
			}
		}
		
		return resultArray;
	}
	
	public int count(String [] arr)
	{
		int count = 0;
		for(int i = 0; i < arr.length; i++)
			if(arr[i].equals("and") || arr[i].equals("or"))
				count++;
		return count;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		if(!queryString.contains("order by"))
		{
			return null;
		}
		
		String str = queryString.toLowerCase().split("\\s+order by\\s+")[1];
		String resultArray[] = str.trim().split(" ");
		return resultArray;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		if(!queryString.contains("group by"))
		{
			return null;
		}
		
		String str = queryString.toLowerCase().split("\\s+group by\\s+")[1];
		String resultArray[] = str.trim().split(" ");
		return resultArray;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {
	    String [] array = queryString.toLowerCase().split(" ");
	   
	    if(array[1].equals("*"))
	      return null;
	    
	    String [] str = array[1].split(",");
	    
	    int count = 0;
	    
	    for(int i = 0; i < str.length; i++) 
	    {
	      if(str[i].contains("sum(") || str[i].contains("count(") || str[i].contains("min(") || str[i].contains("max(") || str[i].contains("avg("))
	        count++;
	    }
	    
	    String [] s = new String[count];
	    
	    int j = 0;
	    
	    for(int i = 0; i < str.length; i++) 
	    {
	      if(str[i].contains("sum(") || str[i].contains("count(") || str[i].contains("min(") || str[i].contains("max(") || str[i].contains("avg(")) 
	      {
	        s[j] = str[i];
	        j++;
	      }  
	    }
	    return s;
	  }

}