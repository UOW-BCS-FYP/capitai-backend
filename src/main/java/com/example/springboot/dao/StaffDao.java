// package com.example.springboot.dao;

// import java.util.List;

// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Update;

// import com.example.springboot.common.BaseDao;

// import com.example.springboot.model.Staff;

// public interface StaffDao extends BaseDao<Staff> {
// 	// @Select("select * from t_staff where staffname like CONCAT('%',#{val},'%') or size like CONCAT('%',#{val},'%') ")
// 	// public List<Staff> search(@Param("val")String val);

// 	 @Select("select * from t_staff ")
// 	 public List<Staff> list();
	
// 	@Update("UPDATE t_staff SET staffname=#{entity.staffname} WHERE id =#{entity.staffid}")
// 	//@Update("UPDATE t_house SET name='mary',size='22' WHERE id ='3'")
// //	@Update("UPDATE t_house SET name=CONCAT('%','jack','%'),size=CONCAT('%','333','%') WHERE id =CONCAT('%','3','%')")
// 	public int  save(@Param("entity")Staff entity);
// }
