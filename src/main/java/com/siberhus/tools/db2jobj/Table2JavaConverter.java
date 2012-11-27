package com.siberhus.tools.db2jobj;

import static com.siberhus.commons.lang.NamingConvention.firstUpperCase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.siberhus.commons.db.Database;

public class Table2JavaConverter {
	
	private boolean doOnce = false;

	public Table2JavaConverter(){

	}
	
	public String generate(Connection conn, BeanOptions options, String tableName) throws SQLException{
		
		Map<String, String> javaImportMap = new HashMap<String, String>();
		String javaImportPart = "\n\n";
		String javaFieldPart = "";
		String javaMethodPart = "";
		String javaClass = "";
		
		String parentClassName = options.getParentClassName();
		
		if(parentClassName!=null){
			if(parentClassName.contains(".")){
				//FQCN
				javaImportPart += parentClassName+";";
				parentClassName = parentClassName.substring(parentClassName.lastIndexOf(".")+1
						, parentClassName.length());
			}
		}
		String interfaceNames[] = null;
		if(options.getInterfaceNames()!=null){
			interfaceNames = new String[options.getInterfaceNames().size()];
			int i=0;
			for(String infName : options.getInterfaceNames()){
				if(infName.contains(".")){
					//FQCN
					javaImportPart += infName+";";
					infName = infName.substring(infName.lastIndexOf(".")+1
							, infName.length());
				}
				interfaceNames[i] = infName;
				i++;
			}
		}
		
		String sql = "select * from "+tableName;		
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSetMetaData rsmd = ps.executeQuery().getMetaData();
		
		for(int i=1;i<=rsmd.getColumnCount();i++){
			
			ColumnInfo columnInfo = new ColumnInfo(rsmd, i);
			
			String fieldName = db2java(columnInfo.getColumnName());
			String columnClassName = columnInfo.getColumnClassName();
			String columnName = columnInfo.getColumnName();
			
			if(!options.isFqTypeName()){
				if(columnClassName.indexOf("java.lang")==-1){
					/* If it's not in java.lang.* */
					if(!javaImportMap.containsKey(columnClassName)){
						javaImportMap.put(columnClassName, null);
						/* If it haven't imported class yet */
						javaImportPart+="import "+columnClassName+";\n";
					}
				}
				columnClassName = StringUtils.substringAfterLast(columnClassName, ".");
			}
			
			
			if(options instanceof JPAOptions){
				JPAOptions jpaOptions = (JPAOptions)options;
				if(jpaOptions.getTableNameCase()!=null){
					if(jpaOptions.getTableNameCase()==JPAOptions.Case.Lower){
						tableName = tableName.toLowerCase();
					}else{
						tableName = tableName.toUpperCase();
					}
				}
				if(doOnce==false){
					javaImportPart+="import javax.persistence.Column;\n"
						+"import javax.persistence.Entity;\n"
						+"import javax.persistence.GeneratedValue;\n"
						+"import javax.persistence.GenerationType;\n"
						+"import javax.persistence.Id;\n"
						+"import javax.persistence.Table;\n";
					if(jpaOptions.getIdGenerationType()==JPAOptions.IdGenerationType.Sequence){
						javaImportPart+="import javax.persistence.SequenceGenerator;\n";
						if(!(jpaOptions.getIdGenerator() instanceof SequenceIdGenerator)){
							throw new IllegalArgumentException("SequenceIdGenerator does not match with IdGenerationType");
						}else{
							SequenceIdGenerator seqGen = (SequenceIdGenerator)jpaOptions.getIdGenerator();
							String seqName = seqGen.getSequenceName(tableName);
							javaFieldPart+="\t@SequenceGenerator(name = \""
								+seqGen.getName(tableName)+"\",sequenceName = \""
								+seqName +"\", initialValue = "+seqGen.getInitialValue()
								+", allocationSize = "+seqGen.getAllocationSize();
							if(seqGen.getSchema()!=null){
								javaFieldPart+=", schema=\""+seqGen.getSchema()+"\"";
							}
							if(seqGen.getCatalog()!=null){
								javaFieldPart+=", catalog=\""+seqGen.getCatalog()+"\"";
							}
							javaFieldPart+=")\n";
							javaFieldPart+= "\t@Id\n"
								+"\t@GeneratedValue(strategy = GenerationType.SEQUENCE, generator =\"" 
						    	+seqName+"\")\n";
						}
					}else if(jpaOptions.getIdGenerationType()==JPAOptions.IdGenerationType.Table){
						javaImportPart+="import javax.persistence.TableGenerator;\n";
						if(!(jpaOptions.getIdGenerator() instanceof TableIdGenerator)){
							throw new IllegalArgumentException("SequenceIdGenerator does not match with IdGenerationType");
						}else{
							TableIdGenerator tabGen = (TableIdGenerator)jpaOptions.getIdGenerator();
							//TODO: fill this later
						}
					}else if(jpaOptions.getIdGenerationType()==JPAOptions.IdGenerationType.Auto){
						javaFieldPart+= "\t@Id\n"
							+"\t@GeneratedValue(strategy = GenerationType.AUTO )\n";
					}else if(jpaOptions.getIdGenerationType()==JPAOptions.IdGenerationType.Identity){
						javaFieldPart+= "\t@Id\n"
							+"\t@GeneratedValue(strategy = GenerationType.IDENTITY )\n";
					}
					
					doOnce = true;
				}
				
				if(jpaOptions.getColumnNameCase()!=null && !columnInfo.isCaseSensitive()){
					if(jpaOptions.getColumnNameCase()==JPAOptions.Case.Lower){
						columnName = columnName.toLowerCase();
					}else{
						columnName = columnName.toUpperCase();
					}
				}
				fieldName = jpaOptions.configureFieldName(columnInfo);
				if(jpaOptions.getAnnotation()==JPAOptions.Annotation.Field){
					for(String annot: jpaOptions.configureAnnotations(columnInfo)){
						javaFieldPart+="\t"+annot+"\n";
					}
					javaFieldPart+=createColumnAnnotation((JPAOptions)options, columnInfo, columnName );
				}
			}
			
			/* Field */
			
			javaFieldPart+="\t";
			switch(options.getFieldAccessModifier()){
			case Private :
				javaFieldPart+="private ";
				break;
			case Protected :
				javaFieldPart+="protected ";
				break;
			case Public :
				javaFieldPart+="public ";
			default:
				javaFieldPart+=" ";
			}
			
			javaFieldPart+=columnClassName+" "+fieldName+";\n\n";
			
			if(options instanceof JPAOptions){
				if(((JPAOptions)options).getAnnotation()==JPAOptions.Annotation.Method){
					javaFieldPart+= createColumnAnnotation((JPAOptions)options, columnInfo, columnName );
				}
			}
			/* Getter method */
			javaMethodPart+="\tpublic "+columnClassName
			+" get"+firstUpperCase(fieldName)
			+"(){\n\t\treturn "+fieldName+";\n\t}\n\n";
			
			/* Setter method */
			javaMethodPart+="\t";
			switch(options.getMethodAccessModifier()){
			case Private :
				javaMethodPart+="private ";
				break;
			case Protected :
				javaMethodPart+="protected ";
				break;
			case Public :
				javaMethodPart+="public ";
				break;
			default:
				javaMethodPart+=" ";
			}
			javaMethodPart+="void set"
			+firstUpperCase(fieldName)
			+"("+columnClassName+" "
			+fieldName+"){\n\t\tthis."
			+fieldName+"="
			+fieldName+";\n\t}\n\n";
		}
		
		/* Compose java class from each part */
		javaClass+=javaImportPart.toString()+"\n\n";
		if(options instanceof JPAOptions){
			javaClass+="@Entity\n"+"@Table(name=\""+tableName+"\")\n";
		}
		javaClass+="public class "+firstUpperCase(db2java(tableName))+" ";
		if(parentClassName!=null){
			javaClass+="extends "+parentClassName+" ";
		}
		if(interfaceNames!=null){
			javaClass+="implements ";
			for(int i=0;i<interfaceNames.length-1;i++){
				javaClass+=interfaceNames[i]+", ";
			}
			javaClass+=interfaceNames[interfaceNames.length-1]+" ";
		}
		javaClass+=" {\n\n";
		if(options.isSerialVersionId()){
			javaClass+="\tprivate static final long serialVersionUID = 1L;\n\n";
		}
		javaClass+=javaFieldPart.toString()+"\n\n"
			+javaMethodPart.toString()+"}\n";
		return javaClass.toString();
	}
	
	
	public static String db2java(String value){
		if(StringUtils.indexOf(value, "_")==-1){
			return StringUtils.lowerCase(value);
		}
		char[] chs = value.toCharArray();
		StringBuilder name = new StringBuilder();
		for(int i=0;i<chs.length;i++){
			if(chs[i]=='_'){
				i++;
				name.append(Character.toUpperCase(chs[i]));
				continue;
			}
			name.append(Character.toLowerCase(chs[i]));
		}
		return name.toString();
	}
	
	private String createColumnAnnotation(JPAOptions jpaOptions, ColumnInfo columnInfo, String newColumName){
		String columnAnnot = "\t@Column(name=\""+newColumName+"\"";
		if(columnInfo.getColumnClass()==String.class){
			columnAnnot += ", length="+columnInfo.getColumnDisplaySize();
		}else if(columnInfo.getColumnClass()==BigDecimal.class){
			columnAnnot += ", precision="+columnInfo.getPrecision()+", scale="
				+columnInfo.getScale();
		}
		columnAnnot += ", nullable="+columnInfo.isNullable();
		columnAnnot+=" )\n";
		return columnAnnot;
	}
	
	public static void main(String[] args) throws Exception {
		
		Table2JavaConverter t2o = new Table2JavaConverter();
//		Database.ORACLE11.loadDriver();
//		Connection conn = DriverManager.getConnection(Database.ORACLE11.getUrl("loyaltydb",1521,"SCG"), "scg", "blp");
//		Connection conn = DriverManager.getConnection(Database.ORACLE11.getUrl("localhost",1521,""), "scg", "blp");
		Database.MYSQL5.loadDriver();
		Connection conn = DriverManager.getConnection(Database.MYSQL5.getUrl("localhost",3306,"opencart"), "admin", "password");
		JPAOptions beanOptions = new JPAOptions();
		System.out.println(t2o.generate(conn, beanOptions, "review"));
		
	}
}
