package com.ming800.core.develop.service.impl;

import com.ming800.core.xdo.dao.XdoDao;
import com.ming800.core.develop.service.SplitBranchManager;
import com.ming800.organization.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-12
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SplitBranchManagerImpl implements SplitBranchManager {

    @Autowired
    private XdoDao xdoDao;

    @Override
    public String checkData(String bname) {
        String result = "success";

        String branchQueryStr = "select b from Branch b where b.id like :bname";
        LinkedHashMap<String, Object> queryParamMap = new LinkedHashMap<>();
        queryParamMap.put("bname", bname + "%");
        List<Branch> branchList = xdoDao.getObjectList(branchQueryStr, queryParamMap);

        if (branchList != null && branchList.size() > 0) {
            Branch branch = branchList.get(0);

            queryParamMap.clear();
            queryParamMap.put("branchId", branch.getId());

            /*校区检测*/
            String teachAreaHql = "select count(s) from TeachArea s where s.branch.id =:branchId";
            String teachAreaSql = "select count(*) from organization_teach_area where organization_id =:branchId";
            if (!checkAmount(teachAreaHql, teachAreaSql, queryParamMap)) {
                result += "校区主键不一致<br/>";
            }

            /*用户检测*/
            String userHql = "select count(s) from BigUser s where s.branch.id =:branchId";
            String userSql = "select count(*) from organization_user where branch_id =:branchId";
            if (!checkAmount(userHql, userSql, queryParamMap)) {
                result += "用户主键不一致<br/>";
            }

            /*专业检测*/
            String clazzHql = "select count(s) from Clazz s where s.branch.id =:branchId";
            String clazzSql = "select count(*) from xedu_clazz where branch_id =:branchId";
            if (!checkAmount(clazzHql, clazzSql, queryParamMap)) {
                result += "专业主键不一致<br/>";
            }


            String teachAreaIdHql = "select s.id from TeachArea s where s.branch.id =:branchId";
            List<String> teachAreaIdList = xdoDao.getObjectList(teachAreaIdHql, queryParamMap);

            queryParamMap.clear();
            queryParamMap.put("teachAreaIds", teachAreaIdList.toArray());

            /*班级检测*/
            String ciHql = "select count(s) from ClazzInstance s where s.teachArea.id in(:teachAreaIds)";
            String ciSql = "select count(*) from xedu_clazz_instance where teach_area_id in(:teachAreaIds)";
            if (!checkAmount(ciHql, ciSql, queryParamMap)) {
                result += "班级主键不一致<br/>";
            }

            /*学员检测*/
            String stHql = "select count(s) from BigStudent s where s.teachArea.id in(:teachAreaIds)";
            String stSql = "select count(*) from xedu_student where teach_area_id in(:teachAreaIds)";
            if (!checkAmount(stHql, stSql, queryParamMap)) {
                result += "学员主键不一致<br/>";
            }


            /*收费记录检测*/
            String accountingHql = "select count(s) from BasicAccounting s where s.teachArea.id in(:teachAreaIds)";
            String accountingSql = "select count(*) from finance_basic_accounting where teach_area_id in(:teachAreaIds)";
            if (!checkAmount(accountingHql, accountingSql, queryParamMap)) {
                result += "收费记录主键不一致<br/>";
            }


        } else {
            result = "机构不存在";
        }

        return result;  //To change body of implemented methods use File | Settings | File Templates.

    }

    private Boolean checkAmount(String queryHql, String querySql, LinkedHashMap<String, Object> queryParamMap) {
        Boolean flag = false;

        List hqlAmount = xdoDao.getObjectList(queryHql, queryParamMap);
        List sqlAmount = (List) xdoDao.executeSql("list", querySql, queryParamMap);

        /*hqlAmount.get(0) 为 long型   sqlAmount.get(0) 为bigInteger型
        * 所以先toString()  然后再转换成Integer  使用== 比较
        * */

        if (hqlAmount != null && sqlAmount != null && Integer.parseInt(hqlAmount.get(0).toString()) == Integer.parseInt(sqlAmount.get(0).toString())) {
            flag = true;
        }
        return flag;
    }


    @Override
    public Boolean executeData(String bname, String type) {

        String tNameQueryStr = " show tables ";
        List<String> tableNameList = (List<String>) xdoDao.executeSql("list", tNameQueryStr, null);

        LinkedHashMap<String, Object> queryParamMap = new LinkedHashMap<>();
        queryParamMap.put("bname", bname + "%");

        for (Object tableName : tableNameList) {
            String tempSqlStr = "delete from " + tableName.toString();
            if (type.equals("1")) {
                tempSqlStr += " where id not like :bname";
            } else {
                tempSqlStr += " where id like :bname";
            }

            xdoDao.executeSql("delete", tempSqlStr, queryParamMap);
        }

        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
