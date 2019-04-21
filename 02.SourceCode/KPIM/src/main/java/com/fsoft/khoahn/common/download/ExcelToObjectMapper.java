package com.fsoft.khoahn.common.download;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.annotation.CellBindByName;
import com.fsoft.khoahn.common.upload.DataRow;
import com.fsoft.khoahn.common.upload.DataTable;
import com.fsoft.khoahn.common.upload.ExcelTable;

/**
 * A simple Excel to Object mapper utility using Apache POI. This class provides
 * utility methods, to read an Excel file and convert each rows of the excel
 * file into appropriate model object as specified and return all rows of excel
 * file as list of given object type.
 *
 * Created by Ranjit Kaliraj on 8/15/17.
 */

public class ExcelToObjectMapper {
	private MultipartFile file;

	public ExcelToObjectMapper(MultipartFile multipartFile) {
		file = multipartFile;
	}

	/**
	 * Read data from Excel file and convert each rows into list of given object
	 * of Type T.
	 * 
	 * @param cls
	 *            Class of Type T.
	 * @param <T>
	 *            Generic type T, result will list of type T objects.
	 * @return List of object of type T.
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws Exception
	 *             if failed to generate mapping.
	 */
	public <T> ArrayList<T> map(Class<T> cls) throws Exception {
		ArrayList<T> list = new ArrayList<T>();

		byte[] bytes = file.getBytes();
		DataTable table = ExcelTable.load(bytes);
		int rowCount = table.rowCount();

		for (int i = 0; i < rowCount; i++) {
			Object obj = cls.newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			DataRow rowData = table.row(i);
			for (Field field : fields) {
				String fieldName = field.getName();
				Field classField = obj.getClass().getDeclaredField(fieldName);
				setObjectFieldValueFromCell(obj, classField, rowData);
			}
			list.add((T) obj);
		}
		return list;
	}

	/**
	 * Read value from Cell and set it to given field of given object. Note:
	 * supported data Type: String, Date, int, long, float, double and boolean.
	 * 
	 * @param obj
	 *            Object whom given field belong.
	 * @param field
	 *            Field which value need to be set.
	 * @param rowData
	 *            Apache POI cell from which value needs to be retrived.
	 */
	private void setObjectFieldValueFromCell(Object obj, Field field, DataRow rowData) {
		CellBindByName annotation = field.getAnnotation(CellBindByName.class);
		field.setAccessible(true);

		// if (cls == String.class) {
		try {
			field.set(obj, rowData.cell(annotation.column()));
		} catch (Exception e) {
			try {
				field.set(obj, null);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
	}
}
